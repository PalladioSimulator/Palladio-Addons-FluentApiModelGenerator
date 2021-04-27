package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.repository.Repository;

public interface Repo extends RepoAddition {

	/**
	 * Defines the name of the repository.
	 * 
	 * @param name
	 * @return this repository
	 */
	Repo withName(String name);

	/**
	 * Defines the description of the repository.
	 * 
	 * @param description
	 * @return this repository
	 */
	Repo withDescription(String description);

	/**
	 * Loads the repository located in <code>path</code> as a Resource and provides
	 * its entities in the fetching methods by calling on the entities with the name
	 * of the repository leading.
	 * 
	 * @param path to the import repository
	 * @return this repository
	 */
	Repo withImportedResource(String path);
	
   /**
     * Provides the entities in the fetching methods of a alread loaded repository
     * by calling on the entities with the name of the repository leading.
     * 
     * @param repository loaded repository to be copied
     * @return this repository
     */
    Repo withImportedResource(Repository repository);

}