package org.palladiosimulator.generator.fluent.system.structure.connector.event;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
 * EventChannelSinkConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
 */
public class EventChannelSinkConnectorCreator extends AbstractConnectorCreator {
    private EventChannel eventChannel;
    private AssemblyContext assemblyContext;
    private SinkRole role;

    public EventChannelSinkConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel} the
     * sink role is connected to.
     *
     * @param eventChannel
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public EventChannelSinkConnectorCreator withEventChannel(final EventChannel eventChannel) {
        IllegalArgumentException.throwIfNull(eventChannel, "The given EventChannel must not be null.");
        this.eventChannel = eventChannel;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel} the
     * sink role is connected to. The event channels added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public EventChannelSinkConnectorCreator withEventChannel(final String name) throws NoSuchElementException {
        final EventChannel channel = this.system.getEventChannelByName(name);
        return this.withEventChannel(channel);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<EventChannelSinkConnectorCreator> withAssemblyContext(
            final AssemblyContext assemblyContext) {
        IllegalArgumentException.throwIfNull(assemblyContext, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<>((context, role) -> {
            this.assemblyContext = context;
            this.role = role;
            return this;
        }, assemblyContext);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<EventChannelSinkConnectorCreator> withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
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
