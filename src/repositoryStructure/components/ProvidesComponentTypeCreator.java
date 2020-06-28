package repositoryStructure.components;

import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class ProvidesComponentTypeCreator extends Component {

	public ProvidesComponentTypeCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public ProvidesComponentTypeCreator withName(String name) {
		return (ProvidesComponentTypeCreator) super.withName(name);
	}

	@Override
	public ProvidesComponentTypeCreator withId(String id) {
		return (ProvidesComponentTypeCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public ProvidesComponentTypeCreator provides(OperationInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.provides(interfce);
	}
	
	@Override
	public ProvidesComponentTypeCreator provides(OperationInterfaceCreator interfce, String name) {
		return (ProvidesComponentTypeCreator) super.provides(interfce, name);
	}

	// provides infrastructure interface
	@Override
	public ProvidesComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce);
	}
	
	@Override
	public ProvidesComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public ProvidesComponentTypeCreator handles(EventGroupCreator eventGroup) {
		return (ProvidesComponentTypeCreator) super.handles(eventGroup);
	}
	
	@Override
	public ProvidesComponentTypeCreator handles(EventGroupCreator eventGroup, String name) {
		return (ProvidesComponentTypeCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public ProvidesComponentTypeCreator requires(OperationInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.requires(interfce);
	}
	
	@Override
	public ProvidesComponentTypeCreator requires(OperationInterfaceCreator interfce, String name) {
		return (ProvidesComponentTypeCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public ProvidesComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce);
	}
	
	@Override
	public ProvidesComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public ProvidesComponentTypeCreator emits(EventGroupCreator eventGroup) {
		return (ProvidesComponentTypeCreator) super.emits(eventGroup);
	}
	
	@Override
	public ProvidesComponentTypeCreator emits(EventGroupCreator eventGroup, String name) {
		return (ProvidesComponentTypeCreator) super.emits(eventGroup, name);
	}

	// resource required role
	@Override
	public ProvidesComponentTypeCreator requiresResource(ResourceInterface resourceInterface) {
		return (ProvidesComponentTypeCreator) super.requiresResource(resourceInterface);
	}

	@Override
	public ProvidesComponentType build() {
		ProvidesComponentType pct = RepositoryFactory.eINSTANCE.createProvidesComponentType();
		if (name != null)
			pct.setEntityName(name);
		if (id != null)
			pct.setId(id);

		pct.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		pct.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		pct.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

		return pct;
	}

}
