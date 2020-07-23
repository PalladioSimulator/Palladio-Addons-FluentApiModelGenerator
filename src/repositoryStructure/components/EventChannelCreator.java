package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.EventGroup;

import apiControlFlowInterfaces.EventChannelCreation.EventChannelComposite;
import apiControlFlowInterfaces.EventChannelCreation.EventChannelSubsystem;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel}.
 * It is used to create the '<em><b>EventChannel</b></em>' object step-by-step,
 * i.e. '<em><b>EventChannelCreator</b></em>' objects are of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.core.composition.EventChannel
 */
public class EventChannelCreator extends Entity implements EventChannelComposite, EventChannelSubsystem {

	private EventGroup eventGroup;
	private List<EventChannelSinkConnector> sinkConnections;
	private List<EventChannelSourceConnector> sourceConnections;

	private CompositeComponentCreator correspondingComponent;
	private SubSystemCreator correspondingSubsystem;

	public EventChannelCreator(CompositeComponentCreator component, RepositoryCreator repo) {
		this.correspondingComponent = component;
		this.repository = repo;
		this.sinkConnections = new ArrayList<>();
		this.sourceConnections = new ArrayList<>();
	}

	public EventChannelCreator(SubSystemCreator component, RepositoryCreator repo) {
		this.correspondingSubsystem = component;
		this.repository = repo;
		this.sinkConnections = new ArrayList<>();
		this.sourceConnections = new ArrayList<>();
	}

	@Override
	public EventChannelCreator withName(String name) {
		return (EventChannelCreator) super.withName(name);
	}

	@Override
	public EventChannelCreator withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection) {
		Objects.requireNonNull(sinkConnection, "sinkConnection must not be null");
		//TODO: Henne-Ei-Problem mit Event Channel und Connector
		sinkConnections.add(sinkConnection);
		return this;
	}

	@Override
	public EventChannelCreator withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection) {
		Objects.requireNonNull(sourceConnection, "sourceConnection must not be null");
		sourceConnections.add(sourceConnection);
		return this;
	}

	@Override
	public EventChannelCreator withEventGroup(EventGroup eventGroup) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		this.eventGroup = eventGroup;
		return this;
	}

	@Override
	public CompositeComponentCreator now1() {
		EventChannel eg = this.build();
		this.repository.addEventChannel(eg);
		correspondingComponent.addEventChannel(eg);
		return correspondingComponent;
	}

	@Override
	public SubSystemCreator now2() {
		EventChannel eg = this.build();
		this.repository.addEventChannel(eg);
		correspondingSubsystem.addEventChannel(eg);
		return correspondingSubsystem;
	}

	@Override
	protected EventChannel build() {
		EventChannel eventChannel = CompositionFactory.eINSTANCE.createEventChannel();
		if (name != null)
			eventChannel.setEntityName(name);
		if (eventGroup != null)
			eventChannel.setEventGroup__EventChannel(eventGroup);

		eventChannel.getEventChannelSinkConnector__EventChannel().addAll(sinkConnections);
		eventChannel.getEventChannelSourceConnector__EventChannel().addAll(sourceConnections);

		return eventChannel;
	}

}
