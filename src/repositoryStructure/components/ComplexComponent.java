package repositoryStructure.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import apiControlFlowInterfaces.EventChannelCreation;

public abstract class ComplexComponent extends Component {

	protected List<AssemblyContext> assemblyContexts = new ArrayList<>();
	protected List<Connector> connectors = new ArrayList<>();
	protected List<EventChannel> eventChannels = new ArrayList<>();
	protected List<ResourceRequiredDelegationConnector> resourceRequiredDelegationConnectors = new ArrayList<>();

	/**
	 * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
	 * AssemblyContext} with the name <code>name</code> and optionally many
	 * <code>configParameterUsages</code> and adds it to the component.
	 * <p>
	 * An existing {@link org.palladiosimulator.pcm.repository.RepositoryComponent
	 * RepositoryComponent} can be fetched from the repository using the factory,
	 * i.e. <code>create.fetchOfComponent(name)</code>.
	 * </p>
	 * 
	 * @param encapsulatedComponent
	 * @param name
	 * @param configParameterUsages
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfComponent(String)
	 */
	public ComplexComponent withAssemblyContext(RepositoryComponent encapsulatedComponent, String name,
			VariableUsageCreator... configParameterUsages) {
		AssemblyContext ac = CompositionFactory.eINSTANCE.createAssemblyContext();
		if (name != null)
			ac.setEntityName(name);
		ac.setEncapsulatedComponent__AssemblyContext(encapsulatedComponent);
		Arrays.asList(configParameterUsages).stream().map(v -> v.build())
				.forEach(v -> ac.getConfigParameterUsages__AssemblyContext().add(v));
		this.assemblyContexts.add(ac);
		this.repository.addAssemblyContext(ac);
		return this;
	}

	/**
	 * Creates a new {@link org.palladiosimulator.pcm.core.composition.EventChannel
	 * EventChannel}.
	 * <p>
	 * Event channels consist of an
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup} and
	 * arbitrarily many
	 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
	 * EventChannelSinkConnector}s and
	 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
	 * EventChannelSinkConnector}s.
	 * </p>
	 * 
	 * @return the event channel in the making
	 * @see repositoryStructure.components.EventChannelCreator#withEventGroup(org.palladiosimulator.pcm.repository.EventGroup)
	 * @see repositoryStructure.components.EventChannelCreator#withEventChannelSinkConnector(EventChannelSinkConnector)
	 * @see repositoryStructure.components.EventChannelCreator#withEventChannelSourceConnector(EventChannelSourceConnector)
	 */
	public abstract EventChannelCreation withEventChannel();

