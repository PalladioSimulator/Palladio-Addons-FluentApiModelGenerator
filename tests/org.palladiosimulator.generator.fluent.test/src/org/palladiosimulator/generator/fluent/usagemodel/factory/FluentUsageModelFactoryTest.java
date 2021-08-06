package org.palladiosimulator.generator.fluent.usagemodel.factory;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.generator.fluent.system.factory.FluentSystemFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
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
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

public class FluentUsageModelFactoryTest {
    FluentUsageModelFactory create;

  //------------------   Util ------------------ 
    
    private void setUp() {
        create = new FluentUsageModelFactory();
    }

    private void createSystemforTest(String systemPath, String assConName, String provRoleName) {
        FluentSystemFactory systemFac = new FluentSystemFactory();
        System s = systemFac.newSystem()
                .addToSystem(systemFac.newAssemblyContext().withName(assConName))
                .addToSystem(systemFac.newOperationProvidedRole().withName(provRoleName))
                .createSystemNow();
        ModelSaver.saveSystem(s, systemPath, false); 
    }
    
    
    private void createRepositoryforTest(String repositoryName, String operSigName, String provRoleName ) {
        FluentRepositoryFactory f = new FluentRepositoryFactory();
        Repository r = f.newRepository()
                .addToRepository(f.newBasicComponent().provides(f.newOperationInterface()
                .withOperationSignature(f.newOperationSignature().withName(operSigName)),provRoleName))
                .createRepositoryNow();
        ModelSaver.saveRepository(r, repositoryName, false);
    }
    
    private void printXML(UsageModel usgModel, String name) {
        //ModelSaver.saveUsageModel(usgModel, name, true);
        ModelSaver.saveUsageModel(usgModel, name, false);
    }

    
  //------------------   TESTS ------------------ 
    
    @Test
    public void basicUsageModelClasses() {
        // Actual Model
        setUp();
        UsageModel usgModel = create.newUsageModel().createUsageModelNow();
        printXML(usgModel, "UsgModClasses");

        // Expected Model
        UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        // Test
        assertEquals(expectedModel.toString(), usgModel.toString());
    }
   
    //@Test
    public void bigExample() {
        setUp();
        UsageModel usgModel = create.newUsageModel().createUsageModelNow();
        //TODO
        printXML(usgModel, "UsgModBigExample");
    }
    

    //------------------   Usage Scenario ------------------ 
   
