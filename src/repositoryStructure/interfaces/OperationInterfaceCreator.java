package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Inter;
import apiControlFlowInterfaces.RepoAddition;
import repositoryStructure.RepositoryCreator;

public class OperationInterfaceCreator extends Interface implements Inter {
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

//	@Override
//	public RepoAddition addToRepository() {
//		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
//		if (name != null)
//			interfce.setEntityName(name);
//		if (id != null)
//			interfce.setId(id);
//		// set repository? what about roles etc; yeah, this programming style is
//		// horrible, I know - TODO!
//		this.repository.interfaces.add(interfce);
//		this.repository.currentInterface = null;
//		return this.repository;
//	}
//
	@Override
	public org.palladiosimulator.pcm.repository.Interface build() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);
		// set repository? what about roles etc; TODO!
		
		return interfce;
	}
}
