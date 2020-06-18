package repositoryStructure.components;

import java.util.ArrayList;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.roles.ProvidesOperationInterfaceCreator;
import repositoryStructure.roles.ProvidesOperationInterfaceCreator.RoleType;

public class BasicComponentCreator extends Component{

	public BasicComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.providedRoles = new ArrayList<>();
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
	
	// provides operation interface
	public BasicComponentCreator provides(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.provides(interfce);
	}
	
	// provides infrastructure interface
	public BasicComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce);
	}
	
	// sink role: handles an event group
	public BasicComponentCreator handles(EventGroupCreator interfce) {
		return (BasicComponentCreator) super.handles(interfce);
	}
//	

	@Override
	public BasicComponent build() {
		BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		if (name != null)
			basicComponent.setEntityName(name);
		if (id != null)
			basicComponent.setId(id);
		
		for (ProvidedRole providedRole : providedRoles) {
			basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(providedRole);
		}
		// TODO: set repository? what about roles etc
		
		return basicComponent;
	}

}
