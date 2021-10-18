package org.palladiosimulator.generator.fluent.resourceenvironment.example;

import org.palladiosimulator.generator.fluent.resourceenvironment.factory.FluentResourceEnvironmentFactory;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.structure.SchedulingPolicies;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * TODO
 */
public class SimplifiedMediaStore {

    public static void main(String[] args) {
        simplifiedMediaStoreResourceEnvironment();
    }

    public static void simplifiedMediaStoreResourceEnvironment() {
        final FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
        final ResourceEnvironment resourceEnvironment = create.newResourceEnvironment()
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
