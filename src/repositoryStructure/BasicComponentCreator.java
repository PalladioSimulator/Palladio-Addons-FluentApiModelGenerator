package repositoryStructure;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Comp;
import apiControlFlowInterfaces.RepoAddition;

public class BasicComponentCreator implements Comp {

	String name;
	String id;

	RepositoryCreator repository;
	ComponentTypeCreator componentType;

	public BasicComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public Comp withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Comp withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public Comp ofType(String todo) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public RepoAddition addToRepository() {
		BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		if (name != null)
			basicComponent.setEntityName(name);
		if (id != null)
			basicComponent.setId(id);
		if (componentType != null)
			basicComponent.setComponentType(null); // TODO:
		// set repository? what about roles etc
		this.repository.components.add(basicComponent);
		this.repository.currentComponent = null;
		return this.repository;
	}

	@Override
	public Repository build() {
		return this.addToRepository().build();
	}

}
