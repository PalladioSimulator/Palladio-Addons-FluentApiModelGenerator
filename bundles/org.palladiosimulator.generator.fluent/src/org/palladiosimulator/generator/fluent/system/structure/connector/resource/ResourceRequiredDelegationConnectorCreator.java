package org.palladiosimulator.generator.fluent.system.structure.connector.resource;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
 * ResourceRequiredDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
 */
public class ResourceRequiredDelegationConnectorCreator {

    private final SystemCreator system;
    private ResourceRequiredRole outerRequiredRole;
    private ResourceRequiredRole innerRequiredRole;

    public ResourceRequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the org.palladiosimulator.generator.fluent.system,
     * delegated to an AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final ResourceRequiredRole role) {
        IllegalArgumentException.requireNonNull(role, "The given Role must not be null.");
        outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the org.palladiosimulator.generator.fluent.system,
     * delegated to an AssemblyContext. The required roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = system.getSystemResourceRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by an assembly context.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final ResourceRequiredRole role) {
        IllegalArgumentException.requireNonNull(role, "The given Role must not be null.");
        innerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by an assembly context. All assembly contexts
     * added to the org.palladiosimulator.generator.fluent.system are searched for a
     * resource required role that matches the given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(final String name) {
        final ResourceRequiredRole role = system.getResourceRequiredRoleByName(name);
        return this.withInnerRequiredRole(role);
    }

    public ResourceRequiredDelegationConnector build() {
        final ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE
                .createResourceRequiredDelegationConnector();
        connector.setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(outerRequiredRole);
        connector.setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(innerRequiredRole);
        return connector;
    }
}
