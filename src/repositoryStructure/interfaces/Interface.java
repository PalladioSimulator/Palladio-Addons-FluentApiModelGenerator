package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredCharacterisation;

import repositoryStructure.Entity;

/**
 * This class provides the general infrastructure of an interface, i.e.
 * OperationInterface, InfrastructureInterface and EventGroup. It provides the
 * implementation of the methods for adding parent interfaces and providing
 * required characterizations.
 * 
 * @author Louisa Lambrecht
 *
 */
public abstract class Interface extends Entity {

	protected List<org.palladiosimulator.pcm.repository.Interface> parentInterfaces = new ArrayList<>();
	protected List<RequiredCharacterisation> requiredCharacterisations = new ArrayList<>();

	public abstract org.palladiosimulator.pcm.repository.Interface build();

	/**
	 * Creates a conforming (parental) connection to the
	 * <code>parentInterface</code> and adds it to this interface/event group.
	 * <p>
	 * An existing <code>parentInterface</code> can be fetched from the repository
	 * using the factory, i.e. <code>create.fetchOfInterface(name)</code>.
	 * </p>
	 * 
	 * @param parentInterface
	 * @return this interface/event group in the making
	 * @see factory.FluentRepositoryFactory#fetchOfInterface(String)
	 * @see org.palladiosimulator.pcm.repository.Interface#getParentInterfaces__Interface()
	 * @see org.palladiosimulator.pcm.repository.Interface
	 */
	public Interface conforms(org.palladiosimulator.pcm.repository.Interface parentInterface) {
		parentInterfaces.add(parentInterface);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.repository.RequiredCharacterisation
	 * RequiredCharacterisation} and adds it to the interface/event group.
	 * <p>
	 * A RequiredCharacterisation is a specification of parameters. It increases the
	 * power of the interfaces and enables extended interoperability checks.<br>
	 * The {@link org.palladiosimulator.pcm.parameter.VariableCharacterisationType
	 * VariableCharacterisationType} <code>type</code> offers the values
	 * '<em><b>STRUCTURE</b></em>', '<em><b>NUMBER_OF_ELEMENTS</b></em>',
	 * '<em><b>BYTESIZE</b></em>', '<em><b>TYPE</b></em>', and
	 * '<em><b>VALUE</b></em>'.
	 * </p>
	 * <p>
	 * An existing <code>parameter</code> from a previously defined signature/event
	 * type can be fetched from the repository using the factory, i.e.
	 * <code>create.fetchOfParameter(name)</code> and
	 * <code>create.fetchOfParameter(name, signatureContext)</code>.
	 * </p>
	 * 
	 * @param parameter that is specified
	 * @param type      of the parameter
	 * @return this interface/event group in the making
	 * @see factory.FluentRepositoryFactory#fetchOfParameter(String)
	 * @see factory.FluentRepositoryFactory#fetchOfParameter(String,
	 *      org.palladiosimulator.pcm.repository.Signature)
	 */
	public Interface withRequiredCharacterisation(Parameter parameter, VariableCharacterisationType type) {
		RequiredCharacterisation reqChar = RepositoryFactory.eINSTANCE.createRequiredCharacterisation();
		reqChar.setParameter(parameter);
		reqChar.setType(type);
		requiredCharacterisations.add(reqChar);
		return this;
	}

}
