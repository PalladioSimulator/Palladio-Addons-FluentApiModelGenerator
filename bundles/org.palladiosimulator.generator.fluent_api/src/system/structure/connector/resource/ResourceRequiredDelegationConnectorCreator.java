package system.structure.connector.resource;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import system.structure.SystemCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
 * ResourceRequiredDelegationConnector}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
 */
public class ResourceRequiredDelegationConnectorCreator {

    private final SystemCreator system;
    private ResourceRequiredRole outerRequiredRole;
    private ResourceRequiredRole innerRequiredRole;

    public ResourceRequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the system, delegated to an AssemblyContext.
     * 
     * @param role
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final ResourceRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the system, delegated to an AssemblyContext. The required roles
     * added to the system are searched for one that matches the given name.
     * 
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = this.system.getSystemResourceRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by an assembly context.
     * 
     * @param role
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final ResourceRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.innerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by an assembly context. All assembly contexts added to the
     * system are searched for a resource required role that matches the given name.
     * 
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
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
