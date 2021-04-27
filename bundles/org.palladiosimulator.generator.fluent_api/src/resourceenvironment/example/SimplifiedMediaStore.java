package resourceenvironment.example;

import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

import resourceenvironment.factory.FluentResourceEnvironmentFactory;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;
import shared.util.ModelSaver;

public class SimplifiedMediaStore {

    public static void main(String[] args) {
        simplifiedMediaStoreResourceEnvironment();
    }

    public static void simplifiedMediaStoreResourceEnvironment() {
        FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
        ResourceEnvironment resourceEnvironment = create.newResourceEnvironment()
                .withName("SimplifiedMediaStore ResourceEnvironment")
                .addToResourceEnvironment(create.newResourceContainer().withName("resource container")
                        .addProcessingResourceSpecification(create.newProcessingResourceSpecification()
                                .withProcessingResourceType(ProcessingResource.CPU)
                                .withSchedulingPolicy(SchedulingPolicies.PROCESS_SHARING).withProcessingRate("3.2GHz")
                                .isRequiredByContainer().withMttf(100).withMttr(5).withNumberOfReplicas(2))
                        .addHddProcessingResourceSpecification(create.newHddProcessingResourceSpecification()
                                .withProcessingResourceType(ProcessingResource.HDD)
                                .withSchedulingPolicy(SchedulingPolicies.FIRST_COME_FIRST_SERVE)
                                .withProcessingRate("6Gbit/s").withWriteProcessingRate("2Gbit/s")
                                .withReadProcessingRate("6Gbit/s").withMttf(500).withMttr(20).withNumberOfReplicas(1)))
                .createResourceEnvironmentNow();
        ModelSaver.saveResourceEnvironment(resourceEnvironment, "./simplifiedMediaStore", true);
    }
}
