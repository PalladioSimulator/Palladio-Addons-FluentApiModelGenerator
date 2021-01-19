package systemModel.structure;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class OperationProvidedRoleCreator extends SystemEntity {
	
	private OperationInterface providedInterface;

	public OperationProvidedRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public OperationProvidedRoleCreator withProvidedInterface(OperationInterface operationInterface) {
		this.providedInterface = operationInterface;
		return this;
	}
	
	public OperationProvidedRoleCreator withProvidedInterface(String name) {
		OperationInterface requiredInterface = (OperationInterface) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(i -> i.getEntityName().equals(name)).findFirst().get();
		return withProvidedInterface(requiredInterface);
	}
	
	@Override
	public OperationProvidedRole build() {
		OperationProvidedRole role = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setProvidedInterface__OperationProvidedRole(providedInterface);
		return role;
	}
	
	@Override
	public OperationProvidedRoleCreator withName(String name) {
		return (OperationProvidedRoleCreator) super.withName(name);
	}

}
