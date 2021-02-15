package system.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class RequiredDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationRequiredRole outerRequiredRole;
    private OperationRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public RequiredDelegationConnectorCreator withOuterRequiredRole(final OperationRequiredRole role) {
        this.outerRequiredRole = role;
        return this;
    }

    public RequiredDelegationConnectorCreator withOuterRequiredRole(final String name) {
        final OperationRequiredRole role = this.system.getSystemOperationRequiredRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterRequiredRole(role);
    }

    public OperationRequiredRoleSelector withRequiringContext(final AssemblyContext context) {
        final RequiredDelegationConnectorCreator creator = this;
        return new OperationRequiredRoleSelector(new IContextRequiredRoleCombinator() {

            @Override
            public RequiredDelegationConnectorCreator combineContextAndRequiredRole(final AssemblyContext context,
                    final OperationRequiredRole role) {
                RequiredDelegationConnectorCreator.this.requringAssemblyContext = context;
                RequiredDelegationConnectorCreator.this.innerRequiredRole = role;
                return creator;
            }
        }, context);
    }

    public OperationRequiredRoleSelector withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredDelegationConnector build() {
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext_RequiredDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole_RequiredDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole_RequiredDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredDelegationConnectorCreator withName(final String name) {
        return (RequiredDelegationConnectorCreator) super.withName(name);
    }

}
