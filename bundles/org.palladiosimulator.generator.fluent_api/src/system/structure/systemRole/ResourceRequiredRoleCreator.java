package system.structure.systemRole;

import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class ResourceRequiredRoleCreator extends SystemEntity {

    private ResourceInterface requiredInterface;

    public ResourceRequiredRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public ResourceRequiredRoleCreator withRequiredInterface(final shared.structure.ResourceInterface resource) {
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
