package repositoryStructure.components;

import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class SubSystemCreator extends Component {

	public SubSystemCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public SubSystemCreator withName(String name) {
		return (SubSystemCreator) super.withName(name);
	}

	@Override
	public SubSystemCreator withId(String id) {
		return (SubSystemCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public SubSystemCreator provides(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.provides(interfce);
	}

	// provides infrastructure interface
	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	@Override
	public SubSystemCreator handles(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public SubSystemCreator requires(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.requires(interfce);
	}

	// require infrastructure interface
	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	@Override
	public SubSystemCreator emits(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.emits(eventGroup);
	}

	// resource required role
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	@Override
	public SubSystemCreator requiresResource(Object o) {
		return (SubSystemCreator) super.requiresResource(o);
	}

	@Override
	public SubSystem build() {
		SubSystem subSystem = SubsystemFactory.eINSTANCE.createSubSystem();
		if (this.name != null)
			subSystem.setEntityName(this.name);
		if (this.id != null)
			subSystem.setId(this.id);

		subSystem.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		subSystem.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);

		// TODO: Lists -> add
		subSystem.getAssemblyContexts__ComposedStructure();
		subSystem.getConnectors__ComposedStructure();
		subSystem.getEventChannel__ComposedStructure();
		subSystem.getResourceRequiredDelegationConnectors_ComposedStructure();
		subSystem.getResourceRequiredRoles__ResourceInterfaceRequiringEntity();
		
		return subSystem;
	}

}