   @Test
    public void basicUsageScenario() {
    
        String name = "UsgScen";
        setUp();
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(create.newScenarioBehavior()
                ,create.newOpenWorkload("10"))
                .withName(name))
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModUsageScenarioBasic");

        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getEntityName());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario());
    }
    
    @Test
    public void usageScenarioWorkloadOpen() {
        String time = "10";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                create.newScenarioBehavior()
                ,create.newOpenWorkload(time)))
                
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenWorkloadOpen");

        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        OpenWorkload o = (OpenWorkload) w;
        assertEquals(time,o.getInterArrivalTime_OpenWorkload().getSpecification());
    }
    
    @Test
    public void usageScenarioWorkloadClosed() {
        String time = "10";
        int population = 100;
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(create.newScenarioBehavior(),
                        create.newClosedWorkload(time)
                        .withPopulation(population)))                
                .createUsageModelNow();
                
        printXML(usgModel, "UsgModScenWorkloadClosed");

        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        ClosedWorkload c = (ClosedWorkload) w;
        assertEquals(time,c.getThinkTime_ClosedWorkload().getSpecification());
        assertEquals(population, c.getPopulation());
    }
      
  //------------------   Usage Scenario  - Scenario Behaviour & Actions ------------------ 
    @Test
    public void usageScenarioBehaviour() {
        String name = "ScenBehaviour";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                        create.newScenarioBehavior()
                        .withName(name)
                        ,create.newClosedWorkload("0")))
                
                .createUsageModelNow();
        printXML(usgModel, "UsgModScenBehavior");
 
        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getEntityName());
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(2, list.size()); //as it adds only additional stop        
    }
   
  @Test
    public void usageScenarioBehavActions() {
        setUp();
        String operSigName = "TestOperationSignature";
        String provRoleName = "TestProvidedRole";
        String systemPath = "System";
        
       createSystemforTest(systemPath,"AssemblyContext", provRoleName);
       createRepositoryforTest("Repository",operSigName,provRoleName);
        
        UsageModel usgModel = create.setSystem("./"+systemPath+".system").setRepository("./"+"Repository"+".repository").newUsageModel().addToUsageModel(create.newUsageScenario(
                        create.newScenarioBehavior()  
                        .addActions(create.newStartAction()
                                .withSuccessor(create.newDelayAction("10")
                                .withSuccessor(create.newBranchAction()
                                .withSuccessor(create.newEntryLevelSystemCall(operSigName, provRoleName)
                                .withSuccessor(create.newLoopAction("1", create.newScenarioBehavior())
                                .withSuccessor(create.newStopAction())))
    ))),create.newOpenWorkload("0")))
                
                .createUsageModelNow();
 
        printXML(usgModel, "UsgModScenBehvActions");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(6, list.size());       
    }
    
    @Test
    public void usageScenarioBehavActionList() {
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                create.newScenarioBehavior().addActions(create.newDelayAction("1").withSuccessor(create.newDelayAction("1")))
                ,create.newOpenWorkload("10")))
                .createUsageModelNow();
                
         printXML(usgModel, "UsgModScenBehvActionList");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(4, list.size());   //needs to be 4 as Start/Stop get added automatically
    }    

    @Test
    public void usageScenarioBehavActionsDelay() {
        String name = "DelayAction";
        String time = "20";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                create.newScenarioBehavior().addActions(create.newDelayAction(time).withName(name))
                , create.newClosedWorkload("0")))
                
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenBehvActionsDelay");
        
        Delay act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof Delay) {                
                act = (Delay) action;}
        }    
        
        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(time, act.getTimeSpecification_Delay().getSpecification());
    }

    @Test
    public void usageScenarioBehavActionsLoop() {
        String name = "LoopAction";
        String iteration = "20";
        String scenName = "ScenBeh";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                create.newScenarioBehavior().addActions(
                        create.newLoopAction(iteration, create.newScenarioBehavior().withName(scenName)).withName(name))
                ,create.newClosedWorkload("0")))
                .createUsageModelNow();        
        
        
         printXML(usgModel, "UsgModScenBehvActionsLoop");
        
        Loop act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
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
        String name = "BranchAction";
        //Double prop = 0.6; TODO
        Double prop = 1.0; 
        String scenName = "ScenBeh";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario(
                create.newScenarioBehavior().addActions(
                        create.newBranchAction().withName(name).addToBranchAction(
                                create.newBranchTransition(create.newScenarioBehavior().withName(scenName)).withProbability(prop)))
                , create.newClosedWorkload("0")))
                .createUsageModelNow();
                
        printXML(usgModel, "UsgModScenBehvActionsBranch");
        
        Branch act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof Branch) {                
                act = (Branch) action;}
        }    
        
        assertNotNull(act);        
        assertEquals(name, act.getEntityName());
        BranchTransition trans = act.getBranchTransitions_Branch().get(0);
        assertEquals(prop,trans.getBranchProbability(),0.001);
        assertEquals(scenName,trans.getBranchedBehaviour_BranchTransition().getEntityName());
    }
 
    @Test
    public void usageScenarioBehavActionsEntryLevelSystemCall() {
        String name = "EntryLevelSystemCall";
        int priority = 1;
        
        String repositoryName = "Repository";
        String operSigName = "OperatioSignature";
        String provRoleName = "ProvidedRole";
        
        createRepositoryforTest(repositoryName, operSigName, provRoleName);
        createSystemforTest("System","AssemblyContext", provRoleName);
        
        setUp();
        
        UsageModel usgModel = create.setSystem("./System.system").setRepository("./"+repositoryName+".repository").newUsageModel().addToUsageModel(
                create.newUsageScenario(
                        create.newScenarioBehavior().addActions(
                                create.newEntryLevelSystemCall(operSigName, provRoleName)
                                    .withName(name)
                                    .addToEntryLevelSystemCallInput(create.newVariableUsage())
                                    .addToEntryLevelSystemCallOutput(create.newVariableUsage())
                                    .withPriority(priority))
                        , create.newClosedWorkload("10")))
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenBehvActionsEntryLevelSystemCall");
        
        EntryLevelSystemCall act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
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
        String assConName = "assemblyContext";
        String systemPath = "System";
        
        //System
       createSystemforTest(systemPath, assConName, "Test");
        
        setUp();
    
        UsageModel usgModel = create.setSystem("./"+systemPath+".system").newUsageModel().addToUsageModel(
                create.newUserData(assConName))
                .createUsageModelNow();
                
         printXML(usgModel, "UsgModUserDataBasic");   
        
        assertFalse(usgModel.getUserData_UsageModel().isEmpty());
    }    

    @Test
    public void usrDataVariableUsage() {
        String assConName = "assemblyContext";
        String systemPath = "System";
        
        //System
       createSystemforTest(systemPath,assConName, "Test");
        
        //Usage Model
        setUp(); 
        
        UsageModel usgModel = create.setSystem("./"+systemPath+".system").newUsageModel().addToUsageModel(
                create.newUserData(assConName).addToUserData(create.newVariableUsage()))
                .createUsageModelNow();

        printXML(usgModel, "UsgModUserDataVarUsage"); 
        
        //Test
        assertFalse(usgModel.getUserData_UsageModel().get(0).getUserDataParameterUsages_UserData().isEmpty());
    }
    
    @Test
    public void usrDataAssemblyContext() {
        String assConName = "assemblyContext";
        String systemPath = "System";
        
        //System
       createSystemforTest(systemPath,assConName, "Test");
        
        //Usage Model
        setUp(); 
        
        UsageModel usgModel = create.setSystem("./"+systemPath+".system").newUsageModel().addToUsageModel(
                create.newUserData(assConName))
                .createUsageModelNow();
        
         printXML(usgModel, "UsgModUserDataAssembly"); 
        
        //Test
        AssemblyContext con = usgModel.getUserData_UsageModel().get(0).getAssemblyContext_userData();
        assertEquals(assConName, con.getEntityName());
    }

}
