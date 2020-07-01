package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;

import repositoryStructure.components.Component;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.interfaces.Interface;

public interface RepoAddition{
	
	RepoAddition addToRepository(CollectionDataType collectionDataType);
	RepoAddition addToRepository(CompositeDataTypeCreator compositeDataType);
	RepoAddition addToRepository(Component c);
	RepoAddition addToRepository(Interface i);
	
	Repository build();
}
