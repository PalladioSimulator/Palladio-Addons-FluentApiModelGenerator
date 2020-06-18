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

public class PalladioTest {

	public static void main(String[] args) {
		new PalladioTest().setupAndSaveEMFInstanceResource();
	}
	
	public void setupAndSaveEMFInstanceResource() {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt
		// this to use your own file extension).
		Resource gastResource = createAndAddResource("/Users/louisalambrecht/Documents/eclipse-workspace/FluentAPICreation/test.repository", new String[] { "repository", "gast", "xml" }, rs);
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
	
	public static Resource readPalladioRepository(String filePath) {
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> map = reg.getExtensionToFactoryMap();
		map.put("*", new XMIResourceFactoryImpl());
		final ResourceSet resSet = new ResourceSetImpl();
		resSet.setResourceFactoryRegistry(reg);
		URI uriInstance = URI.createFileURI(filePath);
		Resource resource = resSet.getResource(uriInstance, true);
		
		return resource;
	}
	
	
	

}
