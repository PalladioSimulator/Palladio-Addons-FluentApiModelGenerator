package org.palladiosimulator.generator.fluent.usagemodel.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.generator.fluent.system.factory.FluentSystemFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;




@SuppressWarnings("static-method")
public class FluentUsageModelFactoryTest {
    FluentUsageModelFactory create;

  //------------------   Util ------------------

    private void setUp() {
        this.create = new FluentUsageModelFactory();
    }

    private static Repository createSimplifiedMediaStoreRepository() {
      //copied from org.palladiosimulator.generator.fluent.repository.examples, added OperationSignature for testing
        final FluentRepositoryFactory create = new FluentRepositoryFactory();
        final Repository repo = create.newRepository().withName("SimplifiedMediaStore Repository")
            .addToRepository(create.newOperationInterface().withName("IAudioDB")
                    .withOperationSignature(create.newOperationSignature().withName("opSignatureAudioDB")))
            .addToRepository(create.newOperationInterface().withName("ISound"))
            .addToRepository(create.newOperationInterface().withName("MediaStoreInterface"))
            .addToRepository(create.newOperationInterface().withName("GUIInterface")
                    .withOperationSignature(create.newOperationSignature().withName("opSignatureGUIInterface")))
            .addToRepository(create.newOperationInterface().withName("AudioDBInterface"))
            .addToRepository(create.newBasicComponent().withName("AudioDB")
                    .provides(create.fetchOfOperationInterface("AudioDBInterface"), "ProvidesAudioDBInterface"))
            .addToRepository(create.newBasicComponent().withName("DBAdapter")
                    .requires(create.fetchOfOperationInterface("AudioDBInterface"), "RequiresAudioDBInterface")
                    .provides(create.fetchOfOperationInterface("IAudioDB"), "ProvidesIAudioDB"))
            .addToRepository(create.newBasicComponent().withName("DigitalWatermarking")
                    .provides(create.fetchOfOperationInterface("ISound"), "ProvidesISound"))
            .addToRepository(create.newBasicComponent().withName("MediaStore")
                    .requires(create.fetchOfOperationInterface("ISound"), "RequiresISound")
                    .requires(create.fetchOfOperationInterface("IAudioDB"), "RequiresIAudioDB").provides(
                            create.fetchOfOperationInterface("MediaStoreInterface"), "ProvidesMediaStoreInterface"))
            .addToRepository(create.newBasicComponent().withName("WebGUI")
                    .requires(create.fetchOfOperationInterface("MediaStoreInterface"),
                            "RequiresMediaStoreInterface")
                    .provides(create.fetchOfOperationInterface("GUIInterface"), "ProvidesGUIInterface"))
            .createRepositoryNow();
        ModelSaver.saveRepository(repo, "./","simplifiedMediaStore");
    return repo;
    }

