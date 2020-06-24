package repositoryStructure.components;

import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

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

	// provides infrastructure interface
	@Override
	public ProvidesComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	@Override
	public ProvidesComponentTypeCreator handles(EventGroupCreator eventGroup) {
		return (ProvidesComponentTypeCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public ProvidesComponentTypeCreator requires(OperationInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.requires(interfce);
	}

	// require infrastructure interface
	@Override
	public ProvidesComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	@Override
	public ProvidesComponentTypeCreator emits(EventGroupCreator eventGroup) {
		return (ProvidesComponentTypeCreator) super.emits(eventGroup);
	}

	// resource required role
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	@Override
	public ProvidesComponentTypeCreator requiresResource(Object o) {
		return (ProvidesComponentTypeCreator) super.requiresResource(o);
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

		// TODO: set repository? 
		// Lists -> add
		pct.getResourceRequiredRoles__ResourceInterfaceRequiringEntity();

		return pct;
	}

}
