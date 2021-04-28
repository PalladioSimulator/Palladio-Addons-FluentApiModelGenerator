package org.palladiosimulator.generator.fluent.system.structure.connector.event;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
 * SinkDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
 */
public class SinkDelegationConnectorCreator extends AbstractConnectorCreator {
    private SinkRole outerRole;
    private SinkRole innerRole;
    private AssemblyContext assemblyContext;

    public SinkDelegationConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} of
     * the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkDelegationConnectorCreator withOuterSinkRole(final SinkRole role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        outerRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} of
     * the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext. The sink roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkDelegationConnectorCreator withOuterSinkRole(final String name) throws NoSuchElementException {
        final SinkRole role = system.getSystemSinkRoleByName(name);
        return this.withOuterSinkRole(role);
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
    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<>((context1, role) -> {
            SinkDelegationConnectorCreator.this.assemblyContext = context1;
            SinkDelegationConnectorCreator.this.innerRole = role;
            return SinkDelegationConnectorCreator.this;
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
    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withAssemblyContext(context);
    }

    @Override
    public SinkDelegationConnector build() {
        final SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setAssemblyContext__SinkDelegationConnector(assemblyContext);
        connector.setOuterSinkRole__SinkRole(outerRole);
        connector.setInnerSinkRole__SinkRole(innerRole);
        return connector;
    }

    @Override
    public SinkDelegationConnectorCreator withName(final String name) {
        return (SinkDelegationConnectorCreator) super.withName(name);
    }

}
