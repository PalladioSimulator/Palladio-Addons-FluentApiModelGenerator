package system.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class EventChannelSinkConnectorCreator extends AbstractConnectorCreator {
    private EventChannel eventChannel;
    private AssemblyContext assemblyContext;
    private SinkRole role;

    public EventChannelSinkConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public EventChannelSinkConnectorCreator withEventChannel(final EventChannel eventChannel) {
        this.eventChannel = eventChannel;
        return this;
    }

    public EventChannelSinkConnectorCreator withEventChannel(final String name) {
        final EventChannel channel = this.system.getEventChannels()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withEventChannel(channel);
    }

    public SinkRoleSelector withAssemblyContext(final AssemblyContext assemblyContext) {
        final EventChannelSinkConnectorCreator creator = this;
        return new SinkRoleSelector(new IContextSinkRoleCombinator() {

            @Override
            public EventChannelSinkConnectorCreator combineContextAndSinkRole(final AssemblyContext context,
                    final SinkRole role) {
                creator.assemblyContext = context;
                creator.role = role;
                return creator;
            }
        }, assemblyContext);
    }

    public SinkRoleSelector withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withAssemblyContext(context);
    }

    @Override
    public EventChannelSinkConnector build() {
        final EventChannelSinkConnector connector = CompositionFactory.eINSTANCE.createEventChannelSinkConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setEventChannel__EventChannelSinkConnector(this.eventChannel);
        connector.setAssemblyContext__EventChannelSinkConnector(this.assemblyContext);
        connector.setSinkRole__EventChannelSinkConnector(this.role);
        return connector;
    }

    @Override
    public EventChannelSinkConnectorCreator withName(final String name) {
        return (EventChannelSinkConnectorCreator) super.withName(name);
    }
}
