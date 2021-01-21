package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.connector.AbstractConnectorCreator;

public class EventChannelSinkConnectorCreator extends AbstractConnectorCreator {
	private EventChannel eventChannel;
	private AssemblyContext context;
	private SinkRole role;

	public EventChannelSinkConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public EventChannelSinkConnectorCreator withEventChannel(EventChannel eventChannel) {
		this.eventChannel = eventChannel;
		return this;
	}
	
	public EventChannelSinkConnectorCreator withEventChannel(String name) {
		EventChannel channel = this.system.getEventChannels().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEventChannel(channel);
	}
	
	public SinkRoleSelector withAssemblyContext(AssemblyContext assemblyContext) {
		EventChannelSinkConnectorCreator creator = this;
		return new SinkRoleSelector(new IContextSinkRoleCombinator() {
			
			@Override
			public EventChannelSinkConnectorCreator combineContextAndSinkRole(AssemblyContext context, SinkRole role) {
				creator.context = context;
				creator.role = role;
				return creator;
			}
		}, assemblyContext);
	}
	
	public SinkRoleSelector withAssemblyContext(String name) {
		AssemblyContext context = this.system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withAssemblyContext(context);
	}
	
	@Override
	public EventChannelSinkConnector build() {
		EventChannelSinkConnector connector = CompositionFactory.eINSTANCE.createEventChannelSinkConnector();
		if (this.name != null) {
			connector.setEntityName(name);
		}
		connector.setEventChannel__EventChannelSinkConnector(eventChannel);
		connector.setAssemblyContext__EventChannelSinkConnector(context);
		connector.setSinkRole__EventChannelSinkConnector(role);
		return connector;
	}

	@Override
	public EventChannelSinkConnectorCreator withName(String name) {
		return (EventChannelSinkConnectorCreator) super.withName(name);
	}
}
