package apiControlFlowInterfaces;

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

}