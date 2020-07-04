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
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

public abstract class ComplexComponent extends Component {

	protected List<AssemblyContext> assemblyContexts = new ArrayList<>();
	protected List<Connector> connectors = new ArrayList<>();
	protected List<EventChannel> eventChannels = new ArrayList<>();
	protected List<ResourceRequiredDelegationConnector> resourceRequiredDelegationConnectors = new ArrayList<>();

	public ComplexComponent withAssemblyContext(RepositoryComponent component, String name,
			VariableUsage... configParameterUsage) {
		AssemblyContext ac = CompositionFactory.eINSTANCE.createAssemblyContext();
		if (name != null)
			ac.setEntityName(name);
		ac.setEncapsulatedComponent__AssemblyContext(component);
		ac.getConfigParameterUsages__AssemblyContext().addAll(Arrays.asList(configParameterUsage));
		this.assemblyContexts.add(ac);
		return this;
	}

	// ------------ operation role connectors ------------
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

	public ComplexComponent withProvidedDelegationConnection(AssemblyContext assemblyContext,
			OperationProvidedRole innerProvidedRole, OperationProvidedRole outerProvidedRole) {
		ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();

		connector.setAssemblyContext_ProvidedDelegationConnector(assemblyContext);
		connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvidedRole);
		connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvidedRole);

		this.connectors.add(connector);
		return this;
	}

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

	public ComplexComponent withEventChannelSourceConnection(AssemblyContext assemblyContext, EventChannel eventChannel,
			SourceRole sourceRole) {
		EventChannelSourceConnector connector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();

		connector.setAssemblyContext__EventChannelSourceConnector(assemblyContext);
		connector.setEventChannel__EventChannelSourceConnector(eventChannel);
		connector.setSourceRole__EventChannelSourceRole(sourceRole);

		this.connectors.add(connector);
		return this;
	}

	public ComplexComponent withSinkDelegationConnection(AssemblyContext assemblyContext, SinkRole innerSinkRole,
			SinkRole outerSinkRole) {
		SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();

		connector.setAssemblyContext__SinkDelegationConnector(assemblyContext);
		connector.setInnerSinkRole__SinkRole(innerSinkRole);
		connector.setOuterSinkRole__SinkRole(outerSinkRole);

		this.connectors.add(connector);
		return this;
	}

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
	public ComplexComponent withAssemblyInfrastructureConnection() {
		AssemblyInfrastructureConnector connector = CompositionFactory.eINSTANCE
				.createAssemblyInfrastructureConnector();

		// TODO: later; erst mal nicht so wichtig

		this.connectors.add(connector);
		return this;
	}

	public ComplexComponent withProvidedInfrastructureDelegationConnection() {
		ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
				.createProvidedInfrastructureDelegationConnector();

		// TODO: later; nicht so wichtig

		this.connectors.add(connector);
		return this;
	}

	public ComplexComponent withRequiredInfrastructureDelegationConnection() {
		RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
				.createRequiredInfrastructureDelegationConnector();

		// TODO: later; nicht so wichtig
		this.connectors.add(connector);
		return this;
	}

	public ComplexComponent withRequiredResourceDelegationConnection() {
		RequiredResourceDelegationConnector connector = CompositionFactory.eINSTANCE
				.createRequiredResourceDelegationConnector();

		// TODO: later; nicht so wichtig
		this.connectors.add(connector);
		return this;
	}

	public EventChannelCreator withEventChannel() {
		EventChannelCreator ecc = new EventChannelCreator(this);
		return ecc;
	}
	
	public void addEventChannel(EventChannel eventChannel) {
		this.eventChannels.add(eventChannel);
	}

	public ComplexComponent resourceRequiredDegelationConnection() {
		ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE
				.createResourceRequiredDelegationConnector();
		// TODO: later; nicht so wichtig

		this.resourceRequiredDelegationConnectors.add(connector);
		return this;

	}

}
