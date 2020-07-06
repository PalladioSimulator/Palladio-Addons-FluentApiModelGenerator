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
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.components.seff.SeffCreator.BodyBehaviour;

public interface Seff {

	public Seff onSignature(Signature signature);

	public Seff withSeffTypeID(String seffTypeID);

	public Seff withInternalBehaviour(Internal internalBehaviour);

	public StartSeff withSeffBehaviour();

	ServiceEffectSpecification build();

	public interface ActionSeff {
		public AcquireSeff acquireAction();

		public BranchSeff branchAction();

		public CollectionSeff collectionIteratorAction();

		public EmitEventSeff emitEventAction();

		public ExternalCallSeff externalCallAction();

		public ForkSeff forkAction();

		public InternalSeff internalAction();

		public InternalCallSeff internalCallAction();

		public LoopSeff loopAction();

		public RecoverySeff recoveryAction();

		public ReleaseSeff releaseAction();

		public SetVariableSeff setVariableAction();

		public StopSeff stopAction();
	}

	public interface FollowSeff {
		public ActionSeff followedBy();
	}

	public interface StartSeff {
		public StartActionSeff withStartAction();
	}

	public interface StartActionSeff {
		public StartActionSeff withName(String name);

		public StartActionSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StartActionSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StartActionSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface AcquireSeff {
		public AcquireSeff withName(String name);

		public AcquireSeff withPassiveResource(PassiveResource passiveResource);

		public AcquireSeff withTimeoutValue(Double timeoutValue);

		public AcquireSeff isTimeout(Boolean isTimeout);

		public AcquireSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public AcquireSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public AcquireSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface BranchSeff {
		public BranchSeff withName(String name);

		public BranchSeff withGuardedBranchTransition(String branchCondition_stochasticExpression,
				SeffCreator branchActions, BodyBehaviour bodyBehaviourType);

		public BranchSeff withProbabilisticBranchTransition(Double branchProbability, SeffCreator branchActions,
				BodyBehaviour bodyBehaviourType);

		public BranchSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public BranchSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public BranchSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface CollectionSeff {
		public CollectionSeff withName(String name);

		public CollectionSeff withParameter(Parameter parameter);

		public CollectionSeff withLoopBody(SeffCreator loopBody);

		public CollectionSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public CollectionSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public CollectionSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface EmitEventSeff {
		public EmitEventSeff withName(String name);

		public EmitEventSeff withEventType(EventType eventType);

		public EmitEventSeff withSourceRole(SourceRole sourceRole);

		public EmitEventSeff withInputVariableUsage();

		public ActionSeff followedBy();

	}

	public interface ExternalCallSeff {
		public ExternalCallSeff withName(String name);

		public ExternalCallSeff withRetryCount(Integer retryCount);

		public ExternalCallSeff withCalledService(OperationSignature signature);

		public ExternalCallSeff withRequiredRole(OperationRequiredRole requiredRole);

		public ExternalCallSeff withInputVariableUsage();

		public ExternalCallSeff withReturnVariableUsage();

		public ExternalCallSeff withFailureType(FailureType failure);

		public ActionSeff followedBy();

	}

	public interface ForkSeff {
		public ForkSeff withName(String name);

		public ForkSeff withOutputParameterUsageAtSynchronisationPoint(VariableUsage variableUsage);

		public ForkSeff withSynchronousForkedBehaviourAtSynchronisationPoint(SeffCreator forkedBehaviours);

		public ForkSeff withAsynchronousForkedBehaviour(SeffCreator forkedBehaviours);

		public ForkSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public ForkSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public ForkSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface InternalSeff {

		public InternalSeff withName(String name);

		public InternalSeff withInternalFailureOccurrenceDescription(Double failureProbability,
				SoftwareInducedFailureType failureType);

		public InternalSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public InternalSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public InternalSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface InternalCallSeff {
		public InternalCallSeff withName(String name);

		public InternalCallSeff withVaribleUsage(VariableUsage inputVariableUsage);

		public InternalCallSeff withInternalBehaviour(SeffCreator seff);

		public InternalCallSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public InternalCallSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public InternalCallSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface LoopSeff {

		public LoopSeff withName(String name);

		public LoopSeff withIterationCount(String iterationCount_stochasticExpression);

		public LoopSeff withLoopBody(SeffCreator loopBody);

		public LoopSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public LoopSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public LoopSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface RecoverySeff {
		public RecoverySeff withName(String name);

		public RecoverySeff withPrimaryBehaviour();

		public RecoverySeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public RecoverySeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public RecoverySeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface ReleaseSeff {
		public ReleaseSeff withName(String name);

		public ReleaseSeff withPassiveResource(PassiveResource passiveResource);

		public ReleaseSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public ReleaseSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public ReleaseSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface SetVariableSeff {
		public SetVariableSeff withName(String name);

		public SetVariableSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public SetVariableSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public SetVariableSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		public ActionSeff followedBy();

	}

	public interface StopSeff {

		public StopSeff withName(String name);

		public SeffCreator createBehaviourNow();

		public StopSeff withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StopSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StopSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	}

}
