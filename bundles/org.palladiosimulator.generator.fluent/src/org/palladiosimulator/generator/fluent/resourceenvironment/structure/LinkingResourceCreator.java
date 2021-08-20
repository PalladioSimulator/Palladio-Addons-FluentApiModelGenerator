package org.palladiosimulator.generator.fluent.resourceenvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.structure.CommunicationLinkResource;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.CommunicationLinkResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource
 * LinkingResource}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
 */
public class LinkingResourceCreator extends ResourceEntity {

    private double failureProbability;
    private CommunicationLinkResourceType resourceType;
    private final List<ResourceContainer> linkedContainers = new ArrayList<>();
    private PCMRandomVariable latencyVariable;
    private PCMRandomVariable throughputVariable;

    public LinkingResourceCreator(final ResourceEnvironmentCreator resourceEnvironmentCreator) {
        this.resourceCreator = resourceEnvironmentCreator;
    }

    /**
     * Defines the failure probability of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}.
     *
     * @param failureProbability
     * @return this <code>LinkingResource</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     */
    public LinkingResourceCreator withFailureProbability(final double failureProbability) {
        this.failureProbability = failureProbability;
        return this;
    }

    /**
     * Defines the communication link resource of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}.
     *
     * @param resource
     * @return this <code>LinkingResource</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     */
    public LinkingResourceCreator withCommunicationLinkResource(final CommunicationLinkResource resource) {
        this.resourceType = this.resourceCreator.getCommunicationLinkResource(resource);
        return this;
    }

    /**
     * Adds a resource container to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}.
     *
     * @param container
     * @return this <code>LinkingResource</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public LinkingResourceCreator addLinkedResourceContainer(final ResourceContainer container) {
        IllegalArgumentException.throwIfNull(container, "The given ResourceContainer must not be null");
        this.linkedContainers.add(container);
        return this;
    }

    /**
     * Adds a resource container to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}. The
     * <code>ResourceEnvironment</code> is searched for a <code>ResourceContainer</code> with he
     * given name.
     *
     * @param name
     * @return this <code>LinkingResource</code>
     * @throws IllegalArgumentException
     *             Throw if no <code>ResourceContainer</code> with the given name exists.
     */
    public LinkingResourceCreator addLinkedResourceContainer(final String name) throws IllegalArgumentException {
        final ResourceContainer container = this.resourceCreator.getResourceContainerByName(name);
        this.linkedContainers.add(container);
        return this;
    }

    /**
     * Defines the latency of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}.
     *
     * @param latency
     * @return this <code>LinkingResource</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     */
    public LinkingResourceCreator withLatency(final String latency) {
        IllegalArgumentException.throwIfNull(latency, "the given latency must not be null");
        this.latencyVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.latencyVariable.setSpecification(latency);
        return this;
    }

    /**
     * Defines the throughput of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}.
     *
     * @param throughput
     * @return this <code>LinkingResource</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     */
    public LinkingResourceCreator withThroughput(final String throughput) {
        IllegalArgumentException.throwIfNull(throughput, "The given throughput must not be null");
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
