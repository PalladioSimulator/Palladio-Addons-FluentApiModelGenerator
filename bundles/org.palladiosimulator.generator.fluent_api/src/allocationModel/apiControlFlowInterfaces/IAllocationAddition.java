package allocationModel.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;

import allocationModel.structure.AllocationContextCreator;

public interface IAllocationAddition {
	Allocation createAllocationNow();
	
	IAllocationAddition withResourceEnvironment(ResourceEnvironment environment);
	
	IAllocationAddition withSystem(System system);
	
	IAllocationAddition addToAllocation(AllocationContextCreator allocationContext);
}