    private System createSimplifiedMediaStoreSystem() {
        //copied from org.palladiosimulator.generator.fluent.system.examples, changed where to get Respository
        final FluentSystemFactory create = new FluentSystemFactory();
        final System system = create.newSystem().withName("SimplifiedMediaStore System")
                //.addRepository(ModelLoader.loadRepository("./simplifiedMediaStore.repository"))
                .addRepository(createSimplifiedMediaStoreRepository())
                .addToSystem(
                        create.newAssemblyContext().withName("AudioDB Component").withEncapsulatedComponent("AudioDB"))
                .addToSystem(create.newAssemblyContext().withName("DBAdapter Component")
                        .withEncapsulatedComponent("DBAdapter"))
                .addToSystem(create.newAssemblyContext().withName("DigitalWatermarking Component")
                        .withEncapsulatedComponent("DigitalWatermarking"))
                .addToSystem(create.newAssemblyContext().withName("MediaStore Component")
                        .withEncapsulatedComponent("MediaStore"))
                .addToSystem(
                        create.newAssemblyContext().withName("WebGUI Component").withEncapsulatedComponent("WebGUI"))
                .addToSystem(create.newAssemblyConnector().withName("AudioDBInterfaceConnector")
                        .withProvidingAssemblyContext("AudioDB Component")
                        .withOperationProvidedRole("ProvidesAudioDBInterface")
                        .withRequiringAssemblyContext("DBAdapter Component")
                        .withOperationRequiredRole("RequiresAudioDBInterface"))
                .addToSystem(create.newAssemblyConnector().withName("IAudioDB Connector")
                        .withProvidingAssemblyContext("DBAdapter Component")
                        .withOperationProvidedRole("ProvidesIAudioDB")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresIAudioDB"))
                .addToSystem(create.newAssemblyConnector().withName("ISound Connector")
                        .withProvidingAssemblyContext("DigitalWatermarking Component")
                        .withOperationProvidedRole("ProvidesISound")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresISound"))
                .addToSystem(create.newAssemblyConnector().withName("MediaStoreInterface Connector")
                        .withProvidingAssemblyContext("MediaStore Component")
                        .withOperationProvidedRole("ProvidesMediaStoreInterface")
                        .withRequiringAssemblyContext("WebGUI Component")
                        .withOperationRequiredRole("RequiresMediaStoreInterface"))
                .addToSystem(create.newOperationProvidedRole().withName("SystemProvidesGUIInterface")
                        .withProvidedInterface("GUIInterface"))
                .addToSystem(create.newProvidedDelegationConnectorCreator().withName("GUIInterface Connector")
                        .withOuterProvidedRole("SystemProvidesGUIInterface").withProvidingContext("WebGUI Component")
                        .withOperationProvidedRole("ProvidesGUIInterface"))
                .createSystemNow();
        ModelSaver.saveSystem(system, "./","simplifiedMediaStore");
        return system;
    }





  //------------------   TESTS ------------------

    @Test
    public void basicUsageModelClasses() {
        // Actual Model
        this.setUp();
        final UsageModel usgModel = this.create.newUsageModel().createUsageModelNow();

        // Expected Model
        final UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        // Test
        assertEquals(expectedModel.toString(), usgModel.toString());
    }

    private System mediaStoreMockUp() {
        final FluentSystemFactory sysFac = new FluentSystemFactory();
        final FluentRepositoryFactory repoFac = new FluentRepositoryFactory();

        final Repository repo = repoFac.newRepository().withName("defaultRepository")
                .addToRepository(repoFac.newCompositeDataType().withName("FileContent"))
                .addToRepository(repoFac.newCompositeDataType().withName("AudioCollectionRequest")
                        .withInnerDeclaration("Count", Primitive.INTEGER)
                        .withInnerDeclaration("Size", Primitive.INTEGER))
                .addToRepository(repoFac.newOperationInterface().withName("IFacade")
                        .withOperationSignature(repoFac.newOperationSignature().withName("upload")
                                .withParameter("file", repoFac.fetchOfDataType("FileContent"), null))
                        .withOperationSignature(repoFac.newOperationSignature().withName("getFileList"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("register"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("login"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("download")
                                .withParameter("audioRequest", repoFac.fetchOfDataType("AudioCollectionRequest"), null)))
                .createRepositoryNow();

        final System system = sysFac.newSystem().withName("defaultSystem")
                .addRepository(repo)
                .addToSystem(sysFac.newOperationProvidedRole().withName("Provided_IWebGui").withProvidedInterface("IFacade"))
                .createSystemNow();

        ModelSaver.saveRepository(repo, "./", "realisticMediaStore");
        ModelSaver.saveSystem(system,  "./", "realisticMediaStore");
        return system;
    }

    @Test
    public void mediaStoreRealistic() {
        //Building MediaStore3-Model: ms_base_usage_realistic.usagemodel and needed System and Repository for that
        this.setUp();
        this.create.addSystem(this.mediaStoreMockUp()).newUsageModel().addToUsageModel(
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

        //TODO: use "boolean compareEMF(UsageModel origin, UsageModel test)" to verify created model
    }


    //------------------   Usage Scenario ------------------

   @Test
    public void basicUsageScenario() {

        final String name = "UsgScen";
        this.setUp();
        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(this.create.newScenarioBehavior()
                ,this.create.newOpenWorkload("10"))
                .withName(name))
                .createUsageModelNow();


        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getEntityName());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario());
    }

    @Test
    public void usageScenarioWorkloadOpen() {
        final String time = "10";
        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior()
                ,this.create.newOpenWorkload(time)))

                .createUsageModelNow();


        final Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        final OpenWorkload o = (OpenWorkload) w;
        assertEquals(time,o.getInterArrivalTime_OpenWorkload().getSpecification());
    }

    @Test
    public void usageScenarioWorkloadClosed() {
        final String time = "10";
        final int population = 100;
        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(this.create.newScenarioBehavior(),
                        this.create.newClosedWorkload(time)
                        .withPopulation(population)))
                .createUsageModelNow();


        final Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        final ClosedWorkload c = (ClosedWorkload) w;
        assertEquals(time,c.getThinkTime_ClosedWorkload().getSpecification());
        assertEquals(population, c.getPopulation());
    }

