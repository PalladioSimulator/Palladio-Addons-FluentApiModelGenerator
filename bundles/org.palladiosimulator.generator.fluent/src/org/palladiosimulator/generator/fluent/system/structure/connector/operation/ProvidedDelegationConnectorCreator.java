package org.palladiosimulator.generator.fluent.system.structure.connector.operation;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
 * ProvidedDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
 */
public class ProvidedDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationProvidedRole outerProvidedRole;
    private OperationProvidedRole innerProvidedRole;
    private AssemblyContext providingAssemblyContext;

    public ProvidedDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} of the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final OperationProvidedRole role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.outerProvidedRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} of the org.palladiosimulator.generator.fluent.system, delegated to an
     * AssemblyContext. The provided roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final String name) throws NoSuchElementException {
        final OperationProvidedRole role = this.system.getSystemOperationProvidedRoleByName(name);
        return this.withOuterProvidedRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator> withProvidingContext(
            final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new OperationProvidedRoleSelector<>((context1, role) -> {
            this.providingAssemblyContext = context1;
            this.innerProvidedRole = role;
            return this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator> withProvidingContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withProvidingContext(context);
    }

    @Override
    public ProvidedDelegationConnector build() {
        final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext_ProvidedDelegationConnector(this.providingAssemblyContext);
        connector.setOuterProvidedRole_ProvidedDelegationConnector(this.outerProvidedRole);
        connector.setInnerProvidedRole_ProvidedDelegationConnector(this.innerProvidedRole);
        return connector;
    }

    @Override
    public ProvidedDelegationConnectorCreator withName(final String name) {
        return (ProvidedDelegationConnectorCreator) super.withName(name);
    }

}
