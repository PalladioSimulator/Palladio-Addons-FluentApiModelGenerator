package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import repositoryStructure.components.seff.AcquireActionCreator;
import repositoryStructure.components.seff.BranchActionCreator;
import repositoryStructure.components.seff.CollectionIteratorActionCreator;
import repositoryStructure.components.seff.EmitEventActionCreator;
import repositoryStructure.components.seff.ExternalCallActionCreator;
import repositoryStructure.components.seff.ForkActionCreator;
import repositoryStructure.components.seff.InternalActionCreator;
import repositoryStructure.components.seff.LoopActionCreator;
import repositoryStructure.components.seff.RecoveryActionCreator;
import repositoryStructure.components.seff.ReleaseActionCreator;
import repositoryStructure.components.seff.SetVariableActionCreator;
import repositoryStructure.components.seff.StartActionCreator;
import repositoryStructure.components.seff.StopActionCreator;

public interface Action {

	public InternalActionCreator internalCallAction();

	public ExternalCallActionCreator externalCallAction();

	public EmitEventActionCreator emitEventAction();

	public SetVariableActionCreator setVariableAction();

	public AcquireActionCreator acquireAction();

	public ReleaseActionCreator releaseAction();

	public LoopActionCreator loopAction();

	public CollectionIteratorActionCreator collectionIteratorAction();

	public BranchActionCreator branchAction();

	public ForkActionCreator forkAction();

	public RecoveryActionCreator recoveryAction();

	public StopActionCreator stopAction();

	public interface Start {

		public StartActionCreator withStartAction();

	}
	
	public interface Seff {
		public Seff onSignature(Signature signature);
		public Seff withSeffTypeID(String seffTypeID);
		public Start withSeffBehaviour();
		ServiceEffectSpecification build();
	}
}
