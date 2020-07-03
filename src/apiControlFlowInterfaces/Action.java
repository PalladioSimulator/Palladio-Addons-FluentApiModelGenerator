package apiControlFlowInterfaces;

import repositoryStructure.SeffCreator;
import repositoryStructure.seff.AcquireActionCreator;
import repositoryStructure.seff.EmitEventActionCreator;
import repositoryStructure.seff.ExternalCallCreator;
import repositoryStructure.seff.InternalCallCreator;
import repositoryStructure.seff.LoopActionCreator;
import repositoryStructure.seff.ReleaseActionCreator;
import repositoryStructure.seff.SetVariableActionCreator;

public interface Action {

//	public Follow withStartAction();

	public InternalCallCreator internalAction();

	public ExternalCallCreator externalCallAction();

	public EmitEventActionCreator emitEventAction();

	public SetVariableActionCreator setVariableAction();

	public AcquireActionCreator acquireAction();

	public ReleaseActionCreator releaseAction();

	public LoopActionCreator loopAction();

	public Follow collectionIteratorAction();

	public Follow branchAction();

	public Follow forkAction();

	public Follow recoveryAction();

	public SeffCreator stopAction();
}
