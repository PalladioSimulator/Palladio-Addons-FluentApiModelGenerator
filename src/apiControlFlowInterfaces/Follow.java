package apiControlFlowInterfaces;

public interface Follow {

	public Action followedBy();

	public interface Start {

		public Follow withStartAction();

	}

}
