package componentModel.examples;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import componentModel.factory.FluentRepositoryFactory;
import componentModel.repositoryStructure.internals.Primitive;
import shared.structure.ProcessingResource;
import shared.structure.ResourceInterface;
import shared.util.ModelSaver;

class Example {

	public static void main(String[] args) {
		invalidExample();
		miniExample();
		readmeExampleBackend();
		readmeExampleFluentAPI();
		mediaStoreExample();
		//exampleWithoutMeaning();

	}
	
	public static void invalidExample() {
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		Repository repository = create.newRepository()
				.withName("invalid")
				.addToRepository(create.newCompleteComponentType())
				.createRepositoryNow();
		ModelSaver.saveRepository(repository, "./", "invalid", true);
	}

	public static void miniExample() {
		// Factory
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		Repository repository = create.newRepository()
				.addToRepository(create.newCollectionDataType("String List", Primitive.STRING))
				.addToRepository(create.newCompositeDataType().withName("Person")
						.withInnerDeclaration("names", create.fetchOfDataType("String List"))
						.withInnerDeclaration("age", Primitive.INTEGER))
				.addToRepository(create.newHardwareInducedFailureType("CPU Hardware Error", ProcessingResource.CPU))
				.addToRepository(create.newOperationInterface().withName("parent interface"))
				.addToRepository(create.newOperationInterface().withName("interface")
						.withOperationSignature(create.newOperationSignature()
								.withName("signature")
								.withParameter("parameter", create.fetchOfDataType("Person"), ParameterModifier.IN))
						.withRequiredCharacterisation(create.fetchOfParameter("parameter"),
								VariableCharacterisationType.STRUCTURE)
						.conforms(create.fetchOfInterface("parent interface")))
				.addToRepository(create.newEventGroup().withName("event group"))
				.addToRepository(create.newInfrastructureInterface().withName("infrastructure interface"))
				.addToRepository(create.newBasicComponent().withName("basic component")
						.provides(create.fetchOfOperationInterface("interface"), "basic component provides interface")
						.requires(create.fetchOfOperationInterface("interface"), "basic component requires interface")
						.emits(create.fetchOfEventGroup("event group"), "emits event")
						.handles(create.fetchOfEventGroup("event group"), "handles event")
						.providesInfrastructure(create.fetchOfInfrastructureInterface("infrastructure interface"), "provides infrastructure")
						.requiresInfrastructure(create.fetchOfInfrastructureInterface("infrastructure interface"), "requres infrastructure")
						.requiresResource(ResourceInterface.CPU, "cpu interface")
						.withServiceEffectSpecification(
								create.newSeff().onSignature(create.fetchOfSignature("signature")).withSeffBehaviour()
										.withStartAction().followedBy().stopAction().createBehaviourNow()))
				.addToRepository(create.newCompositeComponent()
						.withAssemblyContext(create.fetchOfComponent("basic component"), "basic component context")
						.withEventChannel(create.fetchOfEventGroup("event group")).withAssemblyConnection(
								create.fetchOfOperationProvidedRole("basic component provides interface"),
								create.fetchOfAssemblyContext("basic component context"),
								create.fetchOfOperationRequiredRole("basic component requires interface"),
								create.fetchOfAssemblyContext("basic component context")))
				.createRepositoryNow();
		ModelSaver.saveRepository(repository, "./", "miniExample", true);
	}

	public static void readmeExampleBackend() {
		// Factory
		RepositoryFactory repoFact = RepositoryFactory.eINSTANCE;
		// Repository
		Repository repository = repoFact.createRepository();

		// Database component
		BasicComponent databaseComponent = repoFact.createBasicComponent();
		databaseComponent.setEntityName("Database");

		// IDatabase interface
		OperationInterface databaseInterface = repoFact.createOperationInterface();
		databaseInterface.setEntityName("IDatabase");
		
		// Signature store
		OperationSignature store = repoFact.createOperationSignature();
		store.setEntityName("store");
		// with parameters forename, name
		Parameter forename = repoFact.createParameter();
		forename.setParameterName("forename");
		forename.setDataType__Parameter(null); // referencing the imported data types poses another problem
		Parameter name = repoFact.createParameter();
		name.setParameterName("forename");
		name.setDataType__Parameter(null);

		// Providing connection from Database component to IDatabase interface
		OperationProvidedRole dbProvIDb = repoFact.createOperationProvidedRole();
		dbProvIDb.setProvidedInterface__OperationProvidedRole(databaseInterface);
		dbProvIDb.setProvidingEntity_ProvidedRole(databaseComponent);

		// Seff for Database component on service store
		ResourceDemandingSEFF storeSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
		storeSeff.setDescribedService__SEFF(store);
		databaseComponent.getServiceEffectSpecifications__BasicComponent().add(storeSeff);

		// Adding component + interfaces to the repository
		repository.getComponents__Repository().add(databaseComponent);
		repository.getInterfaces__Repository().add(databaseInterface);

		// Web component
		BasicComponent webComponent = repoFact.createBasicComponent();
		databaseComponent.setEntityName("Web");

		OperationInterface webInterface = repoFact.createOperationInterface();
		databaseInterface.setEntityName("IWeb");

		OperationSignature submit = repoFact.createOperationSignature();
		submit.setEntityName("submit");
		// with parameters forename, name
		Parameter forename2 = repoFact.createParameter();
		forename2.setParameterName("forename");
		forename2.setDataType__Parameter(null);
		Parameter name2 = repoFact.createParameter();
		name2.setParameterName("forename");
		name2.setDataType__Parameter(null);

		OperationProvidedRole webProvIweb = repoFact.createOperationProvidedRole();
		webProvIweb.setProvidedInterface__OperationProvidedRole(webInterface);
		webProvIweb.setProvidingEntity_ProvidedRole(webComponent);

		ResourceDemandingSEFF submitSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
		submitSeff.setDescribedService__SEFF(submit);
		webComponent.getServiceEffectSpecifications__BasicComponent().add(submitSeff);

		OperationRequiredRole webRequIDb = repoFact.createOperationRequiredRole();
		webRequIDb.setRequiredInterface__OperationRequiredRole(databaseInterface);
		webRequIDb.setRequiringEntity_RequiredRole(webComponent);

		// Adding component + interfaces to the repository
		repository.getComponents__Repository().add(webComponent);
		repository.getInterfaces__Repository().add(webInterface);

		ModelSaver.saveRepository(repository, "./", "backendExample", false);
	}

