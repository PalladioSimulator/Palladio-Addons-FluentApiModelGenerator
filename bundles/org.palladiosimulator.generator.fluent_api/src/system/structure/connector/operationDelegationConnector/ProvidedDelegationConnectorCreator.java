package system.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class ProvidedDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationProvidedRole outerProvidedRole;
    private OperationProvidedRole innerProvidedRole;
    private AssemblyContext providingAssemblyContext;

    public ProvidedDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final OperationProvidedRole role) {
        this.outerProvidedRole = role;
        return this;
    }

    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final String name) {
        final OperationProvidedRole role = this.system.getSystemOperationProvidedRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterProvidedRole(role);
    }

    public OperationProvidedRoleSelector withProvidingContext(final AssemblyContext context) {
        final ProvidedDelegationConnectorCreator creator = this;
        return new OperationProvidedRoleSelector(new IContextProvidedRoleCombinator() {

            @Override
            public ProvidedDelegationConnectorCreator combineContextAndProvidedRole(final AssemblyContext context,
                    final OperationProvidedRole role) {
                ProvidedDelegationConnectorCreator.this.providingAssemblyContext = context;
                ProvidedDelegationConnectorCreator.this.innerProvidedRole = role;
                return creator;
            }
        }, context);
    }

    public OperationProvidedRoleSelector withProvidingContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withProvidingContext(context);
    }

    @Override
    public ProvidedDelegationConnector build() {
        final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext_ProvidedDelegationConnector(this.providingAssemblyContext);
        connector.setOuterProvidedRole_ProvidedDelegationConnector(this.outerProvidedRole);
        connector.setInnerProvidedRole_ProvidedDelegationConnector(this.innerProvidedRole);
        return connector;
    }

    @Override
    public ProvidedDelegationConnectorCreator withName(final String name) {
        return (ProvidedDelegationConnectorCreator) super.withName(name);
    }

}
