package system.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class EventChannelSourceConnectorCreator extends AbstractConnectorCreator {
    private EventChannel eventChannel;
    private AssemblyContext context;
    private SourceRole role;

    public EventChannelSourceConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public EventChannelSourceConnectorCreator withEventChannel(final EventChannel eventChannel) {
        this.eventChannel = eventChannel;
        return this;
    }

    public EventChannelSourceConnectorCreator withEventChannel(final String name) {
        final EventChannel channel = this.system.getEventChannels()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withEventChannel(channel);
    }

    public SourceRoleSelector withAssemblyContext(final AssemblyContext assemblyContext) {
        final EventChannelSourceConnectorCreator creator = this;
        return new SourceRoleSelector(new IContextSourceRoleCombinator() {

            @Override
            public EventChannelSourceConnectorCreator combineContextAndSourceRole(final AssemblyContext context,
                    final SourceRole role) {
                creator.context = context;
                creator.role = role;
                return creator;
            }
        }, assemblyContext);
    }

    public SourceRoleSelector withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withAssemblyContext(context);
    }

    @Override
    public EventChannelSourceConnector build() {
        final EventChannelSourceConnector connector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setEventChannel__EventChannelSourceConnector(this.eventChannel);
        connector.setAssemblyContext__EventChannelSourceConnector(this.context);
        connector.setSourceRole__EventChannelSourceRole(this.role);
        return connector;
    }

    @Override
    public EventChannelSourceConnectorCreator withName(final String name) {
        return (EventChannelSourceConnectorCreator) super.withName(name);
    }
}
