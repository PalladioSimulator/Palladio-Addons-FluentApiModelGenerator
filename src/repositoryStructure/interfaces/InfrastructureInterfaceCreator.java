package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

public class InfrastructureInterfaceCreator extends Interface{
	
	public InfrastructureInterfaceCreator(RepositoryCreator repo) {
		
	}

	@Override
	public InfrastructureInterfaceCreator withName(String name) {
		return (InfrastructureInterfaceCreator) super.withName(name);
	}

	@Override
	public InfrastructureInterfaceCreator withId(String id) {
		return (InfrastructureInterfaceCreator) super.withId(id);
	}
	
	@Override
	public InfrastructureInterfaceCreator withRequiredCharacterisation(RequiredCharacterisationCreator requiredCharacterisation){
		return (InfrastructureInterfaceCreator) super.withRequiredCharacterisation(requiredCharacterisation);
	}

	@Override
	public InfrastructureInterface build() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
