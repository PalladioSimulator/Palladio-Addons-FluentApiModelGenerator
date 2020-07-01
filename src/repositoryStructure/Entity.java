package repositoryStructure;

import org.eclipse.emf.cdo.CDOObject;

public abstract class Entity {

	protected RepositoryCreator repository;

	protected String name;
	protected String id;

	public Entity withName(String name) {
		this.name = name;
		return this;
	}

	public Entity withId(String id) {
		this.id = id;
		return this;
	}
	
	public abstract CDOObject build();
	
	
}
