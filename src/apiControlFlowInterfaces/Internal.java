package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.components.seff.SeffCreator.BodyBehaviour;

public interface Internal {

	public StartInternal withSeffBehaviour();

	ResourceDemandingInternalBehaviour buildInternalBehaviour();

	public interface ActionInternal {
		public AcquireInternal acquireAction();

		public BranchInternal branchAction();

		public CollectionInternal collectionIteratorAction();

		public EmitEventInternal emitEventAction();

		public ExternalCallInternal externalCallAction();

		public ForkInternal forkAction();

		public InternalInternal internalAction();

		public InternalCallInternal internalCallAction();

		public LoopInternal loopAction();

		public RecoveryInternal recoveryAction();

		public ReleaseInternal releaseAction();

		public SetVariableInternal setVariableAction();

		public StopInternal stopAction();
	}

	public interface FollowInternal {
		public ActionInternal followedBy();
	}

	public interface StartInternal {
		public StartActionInternal withStartAction();
	}

	public interface StartActionInternal {
		public StartActionInternal withName(String name);

		public StartActionInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StartActionInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StartActionInternal withResourceCall(String numberOfCalls_stochasticExpression,
				ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();
	}

	public interface AcquireInternal {
		public AcquireInternal withName(String name);

		public AcquireInternal withPassiveResource(PassiveResource passiveResource);

		public AcquireInternal withTimeoutValue(Double timeoutValue);

		public AcquireInternal isTimeout(Boolean isTimeout);

		public AcquireInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public AcquireInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public AcquireInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();
	}

	public interface BranchInternal {
		public BranchInternal withName(String name);

		public BranchInternal withGuardedBranchTransition(String branchCondition_stochasticExpression,
				SeffCreator branchActions, BodyBehaviour bodyBehaviourType);

		public BranchInternal withProbabilisticBranchTransition(Double branchProbability, SeffCreator branchActions,
				BodyBehaviour bodyBehaviourType);

		public BranchInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public BranchInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public BranchInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface CollectionInternal {
		public CollectionInternal withName(String name);

		public CollectionInternal withParameter(Parameter parameter);

		public CollectionInternal withLoopBody(SeffCreator loopBody);

		public CollectionInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public CollectionInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public CollectionInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface EmitEventInternal {
		public EmitEventInternal withName(String name);

		public EmitEventInternal withEventType(EventType eventType);

		public EmitEventInternal withSourceRole(SourceRole sourceRole);

		public EmitEventInternal withInputVariableUsage();

		public ActionInternal followedBy();

	}

	public interface ExternalCallInternal {
		public ExternalCallInternal withName(String name);

		public ExternalCallInternal withRetryCount(Integer retryCount);

		public ExternalCallInternal withCalledService(OperationSignature signature);

		public ExternalCallInternal withRequiredRole(OperationRequiredRole requiredRole);

		public ExternalCallInternal withInputVariableUsage();

		public ExternalCallInternal withReturnVariableUsage();

		public ExternalCallInternal withFailureType(FailureType failure);

		public ActionInternal followedBy();

	}

	public interface ForkInternal {
		public ForkInternal withName(String name);

		public ForkInternal withOutputParameterUsageAtSynchronisationPoint(VariableUsage variableUsage);

		public ForkInternal withSynchronousForkedBehaviourAtSynchronisationPoint(SeffCreator forkedBehaviours);

		public ForkInternal withAsynchronousForkedBehaviour(SeffCreator forkedBehaviours);

		public ForkInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public ForkInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public ForkInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface InternalInternal {

		public InternalInternal withName(String name);

		public InternalInternal withInternalFailureOccurrenceDescription(Double failureProbability,
				SoftwareInducedFailureType failureType);

		public InternalInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public InternalInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public InternalInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface InternalCallInternal {
		public InternalCallInternal withName(String name);

		public InternalCallInternal withVaribleUsage(VariableUsage inputVariableUsage);

		public InternalCallInternal withInternalBehaviour(SeffCreator seff);

		public InternalCallInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public InternalCallInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public InternalCallInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface LoopInternal {

		public LoopInternal withName(String name);

		public LoopInternal withIterationCount(String iterationCount_stochasticExpression);

		public LoopInternal withLoopBody(SeffCreator loopBody);

		public LoopInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public LoopInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public LoopInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface RecoveryInternal {
		public RecoveryInternal withName(String name);

		public RecoveryInternal withPrimaryBehaviour();

		public RecoveryInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public RecoveryInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public RecoveryInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface ReleaseInternal {
		public ReleaseInternal withName(String name);

		public ReleaseInternal withPassiveResource(PassiveResource passiveResource);

		public ReleaseInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public ReleaseInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public ReleaseInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface SetVariableInternal {
		public SetVariableInternal withName(String name);

		public SetVariableInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public SetVariableInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public SetVariableInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionInternal followedBy();

	}

	public interface StopInternal {

		public StopInternal withName(String name);

		public SeffCreator createBehaviourNow();

		public StopInternal withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StopInternal withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StopInternal withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	}
}
