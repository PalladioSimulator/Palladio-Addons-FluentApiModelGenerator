package org.palladiosimulator.generator.fluent.usagemodel.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.exceptions.FluentApiException;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.ActionCreator;
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
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.system.System;

/**
 * This class provides all the methods to create a UsageModel and create Entities that are added to
 * this Repository. Characteristics of the entities are specified by method chaining.<br>
 * <p>
 * Start creating a repository like this:
 * <code>FluentUsageModelFactory create = new FluentUsageModelFactory();</code><br>
 * <code>UsageModel usgModel = create.newUsageModel()<br>
 * <p style=
"margin-left: 130px">//create datatypes, components, interfaces etc. here</p>
 * <p style="margin-left: 130px">.createUsageModelNow();</p>
 *  </code> Refer to the project's Readme for an introduction and detailed examples.
 *
 * @author Eva-Maria Neumann
 */
@SuppressWarnings("static-method")
public class FluentUsageModelFactory {

    private UsageModelCreator usgModelCreator;
    private final List<System> systems;

    /**
     * Creates an instance of the FluentUsageModelFactory.
     */
    public FluentUsageModelFactory() {
        EcorePlugin.ExtensionProcessor.process(null);
        this.systems = new ArrayList<System>();
    }

    /**
     * Sets the System used in some objects of the usage model.
     *
     * @param system
     *            {@link org.palladiosimulator.pcm.system.System System}
     * @return FluentUsageModelFactory
     * @see org.palladiosimulator.pcm.system.System
     */
    public FluentUsageModelFactory addSystem(final System system) {
        this.systems.add(system);
        return this;
    }

    /**
     * Creates a representation of the model object '<em><b>UsageModel</b></em>'.
     * <p>
     * The usageModel entity allows storing components, data types, and interfaces to be fetched and
     * reused for construction of component instances as well as new component types.
     * </p>
     *
     * @return the <code>UsageModel</code> in the making
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel
     */
    public IUsageModel newUsageModel() {
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        this.usgModelCreator = new UsageModelCreator(validator);
        return this.usgModelCreator;
    }

    // ---------------------- Components ----------------------

    /**
     * Creates a new usage scenario.
     * <p>
     * UsageScenarios are concurrently executed behaviours of users within one UsageModel. It
     * describes which services are directly invoked by users in one specific use case and models
     * the possible sequences of calling them. Each UsageScenario includes a workload and a scenario
     * behaviour.
     * </p>
     * <p>
     * Usage scenarios are defined by their
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator#withName(String)
     * name}
     * </p>
     *
     * @param scenarioBehavior
     *            {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator
     *            ScenarioBehaviourCreator}
     * @param workload
     *            {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.WorkloadCreator
     *            WorkloadCreator}
     * @return the usage scenario in the making
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
     *
     */
    public UsageScenarioCreator newUsageScenario(final ScenarioBehaviourCreator scenarioBehavior,
            final WorkloadCreator workload) {
        return new UsageScenarioCreator(this.usgModelCreator, scenarioBehavior, workload);
    }

