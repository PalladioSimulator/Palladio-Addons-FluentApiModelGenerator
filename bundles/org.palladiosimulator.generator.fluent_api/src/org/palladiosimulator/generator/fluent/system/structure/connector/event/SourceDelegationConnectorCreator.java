package org.palladiosimulator.generator.fluent.system.structure.connector.event;

import java.util.Objects;

import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
 * SourceDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
 */
public class SourceDelegationConnectorCreator extends AbstractConnectorCreator {
    private SourceRole outerRole;
    private SourceRole innerRole;
    private AssemblyContext assemblyContext;

    public SourceDelegationConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SourceRole
     * SourceRole} of the org.palladiosimulator.generator.fluent.system, delegated
     * to an AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceDelegationConnectorCreator withOuterSourceRole(final SourceRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        outerRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SourceRole
     * SourceRole} of the org.palladiosimulator.generator.fluent.system, delegated
     * to an AssemblyContext. The source roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceDelegationConnectorCreator withOuterSourceRole(final String name) throws NoSuchElementException {
        final SourceRole role = system.getSystemSourceRoleByName(name);
        return this.withOuterSourceRole(role);
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
    public SourceRoleSelector<SourceDelegationConnectorCreator> withAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SourceRoleSelector<>((context1, role) -> {
            SourceDelegationConnectorCreator.this.assemblyContext = context1;
            SourceDelegationConnectorCreator.this.innerRole = role;
            return SourceDelegationConnectorCreator.this;
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
    public SourceRoleSelector<SourceDelegationConnectorCreator> withAssemblyContext(final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withAssemblyContext(context);
    }

    @Override
    public SourceDelegationConnector build() {
        final SourceDelegationConnector connector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setAssemblyContext__SourceDelegationConnector(assemblyContext);
        connector.setOuterSourceRole__SourceRole(outerRole);
        connector.setInnerSourceRole__SourceRole(innerRole);
        return connector;
    }

    @Override
    public SourceDelegationConnectorCreator withName(final String name) {
        return (SourceDelegationConnectorCreator) super.withName(name);
    }

}
