package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.repository.SinkRole
 * SinkRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.SinkRole
 */
public class SinkRoleCreator extends SystemEntity {
    private EventGroup eventGroup;

    public SinkRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} this role provides.
     *
     * @param eventGroup
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public SinkRoleCreator withEventGroup(final EventGroup eventGroup) {
        Objects.requireNonNull(eventGroup, "The given EventGroup must not be null.");
        this.eventGroup = eventGroup;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} this role provides. Searches the repositories added to the system
     * for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public SinkRoleCreator withEventGroup(final String name) throws NoSuchElementException {
        EventGroup group;
        try {
            group = (EventGroup) system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an EventGroup. "
                            + "Please make sure all names are unique.", name));
        }
        return this.withEventGroup(group);
    }

    @Override
    public SinkRole build() {
        final SinkRole role = RepositoryFactory.eINSTANCE.createSinkRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setEventGroup__SinkRole(eventGroup);
        return role;
    }

    @Override
    public SinkRoleCreator withName(final String name) {
        return (SinkRoleCreator) super.withName(name);
    }

}
