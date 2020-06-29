package repositoryStructure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ocl.ecore.CollectionType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Repo;
import apiControlFlowInterfaces.RepoAddition;
import factory.MyRepositoryFactory;
import repositoryStructure.components.Component;

public class RepositoryCreator extends Entity implements Repo, RepoAddition {

	private String description;

	private MyRepositoryFactory factory;
	private List<RepositoryComponent> components;
	private List<Interface> interfaces;

	public RepositoryCreator(MyRepositoryFactory factory) {
		this.factory = factory;
		this.components = new ArrayList<>();
		this.interfaces = new ArrayList<>();
	}

	@Override
	public RepositoryCreator withName(String name) {
		return (RepositoryCreator) super.withName(name);
	}

	@Override
	public RepositoryCreator withId(String id) {
		return (RepositoryCreator) super.withId(id);
	}

	@Override
	public RepositoryCreator withDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public RepoAddition addToRepository(Component component) {
		RepositoryComponent c = component.build();
		components.add(c);
		return this;
	}

	@Override
	public RepoAddition addToRepository(repositoryStructure.interfaces.Interface interfce) {
		Interface i = interfce.build();
		interfaces.add(i);
		return this;
	}
	
//	public RepoAddition addToRepository(CollectionType)
	//TODO:
	
	@Override
	public Repository build() {
		Repository repo = RepositoryFactory.eINSTANCE.createRepository();
		if (name != null)
			repo.setEntityName(name);
		if (id != null)
			repo.setId(id);
		if (description != null)
			repo.setRepositoryDescription(description);
		
		//TODO: add components and stuff from the factory as well
		// check that they are not added twice in different versions and stuff
		repo.getComponents__Repository().addAll(components);
		repo.getInterfaces__Repository().addAll(interfaces);
//		repo.getFailureTypes__Repository().addAll(failureTypes);
		
		//TODO: reset all Lists in the factory?
		return repo;
	}

}