    /**
     * Creates a new user data creator.
     * <p>
     * A representation of the model object 'User Data'. UserData characterises data used in
     * specific assembly contexts in the system. This data is the same for all UsageScenarios,
     * i.e.,multiple users accessing the same components access the same data. This UserData refers
     * to component parameters of the system publicized by the software architect (see
     * pcm::parameters package). The domain expert characterises the values of component parameters
     * related to business concepts (e.g., user specific data,data specific for a business domain),
     * whereas the software architect characterises the values of component parameters related to
     * technical concepts (e.g., size of caches, size of a thread pool, configuration data,etc.).
     * One UserData instance includes all parameter characterisation for the annotated entity.
     * </p>
     * <p>
     * User Data offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator#withName(String)
     * name} and
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator#addToUserData(VariableUsageCreator)
     * VariableUsage} and needs a mandatory
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}
     * </p>
     *
     * @param context
     *            {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}
     * @return the user data in the making
     * @see org.palladiosimulator.pcm.usagemodel.UserData
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public UserDataCreator newUserData(final AssemblyContext context) {
        return new UserDataCreator(this.usgModelCreator, context);
    }

    /**
     * Creates a new scenario behaviour.
     * <p>
     * A representation of the model object 'Scenario Behaviour'. A ScenarioBehaviour specifies
     * possible sequences of executing services provided by the system.It contains a set of
     * AbstractUserActions, each referencing a predecessor and successor (except the first and last
     * action), thereby forming a sequence of actions.See the AbstractAction documentation for why
     * it is advantageous to model control flow in this way, as the same principle is used in the
     * RDSEFF language.Concrete user actions of the usage model are:- Branch- Loop-
     * EntryLevelSystemCall- Delay- Start- StopSo far, ScenarioBehaviours do not include forks in
     * the user flow (i.e., splitting the flow with anAND semantic), as it is assumed that users
     * always act sequentially.As there are no random variables depending on other variables in the
     * usage model, there are no equivalent actions to GuardedBranchTransitions or
     * CollectionIteratorActions.
     * </p>
     * <p>
     * Scenario Behaviour offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator#withName(String)
     * name} and
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator#addToScenarioBehaviour(ActionCreator)
     * actions}. The Actions are a chain of
     * {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction actions} combined with an
     * explicit start and end.
     * </p>
     *
     * @return the scenario behaviour in the making
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     */
    public ScenarioBehaviourCreator newScenarioBehavior() {
        return new ScenarioBehaviourCreator(this.usgModelCreator);
    }

    // ---------------------- Actions ----------------------

    /**
     * Creates a new branch action.
     * <p>
     * A representation of the model object 'Branch'. A Branch splits the user flow with a
     * XOR-semantic: one of the included BranchTransitionsis taken depending on the specified branch
     * probabilities. Each BranchTransition contains a nested ScenarioBehaviour, which a user
     * executes once this branch transition is chosen. After execution of the complete nested
     * ScenarioBehaviour, the next action in the user flow after theBranch is its successor action.A
     * constraint ensures that all branchProbabilities of the included BranchTransitions sum up to
     * 1.
     * </p>
     * <p>
     * A Branch offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchActionCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchActionCreator#withSuccessor(ActionCreator)
     * successor} and
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchActionCreator#addToBranchAction(BranchTransitionCreator)
     * BranchTransitions}.
     * </p>
     *
     * @return the branch action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.Branch
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition
     */

    public BranchActionCreator newBranchAction() {
        return new BranchActionCreator();
    }

    /**
     * Creates a new branch transition.
     * <p>
     * A representation of the model object 'Branch Transition'. The BranchTransition is an
     * association class that realises the containment of ScenarioBehaviours in in the branches of a
     * Branch action. It is a separate meta class because it has the additional attribute
     * branchProbability that specifies how probably it is that the references ScenarioBehaviour is
     * executed in the Branch action.See also Branch.
     * </p>
     * <p>
     * BranchTransition offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchTransitionBehaviour#addToBranchTransition(ScenarioBehaviourCreator)
     * branched behaviour},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.BranchTransitionBehaviour#withProbability(double)
     * probability} and needs a mandatory
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator
     * branchedBehaviour}
     * </p>
     *
     * @param branchedBehaviour
     *            {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator
     *            ScenarioBehaviourCreator}
     * @return the branch transition in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     */
    public BranchTransitionCreator newBranchTransition(final ScenarioBehaviourCreator branchedBehaviour) {
        return new BranchTransitionCreator(branchedBehaviour);
    }

    /**
     * Creates a new delay action.
     * <p>
     * A representation of the model object 'Delay'. A Delay represents a timing delay as a
     * RandomVariable between two user actions. The Delay is included into the usage model to
     * express that users do not call system services in direct successions,but usually need some
     * time to determine their next action. User delays are for example useful,if a performance
     * analyst wants to determine the execution time for a complete scenario behaviour(instead of a
     * single service), which needs to include user delays.
     * </p>
     * <p>
     * Delay offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.DelayActionCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.DelayActionCreator#withSuccessor(ActionCreator)
     * successor} and needs a mandatory time specification.
     * </p>
     *
     * @param timeSpecification
     *            String
     * @return the delay action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.Delay
     */
    public DelayActionCreator newDelayAction(final String timeSpecification) {
        return new DelayActionCreator(timeSpecification);
    }

