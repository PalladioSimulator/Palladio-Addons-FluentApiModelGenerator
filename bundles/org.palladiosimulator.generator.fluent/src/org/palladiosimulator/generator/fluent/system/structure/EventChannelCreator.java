package org.palladiosimulator.generator.fluent.system.structure;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.repository.EventGroup;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.EventChannel
 */
public class EventChannelCreator extends SystemEntity {
    private EventGroup eventGroup;

    public EventChannelCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} of this event channel.
     *
     * @param component
     * @return this event group
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public EventChannelCreator withEventGroup(final EventGroup eventGroup) {
        IllegalArgumentException.requireNonNull(eventGroup, "The given EventGroup must not be null.");
        this.eventGroup = eventGroup;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} of this event channel. The repositories added to the
     * org.palladiosimulator.generator.fluent.system are searched for an event group
     * that matches the given name.
     *
     * @param component
     * @return this event group
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public EventChannelCreator withEventGroup(final String name) throws NoSuchElementException {
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
    public EventChannel build() {
        final EventChannel channel = CompositionFactory.eINSTANCE.createEventChannel();
        if (name != null) {
            channel.setEntityName(name);
        }
        channel.setEventGroup__EventChannel(eventGroup);
        return channel;
    }

    @Override
    public EventChannelCreator withName(final String name) {
        return (EventChannelCreator) super.withName(name);
    }

}
