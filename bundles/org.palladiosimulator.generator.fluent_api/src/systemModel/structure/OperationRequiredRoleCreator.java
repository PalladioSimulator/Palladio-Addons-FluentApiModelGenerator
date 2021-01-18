package systemModel.structure;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class OperationRequiredRoleCreator extends SystemEntity {
	
	private OperationInterface requiredInterface;

	public OperationRequiredRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public OperationRequiredRoleCreator withRequiredInterface(OperationInterface operationInterface) {
		this.requiredInterface = operationInterface;
		return this;
	}
	
	public OperationRequiredRoleCreator withRequiredInterfaceByName(String name) {
		OperationInterface requiredInterface = (OperationInterface) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(i -> i.getEntityName().equals(name)).findFirst().get();
		return withRequiredInterface(requiredInterface);
	}
	
	@Override
	public OperationRequiredRole build() {
		OperationRequiredRole role = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setRequiredInterface__OperationRequiredRole(requiredInterface);
		return role;
	}
	
	@Override
	public OperationRequiredRoleCreator withName(String name) {
		return (OperationRequiredRoleCreator) super.withName(name);
	}

}
