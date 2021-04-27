package system.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import system.structure.AssemblyContextCreator;
import system.structure.EventChannelCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.resource.ResourceRequiredDelegationConnectorCreator;
import system.structure.qosAnnotations.QoSAnnotationsCreator;
import system.structure.systemRole.InfrastructureProvidedRoleCreator;
import system.structure.systemRole.InfrastructureRequiredRoleCreator;
import system.structure.systemRole.OperationProvidedRoleCreator;
import system.structure.systemRole.OperationRequiredRoleCreator;
import system.structure.systemRole.ResourceRequiredRoleCreator;
import system.structure.systemRole.SinkRoleCreator;
import system.structure.systemRole.SourceRoleCreator;

public interface ISystemAddition {

    /**
     * Completes the system creation.
     *
     * @return the final system object
     * @see org.palladiosimulator.pcm.system.System
     */
    System createSystemNow();

    /**
     * Adds a repository to the system. Components from added repositories can be
     * added to the system by name.
     *
     * @param repository
     * @return this system
     * @see org.palladiosimulator.pcm.repository.Repository
     */
    ISystemAddition addRepository(Repository repository);

    /**
     * Adds an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} to the system. The creator will be turned into the finished
     * AssemblyContext.
     *
     * @param context
     * @return this system
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    ISystemAddition addToSystem(AssemblyContextCreator context);

    /**
     * Adds a {@link org.palladiosimulator.pcm.core.composition.Connector Connector}
     * to the system. The creator will be turned into the finished connector.
     *
     * @param connector
     * @return this system
     * @see org.palladiosimulator.pcm.core.composition.Connector
     */
    ISystemAddition addToSystem(AbstractConnectorCreator connector);

    /**
     * Adds an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} to the system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    ISystemAddition addToSystem(OperationRequiredRoleCreator role);

    /**
     * Adds an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} to the system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    ISystemAddition addToSystem(OperationProvidedRoleCreator role);

    /**
     * Adds an {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel} to the system. The creator will be turned into the finished
     * connector.
     *
     * @param eventChannel
     * @return this system
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    ISystemAddition addToSystem(EventChannelCreator eventChannel);

    /**
     * Adds a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} to the
     * system. The creator will be turned into the finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    ISystemAddition addToSystem(SinkRoleCreator role);

    /**
     * Adds a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} to
     * the system. The creator will be turned into the finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.
     */
    ISystemAddition addToSystem(SourceRoleCreator role);

    /**
     * Adds an
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} to the system. The creator will be turned into
     * the finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    ISystemAddition addToSystem(InfrastructureRequiredRoleCreator role);

    /**
     * Adds an
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} to the system. The creator will be turned into
     * the finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    ISystemAddition addToSystem(InfrastructureProvidedRoleCreator role);

    /**
     * Adds {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     * QoSAnnotations} to the system. The creator will be turned into the finished
     * connector.
     *
     * @param annotations
     * @return this system
     * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     */
    ISystemAddition addToSystem(QoSAnnotationsCreator annotations);

    /**
     * Adds a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} to the system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this system
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    ISystemAddition addToSystem(ResourceRequiredRoleCreator role);

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * ResourceRequiredDelegationConnector} to the system. The creator will be
     * turned into the finished connector.
     *
     * @param connector
     * @return this system
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     */
    ISystemAddition addToSystem(ResourceRequiredDelegationConnectorCreator connector);

}
