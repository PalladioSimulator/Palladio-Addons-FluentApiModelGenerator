package resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.CommunicationLinkResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;

import shared.structure.CommunicationLinkResource;

public class LinkingResourceCreator extends ResourceEntity {

    private double failureProbability;
    private CommunicationLinkResourceType resourceType;
    private final List<ResourceContainer> linkedContainers = new ArrayList<>();
    private PCMRandomVariable latencyVariable;
    private PCMRandomVariable throughputVariable;

    public LinkingResourceCreator(final ResourceEnvironmentCreator resourceEnvironmentCreator) {
        this.resourceCreator = resourceEnvironmentCreator;
    }

    public LinkingResourceCreator withFailureProbability(final double failureProbability) {
        this.failureProbability = failureProbability;
        return this;
    }

    public LinkingResourceCreator withCommunicationLinkResource(final CommunicationLinkResource resource) {
        this.resourceType = this.resourceCreator.getCommunicationLinkResource(resource);
        return this;
    }

    public LinkingResourceCreator addLinkedResourceContainer(final ResourceContainer container) {
        this.linkedContainers.add(container);
        return this;
    }

    public LinkingResourceCreator addLinkedResourceContainer(final String name) {
        final ResourceContainer container = this.resourceCreator.getResourceContainers()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        this.linkedContainers.add(container);
        return this;
    }

    public LinkingResourceCreator withLatency(final String latency) {
        this.latencyVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.latencyVariable.setSpecification(latency);
        return this;
    }

    public LinkingResourceCreator withThroughput(final String throughput) {
        this.throughputVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.throughputVariable.setSpecification(throughput);
        return this;
    }

    @Override
    public LinkingResourceCreator withName(final String name) {
        return (LinkingResourceCreator) super.withName(name);
    }

    @Override
    public LinkingResource build() {
        final CommunicationLinkResourceSpecification resourceSpecification = ResourceenvironmentFactory.eINSTANCE
            .createCommunicationLinkResourceSpecification();
        resourceSpecification
            .setCommunicationLinkResourceType_CommunicationLinkResourceSpecification(this.resourceType);
        resourceSpecification.setFailureProbability(this.failureProbability);
        resourceSpecification.setLatency_CommunicationLinkResourceSpecification(this.latencyVariable);
        resourceSpecification.setThroughput_CommunicationLinkResourceSpecification(this.throughputVariable);
        final LinkingResource resource = ResourceenvironmentFactory.eINSTANCE.createLinkingResource();
        if (this.name != null) {
            resource.setEntityName(this.name);
        }
        resource.setCommunicationLinkResourceSpecifications_LinkingResource(resourceSpecification);
        resource.getConnectedResourceContainers_LinkingResource()
            .addAll(this.linkedContainers);
        return resource;
    }

}
