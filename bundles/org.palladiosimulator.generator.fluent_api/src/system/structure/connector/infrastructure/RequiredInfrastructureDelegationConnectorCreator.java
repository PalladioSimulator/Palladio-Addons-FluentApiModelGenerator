package system.structure.connector.infrastructure;

import java.util.NoSuchElementException;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
 * RequiredInfrastructureDelegationConnector}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
 */
public class RequiredInfrastructureDelegationConnectorCreator extends AbstractConnectorCreator {
    private InfrastructureRequiredRole outerRequiredRole;
    private InfrastructureRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredInfrastructureDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} of the system, delegated to an AssemblyContext.
     * 
     * @param role
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(
            final InfrastructureRequiredRole role) {
        this.outerRequiredRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} of the system, delegated to an AssemblyContext. The required
     * roles added to the system are searched for one that matches the given name.
     * 
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(final String name)
            throws NoSuchElementException {
        final InfrastructureRequiredRole role = this.system.getSystemInfrastructureRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role.
     * 
     * @param context
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleSelector<RequiredInfrastructureDelegationConnectorCreator> withRequiringContext(
            final AssemblyContext context) {
        return new InfrastructureRequiredRoleSelector<RequiredInfrastructureDelegationConnectorCreator>(
                new IContextRoleCombinator<InfrastructureRequiredRole, RequiredInfrastructureDelegationConnectorCreator>() {

                    @Override
                    public RequiredInfrastructureDelegationConnectorCreator combineContextAndRole(
                            final AssemblyContext context, final InfrastructureRequiredRole role) {
                        RequiredInfrastructureDelegationConnectorCreator.this.requringAssemblyContext = context;
                        RequiredInfrastructureDelegationConnectorCreator.this.innerRequiredRole = role;
                        return RequiredInfrastructureDelegationConnectorCreator.this;
                    }
                }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the system are
     * searched for one that matches the given name.
     * 
     * @param name
     * @return this connector
     * 
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleSelector<RequiredInfrastructureDelegationConnectorCreator> withRequiringContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredInfrastructureDelegationConnector build() {
        final RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createRequiredInfrastructureDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__RequiredInfrastructureDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole__RequiredInfrastructureDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole__RequiredInfrastructureDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredInfrastructureDelegationConnectorCreator withName(final String name) {
        return (RequiredInfrastructureDelegationConnectorCreator) super.withName(name);
    }

}
