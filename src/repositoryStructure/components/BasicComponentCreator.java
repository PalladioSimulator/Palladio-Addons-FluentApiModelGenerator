package repositoryStructure.components;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class BasicComponentCreator extends Component{

	public BasicComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public BasicComponentCreator withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public BasicComponentCreator withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public BasicComponentCreator ofType(String todo) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BasicComponent build() {
		BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		if (name != null)
			basicComponent.setEntityName(name);
		if (id != null)
			basicComponent.setId(id);
		// TODO: set repository? what about roles etc
		
		return basicComponent;
	}

}
