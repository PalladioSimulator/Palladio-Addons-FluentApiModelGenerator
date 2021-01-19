package systemModel.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

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
	ISystemAddition withAssembyContext(AssemblyContext context);
	
	/**
	 * Adds an AssemblyConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition withAssemblyConnector(AssemblyConnector connector);
	
	/**
	 * Adds an OperationRequiredRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition withOperationRequiredRole(OperationRequiredRole role);
	
	/**
	 * Adds a RequiredDelegationConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition withRequiredDelegationConnector(RequiredDelegationConnector connector);
	

	/**
	 * Adds an OperationProvidedRole to the system. 
	 * @param role
	 * @return
	 */
	ISystemAddition withOperationProvidedRole(OperationProvidedRole role);

	/**
	 * Adds a ProvidedDelegationConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition withProvidedDelegationConnector(ProvidedDelegationConnector connector);

	/**
	 * Adds an EventChannel to the system.
	 * @param eventChannel
	 * @return
	 */
	ISystemAddition withEventChannel(EventChannel eventChannel);
	
	/**
	 * Adds an EventChannelSinkRoleConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition withEventChannelSinkRoleConnector(EventChannelSinkConnector connector);

	/**
	 * Adds an EventChannelSourceRoleConnector to the system.
	 * @param connector
	 * @return
	 */
	ISystemAddition withEventChannelSourceRoleConnector(EventChannelSourceConnector connector);



}
