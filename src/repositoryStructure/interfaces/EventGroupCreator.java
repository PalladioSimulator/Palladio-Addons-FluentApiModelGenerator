package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}. It is
 * used to create the '<em><b>EventGroup</b></em>' object step-by-step, i.e.
 * '<em><b>EventGroupCreator</b></em>' objects are of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.EventGroup
 */
public class EventGroupCreator extends Interface {

	private List<EventType> eventTypes;

	public EventGroupCreator(RepositoryCreator repo) {
		this.repository = repo;
		eventTypes = new ArrayList<>();
	}

	@Override
	public EventGroupCreator withName(String name) {
		return (EventGroupCreator) super.withName(name);
	}

//	@Override
//	public EventGroupCreator withId(String id) {
//		return (EventGroupCreator) super.withId(id);
//	}

	// parent Interfaces
	@Override
	public EventGroupCreator conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		return (EventGroupCreator) super.conforms(interfce);
	}

	@Override
	public EventGroupCreator withRequiredCharacterisation(Parameter parameter, VariableCharacterisationType type) {
		return (EventGroupCreator) super.withRequiredCharacterisation(parameter, type);
	}

	/**
	 * Creates a new {@link org.palladiosimulator.pcm.repository.EventType
	 * EventType}.
	 * <p>
	 * Every service of an interface/event group has a unique signature/event type, like <code>void
	 * doSomething(int a)</code>. A PCM signature/event type is comparable to a method
	 * signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An event type contains
	 * <ul>
	 * <li>a
	 * {@link repositoryStructure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value),
	 * <li>an
	 * {@link repositoryStructure.interfaces.EventTypeCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link repositoryStructure.interfaces.EventTypeCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link repositoryStructure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link repositoryStructure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures/event types.
	 * </ul>
	 * A signature/event type has to be unique for an interface/event group through the tuple (identifier,
	 * order of parameters). Different interfaces/event groups can define equally named
	 * signatures/event types, however, they are not identical.
	 * </p>
	 * <p>
	 * To return to editing the event group this event type belongs to, the
	 * modification of the event type has to be completed with calling a
	 * {@link repositoryStructure.interfaces.EventTypeCreator#now() final
	 * method}.
	 * </p>
	 * 
	 * @return the event type in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see repositoryStructure.interfaces.EventTypeCreator#withName(String)
	 * @see repositoryStructure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see repositoryStructure.interfaces.EventTypeCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.EventTypeCreator#withParameter(String,
	 *      repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see repositoryStructure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see repositoryStructure.interfaces.EventTypeCreator#now()
	 */
	public EventTypeCreator withEventType() {
		EventTypeCreator etc = new EventTypeCreator(this, this.repository);
		return etc;
	}

	@Override
	public EventGroup build() {
		EventGroup eventGroup = RepositoryFactory.eINSTANCE.createEventGroup();

		if (name != null)
			eventGroup.setEntityName(name);
//		if (id != null)
//			eventGroup.setId(id);

		eventGroup.getParentInterfaces__Interface().addAll(parentInterfaces);
		eventGroup.getRequiredCharacterisations().addAll(requiredCharacterisations);

		eventGroup.getEventTypes__EventGroup().addAll(eventTypes);

		return eventGroup;
	}

	protected void addEventType(EventType eventType) {
		this.eventTypes.add(eventType);
	}

}
