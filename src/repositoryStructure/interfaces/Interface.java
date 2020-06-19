package repositoryStructure.interfaces;

import repositoryStructure.Entity;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

public abstract class Interface extends Entity{

	protected RequiredCharacterisationCreator requiredCharacterisation;
	
	public abstract org.palladiosimulator.pcm.repository.Interface build();

	public Interface withRequiredCharacterisation(RequiredCharacterisationCreator requiredCharacterisation){
		this.requiredCharacterisation = requiredCharacterisation;
		return this;
	}
	
}
