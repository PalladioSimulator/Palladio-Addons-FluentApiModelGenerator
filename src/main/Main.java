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

		mediaStoreExample();
		nullExample();
		MyRepositoryFactory create = new MyRepositoryFactory();

		Repository repo = create.newRepository()
			.withName("defaultRepository")
			.withDescription("This is my PCM model.")
//			.withId("abc123")
			
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
								.withReturnType(create.fetchOfDataType("Person")).now()
							.withRequiredCharacterisation(create.fetchOfParameter("age"), VariableCharacterisationType.VALUE))
			
			// BASIC COMPONENTS
			.addToRepository(create.newBasicComponent()
				.withName("Database")
//				.withId("comp1")
				.handles(create.newEventGroup()
								.withName("hallo")
								.withEventType()
									.withName("type")
									.withParameter("foo", Primitive.BOOLEAN, null).now()
								.withRequiredCharacterisation(create.fetchOfParameter("foo"), VariableCharacterisationType.STRUCTURE))
				.withServiceEffectSpecification(create.newSeff().withSeffBehaviour().withStartAction().followedBy().externalCallAction().followedBy().stopAction().createBehaviourNow())
				.provides(create.fetchOfOperationInterface("IDatabase"), "provDB")
				.requires(create.newOperationInterface().withName("someInterface"), "reqSomeI")
				.withPassiveResource("2*3", create.fetchOfResourceTimeoutFailureType(Failure.SOFTWARE))
				.withVariableUsage(create.newVariableUsage().withVariableCharacterisation(null, null))
			)
						
			.addToRepository(create.newBasicComponent()
				.withName("Web2")
				.provides(create.newOperationInterface()
				    .withName("IDatabase2")
//				    .withId("face2")
			    )
			)
			
			// COMPOSITE COMPONENTS
			.addToRepository(create.newCompositeComponent()
					.withName("Web")
					.withVariableUsage(create.newVariableUsage().withName(null).withVariableCharacterisation(null, null).withNamespaceReference(null))
					.requires(create.newOperationInterface()
							.withName("HelloWorld"))
					.withAssemblyContext(create.fetchOfComponent("Database"), "DBContext")
					.withAssemblyConnection(create.fetchOfOperationProvidedRole("provDB"), create.fetchOfAssemblyContext("DBContext"), 
							create.fetchOfOperationRequiredRole("reqSomeI"), create.fetchOfAssemblyContext("DBContext"))
					.withEventChannel().withEventGroup(create.fetchOfEventGroup("hallo")).now1()
					)
			
//			.conect("Web", creator.getByUUID("comp1"))
	
			.createRepositoryNow();
		
//		create.newResourceDemandingInternalBehaviour().withSeffBehaviour().withStartAction().
		//SEFF example
		create.newSeff().onSignature(null).withSeffTypeID(null)
				.withInternalBehaviour(create.newInternalBehaviour().withStartAction().followedBy().stopAction().createBehaviourNow())
//				.withInternalBehaviour(create.newResourceDemandingInternalBehaviour().withSeffBehaviour().withStartAction().withName("Start").followedBy().stopAction().createBehaviourNow())
				.withSeffBehaviour().withStartAction()
						.followedBy().internalAction()
											.withResourceDemand(null, null)
											.withInternalFailureOccurrenceDescription(null, null)
											.withInfrastructureCall(null, null, null)
						.followedBy().internalCallAction().withInternalBehaviour(null).withName(null)
						.followedBy().externalCallAction()
											.withCalledService(null)
											.withFailureType(null)
											.withInputVariableUsage(null)
											.withRequiredRole(null)
											.withRetryCount(null)
											.withReturnVariableUsage(null)
						.followedBy().emitEventAction()
											.withEventType(null)
											.withSourceRole(null)
											.withInputVariableUsage(null)
						.followedBy().setVariableAction()
						.followedBy().acquireAction()
											.withPassiveResource(null)
											.withTimeoutValue(null)
											.isTimeout(null)
						.followedBy().releaseAction()
											.withPassiveResource(null)
						.followedBy().loopAction()
											.withIterationCount(null)
											.withLoopBody(null)
//													create.newSeffBehaviour().withStartAction()
//																				.withInfrastructureCall(null, null, null)
//																	.followedBy().internalAction().withInfrastructureCall(null, null, null)
//																	.followedBy().stopAction().createBehaviourNow())
					    .followedBy().collectionIteratorAction()
					    					.withParameter(create.fetchOfParameter("foo"))
					    					.withLoopBody(null)
					    .followedBy().branchAction()
