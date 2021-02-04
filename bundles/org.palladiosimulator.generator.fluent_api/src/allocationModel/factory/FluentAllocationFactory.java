package allocationModel.factory;

import allocationModel.apiControlFlowInterfaces.IAllocation;
import allocationModel.structure.AllocationContextCreator;
import allocationModel.structure.AllocationCreator;

public class FluentAllocationFactory {
	private AllocationCreator allocationCreator;
	
	public IAllocation newSystem() {
		//Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		//logger.setLevel(Level.ALL);
		//IModelValidator validator = new ModelValidator(SystemValidator.INSTANCE, logger);
		allocationCreator = new AllocationCreator();
		return allocationCreator;
	}

	public AllocationContextCreator newAllocationContext() {
		return new AllocationContextCreator(allocationCreator);
	}
}
