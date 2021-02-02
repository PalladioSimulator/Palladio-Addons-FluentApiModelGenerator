package resourceEvironment.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import resourceEvironment.apiControlFlowInterfaces.IResourceEnvironment;
import resourceEvironment.apiControlFlowInterfaces.IResourceEnvironmentAddition;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;

public class ResourceEnvironmentCreator extends ResourceEntity implements IResourceEnvironment {
	private ResourceRepository resources;
	private List<ResourceContainer> resourceContainers = new ArrayList<>();
	
	public ResourceEnvironmentCreator(ResourceRepository resources) {
		this.resources = resources;
	}

	@Override
	public ResourceEnvironmentCreator withName(String name) {
		return (ResourceEnvironmentCreator) super.withName(name);
		
	}

	@Override
	public ResourceEnvironment createResourceEnvironmentNow() {
		ResourceEnvironment environment = this.build();
		return environment;
	}

	@Override
	protected ResourceEnvironment build() {
		ResourceEnvironment environment = ResourceenvironmentFactory.eINSTANCE.createResourceEnvironment();
		if (this.name != null) {
			environment.setEntityName(name);
		}
		environment.getResourceContainer_ResourceEnvironment().addAll(resourceContainers);
		return environment;
	}

	@Override
	public IResourceEnvironmentAddition addToResourceEnvironment(ResourceContainerCreator resourceContainer) {
		resourceContainers.add(resourceContainer.build());
		return this;
	}
	
	public SchedulingPolicy getSchedulingPolicy(SchedulingPolicies policy) {
		return this.resources.getSchedulingPolicies__ResourceRepository().stream()
				.filter(x -> x.getEntityName().equals(policy.policyName)).findFirst().get();
	}
	
	public ProcessingResourceType getProcessingResource(ProcessingResource resource) {
		return (ProcessingResourceType) this.resources.getAvailableResourceTypes_ResourceRepository().stream()
				.filter(x -> x.getEntityName().equals(resource.resourceName)).findFirst().get();
	}
}
