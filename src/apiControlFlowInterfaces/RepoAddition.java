package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;

import repositoryStructure.components.Component;
import repositoryStructure.interfaces.Interface;

public interface RepoAddition extends Finish {

	Comp aComponent();

	Inter anInterface();
	
	RepoAddition addToRepository(Component o);
	RepoAddition addToRepository(Interface o);
//	RepoAddition addToRepository(FailureType o);
//	RepoAddition addToRepository(DataType o);
	
	Repository build();
}
