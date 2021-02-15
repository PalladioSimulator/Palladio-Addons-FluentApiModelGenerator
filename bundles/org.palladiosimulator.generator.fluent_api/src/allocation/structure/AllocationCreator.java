package allocation.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.allocation.AllocationFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;

import allocation.apiControlFlowInterfaces.IAllocation;
import allocation.apiControlFlowInterfaces.IAllocationAddition;
import shared.validate.IModelValidator;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.allocation.Allocation Allocation}.
 * First, the <code>System</code> and <code>ResoruceEnvironment</code> have to be defined.
 * Afterwards <code>AllocationContext</code>s can be added.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.allocation.Allocation
 */
public class AllocationCreator extends AllocationEntity implements IAllocation {
    private final IModelValidator validator;

    private final List<AllocationContext> allocationContexts = new ArrayList<>();
    private ResourceEnvironment resourceEnvironment;
    private System system;

    public AllocationCreator(final IModelValidator validator) {
        this.validator = validator;
    }

    @Override
    protected Allocation build() {
        final Allocation allocation = AllocationFactory.eINSTANCE.createAllocation();
        if (this.name != null) {
            allocation.setEntityName(this.name);
        }
        allocation.getAllocationContexts_Allocation()
            .addAll(this.allocationContexts);
        allocation.setTargetResourceEnvironment_Allocation(this.resourceEnvironment);
        allocation.setSystem_Allocation(this.system);
        return allocation;
    }

    @Override
    public AllocationCreator withName(final String name) {
        return (AllocationCreator) super.withName(name);
    }

    @Override
    public Allocation createAllocationNow() {
        final Allocation allocation = this.build();
        this.validator.validate(allocation, this.name);
        return allocation;
    }

    @Override
    public IAllocationAddition withResourceEnvironment(final ResourceEnvironment environment) {
        this.resourceEnvironment = environment;
        return this;
    }

    @Override
    public IAllocationAddition withSystem(final System system) {
        this.system = system;
        return this;
    }

    @Override
    public IAllocationAddition addToAllocation(final AllocationContextCreator allocationContext) {
        this.allocationContexts.add(allocationContext.build());
        return this;
    }

    /**
     * Searches the defined <code>System</code> for an <code>AssemblyContext</code> with the given
     * name.
     *
     * @param name
     * @return the <code>AssemblyContext</code> with the given name
     */
    public AssemblyContext getAssemblyContextByName(final String name) {
        return this.system.getAssemblyContexts__ComposedStructure()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
    }

    /**
     * Searches the defined <code>System</code> for an <code>EventChannel</code> with the given
     * name.
     *
     * @param name
     * @return the <code>EventChannel</code> with the given name
     */
    public EventChannel getEventChannelByName(final String name) {
        return this.system.getEventChannel__ComposedStructure()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
    }

    /**
     * Searches the defined <code>ResourceEnvironment</code> for a <code>ResourceContainer</code>
     * with the given name.
     *
     * @param name
     * @return the <code>ResourceContainer</code> with the given name
     */
    public ResourceContainer getResourceContainerByName(final String name) {
        return this.resourceEnvironment.getResourceContainer_ResourceEnvironment()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
    }
}
