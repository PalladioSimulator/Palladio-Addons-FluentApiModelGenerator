# Allocation Model
## Structure of the API
The fluent API's main component to create system models is the ```FluentAllocationFactory```. This factory can create
- an allocation
- allocation contexts

## Getting Started
Creating a PCM allocation model via the Palladio Fluent API always starts with the same three lines of code:
```java
FluentAllocationFactory create = new FluentAllocationFactory();
Allocation allocation = create.newAllocation()
    //add allocation contexts
    .createAllocationNow();
```
First, a system and a resource environment have to be selected. The entities of the system are allocated to the resource containers of the resource environment. The system and resource environment are selected like this:
```java
Allocation allocation = create.newAllocation()
    .withSystem(ModelLoader.loadSystem("*.system file"))
    .withResourceEnvironment(ModelLoader.loadResourceEnvironment("*.resourceenvironment file"))
```
Allocation contexts are added to the allocation with the ```addToAllocation``` method.

## Allocation Contexts
Allocation contexts assign one assembly context or one event channel of the system to one resource container of the resource environment.
```java
create.newAllocationContext()
    .withName("context allocation")
    .withAssemblyContext("context")
    .withResourceContainer("resource container")
```
```java
create.newAllocationContext()
    .withName("event channel allocation")
    .withEventChannel("channel")
    .withResourceContainer("resource container")
```

## Example
The system of the simplified media store example can be allocated to the example resource environment like this:
```java
FluentAllocationFactory create = new FluentAllocationFactory();
Allocation allocation = create.newAllocation()
    .withName("SimplifiedMediaStore Allocation")
    .withSystem(ModelLoader.loadSystem("./simplifiedMediaStore.system"))
    .withResourceEnvironment(ModelLoader.loadResourceEnvironment("./simplifiedMediaStore.resourceenvironment"))
    .addToAllocation(create.newAllocationContext()
        .withName("WebGUI Allocation")
        .withAssemblyContext("WebGUI Component")
        .withResourceContainer("resource container"))
    .addToAllocation(create.newAllocationContext()
        .withName("MediaStore Allocation")
        .withAssemblyContext("MediaStore Component")
        .withResourceContainer("resource container"))
    .addToAllocation(create.newAllocationContext()
        .withName("DigitalWatermarking Allocation")
        .withAssemblyContext("DigitalWatermarking Component")
        .withResourceContainer("resource container"))
    .addToAllocation(create.newAllocationContext()
        .withName("DBAdapter Allocation")
        .withAssemblyContext("DBAdapter Component")
        .withResourceContainer("resource container"))
    .addToAllocation(create.newAllocationContext()
        .withName("AudioDB Allocation")
        .withAssemblyContext("AudioDB Component")
        .withResourceContainer("resource container"))
    .createAllocationNow();
```

The package [```allocation.examples```](../bundles/org.palladiosimulator.generator.fluent_api/src/allocation/examples) provides more examples of allocations that were created using the fluent API.