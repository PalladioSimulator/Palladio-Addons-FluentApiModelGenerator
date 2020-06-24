package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class CompositeComponentCreator extends ComplexComponent {

	private ComponentType type;
	private List<CompleteComponentType> conformsCompleteTypes;
	private List<VariableUsage> componentParameterUsages;

	public CompositeComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.conformsCompleteTypes = new ArrayList<>();
		this.componentParameterUsages = new ArrayList<>();
	}

	@Override
	public CompositeComponentCreator withName(String name) {
		return (CompositeComponentCreator) super.withName(name);
	}

	@Override
	public CompositeComponentCreator withId(String id) {
		return (CompositeComponentCreator) super.withId(id);
	}

	// business vs infrstructure component
	public CompositeComponentCreator ofType(ComponentType type) {
		this.type = type;
		return this;
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
	@Override
	public CompositeComponentCreator requiresResource(ResourceInterface resourceInterface) {
		return (CompositeComponentCreator) super.requiresResource(resourceInterface);
	}

	// ------------ other listing characteristics ------------
	// parent complete component types
	public CompositeComponentCreator conforms(CompleteComponentTypeCreator completeComponentType) {
		CompleteComponentType cct = completeComponentType.build();
		this.conformsCompleteTypes.add(cct);
		return this;
	}

	// TODO: Variable Usages
	public CompositeComponentCreator todo(Object toDo) {
		this.componentParameterUsages.add(null);
		return this;
	}

	@Override
	public RepositoryComponent build() {
		CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		if (name != null)
			compositeComponent.setEntityName(name);
		if (id != null)
			compositeComponent.setId(id);
		if (type != null)
			compositeComponent.setComponentType(type);

		compositeComponent.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		compositeComponent.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		compositeComponent.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

		compositeComponent.getParentCompleteComponentTypes().addAll(conformsCompleteTypes);
		compositeComponent.getComponentParameterUsage_ImplementationComponentType().addAll(componentParameterUsages);

		compositeComponent.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
		compositeComponent.getConnectors__ComposedStructure().addAll(connectors);
		compositeComponent.getEventChannel__ComposedStructure().addAll(eventChannels);
		compositeComponent.getResourceRequiredDelegationConnectors_ComposedStructure().addAll(delegationConnectors);

		return compositeComponent;
	}

}