    /**
     * Creates a new entry level system call action.
     * <p>
     * A representation of the model object 'Entry Level System Call'. An EntryLevelSystemCall
     * models the call to a service provided by a system. Therefore, anEntryLevelSystemCall
     * references a ProvidedRole of a PCM System, from which the called interface and the providing
     * component within the system can be derived, and a Signature specifying the called service.
     * Notice, that the usage model does not permit the domain expert to model calls directly to
     * components, but only to system roles. This decouples the System structure(i.e., the
     * component-based software architecture model and its allocation) from the UsageModeland the
     * software architect can change the System (e.g., include new components, remove existing
     * components, or change their wiring or allocation) independently from the domain expert, if
     * the system provided roles are not affected. EntryLevelSystemCalls may include a set of input
     * parameter characterisations and a set of output parameter characterisations (as described in
     * the pcm::parameters package). However, the random variables characterising the input
     * parameters like NUMBER_OF_ELEMENTS can not depend on other variables in the usage model. They
     * have to be composed from literals only including literals describing random variables having
     * a certain fixed distribution.
     * </p>
     * <p>
     * Entry level system call offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.EntryLevelSystemCallCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.EntryLevelSystemCallCreator#withSuccessor(ActionCreator)
     * successor},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.EntryLevelSystemCallCreator#addToEntryLevelSystemCallInput(VariableUsageCreator)
     * input parameter usage},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.EntryLevelSystemCallCreator#addToEntryLevelSystemCallOutput(VariableUsageCreator)
     * output parameter usage} and needs a mandatory
     * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole operation provided role}
     * and related {@link org.palladiosimulator.pcm.repository.OperationSignature operation
     * signature}.
     * </p>
     *
     * @param operationProvidedRole
     *            {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     *            OperationProvidedRole}
     * @param operationSignature
     *            {@link org.palladiosimulator.pcm.repository.OperationSignature OperationSignature}
     * @return the entry level system call action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    private EntryLevelSystemCallCreator newEntryLevelSystemCall(final OperationProvidedRole operationProvidedRole,
            final OperationSignature operationSignature) {
        if (!operationProvidedRole.getProvidedInterface__OperationProvidedRole()
            .getSignatures__OperationInterface()
            .contains(operationSignature)) {
            throw new IllegalArgumentException("No OperationSignature with name " + operationSignature.getEntityName()
                    + " for OperationProvidedRole " + operationProvidedRole.getEntityName() + " exits.");
        }
        return new EntryLevelSystemCallCreator(operationSignature, operationProvidedRole);
    }

    /**
     * TODO New entry level system call.
     *
     * @param operationProvided
     *            the operation provided
     * @return the entry level system call creator
     */
    public EntryLevelSystemCallCreator newEntryLevelSystemCall(final OperationProvidedSignatureRole operationProvided) {
        return newEntryLevelSystemCall(operationProvided.getRole(), operationProvided.getSignature());
    }

    /**
     * Creates a new loop action.
     * <p>
     * A representation of the model object 'Loop'. A Loop models a repeated sequence of actions in
     * the user flow. It contains a nested ScenarioBehaviour specifying the loop body, and a
     * RandomVariable specifying the number of iterations.
     * </p>
     * <p>
     * Loop offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.LoopActionCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.LoopActionCreator#withSuccessor(ActionCreator)
     * successor}, and needs a mandatory iteration and
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator
     * body behaviour}.
     * </p>
     *
     * @param body
     *            behaviour
     *            {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator
     *            ScenarioBehaviourCreator}
     * @param iteration
     *            the iteration
     * @return the delay action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.Loop
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     */
    public LoopActionCreator newLoopAction(final String iteration, final ScenarioBehaviourCreator bodyBehaviour) {
        return new LoopActionCreator(iteration, bodyBehaviour);
    }

    /**
     * Creates a new start action.
     * <p>
     * A representation of the model object 'Start'. Each ScenarioBehaviour has exactly one Start
     * action which marks the action where the control flows begins. Start actions have no
     * predecessor.
     * </p>
     * <p>
     * A Start offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StartActionCreator#withName(String)
     * name} and
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StartActionCreator#withSuccessor(ActionCreator)
     * successor}
     * </p>
     *
     * @return the start action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.Start
     */
    public StartActionCreator newStartAction() {
        return new StartActionCreator();
    }

