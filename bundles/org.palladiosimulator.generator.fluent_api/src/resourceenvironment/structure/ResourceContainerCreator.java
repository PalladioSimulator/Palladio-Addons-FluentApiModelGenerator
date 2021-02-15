package resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

public class ResourceContainerCreator extends ResourceEntity {
    private final List<ProcessingResourceSpecification> processingResourceSpecifications = new ArrayList<>();
    private final List<HDDProcessingResourceSpecification> hddProcessingResourceSpecifications = new ArrayList<>();
    private final List<ResourceContainer> nestedResourceContainers = new ArrayList<>();

    public ResourceContainerCreator(final ResourceEnvironmentCreator resourceCreator) {
        this.resourceCreator = resourceCreator;
    }

    public ResourceContainerCreator addProcessingResourceSpecification(
            final ProcessingResourceSpecificationCreator processingResourceSpecification) {
        this.processingResourceSpecifications.add(processingResourceSpecification.build());
        return this;
    }

    public ResourceContainerCreator addHddProcessingResourceSpecification(
            final HddProcessingResourceSpecificationCreator hddProcessingResourceSpecification) {
        this.hddProcessingResourceSpecifications.add(hddProcessingResourceSpecification.build());
        return this;
    }

    public ResourceContainerCreator addNestedResourceContainer(final ResourceContainerCreator resourceContainer) {
        this.nestedResourceContainers.add(resourceContainer.build());
        return this;
    }

    @Override
    public ResourceContainerCreator withName(final String name) {
        return (ResourceContainerCreator) super.withName(name);
    }

    @Override
    protected ResourceContainer build() {
        final ResourceContainer container = ResourceenvironmentFactory.eINSTANCE.createResourceContainer();
        if (this.name != null) {
            container.setEntityName(this.name);
        }
        container.getActiveResourceSpecifications_ResourceContainer()
            .addAll(this.processingResourceSpecifications);
        container.getActiveResourceSpecifications_ResourceContainer()
            .addAll(this.hddProcessingResourceSpecifications);
        container.getHddResourceSpecifications()
            .addAll(this.hddProcessingResourceSpecifications);
        container.getNestedResourceContainers__ResourceContainer()
            .addAll(this.nestedResourceContainers);
        return container;
    }
}
