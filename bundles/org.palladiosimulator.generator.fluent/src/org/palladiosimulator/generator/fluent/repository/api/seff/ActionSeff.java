package org.palladiosimulator.generator.fluent.repository.api.seff;

import org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator;

/**
 * TODO
 */
public interface ActionSeff {
    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.AcquireAction AcquireAction}.
     * <p>
     * In an RDSEFF, component developers can specify an AcquireAction, which references a passive
     * resource types. Once analysis tools execute this action, they decrease the amount of items
     * available from the referenced passive resource type by one, if at least one item is
     * available. If none item is available, because other, concurrently executed requests have
     * acquired all of them, analysis tools enqueue the current request (first-come-first-serve
     * scheduling policy) and block it's further execution.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withPassiveResource(org.palladiosimulator.pcm.repository.PassiveResource)
     * withPassiveResource(PassiveResource)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withTimeoutValue(Double)
     * withTimeoutValue(Double)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#isTimeout(Boolean)
     * isTimeout(Boolean)}.
     * </p>
     *
     * @return the acquire action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.AcquireActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    AcquireActionCreator acquireAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.BranchAction BranchAction}.
     * <p>
     * The BranchAction splits the RDSEFF control flow with an XOR-semantic, meaning that the
     * control flow continues on exactly one of its attached AbstractBranchTransitions. The RDSEFF
     * supports two different kinds of branch transitions, GuardedBranchTransitions, and
     * ProbabilisticBranchTransitions. RDSEFFs do not allow to use both kinds of transitions on a
     * single BranchAction.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withGuardedBranchTransition(String, org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff.SeffCreator, String)
     * withGuardedBranchTransition(String, SeffCreator, String)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withProbabilisticBranchTransition(Double, org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff.SeffCreator, String)
     * withProbabilisticBranchTransition(Double, SeffCreator, String)}. Do not use both methods
     * within the same branch action.
     * </p>
     *
     * @return the branch action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.BranchActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    BranchActionCreator branchAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.CollectionIteratorAction
     * CollectionIteratorAction}.
     * <p>
     * A Collection Iterator Action models the repeated execution of its inner
     * ResourceDemandingBehaviour for each element of a collection data type.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withParameter(org.palladiosimulator.pcm.repository.Parameter)
     * withParameter(Parameter)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withLoopBody(Seff)
     * withLoopBody(Seff)}.
     * </p>
     *
     * @return the collection iterator action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.CollectionIteratorActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    CollectionIteratorActionCreator collectionIteratorAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.EmitEventAction EmitEventAction}.
     * <p>
     * EmitEventAction specifies when a component emits an event during its processing. The
     * EmitEventAction defines which type of events are emitted, their characteristics and the
     * SourceRole that triggered. Each EmitEventAction is limited to one type of events.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withEventType(org.palladiosimulator.pcm.repository.EventType)
     * withEventType(EventType)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withSourceRole(org.palladiosimulator.pcm.repository.SourceRole)
     * withSourceRole(SourceRole)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withInputVariableUsage(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withInputVariableUsage(VariableUsageCreator)}.
     * </p>
     *
     * @return the emit event action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.EmitEventActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    EmitEventActionCreator emitEventAction();

    /**
     * Creates an {@link org.palladiosimulator.pcm.seff.ExternalCallAction ExternalCallAction}.
     * <p>
     * ExternalCallAction models the invocation of a service specified in a required interface.
     * Therefore, it references a Role, from which the providing component can be derived, and a
     * Signature to specify the called service. ExternalCallActions do not have resource demands by
     * themselves. Component developers need to specify the resource demand of the called service in
     * the RDSEFF of that service.<br>
     * ExternalCallActions may contain two sets of VariableUsages specifying input parameter
     * characterisations and output parameter characterisations respectively. VariableUsages for
     * input parameters may only reference IN or INOUT parameters of the call's referenced
     * signature. The random variable characterisation inside such a VariableUsage may be constants,
     * probability distribution functions, or include a stochastic expression involving for example
     * arithmetic operations. The latter models a dependency between the current service's own input
     * parameters and the input parameters of the required service.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withCalledService(org.palladiosimulator.pcm.repository.OperationSignature)
     * withCalledService(OperationSignature)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withRequiredRole(org.palladiosimulator.pcm.repository.OperationRequiredRole)
     * withRequiredRole(OperationRequiredRole)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withRetryCount(Integer)
     * withRetryCount(Integer)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withInputVariableUsage(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withInputVariableUsage(VariableUsageCreator)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withReturnVariableUsage(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withReturnVariableUsage(VariableUsageCreator)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * withFailureType(FailureType)}.
     * </p>
     *
     * @return the external call action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ExternalCallActionCreator#followedBy()
     */
    ExternalCallActionCreator externalCallAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.ForkAction ForkAction}.
     * <p>
     * Fork Action Splits the RDSEFF control flow with an AND-semantic, meaning that it invokes
     * several ForkedBehaviours concurrently. ForkActions allow both asynchronously and
     * synchronously forked behaviours.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withAsynchronousForkedBehaviour(InternalSeff)
     * withAsynchronousForkedBehaviour(InternalSeff)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withSynchronousForkedBehaviourAtSynchronisationPoint(InternalSeff)
     * withSynchronousForkedBehaviourAtSynchronisationPoint(InternalSeff)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withOutputParameterUsageAtSynchronisationPoint(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withOutputParameterUsageAtSynchronisationPoint(VariableUsageCreator)}.
     * </p>
     *
     * @return the fork action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ForkActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    ForkActionCreator forkAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.InternalAction InternalAction}.
     * <p>
     * Internal Action Combines the execution of a number of internal computations by a component
     * service in a single model entity. It models calculations inside a component service, which do
     * not include calls to required services.
     * </p>
     * <p>
     * Offers the specifying method
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#withInternalFailureOccurrenceDescription(Double, org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType)
     * withInternalFailureOccurrenceDescription(Double, SoftwareInducedFailureType)}.
     * </p>
     *
     * @return the internal action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    InternalActionCreator internalAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.InternalCallAction InternalCallAction}.
     * <p>
     * A "SubSEFF"-Action: Realizes an internal method call within a SEFF.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withInternalBehaviour(InternalSeff)
     * withInternalBehaviour(InternalSeff)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withVaribleUsage(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withVaribleUsage(VariableUsageCreator)}.
     * </p>
     *
     * @return the internal call action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.InternalCallActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    InternalCallActionCreator internalCallAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.LoopAction LoopAction}.
     * <p>
     * Models the repeated execution of its inner ResourceDemandingBehaviour for the loop body. The
     * number of repetitions is specified by a random variable evaluating to integer or an IntPMF.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withLoopBody(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff.SeffCreator)
     * withLoopBody(SeffCreator)} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withIterationCount(String)
     * withIterationCount(String)}.
     * </p>
     *
     * @return the loop action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.LoopActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    LoopActionCreator loopAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.RecoveryAction RecoveryAction}.
     * <p>
     * Recover block actions are a generic failure handling technique. A recovery block consists of
     * a a primary algorithm and one or more alternatives that can be used in case of failure. If
     * the primary algorithm fails, the next alternative is chosen. Here the alternatives also
     * support failure types. Alternatives may specify which kind of failures they can handle.
     * </p>
     * <p>
     * Offers the specifying methods
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withAlternativeBehaviour(RecoverySeff)
     * withAlternativeBehaviour(RecoverySeff)},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withPrimaryBehaviour(RecoverySeff)
     * withPrimaryBehaviour(RecoverySeff)}.
     * </p>
     *
     * @return the recovery action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.RecoveryActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    RecoveryActionCreator recoveryAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.ReleaseAction ReleaseAction}.
     * <p>
     * The ReleaseAction increases the number of available item for the given passive resource type,
     * before the current request can continue. It should be to execute by one of the other
     * concurrent requests. Acquisition and release of passive resources happen instantaneously and
     * do not consume any time except for waiting delays before actual acquisition.
     * </p>
     * <p>
     * Offers the specifying method
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#withPassiveResource(org.palladiosimulator.pcm.repository.PassiveResource)
     * withPassiveResource(PassiveResource)}.
     * </p>
     *
     * @return the release action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.ReleaseActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    ReleaseActionCreator releaseAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.SetVariableAction SetVariableAction}.
     * Requires that the service this SEFF is describing has a return type and sets its value.
     * <p>
     * Offers the specifying method
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#withLocalVariableUsage(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator)
     * withLocalVariableUsage(VariableUsageCreator)}.
     * </p>
     *
     * @return the set variable action in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#followedBy()
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.palladiosimulator.generator.fluent.repository.structure.components.seff.SetVariableActionCreator#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    SetVariableActionCreator setVariableAction();

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.StopAction StopAction}.
     * <p>
     * StopActions end a scenario behaviour and contain only a predecessor.
     * </p>
     * <p>
     * Call on
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#createBehaviourNow()
     * createBehaviourNow()} to finish the SEFF/behaviour creation.
     * </p>
     *
     * @return the stop action in the making
     * @see org.org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#withName(String)
     * @see org.org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#withResourceDemand(String,
     *      org.palladiosimulator.pcm.resourcetype.ProcessingResourceType)
     * @see org.org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#withResourceCall(String,
     *      org.palladiosimulator.pcm.resourcetype.ResourceSignature,
     *      org.palladiosimulator.pcm.core.entity.ResourceRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     * @see org.org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#withInfrastructureCall(String,
     *      org.palladiosimulator.pcm.repository.InfrastructureSignature,
     *      org.palladiosimulator.pcm.repository.InfrastructureRequiredRole,
     *      org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator...)
     */
    StopSeff stopAction();
}
