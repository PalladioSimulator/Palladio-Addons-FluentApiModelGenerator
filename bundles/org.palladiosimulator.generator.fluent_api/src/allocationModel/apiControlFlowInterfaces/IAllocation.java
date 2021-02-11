package allocationModel.apiControlFlowInterfaces;


public interface IAllocation extends IAllocationAddition {
	/**
	 * Defines the name of the allocation
	 * 
	 * @param name
	 * @return this allocation
	 */
	IAllocation withName(String name);
}
