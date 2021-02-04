package resourceEvironment.example;

import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

import resourceEvironment.factory.FluentResourceEnvironmentFactory;
import shared.structure.CommunicationLinkResource;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;
import shared.util.ModelSaver;

public class Example {
	public static void main(String[] args) {
		basicResourceExample();
	}
	
	public static void basicResourceExample() {
		FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
		ResourceEnvironment environment = create.newResourceEnvironment()
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
								//.withProcessingRate("123")
								))
				.addToResourceEnvironment(create.newResourceContainer()
						.withName("container 2")
						.addHddProcessingResourceSpecification(create.newHddProcessingResourceSpecification()
								.withMttf(2)
								.withMttr(5.3)
								.withNumberOfReplicas(1)
								.withSchedulingPolicy(SchedulingPolicies.DELAY)
								.withProcessingResourceType(ProcessingResource.HDD)
								))
				.addToResourceEnvironment(create.newLinkingResource()
						.withName("linkin resource")
						.withCommunicationLinkResource(CommunicationLinkResource.LAN)
						.withFailureProbability(0.2)
						.addLinkedResourceContainer("container 1")
						.addLinkedResourceContainer("container 2"))
				.createResourceEnvironmentNow();
		ModelSaver.saveResourceEnvironment(environment, "./", "basicEnvironment", true);
	}
}
