package system.structure.systemRole;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class SourceRoleCreator extends SystemEntity {
    private EventGroup eventGroup;

    public SourceRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SourceRoleCreator withEventGroup(final EventGroup eventGroup) {
        this.eventGroup = eventGroup;
        return this;
    }

    public SourceRoleCreator withEventGroup(final String name) {
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
    public SourceRole build() {
        final SourceRole role = RepositoryFactory.eINSTANCE.createSourceRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setEventGroup__SourceRole(this.eventGroup);
        return role;
    }

    @Override
    public SourceRoleCreator withName(final String name) {
        return (SourceRoleCreator) super.withName(name);
    }

}
