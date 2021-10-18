package org.palladiosimulator.generator.fluent.system.structure.connector.resource;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
 * RequiredResourceDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
 */
public class RequiredResourceDelegationConnectorCreator extends AbstractConnectorCreator {
    private ResourceRequiredRole outerRequiredRole;
    private ResourceRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredResourceDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(final ResourceRequiredRole role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} of the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext. The required roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = this.system.getSystemResourceRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRoleSelector withRequiringContext(final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new ResourceRequiredRoleSelector((context1, role) -> {
            this.requringAssemblyContext = context1;
            this.innerRequiredRole = role;
            return this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRoleSelector withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
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
