package apiControlFlowInterfaces;

public interface LoopFollow {

	public LoopAction followedInsideLoopBy();

	
	public interface LoopExitFollow {
		
		public Action followedOutsideLoopBy();
		
		public interface LoopStart {
			public LoopFollow startAction();
		}
	}
}
