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
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.Repository;

import factory.MyRepositoryFactory;
import repositoryStructure.datatypes.Failure;
import repositoryStructure.datatypes.Primitive;

public class Main {

	public static void main(String[] args) {

		MyRepositoryFactory create = new MyRepositoryFactory();

		Repository repo = create.newRepository()
			.withName("defaultRepository")
			.withDescription("This is my PCM model.")
			.withId("abc123")
			
			//DATATYPES
			.addToRepository(create.newCollectionDataType("StringList", Primitive.STRING))
			
			.addToRepository(create.newCompositeDataType("Person")
					.withInnerDeclaration("first names", create.fetchOfDataType("StringList"))
					.withInnerDeclaration("age", Primitive.INTEGER))
			
			// INTERFACES
			.addToRepository(create.newOperationInterface()
							.withName("IDatabase")
							.withOperationSignature()
								.withName("saveDatabaseEntry")
								.withParameter("first names", create.fetchOfDataType("StringList"), null)
								.withParameter("age", Primitive.INTEGER, ParameterModifier.INOUT)
								.withReturnType(create.fetchOfDataType("Person"))
							.withRequiredCharacterisation(create.getParameter("age"), VariableCharacterisationType.VALUE))
			
			// BASIC COMPONENTS
			.addToRepository(create.newBasicComponent()
				.withName("Database")
				.withId("comp1")
				.handles(create.newEventGroup()
								.withName("hallo")
								.withEventType()
									.withName("type")
									.withParameter("foo", Primitive.BOOLEAN, null)
								.withRequiredCharacterisation(create.getParameter(""), VariableCharacterisationType.STRUCTURE))
				.withServiceEffectSpecification(create.newSeff().withStartAction().followedBy().externalCallAction().followedBy().stopAction().createSeffNow())
				.provides(create.fetchOfOperationInterface("IDatabase"), "provDB")
				.requires(create.newOperationInterface().withName("someInterface"), "reqSomeI")
				.withPassiveResource("2*3", create.fetchOfFailureType(Failure.SOFTWARE))
			)
						
			.addToRepository(create.newBasicComponent()
				.withName("Web2")
				.provides(create.newOperationInterface()
				    .withName("IDatabase2")
				    .withId("face2")
			    )
			)
			
			// COMPOSITE COMPONENTS
			.addToRepository(create.newCompositeComponent()
					.withName("Web")
					.requires(create.newOperationInterface()
							.withName("HelloWorld"))
					.withAssemblyContext(create.fetchOfComponent("Database"), "DBContext")
					.withAssemblyConnection(create.fetchOfProvidedRole("provDB"), create.fetchOfAssemblyContext("DBContext"), 
							create.fetchOfRequiredRole("reqSomeI"), create.fetchOfAssemblyContext("DBContext"))
					.withEventChannel().withEventGroup(create.fetchOfEventGroup("hallo"))
					)
			
//			.conect("Web", creator.getByUUID("comp1"))
	
			.build();
		
		
		
		create.newSeff().withStartAction()
						.followedBy().internalAction()
											.withResourceDemand(null, null)
											.withInternalFailureOccurrenceDescription(null, null)
											.withInfrastructureCall(null, null, null)
						.followedBy().externalCallAction()
											.withCalledService(null)
											.withFailureType(null)
											.withInputVariableUsage()
											.withRequiredRole(null)
											.withRetryCount(null)
											.withReturnVariableUsage()
						.followedBy().emitEventAction()
											.withEventType(null)
											.withSourceRole(null)
											.withInputVariableUsage()
						.followedBy().setVariableAction()
						.followedBy().acquireAction()
											.withPassiveResource(null)
											.withTimeoutValue(null)
											.isTimeout(null)
						.followedBy().releaseAction()
											.withPassiveResource(null)
						.followedBy().loopAction()
											.withIterationCount(null)
											.withLoopBody().startAction().withInfrastructureCall(null, null, null)
												.followedBy().internalAction().withInfrastructureCall(null, null, null)
												.followedBy().stopAction() //TODO: hier darf create seff now nicht gehen
					    .followedOutsideLoopBy().stopAction().createSeffNow();
		
		
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
