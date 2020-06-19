
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
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;

import factory.MyRepositoryFactory;

public class Main {

	public static void main(String[] args) {

		MyRepositoryFactory create = new MyRepositoryFactory();

		Repository repo = create.newRepository()
			.withName("defaultRepository")
			.withDescription("This is my PCM model.")
			.withId("abc123")
			
			.addToRepository(create.newOperationInterface()
									.withName("IDatabase")
									.withRequiredCharacterisation(create.newRequiredCharacterisation()
																	.withParameter(create.getParameter(""))
																	.withType(VariableCharacterisationType.VALUE))
//									.withRequiredCharacterisation()
//										.withParameter(create.getParameter(""))
//										.withType(VariableCharacterisationType.TYPE_VALUE)
									.withOperationSignature(create.newOperationSignature()
															.withName("saveDatabaseEntry")
															.withParameter(create.newParameter()
																				.withName("name"))
															.withParameter(create.newParameter().withName("age")
																	.ofDataType(null))))
			.addToRepository(create.newBasicComponent()
					.withName("Web")
					.requires(create.newOperationInterface()
									.withName("HelloWorld"))
					)
			.addToRepository(create.newBasicComponent()
				.withName("Database")
				.withId("comp1")
				.handles(create.newEventGroup()
								.withName("hallo"))
				.provides(create.getOperationInterfaceByName("IDatabase"))
			)
			
			
			.addToRepository(create.newBasicComponent()
				.withName("Web2")
//				.provide(creator.newOperationInterface()
//				    .withName("IDatabase2")
//				    .withId("face2")
//			    )
			)
			
//			.conect("Web", creator.getByUUID("comp1"))
	
			.build();
		
		saveRepository(repo);	
	}
	
	public static void saveRepository(Repository repo) {
		String outputFile = "/Users/louisalambrecht/Documents/eclipse-workspace/FluentAPICreation/myrepo.repository";
		String[] fileExtensions = new String[] { "repository", "xml" };

		// Create File
		ResourceSet rs = new ResourceSetImpl();
		for (String fileext : fileExtensions)
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());

		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

		// Put content to file resource
		resource.getContents().add(repo);

		// Save file
		((XMLResource) resource).setEncoding("UTF-8");
		Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());

		try {
			resource.save(saveOptions);
			// print
			((XMLResource) resource).save(System.out, ((XMLResource) resource).getDefaultSaveOptions());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
