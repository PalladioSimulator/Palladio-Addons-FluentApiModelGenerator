package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

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
		InfrastructureInterface interfce = RepositoryFactory.eINSTANCE.createInfrastructureInterface();
		
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);
		
		// TODO: add to Lists
		interfce.getParentInterfaces__Interface();
		interfce.getProtocols__Interface();
		interfce.getRequiredCharacterisations();
		
		interfce.getInfrastructureSignatures__InfrastructureInterface();
		
		return interfce;
	}

	

}
