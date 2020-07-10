package repositoryStructure;

import org.eclipse.emf.cdo.CDOObject;

public abstract class Entity {

	protected RepositoryCreator repository;

	protected String name;
//	protected String id;

	/**
	 * Defines the name of the current entity.
	 * 
	 * @param name
	 * @return current entity
	 */
	public Entity withName(String name) {
		this.name = name;
		return this;
	}

//	public Entity withId(String id) {
//		this.id = id;
//		return this;
//	}

	protected abstract CDOObject build();

}
