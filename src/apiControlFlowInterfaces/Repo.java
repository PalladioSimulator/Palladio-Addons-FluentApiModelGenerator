package apiControlFlowInterfaces;

public interface Repo extends Entity, Finish {
	Repo withName(String name);

	Repo withId(String id);

	Repo withDescription(String description);
}