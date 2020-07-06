package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.StartAction;

import repositoryStructure.components.seff.StartActionCreator;

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
		public StartActionCreator withName(String name);

		public StartActionCreator withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StartActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StartActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
				ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

		StartAction build();
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

		AcquireAction build();
	}

	public interface BranchInternal {

	}

	public interface CollectionInternal {

	}

	public interface EmitEventInternal {

	}

	public interface ExternalCallInternal {

	}

	public interface ForkInternal {

	}

	public interface InternalInternal {

	}

	public interface InternalCallInternal {

	}

	public interface LoopInternal {

	}

	public interface RecoveryInternal {

	}

	public interface ReleaseInternal {

	}

	public interface SetVariableInternal {

	}

	public interface StopInternal {

	}
}
