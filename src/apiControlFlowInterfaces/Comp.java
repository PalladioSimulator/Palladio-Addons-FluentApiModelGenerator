package apiControlFlowInterfaces;

public interface Comp extends Entity, Finish {
	Comp withName(String name);

	Comp withId(String id);

	Comp ofType(String todo);

}