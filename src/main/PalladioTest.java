package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
class PalladioTest {

	public static void main(String[] args) {

//		PalladioTest t = new PalladioTest();
//		ResourceRepository resources = t.loadResourceTypeRepository();
//		Repository p = t.loadPrimitiveTypesRepository();
//		Repository f = t.loadFailureTypesRepository();
//		
		/*
		 * t.setupAndSaveEMFInstanceResource();
		 * 
		 * 
		 * // String fileName = "resources/FailureTypes.repository"; String fileName =
		 * "/Users/louisalambrecht/Documents/eclipse-workspace/PalladioTest/default.repository";
		 * 
		 * Resource repoResource = t.readPalladioRepository(fileName);
		 * 
		 * // Warum funktioniert das nur, wenn vorher setup and save EMF instance
		 * resource // nicht-statisch aufgerufen wurde? Das eine hat doch mit dem
		 * anderen nix zu // tun... if (repoResource.getContents().get(0) instanceof
		 * Repository) { Repository repo = (Repository)
		 * repoResource.getContents().get(0); BasicComponent dbComp = (BasicComponent)
		 * repo.getComponents__Repository().stream() .filter(a ->
		 * a.getEntityName().equals("Database")).findFirst().get(); PassiveResource
		 * passiveResource =
		 * dbComp.getPassiveResource_BasicComponent().stream().findFirst().get();
		 * PCMRandomVariable capacity = passiveResource.getCapacity_PassiveResource();
		 * System.out.println(capacity.getExpression());
		 * System.out.println(capacity.getSpecification());
		 * System.out.println(passiveResource.
		 * getResourceTimeoutFailureType__PassiveResource());
		 * System.out.println("Failure Types:");
		 * repo.getFailureTypes__Repository().stream().forEach(System.out::println); }
		 * 
		 */

	}

	public void setupAndSaveEMFInstanceResource() {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt
		// this to use your own file extension).
		Resource gastResource = createAndAddResource(
				"/Users/louisalambrecht/Documents/eclipse-workspace/FluentAPICreation/test.repository",
				new String[] { "repository", "gast", "xml" }, rs);
		// The root object is created by using (adapt this to create your own root
		// object)
		RepositoryFactory coreFactory = RepositoryFactory.eINSTANCE;
		Repository root = coreFactory.createRepository();

		BasicComponent c = coreFactory.createBasicComponent();
		c.setEntityName("Database");
		BasicComponent c1 = coreFactory.createBasicComponent();
		c1.setEntityName("Web");
		OperationInterface i = coreFactory.createOperationInterface();
		i.setEntityName("IDatabase");
		OperationProvidedRole provRoleDb = coreFactory.createOperationProvidedRole();
		provRoleDb.setProvidedInterface__OperationProvidedRole(i);
		provRoleDb.setProvidingEntity_ProvidedRole(c);

		root.getComponents__Repository().add(c);
		root.getComponents__Repository().add(c1);
		root.getInterfaces__Repository().add(i);

		gastResource.getContents().add(root);
		saveResource(gastResource);
		printResource(gastResource);
	}

	public static Resource createAndAddResource(String outputFile, String[] fileextensions, ResourceSet rs) {
		for (String fileext : fileextensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
		}
		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());
		return resource;
	}

	public static void saveResource(Resource resource) {
		((XMLResource) resource).setEncoding("UTF-8");
		Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());
		try {
			resource.save(saveOptions);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void printResource(Resource resource) {
		try {
			((XMLResource) resource).save(System.out, ((XMLResource) resource).getDefaultSaveOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Resource readPalladioRepository(String filePath) {
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> map = reg.getExtensionToFactoryMap();
		map.put("*", new XMIResourceFactoryImpl());
		final ResourceSet resSet = new ResourceSetImpl();
		resSet.setResourceFactoryRegistry(reg);
		URI uriInstance = URI.createFileURI(filePath);
		Resource resource = resSet.getResource(uriInstance, true);

		return resource;
	}

	public ResourceRepository loadResourceTypeRepository() {
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

	public Repository loadPrimitiveTypesRepository() {
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

	public Repository loadFailureTypesRepository() {
		RepositoryPackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/FailureTypes.repository"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}

}
