package allocationModel.examples;

import org.palladiosimulator.pcm.allocation.Allocation;

import allocationModel.factory.FluentAllocationFactory;
import shared.util.ModelLoader;
import shared.util.ModelSaver;

public class Example {

	public static void main(String[] args) {
		basicAllocation();
	}
	
	public static void basicAllocation() {
		FluentAllocationFactory create = new FluentAllocationFactory();
		Allocation allocation = create.newSystem()
				.withName("allocation")
				.withSystem(ModelLoader.loadSystem("./basicExample.system"))
				.withResourceEnvironment(ModelLoader.loadResourceEnvironment("./basicEnvironment.resourceenvironment"))
				.addToAllocation(create.newAllocationContext()
						.withName("context 1")
						.withResourceContainer("container 1")
						.withAssemblyContext("basic component context 1"))
				.addToAllocation(create.newAllocationContext()
						.withName("context 2")
						.withResourceContainer("container 2")
						.withEventChannel("event channel"))
				.addToAllocation(create.newAllocationContext()
						.withName("context 3")
						.withResourceContainer("container 2")
						.withAssemblyContext("basic component context 2"))
				.createAllocationNow();
		ModelSaver.saveAllocation(allocation, "./", "basicAllocation", true);
	}
}
