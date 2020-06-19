package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;

import repositoryStructure.components.Component;
import repositoryStructure.interfaces.Interface;

public interface RepoAddition{
	
	RepoAddition addToRepository(Component c);
	RepoAddition addToRepository(Interface i);
//	RepoAddition addToRepository(FailureType ft);
//	RepoAddition addToRepository(DataType dt);
	
	Repository build();
}
