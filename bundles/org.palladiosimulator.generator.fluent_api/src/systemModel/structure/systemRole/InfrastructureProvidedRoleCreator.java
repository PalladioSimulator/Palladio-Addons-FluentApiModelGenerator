package systemModel.structure.systemRole;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class InfrastructureProvidedRoleCreator extends SystemEntity {
	
	private InfrastructureInterface providedInterface;

	public InfrastructureProvidedRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public InfrastructureProvidedRoleCreator withProvidedInterface(InfrastructureInterface infrastructureInterface) {
		this.providedInterface = infrastructureInterface;
		return this;
	}
	
	public InfrastructureProvidedRoleCreator withProvidedInterface(String name) {
		InfrastructureInterface requiredInterface = (InfrastructureInterface) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(i -> i.getEntityName().equals(name)).findFirst().get();
		return withProvidedInterface(requiredInterface);
	}
	
	@Override
	public InfrastructureProvidedRole build() {
		InfrastructureProvidedRole role = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setProvidedInterface__InfrastructureProvidedRole(providedInterface);
		return role;
	}
	
	@Override
	public InfrastructureProvidedRoleCreator withName(String name) {
		return (InfrastructureProvidedRoleCreator) super.withName(name);
	}

}
