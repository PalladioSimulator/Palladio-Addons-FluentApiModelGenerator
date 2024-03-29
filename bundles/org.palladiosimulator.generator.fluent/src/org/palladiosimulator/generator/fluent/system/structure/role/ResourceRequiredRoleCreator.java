package org.palladiosimulator.generator.fluent.system.structure.role;

import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.SystemEntity;
import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
 * ResourceRequiredRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
 */
public class ResourceRequiredRoleCreator extends SystemEntity {

    private ResourceInterface requiredInterface;

    public ResourceRequiredRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface
     * ResourceInterface} this role requires.
     *
     * @param resource
     * @return this role creator
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface
     */
    public ResourceRequiredRoleCreator withRequiredInterface(
            final org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface resource) {
        this.requiredInterface = this.system.getResourceInterface(resource);
        return this;
    }

    @Override
    public ResourceRequiredRole build() {
        final ResourceRequiredRole role = EntityFactory.eINSTANCE.createResourceRequiredRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setRequiredResourceInterface__ResourceRequiredRole(this.requiredInterface);
        return role;
    }

    @Override
    public ResourceRequiredRoleCreator withName(final String name) {
        return (ResourceRequiredRoleCreator) super.withName(name);
    }

}
