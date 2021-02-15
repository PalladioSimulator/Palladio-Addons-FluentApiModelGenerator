package system.structure.connector.requiredResourceDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class RequiredResourceDelegationConnectorCreator extends AbstractConnectorCreator {
    private ResourceRequiredRole outerRequiredRole;
    private ResourceRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredResourceDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(final ResourceRequiredRole role) {
        this.outerRequiredRole = role;
        return this;
    }

    public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(final String name) {
        final ResourceRequiredRole role = this.system.getSystemResourceRequiredRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterRequiredRole(role);
    }

    public ResourceRequiredRoleSelector withRequiringContext(final AssemblyContext context) {
        final RequiredResourceDelegationConnectorCreator creator = this;
        return new ResourceRequiredRoleSelector(new IContextRequiredRoleCombinator() {

            @Override
            public RequiredResourceDelegationConnectorCreator combineContextAndRequiredRole(
                    final AssemblyContext context, final ResourceRequiredRole role) {
                RequiredResourceDelegationConnectorCreator.this.requringAssemblyContext = context;
                RequiredResourceDelegationConnectorCreator.this.innerRequiredRole = role;
                return creator;
            }
        }, context);
    }

    public ResourceRequiredRoleSelector withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredResourceDelegationConnector build() {
        final RequiredResourceDelegationConnector connector = CompositionFactory.eINSTANCE
            .createRequiredResourceDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__RequiredResourceDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole__RequiredResourceDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole__RequiredResourceDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredResourceDelegationConnectorCreator withName(final String name) {
        return (RequiredResourceDelegationConnectorCreator) super.withName(name);
    }

}
