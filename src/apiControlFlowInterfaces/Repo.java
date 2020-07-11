package apiControlFlowInterfaces;

public interface Repo extends RepoAddition {

	/**
	 * Defines the name of the repository.
	 * 
	 * @param name
	 * @return the repository
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