package apiControlFlowInterfaces;

import apiControlFlowInterfaces.LoopAction.StartLoop;
import repositoryStructure.seff.StartActionCreator;

public interface LoopFollow {

	public LoopAction followedBy();

	
	public interface LoopExitFollow {
		
		public Action followedOutsideLoopBy();
		
		public interface LoopStart {
			public StartLoop startAction();
		}
	}
}
