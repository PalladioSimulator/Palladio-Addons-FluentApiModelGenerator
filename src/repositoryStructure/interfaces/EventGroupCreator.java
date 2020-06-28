package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

public class EventGroupCreator extends Interface{
	
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
	
	@Override
	public EventGroupCreator withRequiredCharacterisation(RequiredCharacterisationCreator requiredCharacterisation){
		return (EventGroupCreator) super.withRequiredCharacterisation(requiredCharacterisation);
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
