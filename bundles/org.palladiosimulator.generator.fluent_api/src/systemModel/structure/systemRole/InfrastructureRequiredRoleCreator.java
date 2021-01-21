package systemModel.structure.systemRole;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class InfrastructureRequiredRoleCreator extends SystemEntity {
	
	private InfrastructureInterface requiredInterface;

	public InfrastructureRequiredRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public InfrastructureRequiredRoleCreator withRequiredInterface(InfrastructureInterface infrastructureInterface) {
		this.requiredInterface = infrastructureInterface;
		return this;
	}
	
	public InfrastructureRequiredRoleCreator withRequiredInterface(String name) {
		InfrastructureInterface requiredInterface = (InfrastructureInterface) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(i -> i.getEntityName().equals(name)).findFirst().get();
		return withRequiredInterface(requiredInterface);
	}
	
	@Override
	public InfrastructureRequiredRole build() {
		InfrastructureRequiredRole role = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setRequiredInterface__InfrastructureRequiredRole(requiredInterface);
		return role;
	}
	
	@Override
	public InfrastructureRequiredRoleCreator withName(String name) {
		return (InfrastructureRequiredRoleCreator) super.withName(name);
	}

}
