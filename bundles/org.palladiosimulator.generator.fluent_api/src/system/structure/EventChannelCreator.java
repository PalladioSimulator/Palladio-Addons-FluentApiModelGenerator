package system.structure;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.repository.EventGroup;

public class EventChannelCreator extends SystemEntity {
    private EventGroup eventGroup;

    public EventChannelCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public EventChannelCreator withEventGroup(final EventGroup eventGroup) {
        Objects.requireNonNull(eventGroup, "The given EventGroup must not be null.");
        this.eventGroup = eventGroup;
        return this;
    }

    public EventChannelCreator withEventGroup(final String name) throws NoSuchElementException {
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
    public EventChannel build() {
        final EventChannel channel = CompositionFactory.eINSTANCE.createEventChannel();
        if (this.name != null) {
            channel.setEntityName(this.name);
        }
        channel.setEventGroup__EventChannel(this.eventGroup);
        return channel;
    }

    @Override
    public EventChannelCreator withName(final String name) {
        return (EventChannelCreator) super.withName(name);
    }

}
