package main;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationFactory;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;
import org.palladiosimulator.pcm.system.SystemFactory;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;


public class Create {
	
	public static void main(String[] args) {
		new Create().run();
	}

	/**
	 * Helper method. Loads the primitive types repository model from the predefined primitive types repositoryy model loaded as a pathmap model.
	 * @return the primitive types repository.
	 */
	private Repository loadPrimitiveTypesRepository() {
		RepositoryPackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}
	
	/**
	 * Helper method. Loads the resource repository model from the predefined resource repository model loaded as a pathmap model.
	 * @return the resource repository.
	 */
	private ResourceRepository loadResourceTypeRepository() {
		ResourcetypePackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/Palladio.resourcetype"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		ResourceRepository repository = (ResourceRepository) resource.getContents().get(0);
		return repository;
	}
	/**
	 * The run method contains the logic executed when performing the action.
	 */
	public void run() {
		// Retrieve the primitive types repository for later use
		Repository primitiveTypesRepository = loadPrimitiveTypesRepository();
		
		// Retrieve the resource repository, resources, and schedulingPolicies for later use
		ResourceRepository resourceRepository = loadResourceTypeRepository();
		
		ProcessingResourceType cpuResourceType = null;
		ProcessingResourceType diskResourceType = null;
		for (ResourceType resourceType : resourceRepository.getAvailableResourceTypes_ResourceRepository()) {
			if (resourceType instanceof ProcessingResourceType) {
				if (resourceType.getEntityName().toLowerCase().equals("cpu")) {
					cpuResourceType = (ProcessingResourceType) resourceType;
				} else if (resourceType.getEntityName().toLowerCase().equals("hdd")) {
					diskResourceType = (ProcessingResourceType) resourceType;
				}
			}
			
		}
		SchedulingPolicy procSharingSchedPolicy = null;
		for (SchedulingPolicy schedPolicy : resourceRepository.getSchedulingPolicies__ResourceRepository()) {
			if (schedPolicy.getId().equals("ProcessorSharing")) {
				procSharingSchedPolicy = schedPolicy;
				break;
			}
		}
		
		// Create new stub elements (repository, system, allocation, etc)
		Repository repository = RepositoryFactory.eINSTANCE.createRepository();
		repository.setEntityName("WorkloadRepository");
		org.palladiosimulator.pcm.system.System system = SystemFactory.eINSTANCE.createSystem();
		system.setEntityName("WorkloadSystem");
		ResourceEnvironment resourceEnvironment = ResourceenvironmentFactory.eINSTANCE.createResourceEnvironment();
		resourceEnvironment.setEntityName("WorkloadResourecEnvironment");
		Allocation allocation = AllocationFactory.eINSTANCE.createAllocation();
		allocation.setSystem_Allocation(system);
		allocation.setTargetResourceEnvironment_Allocation(resourceEnvironment);
		allocation.setEntityName("WorkloadAllocation");
		UsageModel usageModel = UsagemodelFactory.eINSTANCE.createUsageModel();
		
	}
	

}