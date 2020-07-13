package repositoryStructure;

import org.eclipse.emf.cdo.CDOObject;

public abstract class Entity {

	protected RepositoryCreator repository;

	protected String name;
//	protected String id;

	/**
	 * Defines the <i>unique</i> name of the current entity. Already created
	 * entities are referenced by this name. Only entities with a unique name
	 * can be fetched from the repository.
	 * 
	 * @param name
	 * @return current entity in the making
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
