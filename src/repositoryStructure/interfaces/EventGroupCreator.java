package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.EventGroup;

import repositoryStructure.RepositoryCreator;

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
	public EventGroup build() {
		// TODO Auto-generated method stub
		return null;
	}

}
