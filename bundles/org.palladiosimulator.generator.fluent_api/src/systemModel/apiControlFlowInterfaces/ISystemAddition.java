package systemModel.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
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
	 * Adds a repository to the system. Components from added repositories can be added to the system by name.
	 * @param repository
	 * @return
	 */
	ISystem withRepository(Repository repository);
}
