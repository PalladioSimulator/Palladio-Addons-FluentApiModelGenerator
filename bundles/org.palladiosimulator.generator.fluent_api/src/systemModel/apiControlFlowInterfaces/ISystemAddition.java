package systemModel.apiControlFlowInterfaces;

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
}
