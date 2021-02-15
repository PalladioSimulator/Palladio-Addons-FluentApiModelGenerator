package system.structure;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.repository.EventGroup;

public class EventChannelCreator extends SystemEntity {
    private EventGroup eventGroup;

    public EventChannelCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public EventChannelCreator withEventGroup(final EventGroup eventGroup) {
        this.eventGroup = eventGroup;
        return this;
    }

    public EventChannelCreator withEventGroup(final String name) {
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
