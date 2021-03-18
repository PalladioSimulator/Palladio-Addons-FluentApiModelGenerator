# Resource Environment
## Structure of the API
The fluent API's main component to create resource environments is the ```FluentResourceEnvironmentFactory```. This factory can create
- a resource environment
- resource containers
- processing resource specifications
- HDD processing resource specifications
- linking resources

## Getting Started
Creating a PCM resource environment via the Palladio Fluent API always starts with the same three lines of code:
```java
FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
ResourceEnvironment resourceEnvironment = create.newResourceEnvironment()
    //add entities to the resource environment
    .createResourceEnvironmentNow();
```
This creates an empty resource environment. The next step is to add a resource container with the ```addToResourceEnvironment``` method. Also linking resources can be added to the resource environment, linking multiple resource containers. Resource Containers are added to the system like this:

```java
ResourceEnvironment resourceEnvironment = create.newResourceEnvironment()
    .addToResourceEnvironment(create.newResourceContainer()
        .withName("resource container")
        //add more properties to the resource container using the fluent API
        )
```

## Resource Containers
Resource containers hold resources or nested resources containers. In the allocation model, assembly contexts and event channels are assigned to resource containers. They are created like this:
```java
create.newResourceContainer()
    .withName("resource container")
    .addProcessingResourceSpecification(/*create a ProcessingResourceSpecification using the factory*/)
    .addHddProcessingResourceSpecification(/*create a HddProcessingResourceSpecification using the factory*/)
    .addNestedResourceContainer(/*create another resource container using the factory*/)
```

## Processing Resource Specifications
A processing resource specification describes one resource. The resource type and scheduling policy are defined through the corresponding enums. Additionally the processing rate, number of replicas, mean time to failure and mean time to recovery can be set. Also, the resource can be set to be required by the container. They are created like this:
```java
create.newProcessingResourceSpecification()
    .withProcessingResourceType(ProcessingResource.CPU)
    .withSchedulingPolicy(SchedulingPolicies.PROCESS_SHARING)
    .withProcessingRate("3.2GHz")
    .isRequiredByContainer()
    .withMttf(100)
    .withMttr(5)
    .withNumberOfReplicas(2)
```

## HDD Processing Resource Specification
HDD processing resource specifications have the same properties as regular processing resource specifications. However, they have the additional properties read and write processing rate. They are created like this:
```java
create.newHddProcessingResourceSpecification()
    //set the properties of the processing resource
    .withWriteProcessingRate("2Gbit/s")
    .withReadProcessingRate("6Gbit/s")
```

## Linking Resource
Multiple resource containers can be linked with a linking resource. Additionally the latency, throughput and failure probability can be set. They are created like this:
```java
create.newLinkingResource()
    .withName("linkin resource")
    .withCommunicationLinkResource(CommunicationLinkResource.LAN)
    .addLinkedResourceContainer("container 1")
    .addLinkedResourceContainer("container 2")
    .withFailureProbability(0.02)
    .withLatency("10ms")
    .withThroughput("50Mbit/s")
```

## Example
A resource environment with a single resource container and one processing as well as one HDD resource can be created like this:
```java

FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
ResourceEnvironment resourceEnvironment = create.newResourceEnvironment()
    .withName("SimplifiedMediaStore ResourceEnvironment")
    .addToResourceEnvironment(create.newResourceContainer()
        .withName("resource container")
        .addProcessingResourceSpecification(create.newProcessingResourceSpecification()
            .withProcessingResourceType(ProcessingResource.CPU)
            .withSchedulingPolicy(SchedulingPolicies.PROCESS_SHARING)
            .withProcessingRate("3.2GHz")
            .isRequiredByContainer()
            .withMttf(100)
            .withMttr(5)
            .withNumberOfReplicas(2))
        .addHddProcessingResourceSpecification(create.newHddProcessingResourceSpecification()
            .withProcessingResourceType(ProcessingResource.HDD)
            .withSchedulingPolicy(SchedulingPolicies.FIRST_COME_FIRST_SERVE)
            .withProcessingRate("6Gbit/s")
            .withWriteProcessingRate("2Gbit/s")
            .withReadProcessingRate("6Gbit/s")
            .withMttf(500)
            .withMttr(20)
            .withNumberOfReplicas(1)))
    .createResourceEnvironmentNow();
```

The graphical representation of the model looks like this: ![PCM Resource Environment: Simplified Media Store](pcm_resources_media_store.png "PCM  Resource Environment: Simplified Media Store")

The package [```resourceenvironment.example```](../bundles/org.palladiosimulator.generator.fluent_api/src/resourceenvironment/example) provides more examples of resourceenvironments that were created using the fluent API.