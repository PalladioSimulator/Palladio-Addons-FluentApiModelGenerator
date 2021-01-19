package systemModel.structure;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.repository.EventGroup;


public class EventChannelCreator extends SystemEntity {
	private EventGroup eventGroup;
	
	public EventChannelCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public EventChannelCreator withEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
		return this;
	}
	
	public EventChannelCreator withEventGroup(String name) {
		EventGroup group = (EventGroup) system.getRepositories().stream()
				.flatMap(x -> x.getInterfaces__Repository().stream())
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEventGroup(group);
	}
	
	@Override
	public EventChannel build() {
		EventChannel channel = CompositionFactory.eINSTANCE.createEventChannel();
		if (this.name != null) {
			channel.setEntityName(name);
		}
		channel.setEventGroup__EventChannel(eventGroup);
		return channel;
	}
	
	@Override
	public EventChannelCreator withName(String name) {
		return (EventChannelCreator) super.withName(name);
	}

}