  //------------------   Usage Scenario  - Scenario Behaviour & Actions ------------------

    @Test
    public void usageScenarioBehaviour() {
        final String name = "ScenBehaviour";
        final int countAddedActions = 2;  //as it adds start and stop automatically

        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                        this.create.newScenarioBehavior()
                        .withName(name)
                        ,this.create.newClosedWorkload("0")))

                .createUsageModelNow();

        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getEntityName());

        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());
    }

  @Test
    public void usageScenarioBehavActions() {
        this.setUp();

        final String operSigName = "opSignatureGUIInterface";
        final String provRoleName = "SystemProvidesGUIInterface";
        final int countAddedActions = 6;

        final UsageModel usgModel = this.create.addSystem(this.createSimplifiedMediaStoreSystem())
                .newUsageModel().addToUsageModel(this.create.newUsageScenario(
                        this.create.newScenarioBehavior()
                        .addToScenarioBehaviour(this.create.newStartAction()
                                .withSuccessor(this.create.newDelayAction("10")
                                .withSuccessor(this.create.newBranchAction()
                                .withSuccessor(this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("SimplifiedMediaStore System",provRoleName, operSigName))
                                .withSuccessor(this.create.newLoopAction("1", this.create.newScenarioBehavior())
                                .withSuccessor(this.create.newStopAction())))
    ))),this.create.newOpenWorkload("0")))

                .createUsageModelNow();


        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());
    }

    @Test
    public void usageScenarioBehavActionList() {
        final int countAddedActions = 4; //needs to be 4 as Start/Stop get added automatically
        final String fstDelay = "FirstDelayAction";
        final String sndDelay = "SecondDelayAction";

        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(this.create.newDelayAction("1").withName(fstDelay).withSuccessor(this.create.newDelayAction("1").withName(sndDelay)))
                ,this.create.newOpenWorkload("10")))
                .createUsageModelNow();


        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());

        // Check order, Order is Start, DelayW/fstDelay, DelayW/sndDelay, Stop
        for (final AbstractUserAction act : list) {
            if (fstDelay.equals(act.getEntityName())) {
                // Before Start, After sndDelay
                assertTrue(act.getPredecessor() instanceof Start);
                assertEquals(sndDelay,act.getSuccessor().getEntityName());
            } else if (sndDelay.equals(act.getEntityName())) {
                // Before fstDelay, After Stop
                assertTrue(act.getSuccessor() instanceof Stop);
                assertEquals(fstDelay, act.getPredecessor().getEntityName());
            }
            //no need to check, if Action is Start or Stop as this is testen over the Delay Actions
        }

    }

    @Test
    public void usageScenarioBehavActionsDelay() {
        final String name = "DelayAction";
        final String time = "20";
        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(this.create.newDelayAction(time).withName(name))
                , this.create.newClosedWorkload("0")))

                .createUsageModelNow();


        Delay act = null;
        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for (final AbstractUserAction action : list) {
            if(action instanceof Delay) {
                act = (Delay) action;}
        }

        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(time, act.getTimeSpecification_Delay().getSpecification());
    }

    @Test
    public void usageScenarioBehavActionsLoop() {
        final String name = "LoopAction";
        final String iteration = "20";
        final String scenName = "ScenBeh";
        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(
                        this.create.newLoopAction(iteration, this.create.newScenarioBehavior().withName(scenName)).withName(name))
                ,this.create.newClosedWorkload("0")))
                .createUsageModelNow();



        Loop act = null;
        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for (final AbstractUserAction action : list) {
            if(action instanceof Loop) {
                act = (Loop) action;}
        }

        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(iteration, act.getLoopIteration_Loop().getSpecification());
        assertEquals(scenName, act.getBodyBehaviour_Loop().getEntityName());
    }

    @Test
    public void usageScenarioBehavActionsBranch() {
        final String name = "BranchAction";
        //Double prop = 0.6;
        final Double prop = 1.0;
        final String scenName = "ScenBeh";
        this.setUp();

        final UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(
                        this.create.newBranchAction().withName(name).addToBranchAction(
                                this.create.newBranchTransition(this.create.newScenarioBehavior().withName(scenName)).withProbability(prop)))
                , this.create.newClosedWorkload("0")))
                .createUsageModelNow();


        Branch act = null;
        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for (final AbstractUserAction action : list) {
            if(action instanceof Branch) {
                act = (Branch) action;}
        }

        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        final BranchTransition trans = act.getBranchTransitions_Branch().get(0);
        assertEquals(prop,trans.getBranchProbability(),0.001);
        assertEquals(scenName,trans.getBranchedBehaviour_BranchTransition().getEntityName());
    }

    @Test
    public void usageScenarioBehavActionsEntryLevelSystemCall() {
        final String name = "EntryLevelSystemCall";
        final int priority = 1;

        final String operSigName = "opSignatureGUIInterface";
        final String provRoleName = "SystemProvidesGUIInterface";

        this.setUp();
        final UsageModel usgModel = this.create.addSystem(this.createSimplifiedMediaStoreSystem())
                .newUsageModel().addToUsageModel(
                this.create.newUsageScenario(
                        this.create.newScenarioBehavior().addToScenarioBehaviour(
                                this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("SimplifiedMediaStore System",provRoleName, operSigName))
                                    .withName(name)
                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("TestReferenz"))
                                    .addToEntryLevelSystemCallOutput(this.create.newVariableUsage("TestReferenz"))
                                    .withPriority(priority))
                        , this.create.newClosedWorkload("10")))
                .createUsageModelNow();


        EntryLevelSystemCall act = null;
        final List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for (final AbstractUserAction action : list) {
            if(action instanceof EntryLevelSystemCall) {
                act = (EntryLevelSystemCall) action;}
        }

        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(priority, act.getPriority());
        assertEquals(operSigName, act.getOperationSignature__EntryLevelSystemCall().getEntityName());
        assertEquals(provRoleName, act.getProvidedRole_EntryLevelSystemCall().getEntityName());
        assertFalse(act.getInputParameterUsages_EntryLevelSystemCall().isEmpty());
        assertFalse(act.getOutputParameterUsages_EntryLevelSystemCall().isEmpty());
    }

    //------------------   User Data ------------------

    @Test
    public void basicUserData() {
        final String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for

        this.setUp();

        final UsageModel usgModel = this.create.addSystem(this.createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)))
                .createUsageModelNow();


        assertFalse(usgModel.getUserData_UsageModel().isEmpty());
    }

    @Test
    public void usrDataVariableUsage() {
        final String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for

        //Usage Model
        this.setUp();

        final UsageModel usgModel = this.create.addSystem(this.createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)).addToUserData(this.create.newVariableUsage("TestReferenz")))
                .createUsageModelNow();


        //Test
        assertFalse(usgModel.getUserData_UsageModel().get(0).getUserDataParameterUsages_UserData().isEmpty());
    }

    @Test
    public void usrDataAssemblyContext() {
        final String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for

        this.setUp();

        final UsageModel usgModel = this.create.addSystem(this.createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)))
                .createUsageModelNow();


        //Test
        final AssemblyContext con = usgModel.getUserData_UsageModel().get(0).getAssemblyContext_userData();
        assertEquals(assConName, con.getEntityName());
    }

}
