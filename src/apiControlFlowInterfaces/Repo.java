package apiControlFlowInterfaces;

public interface Repo extends RepoAddition {

	/**
	 * Defines the name of the current entity.
	 * 
	 * @param name
	 * @return current entity
	 */
	Repo withName(String description);

	/**
	 * Adds a description to the repository.
	 * 
	 * @param description
	 * @return the repository
	 */
	Repo withDescription(String description);

}