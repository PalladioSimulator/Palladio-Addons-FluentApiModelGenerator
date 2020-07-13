package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

import repositoryStructure.Entity;

public abstract class Interface extends Entity {

	protected List<org.palladiosimulator.pcm.repository.Interface> parentInterfaces = new ArrayList<>();
	protected List<RequiredCharacterisation> requiredCharacterisations = new ArrayList<>();

	public abstract org.palladiosimulator.pcm.repository.Interface build();

	// parent Interfaces
	/**
	 * Creates a conforming (parental) connection to the
	 * <code>parentInterface</code> and adds it to this interface/event group.
	 * <p>
	 * An existing <code>parentInterface</code> can be fetched from the repository
	 * using the factory, i.e. <code>create.fetchOfInterface(name)</code>.
	 * </p>
	 * 
	 * @param parentInterface
	 * @return the interface/event group in the making
	 * @see factory.MyRepositoryFactory#fetchOfInterface(String)
	 * @see org.palladiosimulator.pcm.repository.Interface#getParentInterfaces__Interface()
	 * @see org.palladiosimulator.pcm.repository.Interface
	 */
	public Interface conforms(org.palladiosimulator.pcm.repository.Interface parentInterface) {
		parentInterfaces.add(parentInterface);
		return this;
	}

	/**
	 * 
	 * @param parameter
	 * @param type
	 * @return
	 */
	public Interface withRequiredCharacterisation(Parameter parameter, VariableCharacterisationType type) {
		RequiredCharacterisation reqChar = RepositoryFactory.eINSTANCE.createRequiredCharacterisation();
		reqChar.setParameter(parameter);
		reqChar.setType(type);
		requiredCharacterisations.add(reqChar);
		return this;
	}

}
