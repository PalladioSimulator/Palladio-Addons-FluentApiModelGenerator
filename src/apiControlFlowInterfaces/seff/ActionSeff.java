package apiControlFlowInterfaces.seff;

public interface ActionSeff extends SeffInterfaces{
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