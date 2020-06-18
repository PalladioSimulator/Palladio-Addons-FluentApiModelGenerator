package repositoryStructure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Comp;
import apiControlFlowInterfaces.Inter;
import apiControlFlowInterfaces.Repo;
import apiControlFlowInterfaces.RepoAddition;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.Component;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class RepositoryCreator implements Repo, RepoAddition {

	String name;
	String id;
	String description;

	List<RepositoryComponent> components;
	List<Interface> interfaces;

	BasicComponentCreator currentComponent;
	OperationInterfaceCreator currentInterface;

	public RepositoryCreator() {
		this.components = new ArrayList<>();
		this.interfaces = new ArrayList<>();
	}

	@Override
	public Repo withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Repo withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public Repo withDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public Comp aComponent() {
		this.currentComponent = new BasicComponentCreator(this);
		return this.currentComponent;
	}

	@Override
	public Inter anInterface() {
		this.currentInterface = new OperationInterfaceCreator(this);
		return this.currentInterface;
	}

	@Override
	public RepoAddition addToRepository(Component o) {
		RepositoryComponent c = o.build();
		components.add(c);
		return this;
	}

	@Override
	public RepoAddition addToRepository(repositoryStructure.interfaces.Interface o) {
		Interface c = o.build();
		interfaces.add(c);
		return this;
	}
	
	@Override
	public Repository build() {
		Repository repo = RepositoryFactory.eINSTANCE.createRepository();
		if (name != null)
			repo.setEntityName(name);
		if (id != null)
			repo.setId(id);
		if (description != null)
			repo.setRepositoryDescription(description);
		
		repo.getComponents__Repository().addAll(components);
		repo.getInterfaces__Repository().addAll(interfaces);
		return repo;
	}

}
