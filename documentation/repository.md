# Repository Model
## Structure of the API
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


## Getting Started
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


## Data Types
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

## Interfaces
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


## Components
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

## SEFFs
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
The package [```examples```](src/component/examples/) provides more examples of repositories that were created using the fluent API.