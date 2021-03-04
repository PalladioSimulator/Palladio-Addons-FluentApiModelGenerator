package system.structure.connector.operation;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class ProvidedDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationProvidedRole outerProvidedRole;
    private OperationProvidedRole innerProvidedRole;
    private AssemblyContext providingAssemblyContext;

    public ProvidedDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final OperationProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerProvidedRole = role;
        return this;
    }

    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final String name) throws NoSuchElementException {
        final OperationProvidedRole role = this.system.getSystemOperationProvidedRoleByName(name);
        return this.withOuterProvidedRole(role);
    }

    public OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator> withProvidingContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final ProvidedDelegationConnectorCreator creator = this;
        return new OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator>(
                new IContextRoleCombinator<OperationProvidedRole, ProvidedDelegationConnectorCreator>() {

                    @Override
                    public ProvidedDelegationConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final OperationProvidedRole role) {
                        ProvidedDelegationConnectorCreator.this.providingAssemblyContext = context;
                        ProvidedDelegationConnectorCreator.this.innerProvidedRole = role;
                        return creator;
                    }
                }, context);
    }

    public OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator> withProvidingContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
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
