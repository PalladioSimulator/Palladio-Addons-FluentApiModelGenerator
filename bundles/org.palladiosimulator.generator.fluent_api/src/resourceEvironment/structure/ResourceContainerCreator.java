package resourceEvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

public class ResourceContainerCreator extends ResourceEntity {
	private List<ProcessingResourceSpecification> processingResourceSpecifications = new ArrayList<>();
	private List<HDDProcessingResourceSpecification> hddProcessingResourceSpecifications = new ArrayList<>();
	private List<ResourceContainer> nestedResourceContainers = new ArrayList<>();
	
	public ResourceContainerCreator(ResourceEnvironmentCreator resourceCreator) {
		this.resourceCreator = resourceCreator;
	}
	
	public ResourceContainerCreator addProcessingResourceSpecification(ProcessingResourceSpecificationCreator processingResourceSpecification) {
		processingResourceSpecifications.add(processingResourceSpecification.build());
		return this;
	}
	
	public ResourceContainerCreator addHddProcessingResourceSpecification(HddProcessingResourceSpecificationCreator hddProcessingResourceSpecification) {
		hddProcessingResourceSpecifications.add(hddProcessingResourceSpecification.build());
		return this;
	}
	
	public ResourceContainerCreator addNestedResourceContainer(ResourceContainerCreator resourceContainer) {
		nestedResourceContainers.add(resourceContainer.build());
		return this;
	}
	
	@Override
	public ResourceContainerCreator withName(String name) {
		return (ResourceContainerCreator) super.withName(name);
	}

	@Override
	protected ResourceContainer build() {
		ResourceContainer container = ResourceenvironmentFactory.eINSTANCE.createResourceContainer();
		if (this.name != null) {
			container.setEntityName(name);
		}
		container.getActiveResourceSpecifications_ResourceContainer().addAll(processingResourceSpecifications);
		container.getActiveResourceSpecifications_ResourceContainer().addAll(hddProcessingResourceSpecifications);
		container.getHddResourceSpecifications().addAll(hddProcessingResourceSpecifications);
		container.getNestedResourceContainers__ResourceContainer().addAll(nestedResourceContainers);
		return container;
	}
}
