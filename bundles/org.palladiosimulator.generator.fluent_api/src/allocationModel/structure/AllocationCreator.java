package allocationModel.structure;

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

import allocationModel.apiControlFlowInterfaces.IAllocation;
import allocationModel.apiControlFlowInterfaces.IAllocationAddition;

public class AllocationCreator extends AllocationEntity implements IAllocation{
	private List<AllocationContext> allocationContexts = new ArrayList<>();
	private ResourceEnvironment resourceEnvironment;
	private System system;

	@Override
	protected Allocation build() {
		Allocation allocation = AllocationFactory.eINSTANCE.createAllocation();
		if (this.name != null) {
			allocation.setEntityName(name);
		}
		allocation.getAllocationContexts_Allocation().addAll(allocationContexts);
		allocation.setTargetResourceEnvironment_Allocation(resourceEnvironment);
		allocation.setSystem_Allocation(system);
		return allocation;
	}
	
	@Override 
	public AllocationCreator withName(String name) {
		return (AllocationCreator) super.withName(name);
	}

	@Override
	public Allocation createAllocationNow() {
		Allocation allocation = build();
		return allocation;
	}

	@Override
	public IAllocationAddition withResourceEnvironment(ResourceEnvironment environment) {
		this.resourceEnvironment = environment;
		return this;
	}

	@Override
	public IAllocationAddition withSystem(System system) {
		this.system = system;
		return this;
	}

	@Override
	public IAllocationAddition addToAllocation(AllocationContextCreator allocationContext) {
		this.allocationContexts.add(allocationContext.build());
		return this;
	}

	public AssemblyContext getAssemblyContextByName(String name) {
		return system.getAssemblyContexts__ComposedStructure().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
	}
	
	public EventChannel getEventChannelByName(String name) {
		return system.getEventChannel__ComposedStructure().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
	}
	
	public ResourceContainer getResourceContainerByName(String name) {
		return resourceEnvironment.getResourceContainer_ResourceEnvironment().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
	}
}
