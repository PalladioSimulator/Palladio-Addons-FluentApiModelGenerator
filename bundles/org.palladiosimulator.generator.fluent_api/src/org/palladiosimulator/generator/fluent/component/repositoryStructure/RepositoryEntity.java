package org.palladiosimulator.generator.fluent.component.repositoryStructure;

import org.palladiosimulator.generator.fluent.shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every RepositoryEntity belongs to a repository and has a name.
 *
 * @author Louisa Lambrecht
 */
public abstract class RepositoryEntity extends Entity {

    protected RepositoryCreator repository;

}
