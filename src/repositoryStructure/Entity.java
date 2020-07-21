package repositoryStructure;

import java.util.Objects;

import org.eclipse.emf.cdo.CDOObject;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every entity belongs to a repository and has a name.
 * 
 * @author Louisa Lambrecht
 *
 */
public abstract class Entity {

	protected RepositoryCreator repository;

	protected String name;
//	protected String id;

	/**
	 * Defines the <i>unique</i> name of this current entity. Once created entities
	 * are referenced by this name. Only entities with a unique name can be fetched
	 * from the repository.
	 * 
	 * @param name
	 * @return this current entity in the making
	 */
	public Entity withName(String name) {
		Objects.requireNonNull(name, "name must not be null");
		this.name = name;
		return this;
	}

//	public Entity withId(String id) {
//		this.id = id;
//		return this;
//	}

	protected abstract CDOObject build();

}
