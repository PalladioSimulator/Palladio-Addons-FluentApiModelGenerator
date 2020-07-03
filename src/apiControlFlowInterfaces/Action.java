package apiControlFlowInterfaces;

import repositoryStructure.seff.AcquireActionCreator;
import repositoryStructure.seff.BranchActionCreator;
import repositoryStructure.seff.CollectionIteratorActionCreator;
import repositoryStructure.seff.EmitEventActionCreator;
import repositoryStructure.seff.ExternalCallCreator;
import repositoryStructure.seff.ForkActionCreator;
import repositoryStructure.seff.InternalCallCreator;
import repositoryStructure.seff.LoopActionCreator;
import repositoryStructure.seff.RecoveryActionCreator;
import repositoryStructure.seff.ReleaseActionCreator;
import repositoryStructure.seff.SetVariableActionCreator;
import repositoryStructure.seff.StopActionCreator;

public interface Action {

	public InternalCallCreator internalAction();

	public ExternalCallCreator externalCallAction();

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
}
