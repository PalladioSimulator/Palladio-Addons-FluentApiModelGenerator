package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompleteComponentType;
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

	// provides infrastructure interface
	@Override
	public CompleteComponentTypeCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce);
	}

	// sink role: handles an event group
	@Override
	public CompleteComponentTypeCreator handles(EventGroupCreator eventGroup) {
		return (CompleteComponentTypeCreator) super.handles(eventGroup);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public CompleteComponentTypeCreator requires(OperationInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.requires(interfce);
	}

	// require infrastructure interface
	@Override
	public CompleteComponentTypeCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce);
	}

	// emits event group (source role)
	@Override
	public CompleteComponentTypeCreator emits(EventGroupCreator eventGroup) {
		return (CompleteComponentTypeCreator) super.emits(eventGroup);
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
