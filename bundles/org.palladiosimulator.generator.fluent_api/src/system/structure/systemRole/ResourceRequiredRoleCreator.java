package system.structure.systemRole;

import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
 * ResourceRequiredRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
 */
public class ResourceRequiredRoleCreator extends SystemEntity {

    private ResourceInterface requiredInterface;

    public ResourceRequiredRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface
     * ResourceInterface} this role requires.
     *
     * @param resource
     * @return this role creator
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface
     */
    public ResourceRequiredRoleCreator withRequiredInterface(final shared.structure.ResourceInterface resource) {
        requiredInterface = system.getResourceInterface(resource);
        return this;
    }

    @Override
    public ResourceRequiredRole build() {
        final ResourceRequiredRole role = EntityFactory.eINSTANCE.createResourceRequiredRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setRequiredResourceInterface__ResourceRequiredRole(requiredInterface);
        return role;
    }

    @Override
    public ResourceRequiredRoleCreator withName(final String name) {
        return (ResourceRequiredRoleCreator) super.withName(name);
    }

}
