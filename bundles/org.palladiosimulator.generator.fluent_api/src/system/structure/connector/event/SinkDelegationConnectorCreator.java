package system.structure.connector.event;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
 * SinkDelegationConnector}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
 */
public class SinkDelegationConnectorCreator extends AbstractConnectorCreator {
    private SinkRole outerRole;
    private SinkRole innerRole;
    private AssemblyContext assemblyContext;

    public SinkDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} of the system,
     * delegated to an AssemblyContext.
     * 
     * @param role
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkDelegationConnectorCreator withOuterSinkRole(final SinkRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRole = role;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} of the system,
     * delegated to an AssemblyContext. The sink roles added to the system are searched for one that
     * matches the given name.
     * 
     * @param role
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkDelegationConnectorCreator withOuterSinkRole(final String name) throws NoSuchElementException {
        final SinkRole role = this.system.getSystemSinkRoleByName(name);
        return this.withOuterSinkRole(role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role.
     * 
     * @param context
     * @return this connector
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<SinkDelegationConnectorCreator>(
                new IContextRoleCombinator<SinkRole, SinkDelegationConnectorCreator>() {

                    @Override
                    public SinkDelegationConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final SinkRole role) {
                        SinkDelegationConnectorCreator.this.assemblyContext = context;
                        SinkDelegationConnectorCreator.this.innerRole = role;
                        return SinkDelegationConnectorCreator.this;
                    }
                }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the sink role. The assembly contexts added to the system are searched
     * for one that matches the given name.
     * 
     * @param name
     * @return this connector
     * 
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * 
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withAssemblyContext(context);
    }

    @Override
    public SinkDelegationConnector build() {
        final SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__SinkDelegationConnector(this.assemblyContext);
        connector.setOuterSinkRole__SinkRole(this.outerRole);
        connector.setInnerSinkRole__SinkRole(this.innerRole);
        return connector;
    }

    @Override
    public SinkDelegationConnectorCreator withName(final String name) {
        return (SinkDelegationConnectorCreator) super.withName(name);
    }

}