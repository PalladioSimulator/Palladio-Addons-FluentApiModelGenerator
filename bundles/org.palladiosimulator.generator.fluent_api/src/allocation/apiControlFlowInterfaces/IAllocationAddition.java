package allocation.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;

import allocation.structure.AllocationContextCreator;

public interface IAllocationAddition {
    /**
     * Completes the allocation creation.
     *
     * @return the created allocation
     * @see org.palladiosimulator.pcm.allocation.Allocation
     */
    Allocation createAllocationNow();

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     * ResourceEnvironmet} used for the allocation.
     *
     * @param environment
     * @return this allocation
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    IAllocationAddition withResourceEnvironment(ResourceEnvironment environment);

    /**
     * Defines the {@link org.palladiosimulator.pcm.system.System System} used for
     * the allocation.
     *
     * @param system
     * @return this allocation
     * @see org.palladiosimulator.pcm.system.System
     */
    IAllocationAddition withSystem(System system);

    /**
     * Adds an {@link org.palladiosimulator.pcm.allocation.AllocationContext
     * AllocationContext} to the allocation. The creator will be turned into a
     * finished context.
     *
     * @param allocationContext
     * @return this allocation
     * @see org.palladiosimulator.pcm.allocation.AllocationContext
     * @see allocation.factory.FluentAllocationFactory#newAllocationContext()
     */
    IAllocationAddition addToAllocation(AllocationContextCreator allocationContext);
}
