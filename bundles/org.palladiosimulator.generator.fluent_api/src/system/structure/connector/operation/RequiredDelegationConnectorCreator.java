package system.structure.connector.operation;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

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
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} of the system, delegated to an AssemblyContext.
     *
     * @param role
     * @return this connector
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public RequiredDelegationConnectorCreator withOuterRequiredRole(final OperationRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} of the system, delegated to an AssemblyContext. The
     * required roles added to the system are searched for one that matches the
     * given name.
     *
     * @param role
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public RequiredDelegationConnectorCreator withOuterRequiredRole(final String name) throws NoSuchElementException {
        final OperationRequiredRole role = this.system.getSystemOperationRequiredRoleByName(name);
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new OperationRequiredRoleSelector<RequiredDelegationConnectorCreator>(
                new IContextRoleCombinator<OperationRequiredRole, RequiredDelegationConnectorCreator>() {

                    @Override
                    public RequiredDelegationConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final OperationRequiredRole role) {
                        RequiredDelegationConnectorCreator.this.requringAssemblyContext = context;
                        RequiredDelegationConnectorCreator.this.innerRequiredRole = role;
                        return RequiredDelegationConnectorCreator.this;
                    }
                }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the
     * system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRoleSelector<RequiredDelegationConnectorCreator> withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredDelegationConnector build() {
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext_RequiredDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole_RequiredDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole_RequiredDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredDelegationConnectorCreator withName(final String name) {
        return (RequiredDelegationConnectorCreator) super.withName(name);
    }

}
