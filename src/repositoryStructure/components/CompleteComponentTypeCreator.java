package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class CompleteComponentTypeCreator extends Component {

	private List<ProvidesComponentType> conformsProvidedTypes;

	public CompleteComponentTypeCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.conformsProvidedTypes = new ArrayList<>();
	}

	@Override
	public CompleteComponentTypeCreator withName(String name) {
		return (CompleteComponentTypeCreator) super.withName(name);
	}

	@Override
	public CompleteComponentTypeCreator withId(String id) {
		return (CompleteComponentTypeCreator) super.withId(id);
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public CompleteComponentTypeCreator provides(OperationInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.provides(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator provides(OperationInterfaceCreator interfce, String name) {
		return (CompleteComponentTypeCreator) super.provides(interfce, name);
	}

	@Override
	public CompleteComponentTypeCreator provides(OperationInterface interfce) {
		return (CompleteComponentTypeCreator) super.provides(interfce);
	}

	@Override
	public CompleteComponentTypeCreator provides(OperationInterface interfce, String name) {
		return (CompleteComponentTypeCreator) super.provides(interfce, name);
	}
	
	// provides infrastructure interface
	@Override
	public CompleteComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce, name);
	}
	
	@Override
	public CompleteComponentTypeCreator providesInfrastructure(InfrastructureInterface interfce) {
		return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator providesInfrastructure(InfrastructureInterface interfce, String name) {
		return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public CompleteComponentTypeCreator handles(EventGroupCreator eventGroup) {
		return (CompleteComponentTypeCreator) super.handles(eventGroup);
	}
	
	@Override
	public CompleteComponentTypeCreator handles(EventGroupCreator eventGroup, String name) {
		return (CompleteComponentTypeCreator) super.handles(eventGroup, name);
	}
	
	@Override
	public CompleteComponentTypeCreator handles(EventGroup eventGroup) {
		return (CompleteComponentTypeCreator) super.handles(eventGroup);
	}
	
	@Override
	public CompleteComponentTypeCreator handles(EventGroup eventGroup, String name) {
		return (CompleteComponentTypeCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public CompleteComponentTypeCreator requires(OperationInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.requires(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator requires(OperationInterfaceCreator interfce, String name) {
		return (CompleteComponentTypeCreator) super.requires(interfce, name);
	}
	
	@Override
	public CompleteComponentTypeCreator requires(OperationInterface interfce) {
		return (CompleteComponentTypeCreator) super.requires(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator requires(OperationInterface interfce, String name) {
		return (CompleteComponentTypeCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public CompleteComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce, name);
	}
	
	@Override
	public CompleteComponentTypeCreator requiresInfrastructure(InfrastructureInterface interfce) {
		return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce);
	}
	
	@Override
	public CompleteComponentTypeCreator requiresInfrastructure(InfrastructureInterface interfce, String name) {
		return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public CompleteComponentTypeCreator emits(EventGroupCreator eventGroup) {
		return (CompleteComponentTypeCreator) super.emits(eventGroup);
	}
	
	@Override
	public CompleteComponentTypeCreator emits(EventGroupCreator eventGroup, String name) {
		return (CompleteComponentTypeCreator) super.emits(eventGroup, name);
	}

	@Override
	public CompleteComponentTypeCreator emits(EventGroup eventGroup) {
		return (CompleteComponentTypeCreator) super.emits(eventGroup);
	}

	
	@Override
	public CompleteComponentTypeCreator emits(EventGroup eventGroup, String name) {
		return (CompleteComponentTypeCreator) super.emits(eventGroup, name);
	}

	// resource required role
	@Override
	public CompleteComponentTypeCreator requiresResource(ResourceInterface resourceInterface) {
		return (CompleteComponentTypeCreator) super.requiresResource(resourceInterface);
	}

	// ------------ type roles ------------
	public CompleteComponentTypeCreator conforms(ProvidesComponentTypeCreator providesComponentType) {
		ProvidesComponentType pct = providesComponentType.build();
		this.conformsProvidedTypes.add(pct);
		return this;
	}

	@Override
	public CompleteComponentType build() {
		CompleteComponentType cct = RepositoryFactory.eINSTANCE.createCompleteComponentType();
		if (name != null)
			cct.setEntityName(name);
		if (id != null)
			cct.setId(id);

		cct.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		cct.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		cct.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);
		cct.getParentProvidesComponentTypes().addAll(conformsProvidedTypes);

		return cct;
	}

}
