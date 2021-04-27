package allocation.examples;

import org.palladiosimulator.pcm.allocation.Allocation;

import allocation.factory.FluentAllocationFactory;
import shared.util.ModelLoader;
import shared.util.ModelSaver;

public class SimplifiedMediaStore {

    public static void main(String[] args) {
        simplifiedMediaStoreAllocation();
    }

    public static void simplifiedMediaStoreAllocation() {
        final FluentAllocationFactory create = new FluentAllocationFactory();
        final Allocation allocation = create.newAllocation().withName("SimplifiedMediaStore Allocation")
                .withSystem(ModelLoader.loadSystem("./simplifiedMediaStore.system"))
                .withResourceEnvironment(
                        ModelLoader.loadResourceEnvironment("./simplifiedMediaStore.resourceenvironment"))
                .addToAllocation(create.newAllocationContext().withName("WebGUI Allocation")
                        .withAssemblyContext("WebGUI Component").withResourceContainer("resource container"))
                .addToAllocation(create.newAllocationContext().withName("MediaStore Allocation")
                        .withAssemblyContext("MediaStore Component").withResourceContainer("resource container"))
                .addToAllocation(create.newAllocationContext().withName("DigitalWatermarking Allocation")
                        .withAssemblyContext("DigitalWatermarking Component")
                        .withResourceContainer("resource container"))
                .addToAllocation(create.newAllocationContext().withName("DBAdapter Allocation")
                        .withAssemblyContext("DBAdapter Component").withResourceContainer("resource container"))
                .addToAllocation(create.newAllocationContext().withName("AudioDB Allocation")
                        .withAssemblyContext("AudioDB Component").withResourceContainer("resource container"))
                .createAllocationNow();
        ModelSaver.saveAllocation(allocation, "./simplifiedMediaStore", true);
    }
}