    /**
     * Creates a new stop action.
     * <p>
     * A representation of the model object 'Stop'. Each ScenarioBehaviour has exactly one Stop
     * action which marks the action where the control flows ends. Stop actions have no successor.
     * </p>
     * <p>
     * Stop offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StopActionCreator#withName(String)
     * name}
     * </p>
     *
     * @return the stop action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.usagemodel.Stop
     */
    public StopActionCreator newStopAction() {
        return new StopActionCreator();
    }

    // ---------------------- Workload ----------------------

    /**
     * Creates a new closed workload.
     * <p>
     * A representation of the model object 'Closed Workload'. ClosedWorkload specifies directly the
     * (constant) user population and a think time. It models that a fixed number of users execute
     * their scenario, then wait (or think) for the specified amount of think time as a
     * RandomVariable, and then reenter the system executing their scenario again.Performance
     * analysts use closed workloads to model scenarios, where the number of users is known(e.g., a
     * fixed number of users in a company).
     * </p>
     * <p>
     * Closed Workload offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.ClosedWorkload#withPopulation(int)
     * population}, and needs a mandatory think time.
     * </p>
     *
     * @param thinkTime
     *            String
     * @return the closed workload in the making
     * @see org.palladiosimulator.pcm.usagemodel.Workload
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     */
    public ClosedWorkloadCreator newClosedWorkload(final String thinkTime) {
        return new ClosedWorkloadCreator(this.usgModelCreator, thinkTime);
    }

    /**
     * Creates a new open workload.
     * <p>
     * A representation of the model object 'Open Workload'. OpenWorkload specifies usage intensity
     * with an inter-arrival time (i.e., the time between two user arrivals at the system) as a
     * RandomVariable with an arbitrary probability distribution. It models that an infinite stream
     * of users arrives at a system. The users execute their scenario, and then leave the system.
     * The user population (i.e., the number of users concurrently present in a system) is not fixed
     * in an OpenWorkload.
     * </p>
     * <p>
     * Open Workload needs a mandatory inter arrival time.
     * </p>
     *
     * @param interArrivalTime
     *            String
     * @return the open workload in the making
     * @see org.palladiosimulator.pcm.usagemodel.Workload
     * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload
     */
    public OpenWorkloadCreator newOpenWorkload(final String interArrivalTime) {
        return new OpenWorkloadCreator(this.usgModelCreator, interArrivalTime);
    }

    // ---------------------- Shared ----------------------

