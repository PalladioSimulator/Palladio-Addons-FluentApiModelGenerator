package org.palladiosimulator.generator.fluent.usageModel.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
import org.palladiosimulator.generator.fluent.usageModel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.UserDataCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.BranchActionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.BranchTransitionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.DelayActionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.EntryLevelSystemCallCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.LoopActionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.StartActionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.StopActionCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.workload.ClosedWorkloadCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.workload.OpenWorkloadCreator;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.system.System;

/**
 * This class provides all the methods to create a UsageModel and create
 * Entities that are added to this Repository. Characteristics of the entities
 * are specified by method chaining.<br>
 * <p>
 * Start creating a repository like this:
 * <code>FluentUsageModelFactory create = new FluentUsageModelFactory();</code><br>
 * <code>UsageModel usgModel = create.newUsageModel()<br>
 * <p style=
"margin-left: 130px">//create datatypes, components, interfaces etc. here</p>
 * <p style="margin-left: 130px">.createUsageModelNow();</p>
 *  </code> Refer to the project's Readme for an introduction and detailed
 * examples.
 *
 * @author Eva-Maria Neumann
 */
public class FluentUsageModelFactory {
    private UsageModelCreator usgModelCreator;
//    private final ResourceEnvironment resourceEnv;
    private final Repository repository;
    private final System system;
    private final ResourceRepository resources ;
    /**
     * Creates an instance of the FluentUsageModelFactory.
     */
    public FluentUsageModelFactory() {
        EcorePlugin.ExtensionProcessor.process(null);
        //resourceEnv = ModelLoader.loadResourceEnvironment(null);//TODO Parameter
        repository = null; // ModelLoader.loadRepository(null); //TODO Parameter 
        system = null; // ModelLoader.loadSystem(null); //TODO Parameter     
        resources = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
    }

    /**
     * Creates a representation of the model object '<em><b>UsageModel</b></em>'.
     * <p>
     * The usageModel entity allows storing components, data types, and interfaces
     * to be fetched and reused for construction of component instances as well as
     * new component types.
     * </p>
     *
     * @return the <code>UsageModel</code> in the making
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel
     */
    public IUsageModel newUsageModel() {
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        usgModelCreator = new UsageModelCreator(system, repository, resources, validator);
        return usgModelCreator;
    }
    
    // ---------------------- Components ----------------------
    
    public UsageScenarioCreator newUsageScenario() {
        return new UsageScenarioCreator(usgModelCreator);
    }
    
    public UserDataCreator newUserData() {
        return new UserDataCreator(usgModelCreator);
    }
    
    public ScenarioBehaviourCreator newScenarioBehavior() {
        return new ScenarioBehaviourCreator(usgModelCreator);
    }
    
    // ---------------------- Actions ----------------------
    
    //TODO: usgModel übergeben oder alle über ActionCreator erstellen siehe Seff als Bsp
    public BranchActionCreator newBranchAction() {
        return new BranchActionCreator();
    }
    
    public BranchTransitionCreator newBranchTransition() {
        return new BranchTransitionCreator();
    }
    
    public DelayActionCreator newDelayAction() {
        return new DelayActionCreator();
    }
    
    public EntryLevelSystemCallCreator newEntryLevelSystemCall() {
        return new EntryLevelSystemCallCreator();
    }
    
    public LoopActionCreator newLoopAction() {
        return new LoopActionCreator();
    }
    
    public StartActionCreator newStartAction() {
        return new StartActionCreator();
    }
    
    public StopActionCreator newStopAction() {
        return new StopActionCreator();
    }
    
    // ---------------------- Workload ----------------------
    
    public ClosedWorkloadCreator newClosedWorkload() {
        return new ClosedWorkloadCreator(usgModelCreator);
    }
    
    public OpenWorkloadCreator newOpenWorkload() {
        return new OpenWorkloadCreator(usgModelCreator);
    }
    
    // ---------------------- Shared ----------------------   
    
    public VariableUsageCreator newVariableUsage() {
        return new VariableUsageCreator(usgModelCreator);
    }
}
