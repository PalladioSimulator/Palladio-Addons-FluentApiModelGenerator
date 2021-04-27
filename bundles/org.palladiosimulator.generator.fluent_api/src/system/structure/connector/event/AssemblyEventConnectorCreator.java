package system.structure.connector.event;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

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
        this.system = systemCreator;
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SourceRoleSelector<>(
                (context1, role) -> {
                  AssemblyEventConnectorCreator.this.sourceContext = context1;
                  AssemblyEventConnectorCreator.this.sourceRole = role;
                  return AssemblyEventConnectorCreator.this;
               }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the source role. The assembly contexts added to the
     * system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRoleSelector<AssemblyEventConnectorCreator> withSourceAssemblyContext(final String name)
            throws NoSuchElementException {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<>(
                (context1, role) -> {
                  AssemblyEventConnectorCreator.this.sinkContext = context1;
                  AssemblyEventConnectorCreator.this.sinkRole = role;
                  return AssemblyEventConnectorCreator.this;
               }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role. The assembly contexts added to the
     * system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<AssemblyEventConnectorCreator> withSinkAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withSinkAssemblyContext(context);
    }

    @Override
    public AssemblyEventConnector build() {
        final AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setSourceAssemblyContext__AssemblyEventConnector(this.sourceContext);
        connector.setSourceRole__AssemblyEventConnector(this.sourceRole);
        connector.setSinkAssemblyContext__AssemblyEventConnector(this.sinkContext);
        connector.setSinkRole__AssemblyEventConnector(this.sinkRole);
        return connector;
    }

    @Override
    public AssemblyEventConnectorCreator withName(final String name) {
        return (AssemblyEventConnectorCreator) super.withName(name);
    }

}
