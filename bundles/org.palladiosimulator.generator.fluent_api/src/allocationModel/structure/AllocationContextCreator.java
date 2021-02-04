package allocationModel.structure;

import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.allocation.AllocationFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;

public class AllocationContextCreator extends AllocationEntity {
	private AssemblyContext assemblyContext;
	private EventChannel eventChannel;
	private ResourceContainer resourceContainer;
	
	public AllocationContextCreator(AllocationCreator allocationCreator) {
		this.allocationCreator = allocationCreator;
	}
	
	public AllocationContextCreator withAssemblyContext(AssemblyContext context) {
		this.assemblyContext = context;
		return this;
	}
	
	public AllocationContextCreator withAssemblyContext(String name) {
		return withAssemblyContext(this.allocationCreator.getAssemblyContextByName(name));
	}
	
	public AllocationContextCreator withEventChannel(EventChannel channel) {
		this.eventChannel = channel;
		return this;
	}
	
	public AllocationContextCreator withEventChannel(String name) {
		return withEventChannel(this.allocationCreator.getEventChannelByName(name));
	}
	
	public AllocationContextCreator withResourceContainer(ResourceContainer container) {
		this.resourceContainer = container;
		return this;
	}
	
	public AllocationContextCreator withResourceContainer(String name) {
		return withResourceContainer(this.allocationCreator.getResourceContainerByName(name));
	}

	@Override
	protected AllocationContext build() {
		AllocationContext context = AllocationFactory.eINSTANCE.createAllocationContext();
		if (this.name != null) {
			context.setEntityName(name);
		}
		context.setAssemblyContext_AllocationContext(assemblyContext);
		context.setEventChannel__AllocationContext(eventChannel);
		context.setResourceContainer_AllocationContext(resourceContainer);
		return context;
	}
	
	@Override
	public AllocationContextCreator withName(String name) {
		return (AllocationContextCreator) super.withName(name);
	}

}