//					    					.withGuardedBranchTransition(null, null)
//					    					.withProbabilisticBranchTransition(null, null)
					    .followedBy().forkAction()
					    					.withAsynchronousForkedBehaviour(null)
					    					.withOutputParameterUsageAtSynchronisationPoint(null)
					    					.withSynchronousForkedBehaviourAtSynchronisationPoint(null)
					    .followedBy().recoveryAction()
						.followedBy().stopAction().createBehaviourNow();
		
		
		saveRepository(repo, "myrepo.repository", true);	
	}
	
	public static void saveRepository(Repository repo, String name, boolean print) {
		String outputFile = "/Users/louisalambrecht/Documents/eclipse-workspace/FluentAPICreation/" + name;
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
			if(print)
				((XMLResource) resource).save(System.out, ((XMLResource) resource).getDefaultSaveOptions());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void nullExample() {
		MyRepositoryFactory create = new MyRepositoryFactory();
		Repository repo = create.newRepository()
			.withName(null)
			.withDescription(null)
			.addToRepository(create.newCollectionDataType(null, Primitive.BOOLEAN))
			.addToRepository(create.newCollectionDataType("bla", Primitive.INTEGER))
			.addToRepository(create.newCollectionDataType(null, create.fetchOfDataType("bla")))
			.addToRepository(create.newCompositeDataType(null)
					.withInnerDeclaration(null, create.fetchOfDataType(Primitive.BOOLEAN)))
			.addToRepository(create.newCompositeDataType(null, null, null).withInnerDeclaration(null, Primitive.BOOLEAN))
			.addToRepository(create.newBasicComponent()
					.withName(null)
					.ofType(null)
					.withPassiveResource(null, null)
					.withVariableUsage(null)
					.withVariableUsage(create.newVariableUsage()
							.withName(null)
							.withVariableCharacterisation(null, null)
							.withNamespaceReference(null, null, null)
							.withVariableReference(null))
					.withServiceEffectSpecification(null)
					//TODO javadoc for seffs
					.withServiceEffectSpecification(create.newSeff()))
			
			.addToRepository(create.newCompleteComponentType())
			.addToRepository(create.newCompositeComponent())
			.addToRepository(create.newProvidesComponentType())
			.addToRepository(create.newSubSystem())
			.createRepositoryNow();
		
		saveRepository(repo, "null.repository", false);
	}
	
	public static void mediaStoreExample() {
		MyRepositoryFactory create = new MyRepositoryFactory();

		//TODO: seffs + failureType + Namen der Provided Roles
		Repository mediaStore = create.newRepository()
			.withName("defaultRepository")
			.withDescription("This is my PCM model.")
//			.withId("abc123")
			.addToRepository(create.newCompositeDataType("FileContent"))
			.addToRepository(create.newCompositeDataType("AudioCollectionRequest")
										.withInnerDeclaration("Count", Primitive.INTEGER)
										.withInnerDeclaration("Size", Primitive.INTEGER))
			.addToRepository(create.newOperationInterface()
								.withName("IFileStorage")
								.withOperationSignature()
									.withName("getFiles")
									.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"), ParameterModifier.NONE)
									.withReturnType(create.fetchOfDataType("FileContent")).now()
								.withOperationSignature()
									.withName("storeFile")
									.withParameter("file", create.fetchOfDataType("FileContent"), null).now())
			.addToRepository(create.newOperationInterface()
								.withName("IDownload")
								.withOperationSignature()
									.withName("download")
									.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"), ParameterModifier.NONE)
									.withReturnType(create.fetchOfDataType("AudioCollectionRequest")).now())
			.addToRepository(create.newOperationInterface()
								.withName("IMediaAccess")
								.withOperationSignature()
									.withName("upload")
									.withParameter("file", create.fetchOfDataType("FileContent"), null).now()
								.withOperationSignature()
									.withName("getFileList").now())
			.addToRepository(create.newOperationInterface()
								.withName("IPackaging")
								.withOperationSignature()
									.withName("zip")
									.withParameter("audios", create.fetchOfDataType("AudioCollectionRequest"), null)
									.withReturnType(create.fetchOfDataType("FileContent")).now())
			.addToRepository(create.newOperationInterface()
								.withName("IMediaManagement")
								.withOperationSignature()
									.withName("upload")
									.withParameter("file", create.fetchOfDataType("FileContent"), null).now()
								.withOperationSignature()
									.withName("download")
									.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"), ParameterModifier.NONE)
									.withReturnType(create.fetchOfDataType("FileContent")).now()	
								.withOperationSignature()
									.withName("getFileList").now())
			.addToRepository(create.newBasicComponent()
								.withName("EnqueueDownloadCache")
								.provides(create.fetchOfOperationInterface("IDownload"))
								.requires(create.fetchOfOperationInterface("IDownload"))
								.withServiceEffectSpecification(create.newSeff()))
			.addToRepository(create.newBasicComponent()
								.withName("InstantDownloadCache")
								.provides(create.fetchOfOperationInterface("IDownload"))
								.requires(create.fetchOfOperationInterface("IDownload"))
								.withServiceEffectSpecification(create.newSeff()))
			.addToRepository(create.newBasicComponent()
								.withName("FileStorage")
								.provides(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
								.withServiceEffectSpecification(create.newSeff())
								.withServiceEffectSpecification(create.newSeff()))
			.addToRepository(create.newBasicComponent()
								.withName("MediaManagement")
								.provides(create.fetchOfOperationInterface("IMediaManagement"))
								.requires(create.fetchOfOperationInterface("IDownload"))
								.requires(create.fetchOfOperationInterface("IPackaging"))
								.requires(create.fetchOfOperationInterface("IMediaAccess"))
								.withServiceEffectSpecification(create.newSeff())
								.withServiceEffectSpecification(create.newSeff())
								.withServiceEffectSpecification(create.newSeff()))
			.addToRepository(create.newBasicComponent()
								.withName("Packaging")
								.provides(create.fetchOfOperationInterface("IPackaging"))
								.withServiceEffectSpecification(create.newSeff()))
			.addToRepository(create.newBasicComponent()
								.withName("MediaAccess")
								.provides(create.fetchOfOperationInterface("IMediaAccess"))
								.provides(create.fetchOfOperationInterface("IDownload"))
								.requires(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
								.withServiceEffectSpecification(create.newSeff())
								.withServiceEffectSpecification(create.newSeff())
								.withServiceEffectSpecification(create.newSeff()))
			.createRepositoryNow();
			
			
		
		saveRepository(mediaStore, "myMediaStore.repository", false);
			
	}

}
