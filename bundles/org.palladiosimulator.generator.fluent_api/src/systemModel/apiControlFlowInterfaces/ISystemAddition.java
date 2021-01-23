package systemModel.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import systemModel.structure.AssemblyContextCreator;
import systemModel.structure.EventChannelCreator;
import systemModel.structure.connector.AbstractConnectorCreator;
import systemModel.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
import systemModel.structure.qosAnnotations.QoSAnnotationsCreator;
import systemModel.structure.systemRole.InfrastructureProvidedRoleCreator;
import systemModel.structure.systemRole.InfrastructureRequiredRoleCreator;
import systemModel.structure.systemRole.OperationProvidedRoleCreator;
import systemModel.structure.systemRole.OperationRequiredRoleCreator;
import systemModel.structure.systemRole.ResourceRequiredRoleCreator;
import systemModel.structure.systemRole.SinkRoleCreator;
import systemModel.structure.systemRole.SourceRoleCreator;

public interface ISystemAddition {

	/**
	 * Turns this system-in-the-making into a
	 * Palladio-'<em><b>System</b></em>' object.
	 * 
	 * @return the final system object
	 * @see org.palladiosimulator.pcm.system.System
	 */
	System createSystemNow();
	
	/**
	 * Adds a repository to the system. Components from added repositories can be added to the system by name.
	 * @param repository
	 * @return
	 */
	ISystem withRepository(Repository repository);
	
	/**
	 * Adds an AssemblyContext to the system.
	 * @param context
	 * @return
	 */
	ISystemAddition addToSystem(AssemblyContextCreator context);
	
	ISystemAddition addToSystem(AbstractConnectorCreator connector);
	
	/**
	 * Adds an OperationRequiredRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(OperationRequiredRoleCreator role);	

	/**
	 * Adds an OperationProvidedRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(OperationProvidedRoleCreator role);

	/**
	 * Adds an EventChannel to the system.
	 * @param eventChannel
	 * @return
	 */
	ISystemAddition addToSystem(EventChannelCreator eventChannel);

	/**
	 * Adds a SinkRole to the system
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(SinkRoleCreator role);
	
	/**
	 * Adds a SourceRole to the system
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(SourceRoleCreator role);
	
	/**
	 * Adds an InfrastructureRequiredRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(InfrastructureRequiredRoleCreator role);

	/**
	 * Adds an InfrastructureProvidedRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(InfrastructureProvidedRoleCreator role);
	
	/**
	 * Adds QoS annotations to the system.
	 * @param annotations
	 * @return
	 */
	ISystemAddition addToSystem(QoSAnnotationsCreator annotations);

	/**
	 * Adds a ResourceRequiredRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition addToSystem(ResourceRequiredRoleCreator role);
	
	/**
	 * Adds a ResourceRequiredDelegationConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition addToSystem(ResourceRequiredDelegationConnectorCreator connector);

}
