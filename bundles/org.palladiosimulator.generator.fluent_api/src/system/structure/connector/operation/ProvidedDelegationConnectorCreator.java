package system.structure.connector.operation;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import exceptions.NoSuchElementException;
import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

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
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} of the system, delegated to an AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final OperationProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        outerProvidedRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} of the system, delegated to an AssemblyContext. The
     * provided roles added to the system are searched for one that matches the
     * given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public ProvidedDelegationConnectorCreator withOuterProvidedRole(final String name) throws NoSuchElementException {
        final OperationProvidedRole role = system.getSystemOperationProvidedRoleByName(name);
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new OperationProvidedRoleSelector<>((context1, role) -> {
            ProvidedDelegationConnectorCreator.this.providingAssemblyContext = context1;
            ProvidedDelegationConnectorCreator.this.innerProvidedRole = role;
            return ProvidedDelegationConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role. The assembly contexts added to the
     * system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRoleSelector<ProvidedDelegationConnectorCreator> withProvidingContext(final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withProvidingContext(context);
    }

    @Override
    public ProvidedDelegationConnector build() {
        final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setAssemblyContext_ProvidedDelegationConnector(providingAssemblyContext);
        connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvidedRole);
        connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvidedRole);
        return connector;
    }

    @Override
    public ProvidedDelegationConnectorCreator withName(final String name) {
        return (ProvidedDelegationConnectorCreator) super.withName(name);
    }

}
