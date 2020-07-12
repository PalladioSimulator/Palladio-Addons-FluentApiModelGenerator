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
 * This class constructs a
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
