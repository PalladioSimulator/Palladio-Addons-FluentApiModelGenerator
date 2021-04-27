package system.structure.systemRole;

import java.util.Objects;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SourceRole;

import exceptions.NoSuchElementException;
import system.structure.SystemCreator;
import system.structure.SystemEntity;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.SourceRole
 */
public class SourceRoleCreator extends SystemEntity {
    private EventGroup eventGroup;

    public SourceRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} this role requires.
     *
     * @param eventGroup
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public SourceRoleCreator withEventGroup(final EventGroup eventGroup) {
        Objects.requireNonNull(eventGroup, "The given EventGroup must not be null.");
        this.eventGroup = eventGroup;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} this role requires. Searches the repositories added to the system
     * for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public SourceRoleCreator withEventGroup(final String name) throws NoSuchElementException {
        EventGroup group;
        try {
            group = (EventGroup) system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(String.format(
                    "An Interface with name '%s' was found, but it was not an EventGroup. Please make sure all names are unique.",
                    name), e);
        }
        return this.withEventGroup(group);
    }

    @Override
    public SourceRole build() {
        final SourceRole role = RepositoryFactory.eINSTANCE.createSourceRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setEventGroup__SourceRole(eventGroup);
        return role;
    }

    @Override
    public SourceRoleCreator withName(final String name) {
        return (SourceRoleCreator) super.withName(name);
    }

}