	public static void readmeExampleFluentAPI() {
		// Factory
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		Repository repository = create.newRepository()
				// Database
				.addToRepository(create.newOperationInterface().withName("IDatabase")
						.withOperationSignature(create.newOperationSignature()
								.withName("store")
								.withParameter("forename", Primitive.STRING, ParameterModifier.NONE)
								.withParameter("name", Primitive.STRING, ParameterModifier.NONE)))
				.addToRepository(create.newBasicComponent().withName("Database")
						.withServiceEffectSpecification(create.newSeff().onSignature(create.fetchOfSignature("store")))
						.provides(create.fetchOfOperationInterface("IDatabase")))
				// Web
				.addToRepository(create.newOperationInterface().withName("IWeb")
						.withOperationSignature(create.newOperationSignature()
						.withName("submit").withParameter("forename", Primitive.STRING, ParameterModifier.NONE)
						.withParameter("name", Primitive.STRING, ParameterModifier.NONE)))
				.addToRepository(create.newBasicComponent().withName("Web")
						.withServiceEffectSpecification(create.newSeff().onSignature(create.fetchOfSignature("submit")))
						.provides(create.fetchOfOperationInterface("IWeb"))
						.requires(create.fetchOfOperationInterface("IDatabase")))
				.createRepositoryNow();

		ModelSaver.saveRepository(repository, "./", "fluentAPIExample", false);
	}
	
	public static void presentationExample() {
		
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		Repository repository = create.newRepository()
				.addToRepository(create.newBasicComponent()
						.withName("Database"))
				.createRepositoryNow();

		
		ModelSaver.saveRepository(repository, "./", "fluentAPIExample", false);
	}

