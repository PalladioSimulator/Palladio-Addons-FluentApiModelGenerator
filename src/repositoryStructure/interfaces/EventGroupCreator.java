package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class EventGroupCreator extends Interface {

	public EventGroupCreator(RepositoryCreator repo) {
		// TODO Auto-generated constructor stub
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

	@Override
	public EventGroup build() {
		EventGroup eventGroup = RepositoryFactory.eINSTANCE.createEventGroup();

		if (name != null)
			eventGroup.setEntityName(name);
		if (id != null)
			eventGroup.setId(id);

		// TODO: add to Lists
		eventGroup.getParentInterfaces__Interface();
		eventGroup.getProtocols__Interface();
		eventGroup.getRequiredCharacterisations();

		eventGroup.getEventTypes__EventGroup();

		return eventGroup;
	}

}
