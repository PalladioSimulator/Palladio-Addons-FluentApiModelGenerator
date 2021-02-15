package system.structure.systemRole;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class SinkRoleCreator extends SystemEntity {
    private EventGroup eventGroup;

    public SinkRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SinkRoleCreator withEventGroup(final EventGroup eventGroup) {
        this.eventGroup = eventGroup;
        return this;
    }

    public SinkRoleCreator withEventGroup(final String name) {
        final EventGroup group = (EventGroup) this.system.getRepositories()
            .stream()
            .flatMap(x -> x.getInterfaces__Repository()
                .stream())
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withEventGroup(group);
    }

    @Override
    public SinkRole build() {
        final SinkRole role = RepositoryFactory.eINSTANCE.createSinkRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setEventGroup__SinkRole(this.eventGroup);
        return role;
    }

    @Override
    public SinkRoleCreator withName(final String name) {
        return (SinkRoleCreator) super.withName(name);
    }

}
