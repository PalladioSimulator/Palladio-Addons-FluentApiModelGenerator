package org.palladiosimulator.generator.fluent.system.structure.connector.operation;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
 * RequiredDelegationConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
 */
public class RequiredDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationRequiredRole outerRequiredRole;
    private OperationRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} of the org.palladiosimulator.generator.fluent.system,
     * delegated to an AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public RequiredDelegationConnectorCreator withOuterRequiredRole(final OperationRequiredRole role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} of the org.palladiosimulator.generator.fluent.system,
     * delegated to an AssemblyContext. The required roles added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public RequiredDelegationConnectorCreator withOuterRequiredRole(final String name) throws NoSuchElementException {
        final OperationRequiredRole role = system.getSystemOperationRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRoleSelector<RequiredDelegationConnectorCreator> withRequiringContext(
            final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new OperationRequiredRoleSelector<>((context1, role) -> {
            RequiredDelegationConnectorCreator.this.requringAssemblyContext = context1;
            RequiredDelegationConnectorCreator.this.innerRequiredRole = role;
            return RequiredDelegationConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that
     * matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRoleSelector<RequiredDelegationConnectorCreator> withRequiringContext(final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredDelegationConnector build() {
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setAssemblyContext_RequiredDelegationConnector(requringAssemblyContext);
        connector.setOuterRequiredRole_RequiredDelegationConnector(outerRequiredRole);
        connector.setInnerRequiredRole_RequiredDelegationConnector(innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredDelegationConnectorCreator withName(final String name) {
        return (RequiredDelegationConnectorCreator) super.withName(name);
    }

}
