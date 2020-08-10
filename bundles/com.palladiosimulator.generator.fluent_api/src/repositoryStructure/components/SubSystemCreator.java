package repositoryStructure.components;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.internals.ResourceInterface;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.subsystem.SubSystem
 * SubSystem}. It is used to create the '<em><b>SubSystem</b></em>' object
 * step-by-step, i.e. '<em><b>SubSystemCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.subsystem.SubSystem
 */
public class SubSystemCreator extends ComplexComponent {

	public SubSystemCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	@Override
	public SubSystemCreator withName(String name) {
		return (SubSystemCreator) super.withName(name);
	}

//	@Override
//	public SubSystemCreator withId(String id) {
//		return (SubSystemCreator) super.withId(id);
//	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public SubSystemCreator provides(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.provides(interfce);
	}

	@Override
	public SubSystemCreator provides(OperationInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.provides(interfce, name);
	}

	@Override
	public SubSystemCreator provides(OperationInterface interfce) {
		return (SubSystemCreator) super.provides(interfce);
	}

	@Override
	public SubSystemCreator provides(OperationInterface interfce, String name) {
		return (SubSystemCreator) super.provides(interfce, name);
	}

	// provides infrastructure interface
	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.providesInfrastructure(interfce, name);
	}

	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterface interfce) {
		return (SubSystemCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public SubSystemCreator providesInfrastructure(InfrastructureInterface interfce, String name) {
		return (SubSystemCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public SubSystemCreator handles(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.handles(eventGroup);
	}

	@Override
	public SubSystemCreator handles(EventGroupCreator eventGroup, String name) {
		return (SubSystemCreator) super.handles(eventGroup, name);
	}

	@Override
	public SubSystemCreator handles(EventGroup eventGroup) {
		return (SubSystemCreator) super.handles(eventGroup);
	}

	@Override
	public SubSystemCreator handles(EventGroup eventGroup, String name) {
		return (SubSystemCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public SubSystemCreator requires(OperationInterfaceCreator interfce) {
		return (SubSystemCreator) super.requires(interfce);
	}

	@Override
	public SubSystemCreator requires(OperationInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.requires(interfce, name);
	}

	@Override
	public SubSystemCreator requires(OperationInterface interfce) {
		return (SubSystemCreator) super.requires(interfce);
	}

	@Override
	public SubSystemCreator requires(OperationInterface interfce, String name) {
		return (SubSystemCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce, name);
	}

	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterface interfce) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public SubSystemCreator requiresInfrastructure(InfrastructureInterface interfce, String name) {
		return (SubSystemCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public SubSystemCreator emits(EventGroupCreator eventGroup) {
		return (SubSystemCreator) super.emits(eventGroup);
	}

	@Override
	public SubSystemCreator emits(EventGroupCreator eventGroup, String name) {
		return (SubSystemCreator) super.emits(eventGroup, name);
	}

	@Override
	public SubSystemCreator emits(EventGroup eventGroup) {
		return (SubSystemCreator) super.emits(eventGroup);
	}

	@Override
	public SubSystemCreator emits(EventGroup eventGroup, String name) {
		return (SubSystemCreator) super.emits(eventGroup, name);
	}

	// resource required role
	@Override
	public SubSystemCreator requiresResource(ResourceInterface resourceInterface) {
		return (SubSystemCreator) super.requiresResource(resourceInterface);
	}

	@Override
	public SubSystemCreator requiresResource(ResourceInterface resourceInterface, String name) {
		return (SubSystemCreator) super.requiresResource(resourceInterface, name);
	}

	// ------------ other listing characteristics ------------
	@Override
	public SubSystemCreator withAssemblyContext(RepositoryComponent component, String name,
			VariableUsageCreator... configParameterUsage) {
		return (SubSystemCreator) super.withAssemblyContext(component, name, configParameterUsage);
	}

	@Override
	public SubSystemCreator withAssemblyContext(RepositoryComponent component,
			VariableUsageCreator... configParameterUsage) {
		return (SubSystemCreator) super.withAssemblyContext(component, configParameterUsage);
	}

	@Override
	public SubSystemCreator withEventChannel(EventGroup eventGroup, String name) {
		return (SubSystemCreator) super.withEventChannel(eventGroup, name);
	}

	@Override
	public SubSystemCreator withEventChannel(EventGroup eventGroup) {
		return (SubSystemCreator) super.withEventChannel(eventGroup);
	}

	// ------------ connectors ------------
	@Override
	public SubSystemCreator withAssemblyConnection(OperationProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, OperationRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		return (SubSystemCreator) super.withAssemblyConnection(providedRole, providingAssemblyContext, requiredRole,
				requiringAssemblyContext);
	}

	@Override
	public SubSystemCreator withProvidedDelegationConnection(AssemblyContext assemblyContext,
			OperationProvidedRole innerProvidedRole, OperationProvidedRole outerProvidedRole) {
		return (SubSystemCreator) super.withProvidedDelegationConnection(assemblyContext, innerProvidedRole,
				outerProvidedRole);
	}

	@Override
	public SubSystemCreator withRequiredDelegationConnection(AssemblyContext assemblyContext,
			OperationRequiredRole innerRequiredRole, OperationRequiredRole outerRequiredRole) {
		return (SubSystemCreator) super.withRequiredDelegationConnection(assemblyContext, innerRequiredRole,
				outerRequiredRole);
	}

	@Override
	public SubSystemCreator withAssemblyEventConnection(SinkRole sinkRole, AssemblyContext sinkAssemblyContext,
			SourceRole sourceRole, AssemblyContext sourceAssemblyContext, String filterCondition_stochasticExpression) {
		return (SubSystemCreator) super.withAssemblyEventConnection(sinkRole, sinkAssemblyContext, sourceRole,
				sourceAssemblyContext, filterCondition_stochasticExpression);
	}

	@Override
	public SubSystemCreator withEventChannelSinkConnection(AssemblyContext assemblyContext, EventChannel eventChannel,
			SinkRole sinkRole, String filterCondition_stochasticExpression) {
		return (SubSystemCreator) super.withEventChannelSinkConnection(assemblyContext, eventChannel, sinkRole,
				filterCondition_stochasticExpression);
	}

	@Override
	public SubSystemCreator withEventChannelSourceConnection(AssemblyContext assemblyContext, EventChannel eventChannel,
			SourceRole sourceRole) {
		return (SubSystemCreator) super.withEventChannelSourceConnection(assemblyContext, eventChannel, sourceRole);
	}

	@Override
	public SubSystemCreator withSinkDelegationConnection(AssemblyContext assemblyContext, SinkRole innerSinkRole,
			SinkRole outerSinkRole) {
		return (SubSystemCreator) super.withSinkDelegationConnection(assemblyContext, innerSinkRole, outerSinkRole);
	}

	@Override
	public SubSystemCreator withSourceDelegationConnection(AssemblyContext assemblyContext, SourceRole innerSourceRole,
			SourceRole outerSourceRole) {
		return (SubSystemCreator) super.withSourceDelegationConnection(assemblyContext, innerSourceRole,
				outerSourceRole);
	}

	@Override
	public SubSystemCreator withAssemblyInfrastructureConnection(InfrastructureProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, InfrastructureRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		return (SubSystemCreator) super.withAssemblyInfrastructureConnection(providedRole, providingAssemblyContext,
				requiredRole, requiringAssemblyContext);
	}

	@Override
	public SubSystemCreator withProvidedInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureProvidedRole innerProvidedRole, InfrastructureProvidedRole outerProvidedRole) {
		return (SubSystemCreator) super.withProvidedInfrastructureDelegationConnection(assemblyContext,
				innerProvidedRole, outerProvidedRole);
	}

	@Override
	public SubSystemCreator withRequiredInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureRequiredRole innerRequiredRole, InfrastructureRequiredRole outerRequiredRole) {
		return (SubSystemCreator) super.withRequiredInfrastructureDelegationConnection(assemblyContext,
				innerRequiredRole, outerRequiredRole);
	}

	@Override
	public SubSystemCreator withRequiredResourceDelegationConnection(AssemblyContext assemblyContext,
			ResourceRequiredRole innerRequiredRole, ResourceRequiredRole outerRequiredRole) {
		return (SubSystemCreator) super.withRequiredResourceDelegationConnection(assemblyContext, innerRequiredRole,
				outerRequiredRole);
	}

	@Override
	public SubSystemCreator resourceRequiredDegelationConnection(ResourceRequiredRole innerRequiredRole,
			ResourceRequiredRole outerRequiredRole) {
		return (SubSystemCreator) super.resourceRequiredDegelationConnection(innerRequiredRole, outerRequiredRole);
	}

	@Override
	public SubSystem build() {
		SubSystem subSystem = SubsystemFactory.eINSTANCE.createSubSystem();
		if (this.name != null)
			subSystem.setEntityName(this.name);
//		if (this.id != null)
//			subSystem.setId(this.id);

		subSystem.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		subSystem.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		subSystem.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

		subSystem.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
		subSystem.getConnectors__ComposedStructure().addAll(connectors);
		subSystem.getEventChannel__ComposedStructure().addAll(eventChannels);
		subSystem.getResourceRequiredDelegationConnectors_ComposedStructure()
				.addAll(resourceRequiredDelegationConnectors);

		return subSystem;
	}

}
