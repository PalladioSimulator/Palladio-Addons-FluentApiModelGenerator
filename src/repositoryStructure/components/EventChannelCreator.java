package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.EventGroup;

import repositoryStructure.Entity;

public class EventChannelCreator extends Entity {

	private EventGroup eventGroup;
	private List<EventChannelSinkConnector> sinkConnections;
	private List<EventChannelSourceConnector> sourceConnections;

	private ComplexComponent component;

	public EventChannelCreator(ComplexComponent component) {
		this.component = component;
		this.sinkConnections = new ArrayList<>();
		this.sourceConnections = new ArrayList<>();
	}

	public EventChannelCreator withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection) {
		sinkConnections.add(sinkConnection);
		return this;
	}

	public EventChannelCreator withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection) {
		sourceConnections.add(sourceConnection);
		return this;
	}

	public ComplexComponent withEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
		EventChannel eg = this.build();
		component.addEventChannel(eg);
		return component;
	}

	@Override
	public EventChannel build() {
		EventChannel eventChannel = CompositionFactory.eINSTANCE.createEventChannel();
		if (eventGroup != null)
			eventChannel.setEventGroup__EventChannel(eventGroup);

		eventChannel.getEventChannelSinkConnector__EventChannel().addAll(sinkConnections);
		eventChannel.getEventChannelSourceConnector__EventChannel().addAll(sourceConnections);

		return eventChannel;
	}

}