    private System getSystemByName(final String name) {
        final List<System> collect = this.systems.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName()
                .contentEquals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            // TODO this.logger.warning("More than repository with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    /**
     * Creates a new variable usage.
     * <p>
     * A representation of the model object 'Variable Usage'. Variable usages are used to
     * characterise variables like input and output variables or component parameters. They contain
     * the specification of the variable as VariableCharacterisation and also refer to the name of
     * the characterised variable in its namedReference association. Note that it was an explicit
     * design decision to refer to variable names instead of the actual variables (i.e., by refering
     * to Parameter class). It eased the writing of transformations (DSolver as well as SimuCom) but
     * put some complexity in the frontend for entering the variable usages.
     * </p>
     * <p>
     * Variable Usage offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withName( String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator# withVariableCharacterisation(String,VariableCharacterisationType)
     * variable characterisation} and needs a mandatory namespace reference and inner references.
     *
     * </p>
     *
     * @param namespaceReference
     *            String
     * 
     * @param innerReferences
     *            String...
     * 
     * @return the variable usage in the making
     * 
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * 
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * 
     * @see de.uka.ipd.sdq.stoex.NamespaceReference
     */
    public VariableUsageCreator newVariableUsage(final String namespaceReference, final String... innerReferences) {
        return new VariableUsageCreator(namespaceReference, innerReferences);
    }

    /**
     * Creates a new variable usage.
     * <p>
     * A representation of the model object 'Variable Usage'. Variable usages are used to
     * characterise variables like input and output variables or component parameters. They contain
     * the specification of the variable as VariableCharacterisation and also refer to the name of
     * the characterised variable in its namedReference association. Note that it was an explicit
     * design decision to refer to variable names instead of the actual variables (i.e., by
     * referring to Parameter class). It eased the writing of transformations (DSolver as well as
     * SimuCom) but put some complexity in the front end for entering the variable usages.
     * </p>
     * <p>
     * Variable Usage offers the characteristics
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withVariableCharacterisation(String,VariableCharacterisationType)
     * variable characterisation} and needs a mandatory variable reference.
     *
     * </p>
     *
     * @param variableReference
     *            String
     * @return the variable usage in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * @see de.uka.ipd.sdq.stoex.VariableReference
     */
    public VariableUsageCreator newVariableUsage(final String variableReference) {
        return new VariableUsageCreator(variableReference);
    }

    // ---------------------- Fetch Methods ----------------------

    /**
     * Extracts the assembly context referenced by <code>name</code> from the system.
     * <p>
     * This method throws a FluentApiException if no parameter is present under the given
     * <code>name</code>. If more than one parameter with this <code>name</code> is present, the
     * org.palladiosimulator.generator.fluent.usagemodel chooses the first parameter it finds.
     * </p>
     *
     * @param name
     *            the name
     * @return the assembly context
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContext fetchOffAssemblyContextByName(final String name) {
        /*
         * if (this.systems.isEmpty()) { throw new
         * IllegalArgumentException("The referred System was not set correctly in FluentUsageModelFactory"
         * ); }
         */
        final String[] split = name.split("\\."); // 0 - System; 1 - name
        if (split.length != 2) {
            throw new IllegalArgumentException(
                    name + " is not correct syntax to get the assembly context out of system.");
        }
        final System system = getSystemByName(split[0]);
        return system.getAssemblyContexts__ComposedStructure()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(split[1]))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No AssemblyContext with name " + split[1] + " found."));
    }

    /**
     * Extracts the operation provided role referenced by <code>name</code> from the system.
     * <p>
     * This method throws a FluentApiException if no parameter is present under the given
     * <code>name</code>. If more than one parameter with this <code>name</code> is present, the
     * org.palladiosimulator.generator.fluent.usagemodel chooses the first parameter it finds.
     * </p>
     *
     * @param name
     *            the name
     * @return the operation provided role
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    private OperationProvidedRole fetchOffOperationProvidedRoleByName(final System system, final String name) {

        OperationProvidedRole role = null;

        final ProvidedRole r = system.getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No ProvidedRole with name " + name + " found."));

        if (r instanceof OperationProvidedRole) {
            role = (OperationProvidedRole) r;
        }

        IllegalArgumentException.throwIfNull(role, "No OperationProvidedRole with name " + name + " found.");
        return role;
    }

    /**
     * Extracts the operation provided role referenced by <code>operationProvidedRole</code> from
     * the system and the dependend <code>operationSignature</code> from the repository.
     * <p>
     * This method throws a FluentApiException if no parameter is present under the given
     * <code>operationProvidedRole</code> or <code>operationSignature</code>. If more than one
     * parameter with the given Strings is present, the
     * org.palladiosimulator.generator.fluent.usagemodel chooses each time the first parameter it
     * finds.
     * </p>
     *
     * @param operationProvidedRole
     *            the operation provided role
     * @param operationSignature
     *            the operation signature
     * @return Operation Provided Role & OperationSIgnature combined in one class
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.OperationProvidedSignatureRole
     */
    public OperationProvidedSignatureRole fetchOffOperationRoleAndSignature(final String systemName,
            final String operationProvidedRole, final String operationSignature) {

        final System system = getSystemByName(systemName);
        
        final OperationProvidedRole role = fetchOffOperationProvidedRoleByName(system, operationProvidedRole);
        final OperationSignature sig = role.getProvidedInterface__OperationProvidedRole()
            .getSignatures__OperationInterface()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(operationSignature))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No OperationSignature with name " + operationSignature
                    + " for OperationProvidedRole " + operationProvidedRole + " found."));

        return new OperationProvidedSignatureRole(role, sig);
    }
}
