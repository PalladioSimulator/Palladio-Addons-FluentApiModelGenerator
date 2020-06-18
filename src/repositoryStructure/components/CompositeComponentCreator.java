package repositoryStructure.components;

import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class CompositeComponentCreator extends Component{
	
	public CompositeComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public CompositeComponentCreator withName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public CompositeComponentCreator withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public CompositeComponentCreator ofType(String todo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepositoryComponent build() {
		CompositeComponent basicComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		if (name != null)
			basicComponent.setEntityName(name);
		if (id != null)
			basicComponent.setId(id);
		// TODO: set repository? what about roles etc
		
		return basicComponent;
	}

}
