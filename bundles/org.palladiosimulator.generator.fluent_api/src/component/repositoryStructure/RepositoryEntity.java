package component.repositoryStructure;

import shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every RepositoryEntity belongs to a repository and has a name.
 *
 * @author Louisa Lambrecht
 */
public abstract class RepositoryEntity extends Entity {

    protected RepositoryCreator repository;

}
