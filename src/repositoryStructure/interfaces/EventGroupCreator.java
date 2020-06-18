package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.EventGroup;

import apiControlFlowInterfaces.Inter;

public class EventGroupCreator extends Interface{

	@Override
	public EventGroupCreator withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public EventGroupCreator withId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventGroup build() {
		// TODO Auto-generated method stub
		return null;
	}

}
