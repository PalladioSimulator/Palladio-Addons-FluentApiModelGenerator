package apiControlFlowInterfaces;

import apiControlFlowInterfaces.LoopFollow.LoopExitFollow;

public interface LoopAction {

	public LoopFollow withStartAction();

	public LoopFollow internalAction();

	public LoopFollow externalCallAction();

	public LoopFollow emitEventAction();

	public LoopFollow setVariableAction();

	public LoopFollow acquireAction();

	public LoopFollow releaseAction();

	public LoopFollow loopAction();

	public LoopFollow collectionIteratorAction();

	public LoopFollow branchAction();

	public LoopFollow forkAction();

	public LoopFollow recoveryAction();

	public LoopExitFollow stopAction();
}
