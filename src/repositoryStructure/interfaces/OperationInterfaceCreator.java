package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class OperationInterfaceCreator extends Interface {
	RepositoryCreator repository;

	String name;
	String id;

	public OperationInterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public OperationInterfaceCreator withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public OperationInterfaceCreator withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public org.palladiosimulator.pcm.repository.OperationInterface build() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);
		// set repository? what about roles etc; TODO!
		
		return interfce;
	}
}
