package system.structure.connector.infrastructureDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class RequiredInfrastructureDelegationConnectorCreator extends AbstractConnectorCreator {
    private InfrastructureRequiredRole outerRequiredRole;
    private InfrastructureRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredInfrastructureDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(
            final InfrastructureRequiredRole role) {
        this.outerRequiredRole = role;
        return this;
    }

    public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(final String name) {
        final InfrastructureRequiredRole role = this.system.getSystemInfrastructureRequiredRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterRequiredRole(role);
    }

    public InfrastructureRequiredRoleSelector withRequiringContext(final AssemblyContext context) {
        final RequiredInfrastructureDelegationConnectorCreator creator = this;
        return new InfrastructureRequiredRoleSelector(new IContextRequiredRoleCombinator() {

            @Override
            public RequiredInfrastructureDelegationConnectorCreator combineContextAndRequiredRole(
                    final AssemblyContext context, final InfrastructureRequiredRole role) {
                RequiredInfrastructureDelegationConnectorCreator.this.requringAssemblyContext = context;
                RequiredInfrastructureDelegationConnectorCreator.this.innerRequiredRole = role;
                return creator;
            }
        }, context);
    }

    public InfrastructureRequiredRoleSelector withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredInfrastructureDelegationConnector build() {
        final RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createRequiredInfrastructureDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__RequiredInfrastructureDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole__RequiredInfrastructureDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole__RequiredInfrastructureDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredInfrastructureDelegationConnectorCreator withName(final String name) {
        return (RequiredInfrastructureDelegationConnectorCreator) super.withName(name);
    }

}
