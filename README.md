
# Palladio-Addons-FluentApiModelGenerator
A fluid interface for easy and programmatic creation of PCM repository model instances.

If you are familiar with the backgroud of PCM and fluent interfaces, jump directly to [Motivation](#motivation) or [How to use the Fluent API Model Generator](#how-to-use-the-fluent-api-model-generator).

## Background

### Palladio Component Model (PCM)
Palladio is a software architecture simulation approach which analyses your software at the model level for performance bottlenecks, scalability issues, reliability threats, and allows for a subsequent optimisation.
The Palladio Component Model (PCM) is one of the core assets of the Palladio approach. It is designed to enable early performance, reliability, maintainability, and cost predictions for software architectures and is aligned with a component-based software development process. The PCM is implemented using the Eclipse Modeling Framework (EMF). Visit the [Homepage](https://www.palladio-simulator.com) for more information.

#### PCM Repository Model
The PCM is realized as an Eclipse Plugin. Creating PCM repository model is fairly simple using the diagram editor. The image below shows the graphical diagram editor with a simple repository model. The palette on the right provides the user with all the model elements. Selecting an element and clicking onto the model plane creates the basic model elements. Additionally, most elements can be further edited using the ```Properties``` view.
![PCM Repository Model: Diagram Editor](documentation/pcm_repo_model_diagram.png "PCM Repository Model: Diagram Editor")
The tree view of the repository model editor shows the model elements in their structure. New model elements, i.e. children of the tree branches, can be created by right clicking on a tree node. The editor shows the selection that is sensible at this point in the model structure. Furthermore, the tree view shows the 3 repositories that are imported by default: primitive types, failure types and a resource repository. Their elements can be used freely.
![PCM Repository Model: Tree Editor](documentation/pcm_repo_model_tree.png "PCM Repository Model: Tree Editor")

#### Fluent Interfaces
A fluent interface, also called fluent API, is a certain style of interface which is especially useful for creating and manipulating objects. The goal of a fluent interface is to increase code legibility by creating a domain-specific language (DSL). Its design relies on method chaining to implement method cascading. Thus, each method usually returns this, i.e. the manipulated object itself. Furthermore, the chaining methods are supposed to "flow like a natural sentence" (hence the name "fluent interface"), automatically guiding the user and giving a natural feeling of the available features.

Prominent examples of fluent interfaces are the [Java Stream API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html) and [JMock](http://jmock.org).

## Motivation
Even though the model editor provides a comfortable and graphic possibility of creating PCM repository models, experienced users may find it exhausting to work with a graphical interface and wish for a simple API to create their models programmatically and therefore faster.
However, the backend of PCM provides not just one but around 10 different factories, that are needed to create a PCM repository model. Searching for the correct factory for the different model elements and the method names that sets the desired properties is not user friendly. Especially, because the model objects offer more method proposals than sensible for creating a repository model.

The following code example shows the code needed for creating half of the repository model from the image of the graphical editor.

```java
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

// Seff for Database component on service store
ResourceDemandingSEFF storeSeff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
storeSeff.setDescribedService__SEFF(store);
databaseComponent.getServiceEffectSpecifications__BasicComponent().add(storeSeff);

// Providing connection from Database component to IDatabase interface
OperationProvidedRole dbProvIDb = repoFact.createOperationProvidedRole();
dbProvIDb.setProvidedInterface__OperationProvidedRole(databaseInterface);
dbProvIDb.setProvidingEntity_ProvidedRole(databaseComponent);

// Adding component + interfaces to the repository
repository.getComponents__Repository().add(databaseComponent);
repository.getInterfaces__Repository().add(databaseInterface);
```

The overhead of creating the repository model that way is extesive. The fluent API has the goal not only to reduce the overhead of creating a model programmatically but also to provide a clear frame that guides the user through the different steps of the model creation, naturally indicating which step comes next. Consequently, the API is easy to use even for unexperienced users.

Check out the full code of the example from the image of the graphical editor [here](#example).

## How to use the Fluent API Model Generator

### Project Setup

For using the fluent API, three dependencies are required:
1. Palladio-Core-PCM (org.palladiosimulator.pcm)
2. Palladio-Core-PCM Resources (org.palladiosimulator.pcm.resources)
3. Palladio FluentAPI (org.palladiosimulator.fluentapi)

It is recommended to work with a PCM installation. Therefor, install the PCM Nightly as described at [PCM_Installation#PCM_Nightly](https://sdqweb.ipd.kit.edu/wiki/PCM_Installation#PCM_Nightly).
Create your own Plug-in Project and add the three dependencies in the MANIFEST.MF file.
You are now ready to use the `FluentRepositoryFactory` to create a Repository.


### Structure of the API
The Palladio Fluent API's main component is the ```FluentRepositoryFactory```. This factory can create
* a repository,
* basic model elements like
    * components,
    * interfaces,
    * data types,
    * failure types and
* the more complex model elements like
    * SEFFs and
    * variable usages.
All other internal model elements are created using method chaining. Therefore, no other objects have to be instantiated by the user. In addition, the factory provides numerous fetching methods that allows the user to reference created model elements in a later context.


### Getting Started
Creating a PCM repository model via the Palladio Fluent API always starts with the same three lines of code:
```java
FluentRepositoryFactory create = new FluentRepositoryFactory();
Repository repository = create.newRepository()
	.createRepositoryNow();
```
It is the absolute minimum of creating an empty repository that does not contain any model elements. Apart from adding a name and description to the repository, the user can also load other repositories as imports. However, the main interest at this stage will be the ```addToRepository``` method that provides the context for creating new model elements.
For example:
```java
Repository repository = create.newRepository()
	.addToRepository(create.newCollectionDataType("StringList", Primitive.STRING))
	.addToRepository(create.newOperationInterface())
	.addToRepository(create.newBasicComponent())
	.addToRepository(create.newCompositeComponent())
	.createRepositoryNow();
```
All model elements offer the specification of a name. This name should be unique among the repository model as the model elements are referenced by this name in the fetching methods.

The three repositories that are imported by default in the graphical and tree editor are also part of the API. The built in primitive types, failure types and resources from the repository resource can be referenced using the enum classes ```Primitive```, ```Failure```, ```ProcessingResource```, ```ResourceInterface```, ```ResourceSignature``` and ```CommunicationLinkResource```. See, for example, the generation of a list of strings in line 2 of the example above. The primitive data type String from the imported ```PrimitiveDataTypes.repository``` is referenced by ```Primitive.STRING```.


### Data Types
There are two kinds of data types a user can create: collection data types that represent a list or a set of a certain type, and composite data types that similar to a Java class form a container to store several values that belong together.

Collection data types simply need a name and another data type that can be any data type from primitive to a collection or composition data type.
```java
create.newCollectionDataType("StringList", Primitive.STRING)
```
Composite data types should also be provided with a name (even though not required), so it can be refereced later on. The inner declaration are the "fields" that this data type is composed of. Previously created data types can be fetched from the repository as indicated in line 3 below.
```java
create.newCompositeDataType()
	.withName("Person")
	.withInnerDeclaration("names", create.fetchOfDataType("StringList"))
	.withInnerDeclaration("age", Primitive.INTEGER)
```

### Interfaces
There are three types of interfaces: operation interfaces, infrastructure interfaces, and event groups. All of them can bear a signature and a required characterisation of parameters and have parent interfaces.
```java
create.newOperationInterface()
	.withName("interface")
	.withOperationSignature()
		.withName("signature")
		.withParameter("parameter", create.fetchOfDataType("Person"), ParameterModifier.IN)
		.createSignature()
	.withRequiredCharacterisation(create.fetchOfParameter("parameter"), VariableCharacterisationType.STRUCTURE)
	.conforms(create.fetchOfInterface("parent interface")
```
Signatures require a call on the ```createSignature()``` method for technical reasons of the API design to return to the interface that is currently created.


### Components
Basic components, composite components, subsystems and the component types (complete and provides) can all connect in certain roles to interfaces.
```java
create.newBasicComponent()
	.withName("basic component")
	.provides(create.fetchOfOperationInterface("interface"), "basic component provides interface")
	.requires(create.fetchOfOperationInterface("interface"), "basic component requires interface")
```

Composite components and subsystem can further define assembly contexts, various connectors and event channels.
```java
create.newCompositeComponent()
	.withAssemblyContext(create.fetchOfComponent("basic component"), "basic component context")
	.withAssemblyConnection(create.fetchOfOperationProvidedRole("basic component provides interface"),
		create.fetchOfAssemblyContext("basic component context"),
		create.fetchOfOperationRequiredRole("basic component requires interface"),
		create.fetchOfAssemblyContext("basic component context"))
	.withEventChannel(create.fetchOfEventGroup("event group"))
```

### SEFFs
Basic components can define the behaviour of signature/method of an interface it provides.
```java
create.newBasicComponent()
	.withServiceEffectSpecification(create.newSeff()
		.onSignature(create.fetchOfSignature("signature"))
		.withSeffBehaviour().withStartAction().followedBy().stopAction().createBehaviourNow()))
```
The SEFF behaviour consists of a start action followed by a stop action at the very least. In between there can follow arbitrarily many other actions, like internal actions, external actions, aquire actions, branch actions, loop actions etc. As the signatures of the interfaces, a behaviour has to be terminated with a call on the ```createBehaviourNow()``` method for technical reasons of the API design.

## Example
Creating the whole example repository from the image of the graphical editor using the fluent API is much simpler, shorter and easier to understand than when using the backend directly.

```java
// Factory
FluentRepositoryFactory create = new FluentRepositoryFactory();

Repository repository = create.newRepository()
	// Database
	.addToRepository(create.newOperationInterface()
			.withName("IDatabase")
			.withOperationSignature()
				.withName("store")
				.withParameter("forename", Primitive.STRING, ParameterModifier.NONE)
				.withParameter("name", Primitive.STRING, ParameterModifier.NONE).createSignature())
	.addToRepository(create.newBasicComponent()
			.withName("Database")
			.withServiceEffectSpecification(create.newSeff().onSignature(create.fetchOfSignature("store")))
			.provides(create.fetchOfOperationInterface("IDatabase")))
	// Web
	.addToRepository(create.newOperationInterface()
			.withName("IWeb")
			.withOperationSignature()
				.withName("submit")
				.withParameter("forename", Primitive.STRING, ParameterModifier.NONE)
				.withParameter("name", Primitive.STRING, ParameterModifier.NONE).createSignature())
	.addToRepository(create.newBasicComponent()
			.withName("Web")
			.withServiceEffectSpecification(create.newSeff().onSignature(create.fetchOfSignature("submit")))
			.provides(create.fetchOfOperationInterface("IWeb"))
			.requires(create.fetchOfOperationInterface("IDatabase")))
	.createRepositoryNow();
```
The package [```examples```](src/examples/) provides more examples of repositories that were created using the fluent API.

## Miscellaneous
See issues on git for further enhancements of this API.
