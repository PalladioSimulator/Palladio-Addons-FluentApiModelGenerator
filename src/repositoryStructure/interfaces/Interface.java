package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.protocol.Protocol;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

import repositoryStructure.Entity;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

public abstract class Interface extends Entity{

	protected RequiredCharacterisationCreator requiredCharacterisation;

	protected List<org.palladiosimulator.pcm.repository.Interface> parentInterfaces= new ArrayList<>();
	protected List<Protocol> protocols = new ArrayList<>();
	protected List<RequiredCharacterisation> requiredCharacterisations = new ArrayList<>();
	
	public abstract org.palladiosimulator.pcm.repository.Interface build();

	public Interface withRequiredCharacterisation(RequiredCharacterisationCreator requiredCharacterisation){
		this.requiredCharacterisation = requiredCharacterisation;
		return this;
	}
	
}
