package apiControlFlowInterfaces;

import repositoryStructure.SeffCreator;

public interface Action {

	public Follow withStartAction();

	public Follow internalAction();

	public Follow externalCallAction();

	public Follow emitEventAction();

	public Follow setVariableAction();

	public Follow acquireAction();

	public Follow releaseAction();

	public Follow loopAction();

	public Follow collectionIteratorAction();

	public Follow branchAction();

	public Follow forkAction();

	public Follow recoveryAction();

	public SeffCreator stopAction();
}
