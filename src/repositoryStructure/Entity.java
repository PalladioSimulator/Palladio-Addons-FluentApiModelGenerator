package repositoryStructure;


public abstract class Entity {

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

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	

}
