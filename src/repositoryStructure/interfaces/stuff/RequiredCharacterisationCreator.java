package repositoryStructure.interfaces.stuff;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

import repositoryStructure.Entity;

public class RequiredCharacterisationCreator extends Entity{

	private Parameter parameter;
	private VariableCharacterisationType type;
	
	public RequiredCharacterisationCreator withParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}
	
	public RequiredCharacterisationCreator withType(VariableCharacterisationType type) {
		this.type = type;
		return this;
	}

	@Override
	public RequiredCharacterisation build() {
		RequiredCharacterisation reqChar = RepositoryFactory.eINSTANCE.createRequiredCharacterisation();
		reqChar.setParameter(parameter);
		reqChar.setType(type);
		return reqChar;
	}
}
