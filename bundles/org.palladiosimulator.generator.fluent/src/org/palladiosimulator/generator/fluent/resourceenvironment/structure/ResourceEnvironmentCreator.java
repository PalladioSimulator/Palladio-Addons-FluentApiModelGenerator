package org.palladiosimulator.generator.fluent.resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.resourceenvironment.api.IResourceEnvironment;
import org.palladiosimulator.generator.fluent.resourceenvironment.api.IResourceEnvironmentAddition;
import org.palladiosimulator.generator.fluent.shared.structure.CommunicationLinkResource;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.structure.SchedulingPolicies;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * ResourceEnvironment}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 */
public class ResourceEnvironmentCreator extends ResourceEntity implements IResourceEnvironment {
    private final IModelValidator validator;
    private final ResourceRepository resources;

    private final List<ResourceContainer> resourceContainers = new ArrayList<>();
    private final List<LinkingResource> linkingResources = new ArrayList<>();

    public ResourceEnvironmentCreator(final ResourceRepository resources, final IModelValidator validator) {
        this.resources = resources;
        this.validator = validator;
    }

    @Override
    public ResourceEnvironmentCreator withName(final String name) {
        return (ResourceEnvironmentCreator) super.withName(name);

    }

    @Override
    public ResourceEnvironment createResourceEnvironmentNow() {
        final ResourceEnvironment environment = this.build();
        this.validator.validate(environment, this.name);
        return environment;
    }

    @Override
    protected ResourceEnvironment build() {
        final ResourceEnvironment environment = ResourceenvironmentFactory.eINSTANCE.createResourceEnvironment();
        if (this.name != null) {
            environment.setEntityName(this.name);
        }
        environment.getResourceContainer_ResourceEnvironment()
            .addAll(this.resourceContainers);
        environment.getLinkingResources__ResourceEnvironment()
            .addAll(this.linkingResources);
        return environment;
    }

    @Override
    public IResourceEnvironmentAddition addToResourceEnvironment(final ResourceContainerCreator resourceContainer) {
        IllegalArgumentException.throwIfNull(resourceContainer, "The given ResourceContainer must not be null");
        this.resourceContainers.add(resourceContainer.build());
        return this;
    }

    @Override
    public IResourceEnvironmentAddition addToResourceEnvironment(final LinkingResourceCreator linkingResource) {
        IllegalArgumentException.throwIfNull(linkingResource, "The given LinkingResource must not be null");
        this.linkingResources.add(linkingResource.build());
        return this;
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourcetype.SchedulingPolicy SchedulingPolicy}
     * matching the given policy.
     *
     * @param policy
     * @return the matching <code>SchedulingPolicy</code>
     */
    public SchedulingPolicy getSchedulingPolicy(final SchedulingPolicies policy) {
        IllegalArgumentException.throwIfNull(policy, "The given SchedulingPolicy must not be null");
        return this.resources.getSchedulingPolicies__ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(policy.getPolicyName()))
            .findFirst()
            .get();
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourcetype.ProcessingResourceType
     * ProcessingResourceType} matching the given resource.
     *
     * @param resource
     * @return the matching <code>ProcessingResourceType</code>
     */
    public ProcessingResourceType getProcessingResource(final ProcessingResource resource) {
        IllegalArgumentException.throwIfNull(resource, "The given ProcessignResource must not be null");
        return (ProcessingResourceType) this.resources.getAvailableResourceTypes_ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType
     * CommunicationLinkResourceType} matching the given resource.
     *
     * @param resource
     * @return the matching <code>CommunicationLinkResourceType</code>
     */
    public CommunicationLinkResourceType getCommunicationLinkResource(final CommunicationLinkResource resource) {
        IllegalArgumentException.throwIfNull(resource, "The given CommunicationLinkResource must not be null");
        return (CommunicationLinkResourceType) this.resources.getAvailableResourceTypes_ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContaienr} with the given name.
     *
     * @param name
     * @return the <code>ResourceContainer</code>
     * @throws IllegalArgumentException
     *             Thrown if no <code>ResourceContainer</code> has the given name.
     */
    public ResourceContainer getResourceContainerByName(final String name) throws IllegalArgumentException {
        return this.resourceContainers.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No ResourceContainer with name " + name + " found"));
    }
}
