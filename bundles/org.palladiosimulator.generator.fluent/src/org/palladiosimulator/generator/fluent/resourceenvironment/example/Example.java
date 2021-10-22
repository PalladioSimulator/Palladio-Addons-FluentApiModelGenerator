package org.palladiosimulator.generator.fluent.resourceenvironment.example;

import org.palladiosimulator.generator.fluent.resourceenvironment.factory.FluentResourceEnvironmentFactory;
import org.palladiosimulator.generator.fluent.shared.structure.CommunicationLinkResource;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.structure.SchedulingPolicies;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * TODO
 */
public class Example {
    public static void main(final String[] args) {
        basicResourceExample();
    }

    public static void basicResourceExample() {
        final FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
        final ResourceEnvironment environment = create.newResourceEnvironment()
            .withName("resource environment")
            .addToResourceEnvironment(create.newResourceContainer()
                .withName("container 1")
                .addProcessingResourceSpecification(create.newProcessingResourceSpecification()
                    .withMttf(3)
                    .withMttr(4)
                    .isRequiredByContainer()
                    .withNumberOfReplicas(2)
                    .withSchedulingPolicy(SchedulingPolicies.FIRST_COME_FIRST_SERVE)
                    .withProcessingResourceType(ProcessingResource.CPU)
                    .withProcessingRate("42")))
            .addToResourceEnvironment(create.newResourceContainer()
                .withName("container 2")
                .addHddProcessingResourceSpecification(create.newHddProcessingResourceSpecification()
                    .withMttf(2)
                    .withMttr(5.3)
                    .withNumberOfReplicas(1)
                    .withSchedulingPolicy(SchedulingPolicies.DELAY)
                    .withProcessingResourceType(ProcessingResource.HDD)
                    .withProcessingRate("1")
                    .withReadProcessingRate("2")
                    .withWriteProcessingRate("3")))
            .addToResourceEnvironment(create.newLinkingResource()
                .withName("linkin resource")
                .withCommunicationLinkResource(CommunicationLinkResource.LAN)
                .withFailureProbability(0.2)
                .addLinkedResourceContainer("container 1")
                .addLinkedResourceContainer("container 2")
                .withLatency("10ms")
                .withThroughput("3"))
            .createResourceEnvironmentNow();
        ModelSaver.saveResourceEnvironment(environment, "./basicEnvironment", true);
    }
}
