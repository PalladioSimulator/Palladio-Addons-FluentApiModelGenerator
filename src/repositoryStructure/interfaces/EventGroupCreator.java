package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

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

	@Override
	public EventGroupCreator withId(String id) {
		return (EventGroupCreator) super.withId(id);
	}

	// parent Interfaces
	@Override
	public EventGroupCreator conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		return (EventGroupCreator) super.conforms(interfce);
	}

	@Override
	public EventGroupCreator withRequiredCharacterisation(Parameter parameter, VariableCharacterisationType type) {
		return (EventGroupCreator) super.withRequiredCharacterisation(parameter, type);
	}

	@Override
	public EventGroupCreator withProtocol() {
		return (EventGroupCreator) super.withProtocol();
	}

	public EventTypeCreator withEventType() {
		EventTypeCreator etc = new EventTypeCreator(this);
		return etc;
	}

	@Override
	public EventGroup build() {
		EventGroup eventGroup = RepositoryFactory.eINSTANCE.createEventGroup();

		if (name != null)
			eventGroup.setEntityName(name);
		if (id != null)
			eventGroup.setId(id);

		eventGroup.getParentInterfaces__Interface().addAll(parentInterfaces);
		eventGroup.getProtocols__Interface().addAll(protocols);
		eventGroup.getRequiredCharacterisations().addAll(requiredCharacterisations);

		eventGroup.getEventTypes__EventGroup().addAll(eventTypes);

		return eventGroup;
	}

	public void addEventType(EventType eventType) {
		this.eventTypes.add(eventType);
	}

}
