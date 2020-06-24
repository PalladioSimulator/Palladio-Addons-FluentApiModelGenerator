package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class CompositeComponentCreator extends Component {

	private List<CompleteComponentType> conformsCompleteTypes;

	public CompositeComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.conformsCompleteTypes = new ArrayList<>();
	}

	@Override
	public CompositeComponentCreator withName(String name) {
		return (CompositeComponentCreator) super.withName(name);
	}

	@Override
	public CompositeComponentCreator withId(String id) {
		return (CompositeComponentCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public CompositeComponentCreator provides(OperationInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.provides(interfce);
	}

	// provides infrastructure interface
	@Override
	public CompositeComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	@Override
	public CompositeComponentCreator handles(EventGroupCreator eventGroup) {
		return (CompositeComponentCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public CompositeComponentCreator requires(OperationInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.requires(interfce);
	}

	// require infrastructure interface
	@Override
	public CompositeComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	@Override
	public CompositeComponentCreator emits(EventGroupCreator eventGroup) {
		return (CompositeComponentCreator) super.emits(eventGroup);
	}

	// resource required role
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	@Override
	public CompositeComponentCreator requiresResource(Object o) {
		return (CompositeComponentCreator) super.requiresResource(o);
	}

	public CompositeComponentCreator ofType_conforms(CompleteComponentTypeCreator completeComponentType) {
		CompleteComponentType cct = completeComponentType.build();
		this.conformsCompleteTypes.add(cct);
		return this;
	}

	@Override
	public RepositoryComponent build() {
		CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		if (name != null)
			compositeComponent.setEntityName(name);
		if (id != null)
			compositeComponent.setId(id);

		compositeComponent.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		compositeComponent.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		compositeComponent.getParentCompleteComponentTypes().addAll(conformsCompleteTypes);
		
		// TODO: set repository? variable usage, connectors etc
		// Lists -> add
		compositeComponent.getComponentParameterUsage_ImplementationComponentType();
		compositeComponent.getConnectors__ComposedStructure();
		compositeComponent.getEventChannel__ComposedStructure();
		compositeComponent.getResourceRequiredDelegationConnectors_ComposedStructure();
		compositeComponent.getResourceRequiredRoles__ResourceInterfaceRequiringEntity();
		
		// Parameter -> set
		compositeComponent.setComponentType(null);
		
		return compositeComponent;
	}

}
