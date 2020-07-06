package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import apiControlFlowInterfaces.VariableUsageCreation.Composite;
import apiControlFlowInterfaces.EventChannelCreation.Comp;
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

//	@Override
//	public CompositeComponentCreator withId(String id) {
//		return (CompositeComponentCreator) super.withId(id);
//	}

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

	@Override
	public CompositeComponentCreator provides(OperationInterfaceCreator interfce, String name) {
		return (CompositeComponentCreator) super.provides(interfce, name);
	}

	@Override
	public CompositeComponentCreator provides(OperationInterface interfce) {
		return (CompositeComponentCreator) super.provides(interfce);

	}

	@Override
	public CompositeComponentCreator provides(OperationInterface interfce, String name) {
		return (CompositeComponentCreator) super.provides(interfce, name);
	}

	// provides infrastructure interface
	@Override
	public CompositeComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public CompositeComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (CompositeComponentCreator) super.providesInfrastructure(interfce, name);
	}

	@Override
	public CompositeComponentCreator providesInfrastructure(InfrastructureInterface interfce) {
		return (CompositeComponentCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public CompositeComponentCreator providesInfrastructure(InfrastructureInterface interfce, String name) {
		return (CompositeComponentCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public CompositeComponentCreator handles(EventGroupCreator eventGroup) {
		return (CompositeComponentCreator) super.handles(eventGroup);
	}

	@Override
	public CompositeComponentCreator handles(EventGroupCreator eventGroup, String name) {
		return (CompositeComponentCreator) super.handles(eventGroup, name);
	}

	@Override
	public CompositeComponentCreator handles(EventGroup eventGroup) {
		return (CompositeComponentCreator) super.handles(eventGroup);
	}

	@Override
	public CompositeComponentCreator handles(EventGroup eventGroup, String name) {
		return (CompositeComponentCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public CompositeComponentCreator requires(OperationInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.requires(interfce);
	}

	@Override
	public CompositeComponentCreator requires(OperationInterfaceCreator interfce, String name) {
		return (CompositeComponentCreator) super.requires(interfce, name);
	}

	@Override
	public CompositeComponentCreator requires(OperationInterface interfce) {
		return (CompositeComponentCreator) super.requires(interfce);
	}

	@Override
	public CompositeComponentCreator requires(OperationInterface interfce, String name) {
		return (CompositeComponentCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public CompositeComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (CompositeComponentCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public CompositeComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (CompositeComponentCreator) super.requiresInfrastructure(interfce, name);
	}

	@Override
	public CompositeComponentCreator requiresInfrastructure(InfrastructureInterface interfce) {
		return (CompositeComponentCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public CompositeComponentCreator requiresInfrastructure(InfrastructureInterface interfce, String name) {
		return (CompositeComponentCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public CompositeComponentCreator emits(EventGroupCreator eventGroup) {
		return (CompositeComponentCreator) super.emits(eventGroup);
	}

	@Override
	public CompositeComponentCreator emits(EventGroupCreator eventGroup, String name) {
		return (CompositeComponentCreator) super.emits(eventGroup, name);
	}

	@Override
	public CompositeComponentCreator emits(EventGroup eventGroup) {
		return (CompositeComponentCreator) super.emits(eventGroup);
	}

	@Override
	public CompositeComponentCreator emits(EventGroup eventGroup, String name) {
		return (CompositeComponentCreator) super.emits(eventGroup, name);
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
		this.repository.addComponent(cct);
		return this;
	}

	public Composite withVariableUsage() {
		VariableUsageCreator vuc = new VariableUsageCreator(this, repository);
		return vuc;
	}

	@Override
	public CompositeComponentCreator withAssemblyContext(RepositoryComponent component, String name,
			VariableUsage... configParameterUsage) {
		return (CompositeComponentCreator) super.withAssemblyContext(component, name, configParameterUsage);
	}

	@Override
	public Comp withEventChannel() {
		return new EventChannelCreator(this);
	}

	// ------------ connectors ------------
	@Override
	public CompositeComponentCreator withAssemblyConnection(OperationProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, OperationRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		return (CompositeComponentCreator) super.withAssemblyConnection(providedRole, providingAssemblyContext,
				requiredRole, requiringAssemblyContext);
	}

	@Override
	public CompositeComponentCreator withProvidedDelegationConnection(AssemblyContext assemblyContext,
			OperationProvidedRole innerProvidedRole, OperationProvidedRole outerProvidedRole) {
		return (CompositeComponentCreator) super.withProvidedDelegationConnection(assemblyContext, innerProvidedRole,
				outerProvidedRole);
	}

	@Override
	public CompositeComponentCreator withRequiredDelegationConnection(AssemblyContext assemblyContext,
			OperationRequiredRole innerRequiredRole, OperationRequiredRole outerRequiredRole) {
		return (CompositeComponentCreator) super.withRequiredDelegationConnection(assemblyContext, innerRequiredRole,
				outerRequiredRole);
	}

	@Override
	public CompositeComponentCreator withAssemblyEventConnection(SinkRole sinkRole, AssemblyContext sinkAssemblyContext,
			SourceRole sourceRole, AssemblyContext sourceAssemblyContext, String filterCondition_stochasticExpression) {
		return (CompositeComponentCreator) super.withAssemblyEventConnection(sinkRole, sinkAssemblyContext, sourceRole,
				sourceAssemblyContext, filterCondition_stochasticExpression);
	}

	@Override
	public CompositeComponentCreator withEventChannelSinkConnection(AssemblyContext assemblyContext,
			EventChannel eventChannel, SinkRole sinkRole, String filterCondition_stochasticExpression) {
		return (CompositeComponentCreator) super.withEventChannelSinkConnection(assemblyContext, eventChannel, sinkRole,
				filterCondition_stochasticExpression);
	}

	@Override
	public CompositeComponentCreator withEventChannelSourceConnection(AssemblyContext assemblyContext,
			EventChannel eventChannel, SourceRole sourceRole) {
		return (CompositeComponentCreator) super.withEventChannelSourceConnection(assemblyContext, eventChannel,
				sourceRole);
	}

	@Override
	public CompositeComponentCreator withSinkDelegationConnection(AssemblyContext assemblyContext,
			SinkRole innerSinkRole, SinkRole outerSinkRole) {
		return (CompositeComponentCreator) super.withSinkDelegationConnection(assemblyContext, innerSinkRole,
				outerSinkRole);
	}

	@Override
	public CompositeComponentCreator withSourceDelegationConnection(AssemblyContext assemblyContext,
			SourceRole innerSourceRole, SourceRole outerSourceRole) {
		return (CompositeComponentCreator) super.withSourceDelegationConnection(assemblyContext, innerSourceRole,
				outerSourceRole);
	}

	// ------------ infrastructure role connectors ------------
	@Override
	public CompositeComponentCreator withAssemblyInfrastructureConnection(InfrastructureProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, InfrastructureRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		return (CompositeComponentCreator) super.withAssemblyInfrastructureConnection(providedRole,
				providingAssemblyContext, requiredRole, requiringAssemblyContext);
	}

	@Override
	public CompositeComponentCreator withProvidedInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureProvidedRole innerProvidedRole, InfrastructureProvidedRole outerProvidedRole) {
		return (CompositeComponentCreator) super.withProvidedInfrastructureDelegationConnection(assemblyContext,
				innerProvidedRole, outerProvidedRole);
	}

	@Override
	public CompositeComponentCreator withRequiredInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureRequiredRole innerRequiredRole, InfrastructureRequiredRole outerRequiredRole) {
		return (CompositeComponentCreator) super.withRequiredInfrastructureDelegationConnection(assemblyContext,
				innerRequiredRole, outerRequiredRole);
	}

	@Override
	public CompositeComponentCreator withRequiredResourceDelegationConnection(AssemblyContext assemblyContext,
			ResourceRequiredRole innerRequiredRole, ResourceRequiredRole outerRequiredRole) {
		return (CompositeComponentCreator) super.withRequiredResourceDelegationConnection(assemblyContext,
				innerRequiredRole, outerRequiredRole);
	}

	@Override
	public CompositeComponentCreator resourceRequiredDegelationConnection(ResourceRequiredRole innerRequiredRole,
			ResourceRequiredRole outerRequiredRole) {
		return (CompositeComponentCreator) super.resourceRequiredDegelationConnection(innerRequiredRole,
				outerRequiredRole);
	}

	@Override
	public RepositoryComponent build() {
		CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		if (name != null)
			compositeComponent.setEntityName(name);
//		if (id != null)
//			compositeComponent.setId(id);
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
		compositeComponent.getResourceRequiredDelegationConnectors_ComposedStructure()
				.addAll(resourceRequiredDelegationConnectors);

		return compositeComponent;
	}

	protected void addVariableUsage(VariableUsage varUsage) {
		this.componentParameterUsages.add(varUsage);
	}
}
