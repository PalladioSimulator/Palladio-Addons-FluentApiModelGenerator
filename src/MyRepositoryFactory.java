
import apiControlFlowInterfaces.Repo;
import repositoryStructure.RepositoryCreator;

public class MyRepositoryFactory {

	RepositoryCreator repo;

	// TODO: braucht man diese Klasse und welchen passenderen Namen könnte sie
	// bekommen?
	public Repo createRepository() {
		this.repo = new RepositoryCreator();
		return repo;
	}

}
