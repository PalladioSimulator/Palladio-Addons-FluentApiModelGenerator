package systemModel.structure.systemRole;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SourceRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class SourceRoleCreator extends SystemEntity {
	private EventGroup eventGroup;

	public SourceRoleCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public SourceRoleCreator withEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
		return this;
	}
	
	public SourceRoleCreator withEventGroup(String name) {
		EventGroup group = (EventGroup) this.system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEventGroup(group);
	}
	
	@Override
	public SourceRole build() {
		SourceRole role = RepositoryFactory.eINSTANCE.createSourceRole();
		if (this.name != null) {
			role.setEntityName(name);
		}
		role.setEventGroup__SourceRole(eventGroup);
		return role;
	}
	
	@Override
	public SourceRoleCreator withName(String name) {
		return (SourceRoleCreator) super.withName(name);
	}

}
