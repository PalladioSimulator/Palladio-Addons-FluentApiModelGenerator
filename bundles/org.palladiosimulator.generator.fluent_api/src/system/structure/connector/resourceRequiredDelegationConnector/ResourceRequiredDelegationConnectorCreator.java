package system.structure.connector.resourceRequiredDelegationConnector;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import system.structure.SystemCreator;

public class ResourceRequiredDelegationConnectorCreator {

    private final SystemCreator system;
    private ResourceRequiredRole outerRequiredRole;
    private ResourceRequiredRole innerRequiredRole;

    public ResourceRequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final ResourceRequiredRole role) {
        this.outerRequiredRole = role;
        return this;
    }

    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final String name) {
        final ResourceRequiredRole role = this.system.getSystemResourceRequiredRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterRequiredRole(role);
    }

    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final ResourceRequiredRole role) {
        this.innerRequiredRole = role;
        return this;
    }

    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final String name) {
        final ResourceRequiredRole role = this.system.getAssemblyContexts()
            .stream()
            .flatMap(x -> x.getEncapsulatedComponent__AssemblyContext()
                .getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
                .stream())
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withInnerRequiredRole(role);
    }

    public ResourceRequiredDelegationConnector build() {
        final ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE
            .createResourceRequiredDelegationConnector();
        connector.setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(this.outerRequiredRole);
        connector.setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(this.innerRequiredRole);
        return connector;
    }
}
