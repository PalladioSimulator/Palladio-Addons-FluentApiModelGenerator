package allocationModel.factory;


import java.util.logging.Level;
import java.util.logging.Logger;

import allocationModel.apiControlFlowInterfaces.IAllocation;
import allocationModel.structure.AllocationContextCreator;
import allocationModel.structure.AllocationCreator;
import shared.validate.IModelValidator;
import shared.validate.ModelValidator;

public class FluentAllocationFactory {
	private AllocationCreator allocationCreator;
	
	public IAllocation newSystem() {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		IModelValidator validator = new ModelValidator(logger);
		allocationCreator = new AllocationCreator(validator);
		return allocationCreator;
	}

	public AllocationContextCreator newAllocationContext() {
		return new AllocationContextCreator(allocationCreator);
	}
}
