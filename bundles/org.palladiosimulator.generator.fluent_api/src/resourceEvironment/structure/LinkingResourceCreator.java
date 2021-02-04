package resourceEvironment.structure;

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
	private List<ResourceContainer> linkedContainers = new ArrayList<>();
	private PCMRandomVariable latencyVariable;
	private PCMRandomVariable throughputVariable;

	public LinkingResourceCreator(ResourceEnvironmentCreator resourceEnvironmentCreator) {
		this.resourceCreator = resourceEnvironmentCreator;
	}
	
	public LinkingResourceCreator withFailureProbability(double failureProbability) {
		this.failureProbability = failureProbability;
		return this;
	}
	
	public LinkingResourceCreator withCommunicationLinkResource(CommunicationLinkResource resource) {
		this.resourceType = this.resourceCreator.getCommunicationLinkResource(resource);
		return this;
	}
	
	public LinkingResourceCreator addLinkedResourceContainer(ResourceContainer container) {
		this.linkedContainers.add(container);
		return this;
	}
	
	public LinkingResourceCreator addLinkedResourceContainer(String name) {
		ResourceContainer container = this.resourceCreator.getResourceContainers().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		this.linkedContainers.add(container);
		return this;
	}
	
	public LinkingResourceCreator withLatency(String latency) {
		latencyVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		latencyVariable.setSpecification(latency);
		return this;
	}
	
	public LinkingResourceCreator withThroughput(String throughput) {
		throughputVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		throughputVariable.setSpecification(throughput);
		return this;
	}
	
	@Override
	public LinkingResourceCreator withName(String name) {
		return (LinkingResourceCreator) super.withName(name);
	}
	
	@Override
	public LinkingResource build() {
		CommunicationLinkResourceSpecification resourceSpecification = ResourceenvironmentFactory.eINSTANCE.createCommunicationLinkResourceSpecification();
		resourceSpecification.setCommunicationLinkResourceType_CommunicationLinkResourceSpecification(resourceType);
		resourceSpecification.setFailureProbability(failureProbability);
		resourceSpecification.setLatency_CommunicationLinkResourceSpecification(latencyVariable);
		resourceSpecification.setThroughput_CommunicationLinkResourceSpecification(throughputVariable);
		LinkingResource resource = ResourceenvironmentFactory.eINSTANCE.createLinkingResource();
		if (this.name != null) {
			resource.setEntityName(name);
		}
		resource.setCommunicationLinkResourceSpecifications_LinkingResource(resourceSpecification);
		resource.getConnectedResourceContainers_LinkingResource().addAll(linkedContainers);
		return resource;
	}

}
