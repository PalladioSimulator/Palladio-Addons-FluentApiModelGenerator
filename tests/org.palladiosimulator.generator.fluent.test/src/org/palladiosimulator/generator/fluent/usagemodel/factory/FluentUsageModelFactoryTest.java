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
    FluentSystemFactory systemFac;

    
  //------------------   Util ------------------ 
    
    private void setUp() {
        create = new FluentUsageModelFactory();
    }

    /*
    private System createSystem() {
        //String path = ModelLoader.STANDARD_SYSTEM_PATH;
        systemFac = new FluentSystemFactory();
        return s s = systemFac.newSystem().addToSystem(systemFac.newAssemblyContext()).createSystemNow();
        ModelSaver.saveSystem(s, path, false);
    }*/
    
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(create.newOpenWorkload())
                .addToUsageScenario(create.newScenarioBehavior())
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()
                
                .addToUsageScenario(create.newOpenWorkload().addToWorkload(time)))
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()
                
                .addToUsageScenario(create.newClosedWorkload().addToWorkload(time).withPopulation(population)))
                
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .withName(name)
                        .addActions(create.newStartAction())
                        ))
                
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()  
                        .addActions(create.newStartAction().withSuccessor(create.newDelayAction()
                                .withSuccessor(create.newBranchAction()
                                .withSuccessor(create.newEntryLevelSystemCall()
                                .withSuccessor(create.newLoopAction()
                                .withSuccessor(create.newStopAction())))
    )))))
                
                .createUsageModelNow();
        printXML(usgModel, "UsgModScenBehvActions");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(6, list.size());       
    }
    
    @Test
    public void usageScenarioBehavActionList() {
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addActions(create.newDelayAction().withSuccessor(create.newDelayAction()))
                        ))
                
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addActions(create.newDelayAction()
                                .withName(name)
                                .addToDelayAction(time))
                        ))
                
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
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addActions(create.newLoopAction()
                                .withName(name)
                                .addToLoopAction(iteration)
                                .addToLoopAction(create.newScenarioBehavior().withName(scenName)))
                        ))
                
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
        Double prop = 0.6;
        String scenName = "ScenBeh";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addActions(create.newBranchAction()
                                .withName(name)
                                .addToBranchAction(
                                        create.newBranchTransition()
                                        .addToBranchTransition(create.newScenarioBehavior().withName(scenName))
                                        .withProbability(prop))                                        
                                )
                        ))
                
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
        String operSigName = "TestOperationSignature";
        String provRoleName = "TestProvidedRole";
        
        String repositoryName = "Repository";
        
        //Repository
        FluentRepositoryFactory f = new FluentRepositoryFactory();
        Repository r = f.newRepository()
                .addToRepository(f.newBasicComponent().provides(f.newOperationInterface()
                .withOperationSignature(f.newOperationSignature().withName(operSigName)),provRoleName))
                .createRepositoryNow();
        ModelSaver.saveRepository(r, repositoryName, false);
        
        setUp();
        
        UsageModel usgModel = create.setRepository("./"+repositoryName+".repository").newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addActions(create.newEntryLevelSystemCall()
                                .withName(name)
                                .addToEntryLevelSystemCallInput(create.newVariableUsage())
                                .addToEntryLevelSystemCallOutput(create.newVariableUsage())
                                .withPriority(priority)
                                .withOperationSignatureEntryLevelSystemCall(operSigName) 
                                .withProvidedRoleEntryLevelSystemCall(provRoleName)           
                       )))
                
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
        setUp();
    
        UsageModel usgModel = create.newUsageModel()
                .addToUsageModel(
                        create.newUserData()
                        //.withAssemblyContext(null)
                        .addToUserData(create.newVariableUsage()))
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModUserDataBasic");   
        
        assertFalse(usgModel.getUserData_UsageModel().get(0).getUserDataParameterUsages_UserData().isEmpty());
    
    }    

    
    @Test
    public void usrDataAssemblyContext() {
        String assConName = "assemblyContext";
        String systemPath = "System";
        
        //System
        systemFac = new FluentSystemFactory();
        System s = systemFac.newSystem().addToSystem(systemFac.newAssemblyContext().withName(assConName)).createSystemNow();
        ModelSaver.saveSystem(s, systemPath, false);        
        
        //Usage Model
        setUp(); 
        
        UsageModel usgModel = create.setSystem("./"+systemPath+".system").newUsageModel()
                .addToUsageModel(
                        create.newUserData().withAssemblyContext(assConName)
                        )
                .createUsageModelNow();
        printXML(usgModel, "UsgModUserDataAssembly"); 
        
        //Test
        AssemblyContext con = usgModel.getUserData_UsageModel().get(0).getAssemblyContext_userData();
        assertEquals(assConName, con.getEntityName());
    }
    
}
