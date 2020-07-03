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
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.seff.AcquireActionCreator;
import repositoryStructure.seff.BranchActionCreator;
import repositoryStructure.seff.CollectionIteratorActionCreator;
import repositoryStructure.seff.EmitEventActionCreator;
import repositoryStructure.seff.ExternalCallCreator;
import repositoryStructure.seff.ForkActionCreator;
import repositoryStructure.seff.LoopActionCreator;
import repositoryStructure.seff.RecoveryActionCreator;
import repositoryStructure.seff.ReleaseActionCreator;
import repositoryStructure.seff.StopActionCreator;

public interface LoopAction {

	public InternalLoop internalAction();

	public ExternalLoop externalCallAction();

	public EmitEventLoop emitEventAction();

	public SetVariableLoop setVariableAction();

	public AcquireLoop acquireAction();

	public ReleaseLoop releaseAction();

	public LoopActionCreator loopAction();

	public CollectionIteratorActionCreator collectionIteratorAction();

	public BranchActionCreator branchAction();

	public ForkActionCreator forkAction();

	public RecoveryActionCreator recoveryAction();

	public StopActionCreator stopAction();

	public interface AbstractActionLoop extends LoopFollow {

		public AbstractActionLoop withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public AbstractActionLoop withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public AbstractActionLoop withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	}

	public interface StartLoop extends AbstractActionLoop {
	}

	public interface InternalLoop extends AbstractActionLoop {

		public InternalLoop withInternalFailureOccurrenceDescription(Double failureProbability,
				SoftwareInducedFailureType failureType);
		
	}

	public interface ExternalLoop extends LoopFollow {
		public ExternalLoop withRetryCount(Integer retryCount);
		public ExternalLoop withCalledService(OperationSignature signature);
		public ExternalLoop withRequiredRole(OperationRequiredRole requiredRole);
		public ExternalLoop withInputVariableUsage();
		public ExternalLoop withReturnVariableUsage();
		public ExternalLoop withFailureType(FailureType failure);

	}
	
	public interface EmitEventLoop extends LoopFollow {

		public EmitEventLoop withEventType(EventType eventType);
		public EmitEventLoop withSourceRole(SourceRole sourceRole);
		public EmitEventLoop withInputVariableUsage();
	}
	
	public interface SetVariableLoop extends AbstractActionLoop {

	}
	
	public interface AcquireLoop extends AbstractActionLoop {
		public AcquireLoop withPassiveResource(PassiveResource passiveResource);
		public AcquireLoop withTimeoutValue(Double timeoutValue);
		public AcquireLoop isTimeout(Boolean isTimeout);
	}
	
	public interface ReleaseLoop extends AbstractActionLoop {
		public ReleaseLoop withPassiveResource(PassiveResource passiveResource);
	}
	
	public interface StopLoop {
		public StopLoop withResourceDemand(String specification_stochasticExpression,
				ProcessingResourceType processingResource);

		public StopLoop withInfrastructureCall(String numberOfCalls_stochasticExpression,
				InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
				VariableUsage... variableUsages);

		public StopLoop withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
				ResourceRequiredRole requiredRole, VariableUsage... variableUsages);
	
		public Action followedOutsideLoopBy();
	}
	




}
