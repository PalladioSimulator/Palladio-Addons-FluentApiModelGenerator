package org.palladiosimulator.generator.fluent.usageModel.factory;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

public class FluentUsageModelFactoryTest {
    FluentUsageModelFactory create;

    private void setUp() {
        create = new FluentUsageModelFactory();
    }

    private void printXML(UsageModel usgModel, String name) {
        //ModelSaver.saveUsageModel(usgModel, name, true);
        // ModelSaver.saveUsageModel(usgModel, name, false);
    }

    @Test
    public void basicUsageModelClasses() {
        // Actual Model
        setUp();
        UsageModel usgModel = create.newUsageModel().createUsageModelNow();
        printXML(usgModel, "./UsgModClasses");

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
        printXML(usgModel, "./UsgModBigExample");
    }
    
    public void checkDefaultValues() {
        setUp();
        UsageModel usgModel = create.newUsageModel()
                .addToUsageModel(create.newUsageScenario()
                        .addToUsageScenario(create.newClosedWorkload()))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModDefaultValues");
        
        //Default Values are:
        //Branch Transition -> Probability 0.0 
        //Entry Level System Call -> Priority 0
        //Closed Workload -> Population 0
        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        ClosedWorkload c = (ClosedWorkload) w;
        assertEquals(0, c.getPopulation());
        
        //-->Default Values does not need to be set while creating the model
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
        printXML(usgModel, "./UsgModUsageScenarioBasic");

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
        
        printXML(usgModel, "./UsgModScenWorkloadOpen");

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
        printXML(usgModel, "./UsgModScenWorkloadClosed");

        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        ClosedWorkload c = (ClosedWorkload) w;
        assertEquals(time,c.getThinkTime_ClosedWorkload().getSpecification());
        assertEquals(population, c.getPopulation());
    }
    
    public void basicUsageScenarioClasses() {
        // Actual Model
        setUp();
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(create.newOpenWorkload())
                .addToUsageScenario(create.newScenarioBehavior()))

                .createUsageModelNow();
        printXML(usgModel, "./UsgModUsageScenarioClasses");

        // Expected Model
        UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        UsageScenario usgScen = UsagemodelFactory.eINSTANCE.createUsageScenario();
        usgScen.setWorkload_UsageScenario(UsagemodelFactory.eINSTANCE.createOpenWorkload());
        usgScen.setScenarioBehaviour_UsageScenario(UsagemodelFactory.eINSTANCE.createScenarioBehaviour());

        expectedModel.getUsageScenario_UsageModel().add(usgScen);

        // Test
        // TODO
        assertEquals(expectedModel.toString(), usgModel.toString());
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
                        .addAction(create.newStartAction())
                        .addAction(create.newStopAction())
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehavior");
        
        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getEntityName());
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        
    }
    
    @Test
    public void usageScenarioBehavActions() {
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addAction(create.newStartAction())
                        .addAction(create.newDelayAction())
                        .addAction(create.newBranchAction())
                        .addAction(create.newEntryLevelSystemCall())
                        .addAction(create.newLoopAction())
                        .addAction(create.newStopAction())
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActions");
        
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
                        .addAction(create.newStartAction().withSuccessor(create.newDelayAction().withSuccessor(create.newStopAction())))
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActionList");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());  
    }
    
    
    @Test
    public void usageScenarioBehavActionsDelay() {
        String name = "DelayAction";
        String time = "20";
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addAction(create.newDelayAction()
                                .withName(name)
                                .addToDelayAction(time))
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActionsDelay");
        
        AbstractUserAction action = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().get(0);
        Delay delay = (Delay) action;
        
        assertEquals(name, delay.getEntityName());
        assertEquals(time, delay.getTimeSpecification_Delay().getSpecification());
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
                        .addAction(create.newLoopAction()
                                .withName(name)
                                .addToLoopAction(iteration)
                                .addToLoopAction(create.newScenarioBehavior().withName(scenName)))
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActionsLoop");
        
        AbstractUserAction action = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().get(0);
        Loop act = (Loop) action;
        
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
                        .addAction(create.newBranchAction()
                                .withName(name)
                                .addToBranchAction(
                                        create.newBranchTransition()
                                        .addToBranchTransition(create.newScenarioBehavior().withName(scenName))
                                        .withProbability(prop))                                        
                                )
                        ))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActionsBranch");
        
        AbstractUserAction action = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().get(0);
        Branch act = (Branch) action;
        
        assertEquals(name, act.getEntityName());
        BranchTransition trans = act.getBranchTransitions_Branch().get(0);
        assertEquals(prop,trans.getBranchProbability(),0.001);
        assertEquals(scenName,trans.getBranchedBehaviour_BranchTransition().getEntityName());
    }
 
    @Test
    public void usageScenarioBehavActionsEntryLevelSystemCall() {
        String name = "EntryLevelSystemCall";
        int priority = 1;
        
        setUp();
        
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()

                .addToUsageScenario(
                        create.newScenarioBehavior()
                        .addAction(create.newEntryLevelSystemCall()
                                .withName(name)
                                .addToEntryLevelSystemCallInput(create.newVariableUsage())
                                .addToEntryLevelSystemCallOutput(create.newVariableUsage())
                                .withPriority(priority)
                               //TODO .withOperationSignatureEntryLevelSystemCall(null)
                               //TODO .withProvidedRoleEntryLevelSystemCall(null)           
                       )))
                
                .createUsageModelNow();
        printXML(usgModel, "./UsgModScenBehvActionsEntryLevelSystemCall");
        
        AbstractUserAction action = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour().get(0);
        EntryLevelSystemCall act = (EntryLevelSystemCall) action;
        
        assertEquals(name, act.getEntityName());
        assertEquals(priority, act.getPriority());
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
                        //TODO .withAssemblyContext(null)
                        .addToUserData(create.newVariableUsage()))
                .createUsageModelNow();
        
        printXML(usgModel, "./UsgModUserDataBasic");   
        
        assertFalse(usgModel.getUserData_UsageModel().get(0).getUserDataParameterUsages_UserData().isEmpty());
    
    }

}
