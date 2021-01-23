package systemModel.structure.systemRole;

import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class ResourceRequiredRoleCreator extends SystemEntity {
	
	private ResourceInterface requiredInterface;

	public ResourceRequiredRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
		
	public ResourceRequiredRoleCreator withRequiredInterface(shared.structure.ResourceInterface resource) {
		this.requiredInterface = system.getResourceInterface(resource);
		return this;
	}
	
	@Override
	public ResourceRequiredRole build() {
		ResourceRequiredRole role = EntityFactory.eINSTANCE.createResourceRequiredRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setRequiredResourceInterface__ResourceRequiredRole(requiredInterface);
		return role;
	}
	
	@Override
	public ResourceRequiredRoleCreator withName(String name) {
		return (ResourceRequiredRoleCreator) super.withName(name);
	}

}
