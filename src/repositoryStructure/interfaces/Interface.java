package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.protocol.Protocol;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

import repositoryStructure.Entity;

public abstract class Interface extends Entity {

	protected List<org.palladiosimulator.pcm.repository.Interface> parentInterfaces = new ArrayList<>();
	protected List<Protocol> protocols = new ArrayList<>();
	protected List<RequiredCharacterisation> requiredCharacterisations = new ArrayList<>();

	public abstract org.palladiosimulator.pcm.repository.Interface build();

	// parent Interfaces
	public Interface conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		parentInterfaces.add(interfce);
		return this;
	}

	public Interface withRequiredCharacterisation(Parameter parameter, VariableCharacterisationType type) {
		RequiredCharacterisation reqChar = RepositoryFactory.eINSTANCE.createRequiredCharacterisation();
		reqChar.setParameter(parameter);
		reqChar.setType(type);
		requiredCharacterisations.add(reqChar);
		return this;
	}

}
