# Usage Model
## Structure of the API 
The fluent API's main component to create resource environments is the ```FluentUsageModelFactory```. This factory can create
* a usage model
* usage scenario with
    * scenario behaviour with actions
	* workload
* user data with
	* assembly context
	
All the model elements get added and created by method chaining. For the objects out of the a System Model the factory allows the user to reference them over fetching methods. With required parameter values it gets assured that the final usage model is valid.

##Getting Started
Creating a PCM usage model via the Palladio Fluent API always starts with the same three lines of code:
```java
FluentUsageModelFactory create = new FluentUsageModelFactory();
UsageModel usageModel = create.newUsageModel()
    //add entities to the resource environment
    .createUsageModelNow();
```
This creates an empty usage model. The next step would be to add an usage scenario and/or specifiy user data with the importatnt method ```addToUsageModel```.
If one or more predefined systems are needed later, they get added first. 
```java
FluentUsageModelFactory create = new FluentUsageModelFactory();
UsageModel usageModel = create.addSystem(org.palladiosimulator.pcm.system.System System1)
	.newUsageModel()    
    .createUsageModelNow();
```

##Usage Scenario
TODO

##UserData
TODO

##Example
An example of the usage model of a realistic media store example can be created like this with a mock of the needed System called system.

```java
FluentUsageModelFactory create = new FluentUsageModelFactory();
UsageModel usgModel = this.create.addSystem(system).newUsageModel().addToUsageModel(
                this.create.newUsageScenario(
                        this.create.newScenarioBehavior().withName("RealisticUsageScenarioBehaviour")
                        .addToScenarioBehaviour(
                                this.create.newStartAction().withName("startUsage").withSuccessor(
                                this.create.newBranchAction().withName("isRegistered")
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().addToScenarioBehaviour(
                                            this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui", "register")).withName("register"))
                                            .withName("needsToRegister")).withProbability(0.6))
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("isAlreadyRegistered")
                                            ).withProbability(0.4)).withSuccessor(                                
                                this.create.newEntryLevelSystemCall(
                                        this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","login"))
                                        .withName("login").withSuccessor(
                                this.create.newDelayAction("GammaMoments(3000,0.3)").withName("userDelayAfterLogin").withSuccessor(
                                this.create.newEntryLevelSystemCall(
                                        this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","getFileList"))
                                        .withName("getFileList").withSuccessor(
                                this.create.newDelayAction("GammaMoments(6000,0.3)").withName("userDelayAfterGetFileList").withSuccessor(
                                this.create.newBranchAction().withName("downloadOrUpload")
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("downloadCase")
                                            .addToScenarioBehaviour(this.create.newEntryLevelSystemCall(
                                                    this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","download"))
                                                    .withName("download")
                                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("audioRequest", "Size").withVariableCharacterisation("IntPMF[(38303999;0.16666667)(38304000;0.16666667)(40568000;0.16666667)(41544000;0.16666667)(48280000;0.16666666)(65000000;0.16666667)(88216000;0.16666666)]", VariableCharacterisationType.BYTESIZE))
                                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("audioRequest", "Count").withVariableCharacterisation("2", VariableCharacterisationType.VALUE))
                                                    )).withProbability(0.8))
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("uploadCase")
                                            .addToScenarioBehaviour(this.create.newEntryLevelSystemCall(
                                                    this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","upload"))
                                                    .withName("upload").addToEntryLevelSystemCallInput(this.create.newVariableUsage("file").withVariableCharacterisation("IntPMF[(38303999;0.16666667)(38304000;0.16666667)(40568000;0.16666667)(41544000;0.16666667)(48280000;0.16666666)(65000000;0.16666667)(88216000;0.16666666)]", VariableCharacterisationType.BYTESIZE))
                                                    )).withProbability(0.2)).withSuccessor(
                                this.create.newStopAction().withName("stopUsage"))))))))),
                        
                        this.create.newOpenWorkload("Exp(0.00004)")).withName("RealisticUsageScenario"))               
                
                .createUsageModelNow();
```

More examples and testing can be found in the JUnit Test TODO Ort