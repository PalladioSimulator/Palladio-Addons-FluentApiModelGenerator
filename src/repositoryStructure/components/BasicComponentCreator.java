package repositoryStructure.components;

import java.util.ArrayList;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.roles.ProvidesOperationInterfaceCreator;
import repositoryStructure.roles.ProvidesOperationInterfaceCreator.RoleType;

public class BasicComponentCreator extends Component {

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

	// ------------ providing roles ------------
	// provides operation interface
	public BasicComponentCreator provides(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.provides(interfce);
	}

	// provides infrastructure interface
	public BasicComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	public BasicComponentCreator handles(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	public Component requires(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requires(interfce);
	}

	// require infrastructure interface
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	public Component emits(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.emits(eventGroup);
	}

	// resource required role
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	public Component requiresResource(Object o) {
		return (BasicComponentCreator) super.requiresResource(o);
	}

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
		for (RequiredRole requiredRole : requiredRoles) {
			basicComponent.getRequiredRoles_InterfaceRequiringEntity().add(requiredRole);
		}
		// TODO: set repository? what about roles etc

		return basicComponent;
	}

}
