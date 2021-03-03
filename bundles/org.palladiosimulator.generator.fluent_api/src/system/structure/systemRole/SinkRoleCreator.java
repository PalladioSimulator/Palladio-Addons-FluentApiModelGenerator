package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

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
        Objects.requireNonNull(eventGroup, "The given EventGroup must not be null.");
        this.eventGroup = eventGroup;
        return this;
    }

    public SinkRoleCreator withEventGroup(final String name) throws NoSuchElementException {
        EventGroup group;
        try {
            group = (EventGroup) this.system.getInterfaceByName(name);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an EventGroup. "
                            + "Please make sure all names are unique.", name));
        }
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
