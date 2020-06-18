package repositoryStructure.components;

import org.palladiosimulator.pcm.repository.RepositoryComponent;

import apiControlFlowInterfaces.Comp;
import repositoryStructure.RepositoryCreator;

public abstract class Component implements Comp{

	protected String name;
	protected String id;
	
	protected RepositoryCreator repository;
	
	public abstract RepositoryComponent build();
}
