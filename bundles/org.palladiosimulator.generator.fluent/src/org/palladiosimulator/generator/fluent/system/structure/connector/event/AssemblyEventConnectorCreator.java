package org.palladiosimulator.generator.fluent.system.structure.connector.event;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
 * AssemblyEventConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
 */
public class AssemblyEventConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext sourceContext;
    private SourceRole sourceRole;
    private AssemblyContext sinkContext;
    private SinkRole sinkRole;

    public AssemblyEventConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the source role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRoleSelector<AssemblyEventConnectorCreator> withSourceAssemblyContext(final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new SourceRoleSelector<>((context1, role) -> {
            AssemblyEventConnectorCreator.this.sourceContext = context1;
            AssemblyEventConnectorCreator.this.sourceRole = role;
            return AssemblyEventConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the source role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRoleSelector<AssemblyEventConnectorCreator> withSourceAssemblyContext(final String name)
            throws NoSuchElementException {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withSourceAssemblyContext(context);
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
    public SinkRoleSelector<AssemblyEventConnectorCreator> withSinkAssemblyContext(final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<>((context1, role) -> {
            AssemblyEventConnectorCreator.this.sinkContext = context1;
            AssemblyEventConnectorCreator.this.sinkRole = role;
            return AssemblyEventConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<AssemblyEventConnectorCreator> withSinkAssemblyContext(final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withSinkAssemblyContext(context);
    }

    @Override
    public AssemblyEventConnector build() {
        final AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setSourceAssemblyContext__AssemblyEventConnector(sourceContext);
        connector.setSourceRole__AssemblyEventConnector(sourceRole);
        connector.setSinkAssemblyContext__AssemblyEventConnector(sinkContext);
        connector.setSinkRole__AssemblyEventConnector(sinkRole);
        return connector;
    }

    @Override
    public AssemblyEventConnectorCreator withName(final String name) {
        return (AssemblyEventConnectorCreator) super.withName(name);
    }

}
