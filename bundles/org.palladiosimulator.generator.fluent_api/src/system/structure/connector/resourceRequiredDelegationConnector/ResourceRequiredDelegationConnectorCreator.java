package system.structure.connector.resourceRequiredDelegationConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

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
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRequiredRole = role;
        return this;
    }

    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = this.system.getSystemResourceRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final ResourceRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.innerRequiredRole = role;
        return this;
    }

    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final String name) {
        final ResourceRequiredRole role = this.system.getResourceRequiredRoleByName(name);
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
