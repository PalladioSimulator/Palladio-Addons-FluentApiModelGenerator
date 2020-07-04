package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.EventGroup;

import apiControlFlowInterfaces.EventChannelCreation.Comp;
import apiControlFlowInterfaces.EventChannelCreation.Sub;
import repositoryStructure.Entity;

public class EventChannelCreator extends Entity implements Comp, Sub {

	private EventGroup eventGroup;
	private List<EventChannelSinkConnector> sinkConnections;
	private List<EventChannelSourceConnector> sourceConnections;

	private CompositeComponentCreator correspondingComponent;
	private SubSystemCreator correspondingSubsystem;

	public EventChannelCreator(CompositeComponentCreator component) {
		this.correspondingComponent = component;
		this.sinkConnections = new ArrayList<>();
		this.sourceConnections = new ArrayList<>();
	}

	public EventChannelCreator(SubSystemCreator component) {
		this.correspondingSubsystem = component;
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

	public EventChannelCreator withEventGroup(EventGroup eventGroup) {
		this.eventGroup = eventGroup;
		return this;
	}

	public CompositeComponentCreator todo1() {
		EventChannel eg = this.build();
		correspondingComponent.addEventChannel(eg);
		return correspondingComponent;
	}

	public SubSystemCreator todo2() {
		EventChannel eg = this.build();
		correspondingSubsystem.addEventChannel(eg);
		return correspondingSubsystem;
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
