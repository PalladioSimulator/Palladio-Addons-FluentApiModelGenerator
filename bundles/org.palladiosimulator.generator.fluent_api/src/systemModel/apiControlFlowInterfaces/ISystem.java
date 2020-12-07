package systemModel.apiControlFlowInterfaces;

public interface ISystem extends ISystemAddition {

	/**
	 * Defines the name of the system.
	 * 
	 * @param name
	 * @return this system
	 */
	ISystem withName(String name);
}
