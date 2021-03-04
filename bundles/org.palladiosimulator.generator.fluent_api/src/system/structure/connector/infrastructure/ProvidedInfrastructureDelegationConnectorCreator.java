package system.structure.connector.infrastructure;

import java.util.NoSuchElementException;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
 * ProvidedInfrastructureDelegationConnector}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
 */
public class ProvidedInfrastructureDelegationConnectorCreator extends AbstractConnectorCreator {
    private InfrastructureProvidedRole outerProvidedRole;
    private InfrastructureProvidedRole innerProvidedRole;
    private AssemblyContext providingAssemblyContext;

    public ProvidedInfrastructureDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} of the system, delegated to an AssemblyContext.
     * 
     * @param role
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(
            final InfrastructureProvidedRole role) {
        this.outerProvidedRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} of the system, delegated to an AssemblyContext. The provided
     * roles added to the system are searched for one that matches the given name.
     * 
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(final String name)
            throws NoSuchElementException {
        final InfrastructureProvidedRole role = this.system.getSystemInfrastructureProvidedRoleByName(name);
        return this.withOuterProvidedRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role.
     * 
     * @param context
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator> withProvidingContext(
            final AssemblyContext context) {
        return new InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator>(
                new IContextRoleCombinator<InfrastructureProvidedRole, ProvidedInfrastructureDelegationConnectorCreator>() {

                    @Override
                    public ProvidedInfrastructureDelegationConnectorCreator combineContextAndRole(
                            final AssemblyContext context, final InfrastructureProvidedRole role) {
                        ProvidedInfrastructureDelegationConnectorCreator.this.providingAssemblyContext = context;
                        ProvidedInfrastructureDelegationConnectorCreator.this.innerProvidedRole = role;
                        return ProvidedInfrastructureDelegationConnectorCreator.this;
                    }
                }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role. The assembly contexts added to the system are
     * searched for one that matches the given name.
     * 
     * @param name
     * @return this connector
     * 
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator> withProvidingContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withProvidingContext(context);
    }

    @Override
    public ProvidedInfrastructureDelegationConnector build() {
        final ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createProvidedInfrastructureDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__ProvidedInfrastructureDelegationConnector(this.providingAssemblyContext);
        connector.setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(this.outerProvidedRole);
        connector.setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(this.innerProvidedRole);
        return connector;
    }

    @Override
    public ProvidedInfrastructureDelegationConnectorCreator withName(final String name) {
        return (ProvidedInfrastructureDelegationConnectorCreator) super.withName(name);
    }

}
