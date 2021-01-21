package systemModel.structure.systemRole;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class SinkRoleCreator extends SystemEntity {
	private EventGroup eventGroup;

	public SinkRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public SinkRoleCreator withEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
		return this;
	}
	
	public SinkRoleCreator withEventGroup(String name) {
		EventGroup group = (EventGroup) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEventGroup(group);
	}
	
	@Override
	public SinkRole build() {
		SinkRole role = RepositoryFactory.eINSTANCE.createSinkRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setEventGroup__SinkRole(eventGroup);
		return role;
	}
	
	@Override
	public SinkRoleCreator withName(String name) {
		return (SinkRoleCreator) super.withName(name);
	}

}
