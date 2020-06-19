package repositoryStructure;

import org.eclipse.emf.cdo.CDOObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;

public abstract class Entity {

	protected RepositoryCreator repository;

	protected String name;
	protected String id;

	public Entity withName(String name) {
		this.name = name;
		return this;
	}

	public Entity withId(String id) {
		this.id = id;
		return this;
	}
	
	public abstract CDOObject build();

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	

}
