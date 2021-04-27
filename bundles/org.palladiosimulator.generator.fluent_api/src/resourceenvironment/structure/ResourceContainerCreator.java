package resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
 * ResourceContainer}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
 */
public class ResourceContainerCreator extends ResourceEntity {
    private final List<ProcessingResourceSpecification> processingResourceSpecifications = new ArrayList<>();
    private final List<HDDProcessingResourceSpecification> hddProcessingResourceSpecifications = new ArrayList<>();
    private final List<ResourceContainer> nestedResourceContainers = new ArrayList<>();

    public ResourceContainerCreator(final ResourceEnvironmentCreator resourceCreator) {
        this.resourceCreator = resourceCreator;
    }

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification} to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @param processingResourceSpecification
     * @return this <code>ResourceContainer</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public ResourceContainerCreator addProcessingResourceSpecification(
            final ProcessingResourceSpecificationCreator processingResourceSpecification) {
        Objects.requireNonNull(processingResourceSpecification,
                "The given ProcessingResourceSpecification must not be null");
        this.processingResourceSpecifications.add(processingResourceSpecification.build());
        return this;
    }

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification} to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @param hddProcessingResourceSpecification
     * @return this <code>ResourceContainer</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public ResourceContainerCreator addHddProcessingResourceSpecification(
            final HddProcessingResourceSpecificationCreator hddProcessingResourceSpecification) {
        Objects.requireNonNull(hddProcessingResourceSpecification,
                "The given HddProcessingResourceSpecification must not be null");
        this.hddProcessingResourceSpecifications.add(hddProcessingResourceSpecification.build());
        return this;
    }

    /**
     * Adds a nested resource container to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @param resourceContainer
     * @return this <code>ResourceContainer</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public ResourceContainerCreator addNestedResourceContainer(final ResourceContainerCreator resourceContainer) {
        Objects.requireNonNull(resourceContainer, "The given ResourceContainer must not be null");
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
        container.getActiveResourceSpecifications_ResourceContainer().addAll(this.processingResourceSpecifications);
        container.getActiveResourceSpecifications_ResourceContainer().addAll(this.hddProcessingResourceSpecifications);
        container.getHddResourceSpecifications().addAll(this.hddProcessingResourceSpecifications);
        container.getNestedResourceContainers__ResourceContainer().addAll(this.nestedResourceContainers);
        return container;
    }
}
