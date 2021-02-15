package resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import resourceenvironment.apiControlFlowInterfaces.IResourceEnvironment;
import resourceenvironment.apiControlFlowInterfaces.IResourceEnvironmentAddition;
import shared.structure.CommunicationLinkResource;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;
import shared.validate.IModelValidator;

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
        this.resourceContainers.add(resourceContainer.build());
        return this;
    }

    @Override
    public IResourceEnvironmentAddition addToResourceEnvironment(final LinkingResourceCreator linkingResource) {
        this.linkingResources.add(linkingResource.build());
        return this;
    }

    public SchedulingPolicy getSchedulingPolicy(final SchedulingPolicies policy) {
        return this.resources.getSchedulingPolicies__ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(policy.getPolicyName()))
            .findFirst()
            .get();
    }

    public ProcessingResourceType getProcessingResource(final ProcessingResource resource) {
        return (ProcessingResourceType) this.resources.getAvailableResourceTypes_ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }

    public CommunicationLinkResourceType getCommunicationLinkResource(final CommunicationLinkResource resource) {
        return (CommunicationLinkResourceType) this.resources.getAvailableResourceTypes_ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }

    public List<ResourceContainer> getResourceContainers() {
        return this.resourceContainers;
    }
}
