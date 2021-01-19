package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class EventChannelSourceConnectorCreator extends SystemEntity {
	private EventChannel eventChannel;
	private AssemblyContext context;
	private SourceRole role;

	public EventChannelSourceConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public EventChannelSourceConnectorCreator withEventChannel(EventChannel eventChannel) {
		this.eventChannel = eventChannel;
		return this;
	}
	
	public EventChannelSourceConnectorCreator withEventChannel(String name) {
		EventChannel channel = this.system.getEventChannels().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEventChannel(channel);
	}
	
	public SourceRoleSelector withAssemblyContext(AssemblyContext assemblyContext) {
		EventChannelSourceConnectorCreator creator = this;
		return new SourceRoleSelector(new IContextSourceRoleCombinator() {
			
			@Override
			public EventChannelSourceConnectorCreator CombineContextAndSourceRole(AssemblyContext context, SourceRole role) {
				creator.context = context;
				creator.role = role;
				return creator;
			}
		}, assemblyContext);
	}
	
	public SourceRoleSelector withAssemblyContext(String name) {
		AssemblyContext context = this.system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withAssemblyContext(context);
	}
	
	@Override
	public EventChannelSourceConnector build() {
		EventChannelSourceConnector connector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();
		if (this.name != null) {
			connector.setEntityName(name);
		}
		connector.setEventChannel__EventChannelSourceConnector(eventChannel);
		connector.setAssemblyContext__EventChannelSourceConnector(context);
		connector.setSourceRole__EventChannelSourceRole(role);
		return connector;
	}

	@Override
	public EventChannelSourceConnectorCreator withName(String name) {
		return (EventChannelSourceConnectorCreator) super.withName(name);
	}
}
