package apiControlFlowInterfaces;

import repositoryStructure.components.Component;
import repositoryStructure.interfaces.Interface;

public interface Repo extends Entity, Finish {
	Repo withName(String name);

	Repo withId(String id);

	Repo withDescription(String description);
	
	RepoAddition addToRepository(Component o);
	RepoAddition addToRepository(Interface o);
//	RepoAddition addToRepository(FailureType o);
//	RepoAddition addToRepository(DataType o);
}