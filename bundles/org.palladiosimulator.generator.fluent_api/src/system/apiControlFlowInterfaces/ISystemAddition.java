package system.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import system.structure.AssemblyContextCreator;
import system.structure.EventChannelCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
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
     * Turns this system-in-the-making into a Palladio-'<em><b>System</b></em>' object.
     *
     * @return the final system object
     * @see org.palladiosimulator.pcm.system.System
     */
    System createSystemNow();

    /**
     * Adds a repository to the system. Components from added repositories can be added to the
     * system by name.
     *
     * @param repository
     * @return
     */
    ISystem withRepository(Repository repository);

    /**
     * Adds an AssemblyContext to the system.
     *
     * @param context
     * @return
     */
    ISystemAddition addToSystem(AssemblyContextCreator context);

    ISystemAddition addToSystem(AbstractConnectorCreator connector);

    /**
     * Adds an OperationRequiredRole to the system.
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(OperationRequiredRoleCreator role);

    /**
     * Adds an OperationProvidedRole to the system.
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(OperationProvidedRoleCreator role);

    /**
     * Adds an EventChannel to the system.
     *
     * @param eventChannel
     * @return
     */
    ISystemAddition addToSystem(EventChannelCreator eventChannel);

    /**
     * Adds a SinkRole to the system
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(SinkRoleCreator role);

    /**
     * Adds a SourceRole to the system
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(SourceRoleCreator role);

    /**
     * Adds an InfrastructureRequiredRole to the system.
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(InfrastructureRequiredRoleCreator role);

    /**
     * Adds an InfrastructureProvidedRole to the system.
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(InfrastructureProvidedRoleCreator role);

    /**
     * Adds QoS annotations to the system.
     *
     * @param annotations
     * @return
     */
    ISystemAddition addToSystem(QoSAnnotationsCreator annotations);

    /**
     * Adds a ResourceRequiredRole to the system.
     *
     * @param role
     * @return
     */
    ISystemAddition addToSystem(ResourceRequiredRoleCreator role);

    /**
     * Adds a ResourceRequiredDelegationConnector to the system.
     *
     * @param connector
     * @return
     */
    ISystemAddition addToSystem(ResourceRequiredDelegationConnectorCreator connector);

}
