package org.palladiosimulator.generator.fluent.allocation.api;

import org.palladiosimulator.generator.fluent.allocation.structure.AllocationContextCreator;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;

public interface IAllocationAddition {
    /**
     * Completes the org.palladiosimulator.generator.fluent.allocation creation.
     *
     * @return the created org.palladiosimulator.generator.fluent.allocation
     * @see org.palladiosimulator.pcm.allocation.Allocation
     */
    Allocation createAllocationNow();

    /**
     * Defines the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     * ResourceEnvironmet} used for the org.palladiosimulator.generator.fluent.allocation.
     *
     * @param environment
     * @return this org.palladiosimulator.generator.fluent.allocation
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    IAllocationAddition withResourceEnvironment(ResourceEnvironment environment);

    /**
     * Defines the {@link org.palladiosimulator.pcm.system.System System} used for the
     * org.palladiosimulator.generator.fluent.allocation.
     *
     * @param org.palladiosimulator.generator.fluent.system
     * @return this org.palladiosimulator.generator.fluent.allocation
     * @see org.palladiosimulator.pcm.system.System
     */
    IAllocationAddition withSystem(System system);

    /**
     * Adds an {@link org.palladiosimulator.pcm.allocation.AllocationContext AllocationContext} to
     * the org.palladiosimulator.generator.fluent.allocation. The creator will be turned into a
     * finished context.
     *
     * @param allocationContext
     * @return this org.palladiosimulator.generator.fluent.allocation
     * @see org.palladiosimulator.pcm.allocation.AllocationContext
     * @see org.palladiosimulator.generator.fluent.allocation.factory.FluentAllocationFactory#newAllocationContext()
     */
    IAllocationAddition addToAllocation(AllocationContextCreator allocationContext);
}
