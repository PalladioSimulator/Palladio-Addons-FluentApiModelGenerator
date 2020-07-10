package apiControlFlowInterfaces.seff;

import repositoryStructure.components.seff.AcquireActionCreator;
import repositoryStructure.components.seff.BranchActionCreator;
import repositoryStructure.components.seff.CollectionIteratorActionCreator;
import repositoryStructure.components.seff.EmitEventActionCreator;
import repositoryStructure.components.seff.ExternalCallActionCreator;
import repositoryStructure.components.seff.ForkActionCreator;
import repositoryStructure.components.seff.InternalActionCreator;
import repositoryStructure.components.seff.InternalCallActionCreator;
import repositoryStructure.components.seff.LoopActionCreator;
import repositoryStructure.components.seff.RecoveryActionCreator;
import repositoryStructure.components.seff.ReleaseActionCreator;
import repositoryStructure.components.seff.SetVariableActionCreator;

public interface ActionSeff {
	public AcquireActionCreator acquireAction();

	public BranchActionCreator branchAction();

	public CollectionIteratorActionCreator collectionIteratorAction();

	public EmitEventActionCreator emitEventAction();

	public ExternalCallActionCreator externalCallAction();

	public ForkActionCreator forkAction();

	public InternalActionCreator internalAction();

	public InternalCallActionCreator internalCallAction();

	public LoopActionCreator loopAction();

	public RecoveryActionCreator recoveryAction();

	public ReleaseActionCreator releaseAction();

	public SetVariableActionCreator setVariableAction();

	public StopSeff stopAction();
}