	public static void mediaStoreExample() {
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		// TODO: mediastore Beispiel vervollständigen: seffs + failureType + Namen der
		// Provided Roles
		Repository mediaStore = create.newRepository().withName("MediaStoreRepository")
				.addToRepository(create.newCompositeDataType().withName("FileContent"))
				.addToRepository(create.newCompositeDataType().withName("AudioCollectionRequest")
						.withInnerDeclaration("Count", Primitive.INTEGER)
						.withInnerDeclaration("Size", Primitive.INTEGER))
				.addToRepository(create.newOperationInterface().withName("IFileStorage")
						.withOperationSignature(create.newOperationSignature()
						.withName("getFiles")
						.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"),
								ParameterModifier.NONE)
						.withReturnType(create.fetchOfDataType("FileContent")))
						.withOperationSignature(create.newOperationSignature().withName("storeFile")
						.withParameter("file", create.fetchOfDataType("FileContent"), null)))
				.addToRepository(create.newOperationInterface().withName("IDownload")
						.withOperationSignature(create.newOperationSignature()
						.withName("download")
						.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"),
								ParameterModifier.NONE)
						.withReturnType(create.fetchOfDataType("AudioCollectionRequest"))))
				.addToRepository(create.newOperationInterface().withName("IMediaAccess")
						.withOperationSignature(create.newOperationSignature()
						.withName("upload").withParameter("file", create.fetchOfDataType("FileContent"), null))
						.withOperationSignature(create.newOperationSignature().withName("getFileList")))
				.addToRepository(create.newOperationInterface().withName("IPackaging")
						.withOperationSignature(create.newOperationSignature()
						.withName("zip").withParameter("audios", create.fetchOfDataType("AudioCollectionRequest"), null)
						.withReturnType(create.fetchOfDataType("FileContent"))))
				.addToRepository(create.newOperationInterface().withName("IMediaManagement")
						.withOperationSignature(create.newOperationSignature()
								.withName("upload")
								.withParameter("file", create.fetchOfDataType("FileContent"), null))
						.withOperationSignature(create.newOperationSignature()
								.withName("download")
								.withParameter("audioRequest", create.fetchOfDataType("AudioCollectionRequest"), ParameterModifier.NONE)
								.withReturnType(create.fetchOfDataType("FileContent")))
						.withOperationSignature(create.newOperationSignature()
								.withName("getFileList")))
				.addToRepository(create.newBasicComponent().withName("EnqueueDownloadCache")
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("InstantDownloadCache")
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("FileStorage")
						.provides(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("MediaManagement")
						.provides(create.fetchOfOperationInterface("IMediaManagement"))
						.requires(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IPackaging"))
						.requires(create.fetchOfOperationInterface("IMediaAccess"))
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("Packaging")
						.provides(create.fetchOfOperationInterface("IPackaging"))
						.withServiceEffectSpecification(create.newSeff()))
				.addToRepository(create.newBasicComponent().withName("MediaAccess")
						.provides(create.fetchOfOperationInterface("IMediaAccess"))
						.provides(create.fetchOfOperationInterface("IDownload"))
						.requires(create.fetchOfOperationInterface("IFileStorage"), "IDataStorage")
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff())
						.withServiceEffectSpecification(create.newSeff()))
				.createRepositoryNow();

		ModelSaver.saveRepository(mediaStore, "./", "myMediaStore", false);
	}

	public static void exampleWithoutMeaning() {
		FluentRepositoryFactory create = new FluentRepositoryFactory();

		// TODO: Ausführliches Beispiel zum Testen der API
		Repository repository = create.newRepository().withName("defaultRepository")
				.withDescription("This is my PCM model.")

				// DATATYPES
				.addToRepository(create.newCollectionDataType("StringList", Primitive.STRING))
				.addToRepository(create.newResourceTimeoutFailureType("blub"))

				.addToRepository(create.newCompositeDataType().withName("Person")
						.withInnerDeclaration("first names", create.fetchOfDataType("StringList"))
						.withInnerDeclaration("age", Primitive.INTEGER))
				.addToRepository(create.newExceptionType().withName("myException").withExceptionMessage("FEHLER!"))

				// INTERFACES
				.addToRepository(create.newOperationInterface().withName("IDatabase")
						.withOperationSignature(create.newOperationSignature()
								.withName("saveDatabaseEntry")
								.withParameter("first names", create.fetchOfDataType("StringList"), null)
								.withParameter("age", Primitive.INTEGER, ParameterModifier.INOUT)
								.withReturnType(create.fetchOfDataType("Person")))
						.withRequiredCharacterisation(create.fetchOfParameter("age"),
								VariableCharacterisationType.VALUE))
				.addToRepository(create.newEventGroup().withName("haha"))

				// BASIC COMPONENTS
				.addToRepository(create.newBasicComponent().withName("Database")
						.handles(create.newEventGroup()
								.withName("hallo")
								.withEventType(create.newEventType().withName("type")
								.withParameter("foo", Primitive.BOOLEAN, null))
								.withRequiredCharacterisation(create.fetchOfParameter("foo"),
										VariableCharacterisationType.STRUCTURE))
						.withServiceEffectSpecification(create.newSeff().withSeffBehaviour().withStartAction()
								.followedBy().externalCallAction().followedBy().stopAction().createBehaviourNow())
						.withPassiveResource("3", create.fetchOfResourceTimeoutFailureType("blub"), "passivo")
						.provides(create.fetchOfOperationInterface("IDatabase"), "provDB")
						.requires(create.newOperationInterface().withName("someInterface"), "reqSomeI")
						.withPassiveResource("2*3", create.fetchOfResourceTimeoutFailureType("blub"), "passResource")
						.withVariableUsage(create.newVariableUsage()))

				.addToRepository(create.newBasicComponent().withName("Web2")
						.provides(create.newOperationInterface().withName("IDatabase2")))

				// COMPOSITE COMPONENTS
				.addToRepository(
						create.newCompositeComponent().withName("Web").withVariableUsage(create.newVariableUsage())
								.requires(create.newOperationInterface().withName("HelloWorld"))
								.withAssemblyContext(create.fetchOfComponent("Database"), "DBContext")
								.withAssemblyConnection(create.fetchOfOperationProvidedRole("provDB"),
										create.fetchOfAssemblyContext("DBContext"),
										create.fetchOfOperationRequiredRole("reqSomeI"),
										create.fetchOfAssemblyContext("DBContext"))
								.withEventChannel(create.fetchOfEventGroup("haha")))
				.createRepositoryNow();

		ModelSaver.saveRepository(repository, "./", "meaninglessExample", false);
	}
}
