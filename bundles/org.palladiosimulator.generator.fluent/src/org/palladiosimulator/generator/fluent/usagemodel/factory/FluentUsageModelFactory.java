package org.palladiosimulator.generator.fluent.usagemodel.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchTransitionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.DelayActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.EntryLevelSystemCallCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.LoopActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StartActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StopActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.ClosedWorkloadCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.OpenWorkloadCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.WorkloadCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
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
    private Repository repository;
    private System system;

    
    /**
     * Creates an instance of the FluentUsageModelFactory.
     */
    public FluentUsageModelFactory() {
        EcorePlugin.ExtensionProcessor.process(null);
        repository = null;
        system = null;
    }
    
    public FluentUsageModelFactory setSystem(System system) {
        this.system = system;
        return this;
    }
    public FluentUsageModelFactory setRepository(Repository repository) {
        this.repository = repository;
        return this;
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
        usgModelCreator = new UsageModelCreator(system, repository, validator);
        return usgModelCreator;
    }
    
    
    // ---------------------- Components ----------------------
    
    /**
     * Creates a new usage scenario.
     * <p>
     * UsageScenarios are concurrently executed behaviours of users within one UsageModel.
     * It describes which services are directly invoked by users in one specific use case and models the possible sequences of calling them.
     * Each UsageScenario includes a workload and a scenario behaviour.
     * </p>
     * <p>
     * Usage scenarios are defined by their
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator#withName(String)
     * name}
     * </p>
     *
     * @param scenarioBehavior {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator ScenarioBehaviourCreator}
     * @param workload {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.WorkloadCreator workload}
     * @return the usage scenario in the making
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     */
    public UsageScenarioCreator newUsageScenario(ScenarioBehaviourCreator scenarioBehavior, WorkloadCreator workload) {
        return new UsageScenarioCreator(usgModelCreator, scenarioBehavior, workload);
    }
    
    public UserDataCreator newUserData(AssemblyContext context) {
        return new UserDataCreator(usgModelCreator, context);
    }
    
    public UserDataCreator newUserData(String assemblyContext) {
        AssemblyContext context = usgModelCreator.getAssemblyContextByName(assemblyContext);
        return new UserDataCreator(usgModelCreator, context);
    }
    
    
    public ScenarioBehaviourCreator newScenarioBehavior() {
        return new ScenarioBehaviourCreator(usgModelCreator);
    }
    
    // ---------------------- Actions ----------------------
    
    public BranchActionCreator newBranchAction() {
        return new BranchActionCreator();
    }
    
    public BranchTransitionCreator newBranchTransition(ScenarioBehaviourCreator branchedBehaviour) {
        return new BranchTransitionCreator(branchedBehaviour);
    }
    
    
    public DelayActionCreator newDelayAction(String timeSpecification) {
        return new DelayActionCreator(timeSpecification);
    }
    
    
    //TODO: testen obs zusammen passt
    public EntryLevelSystemCallCreator newEntryLevelSystemCall(OperationSignature operationSignature, OperationProvidedRole operationProvidedRole) {
        return new EntryLevelSystemCallCreator(usgModelCreator, operationSignature, operationProvidedRole);
    }
    
    public EntryLevelSystemCallCreator newEntryLevelSystemCall(String operationSignature, OperationProvidedRole operationProvidedRole) {
        return new EntryLevelSystemCallCreator(usgModelCreator, usgModelCreator.getOperationSignatureByName(operationSignature), operationProvidedRole);
    }
    
    public EntryLevelSystemCallCreator newEntryLevelSystemCall(String operationSignature, String operationProvidedRole) {
        return new EntryLevelSystemCallCreator(usgModelCreator,
                usgModelCreator.getOperationSignatureByName(operationSignature),
                usgModelCreator.getOperationProvidedRoleByName(operationProvidedRole));
    }
    
    public EntryLevelSystemCallCreator newEntryLevelSystemCall(OperationSignature operationSignature, String operationProvidedRole) {
        return new EntryLevelSystemCallCreator(usgModelCreator,
                operationSignature,
                usgModelCreator.getOperationProvidedRoleByName(operationProvidedRole));
    }
    
    public LoopActionCreator newLoopAction(String iteration, ScenarioBehaviourCreator bodyBehaviour) {
        return new LoopActionCreator(iteration, bodyBehaviour);
    }
    
    public StartActionCreator newStartAction() {
        return new StartActionCreator();
    }
    
    public StopActionCreator newStopAction() {
        return new StopActionCreator();
    }
    
    // ---------------------- Workload ----------------------
    
    public ClosedWorkloadCreator newClosedWorkload(String thinkTime) {
        return new ClosedWorkloadCreator(usgModelCreator, thinkTime);
    }

    
    public OpenWorkloadCreator newOpenWorkload(String interArrivalTime) {
        return new OpenWorkloadCreator(usgModelCreator, interArrivalTime);
    }

    
    // ---------------------- Shared ----------------------   
    
    public VariableUsageCreator newVariableUsage() {
        return new VariableUsageCreator(usgModelCreator);
    }
}
