package repositoryStructure;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Inter;
import apiControlFlowInterfaces.RepoAddition;

public class InterfaceCreator implements Inter {
	RepositoryCreator repository;

	String name;
	String id;

	public InterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public Inter withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Inter withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public RepoAddition addToRepository() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);
		// set repository? what about roles etc; yeah, this programming style is
		// horrible, I know - TODO!
		this.repository.interfaces.add(interfce);
		this.repository.currentInterface = null;
		return this.repository;
	}

	@Override
	public Repository build() {
		return this.addToRepository().build();
	}
}
