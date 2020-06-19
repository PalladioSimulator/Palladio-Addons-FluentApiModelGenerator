package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class BasicComponentCreator extends Component {

	private List<CompleteComponentType> conformsCompleteTypes;
	
	public BasicComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.conformsCompleteTypes = new ArrayList<>();
	}

	@Override
	public BasicComponentCreator withName(String name) {
		return (BasicComponentCreator) super.withName(name);
	}

	@Override
	public BasicComponentCreator withId(String id) {
		return (BasicComponentCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public BasicComponentCreator provides(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.provides(interfce);
	}

	// provides infrastructure interface
	@Override
	public BasicComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	@Override
	public BasicComponentCreator handles(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public BasicComponentCreator requires(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requires(interfce);
	}

	// require infrastructure interface
	@Override
	public BasicComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	@Override
	public BasicComponentCreator emits(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.emits(eventGroup);
	}

	// resource required role
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	@Override
	public BasicComponentCreator requiresResource(Object o) {
		return (BasicComponentCreator) super.requiresResource(o);
	}

	public BasicComponentCreator ofType_conforms(CompleteComponentTypeCreator completeComponentType) {
		CompleteComponentType cct = completeComponentType.build();
		this.conformsCompleteTypes.add(cct);
		return this;
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
		for(CompleteComponentType cct: conformsCompleteTypes) {
			//TODO: how to set cct as conforming type?
		}
		// TODO: set repository? variable usage, seff etc

		return basicComponent;
	}

}
