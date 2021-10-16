package org.palladiosimulator.generator.fluent.repository.api;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.pcm.repository.Repository;

public interface Repository extends RepositoryAddition {

    /**
     * Defines the name of the repository.
     *
     * @param name
     * @return this repository
     */
    Repository withName(String name);

    /**
     * Defines the description of the repository.
     *
     * @param description
     * @return this repository
     */
    Repository withDescription(String description);

    /**
     * Loads the repository located in <code>path</code> as a Resource and provides its entities in
     * the fetching methods by calling on the entities with the name of the repository leading.
     *
     * @param path
     *            to the import repository
     * @return this repository
     */
    Repository withImportedResource(String path);

    /**
     * Loads the repository located in <code>uri</code> as a Resource and provides its entities in
     * the fetching methods by calling on the entities with the name of the repository leading.
     *
     * @param uri
     *            to the import repository
     * @return this repository
     */
    Repository withImportedResource(URI uri);

    /**
     * Provides the entities in the fetching methods of a already loaded repository by calling on
     * the entities with the name of the repository leading.
     *
     * @param repository
     *            loaded repository to be copied
     * @return this repository
     */
    Repository withImportedResource(Repository repository);
}