	// ------------ operation role connectors ------------
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
	 * AssemblyConnector} and adds it to the component.
	 * <p>
	 * An AssemblyConnector is a bidirectional link of two assembly contexts.
	 * Intuitively, an AssemblyConnector connects a provided and a required
	 * interface of two different components. AssemblyContext must refer to the
	 * tuple (Role, AssemblyContext) in order to uniquely identify which component
	 * roles communicate with each other.
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param providedRole
	 * @param providingAssemblyContext
	 * @param requiredRole
	 * @param requiringAssemblyContext
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfOperationProvidedRole(String)
	 * @see factory.MyRepositoryFactory#fetchOfOperationRequiredRole(String)
	 */
	public ComplexComponent withAssemblyConnection(OperationProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, OperationRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		AssemblyConnector assemblyConnector = CompositionFactory.eINSTANCE.createAssemblyConnector();

		assemblyConnector.setProvidedRole_AssemblyConnector(providedRole);
		assemblyConnector.setProvidingAssemblyContext_AssemblyConnector(providingAssemblyContext);
		assemblyConnector.setRequiredRole_AssemblyConnector(requiredRole);
		assemblyConnector.setRequiringAssemblyContext_AssemblyConnector(requiringAssemblyContext);

		this.connectors.add(assemblyConnector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
	 * ProvidedDelegationConnector} and adds it to the component.
	 * <p>
	 * A ProvidedDelegationConnector delegates incoming calls of provided roles to
	 * inner provided roles of encapsulated assembly contexts.
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerProvidedRole
	 * @param outerProvidedRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfOperationProvidedRole(String)
	 */
	public ComplexComponent withProvidedDelegationConnection(AssemblyContext assemblyContext,
			OperationProvidedRole innerProvidedRole, OperationProvidedRole outerProvidedRole) {
		ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();

		connector.setAssemblyContext_ProvidedDelegationConnector(assemblyContext);
		connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvidedRole);
		connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvidedRole);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
	 * RequiredDelegationConnector} and adds it to the component.
	 * <p>
	 * A RequiredDelegationConnector delegates required roles of encapsulated
	 * assembly contexts to outer required roles .
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerRequiredRole
	 * @param outerRequiredRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfOperationRequiredRole(String)
	 */
	public ComplexComponent withRequiredDelegationConnection(AssemblyContext assemblyContext,
			OperationRequiredRole innerRequiredRole, OperationRequiredRole outerRequiredRole) {
		RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();

		connector.setAssemblyContext_RequiredDelegationConnector(assemblyContext);
		connector.setInnerRequiredRole_RequiredDelegationConnector(innerRequiredRole);
		connector.setOuterRequiredRole_RequiredDelegationConnector(outerRequiredRole);

		this.connectors.add(connector);
		return this;
	}

	// ------------ event channel role connectors ------------
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
	 * AssemblyEventConnector} and adds it to the component.
	 * <p>
	 * An AssemblyConnector is a bidirectional link of two assembly contexts.
	 * Intuitively, an AssemblyEventConnector connects a sink and a source.
	 * AssemblyContext must refer to the tuple (Role,AssemblyContext) in order to
	 * uniquely identify which component sink and source roles communicate with each
	 * other.
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param sinkRole
	 * @param sinkAssemblyContext
	 * @param sourceRole
	 * @param sourceAssemblyContext
	 * @param filterCondition_stochasticExpression
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfSinkRole(String)
	 * @see factory.MyRepositoryFactory#fetchOfSourceRole(String)
	 */
	public ComplexComponent withAssemblyEventConnection(SinkRole sinkRole, AssemblyContext sinkAssemblyContext,
			SourceRole sourceRole, AssemblyContext sourceAssemblyContext, String filterCondition_stochasticExpression) {
		AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();

		connector.setSinkRole__AssemblyEventConnector(sinkRole);
		connector.setSinkAssemblyContext__AssemblyEventConnector(sinkAssemblyContext);
		connector.setSourceRole__AssemblyEventConnector(sourceRole);
		connector.setSourceAssemblyContext__AssemblyEventConnector(sourceAssemblyContext);

		if (filterCondition_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(filterCondition_stochasticExpression);
			connector.setFilterCondition__AssemblyEventConnector(rand);
		}

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
	 * EventChannelSinkConnector} and adds it to the component.
	 * <p>
	 * Existing roles, assembly contexts and event channels can be fetched from the
	 * repository using the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param eventChannel
	 * @param sinkRole
	 * @param filterCondition_stochasticExpression
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfEventChannel(String)
	 * @see factory.MyRepositoryFactory#fetchOfSinkRole(String)
	 */
	public ComplexComponent withEventChannelSinkConnection(AssemblyContext assemblyContext, EventChannel eventChannel,
			SinkRole sinkRole, String filterCondition_stochasticExpression) {
		EventChannelSinkConnector connector = CompositionFactory.eINSTANCE.createEventChannelSinkConnector();

		connector.setAssemblyContext__EventChannelSinkConnector(assemblyContext);
		connector.setEventChannel__EventChannelSinkConnector(eventChannel);
		connector.setSinkRole__EventChannelSinkConnector(sinkRole);

		if (filterCondition_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(filterCondition_stochasticExpression);
			connector.setFilterCondition__EventChannelSinkConnector(rand);
		}

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
	 * EventChannelSourceConnector} and adds it to the component.
	 * <p>
	 * Existing roles, assembly contexts and event channels can be fetched from the
	 * repository using the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param eventChannel
	 * @param sourceRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfEventChannel(String)
	 * @see factory.MyRepositoryFactory#fetchOfSourceRole(String)
	 */
	public ComplexComponent withEventChannelSourceConnection(AssemblyContext assemblyContext, EventChannel eventChannel,
			SourceRole sourceRole) {
		EventChannelSourceConnector connector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();

		connector.setAssemblyContext__EventChannelSourceConnector(assemblyContext);
		connector.setEventChannel__EventChannelSourceConnector(eventChannel);
		connector.setSourceRole__EventChannelSourceRole(sourceRole);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
	 * SinkDelegationConnector} and adds it to the component.
	 * <p>
	 * A SinkDelegationConnector delegates an incoming event to the encapsulated
	 * assembly contexts to inner sink roles.
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerSinkRole
	 * @param outerSinkRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfSinkRole(String)
	 */
	public ComplexComponent withSinkDelegationConnection(AssemblyContext assemblyContext, SinkRole innerSinkRole,
			SinkRole outerSinkRole) {
		SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();

		connector.setAssemblyContext__SinkDelegationConnector(assemblyContext);
		connector.setInnerSinkRole__SinkRole(innerSinkRole);
		connector.setOuterSinkRole__SinkRole(outerSinkRole);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
	 * SourceDelegationConnector} and adds it to the component.
	 * <p>
	 * A SourceDelegationConnector delegates outgoing events of encapsulated
	 * assembly contexts to an external souce role of the enclosing assembly
	 * context.
	 * </p>
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerSourceRole
	 * @param outerSourceRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfSourceRole(String)
	 */
	public ComplexComponent withSourceDelegationConnection(AssemblyContext assemblyContext, SourceRole innerSourceRole,
			SourceRole outerSourceRole) {
		SourceDelegationConnector connector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();

		connector.setAssemblyContext__SourceDelegationConnector(assemblyContext);
		connector.setInnerSourceRole__SourceRole(innerSourceRole);
		connector.setOuterSourceRole__SourceRole(outerSourceRole);

		this.connectors.add(connector);
		return this;
	}

	// ------------ infrastructure role connectors ------------
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
	 * AssemblyInfrastructureConnector} and adds it to the component.
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param providedRole
	 * @param providingAssemblyContext
	 * @param requiredRole
	 * @param requiringAssemblyContext
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureProvidedRole(String)
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureRequiredRole(String)
	 */
	public ComplexComponent withAssemblyInfrastructureConnection(InfrastructureProvidedRole providedRole,
			AssemblyContext providingAssemblyContext, InfrastructureRequiredRole requiredRole,
			AssemblyContext requiringAssemblyContext) {
		AssemblyInfrastructureConnector connector = CompositionFactory.eINSTANCE
				.createAssemblyInfrastructureConnector();

		connector.setProvidedRole__AssemblyInfrastructureConnector(providedRole);
		connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(providingAssemblyContext);
		connector.setRequiredRole__AssemblyInfrastructureConnector(requiredRole);
		connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(requiringAssemblyContext);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
	 * ProvidedInfrastructureDelegationConnector} and adds it to the component.
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerProvidedRole
	 * @param outerProvidedRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureProvidedRole(String)
	 */
	public ComplexComponent withProvidedInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureProvidedRole innerProvidedRole, InfrastructureProvidedRole outerProvidedRole) {
		ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
				.createProvidedInfrastructureDelegationConnector();
		connector.setAssemblyContext__ProvidedInfrastructureDelegationConnector(assemblyContext);
		connector.setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(innerProvidedRole);
		connector.setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(outerProvidedRole);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
	 * RequiredInfrastructureDelegationConnector} and adds it to the component.
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerRequiredRole
	 * @param outerRequiredRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureRequiredRole(String)
	 */
	public ComplexComponent withRequiredInfrastructureDelegationConnection(AssemblyContext assemblyContext,
			InfrastructureRequiredRole innerRequiredRole, InfrastructureRequiredRole outerRequiredRole) {
		RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
				.createRequiredInfrastructureDelegationConnector();
		connector.setAssemblyContext__RequiredInfrastructureDelegationConnector(assemblyContext);
		connector.setInnerRequiredRole__RequiredInfrastructureDelegationConnector(innerRequiredRole);
		connector.setOuterRequiredRole__RequiredInfrastructureDelegationConnector(outerRequiredRole);

		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
	 * RequiredResourceDelegationConnector} and adds it to the component.
	 * <p>
	 * Existing roles and assembly contexts can be fetched from the repository using
	 * the factory.
	 * </p>
	 * 
	 * @param assemblyContext
	 * @param innerRequiredRole
	 * @param outerRequiredRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfAssemblyContext(String)
	 * @see factory.MyRepositoryFactory#fetchOfResourceRequiredRole(String)
	 */
	public ComplexComponent withRequiredResourceDelegationConnection(AssemblyContext assemblyContext,
			ResourceRequiredRole innerRequiredRole, ResourceRequiredRole outerRequiredRole) {
		RequiredResourceDelegationConnector connector = CompositionFactory.eINSTANCE
				.createRequiredResourceDelegationConnector();
		connector.setAssemblyContext__RequiredResourceDelegationConnector(assemblyContext);
		connector.setInnerRequiredRole__RequiredResourceDelegationConnector(innerRequiredRole);
		connector.setOuterRequiredRole__RequiredResourceDelegationConnector(outerRequiredRole);
		this.connectors.add(connector);
		return this;
	}

	/**
	 * Creates a
	 * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
	 * ResourceRequiredDelegationConnector} and adds it to the component.
	 * <p>
	 * Existing required roles can be fetched from the repository using the factory,
	 * i.e. <code>create.fetchOfResourceRequiredRole(name)</code>.
	 * </p>
	 * 
	 * @param innerRequiredRole
	 * @param outerRequiredRole
	 * @return the component in the making
	 * @see factory.MyRepositoryFactory#fetchOfResourceRequiredRole(String)
	 */
	public ComplexComponent resourceRequiredDegelationConnection(ResourceRequiredRole innerRequiredRole,
			ResourceRequiredRole outerRequiredRole) {
		ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE
				.createResourceRequiredDelegationConnector();
		connector.setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(innerRequiredRole);
		connector.setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(outerRequiredRole);

		this.resourceRequiredDelegationConnectors.add(connector);
		return this;

	}

	protected void addEventChannel(EventChannel eventChannel) {
		this.eventChannels.add(eventChannel);
	}

}
