package repositoryStructure.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

public abstract class ComplexComponent extends Component {

	protected List<AssemblyContext> assemblyContexts = new ArrayList<>();
	protected List<Connector> connectors = new ArrayList<>();
	protected List<EventChannel> eventChannels = new ArrayList<>();
	protected List<ResourceRequiredDelegationConnector> resourceRequiredDelegationConnectors = new ArrayList<>();
	
	
	public ComplexComponent withAssemblyContext(RepositoryComponent component, VariableUsage ... configParameterUsage) {
		AssemblyContext ac = CompositionFactory.eINSTANCE.createAssemblyContext();
		ac.setEncapsulatedComponent__AssemblyContext(component);
		ac.getConfigParameterUsages__AssemblyContext().addAll(Arrays.asList(configParameterUsage));
		this.assemblyContexts.add(ac);
		return this;
	}
	
	//TODO: in mehrere Methoden aufspalten
	public ComplexComponent connectingTo(Object o) {
		AssemblyConnector assemblyConnector = CompositionFactory.eINSTANCE.createAssemblyConnector();
		assemblyConnector.getProvidedRole_AssemblyConnector();
		assemblyConnector.getProvidingAssemblyContext_AssemblyConnector();
		assemblyConnector.getRequiredRole_AssemblyConnector();
		assemblyConnector.getRequiringAssemblyContext_AssemblyConnector();

		AssemblyEventConnector createAssemblyEventConnector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();
		AssemblyInfrastructureConnector createAssemblyInfrastructureConnector = CompositionFactory.eINSTANCE.createAssemblyInfrastructureConnector();
		EventChannelSinkConnector createEventChannelSinkConnector = CompositionFactory.eINSTANCE.createEventChannelSinkConnector();
		EventChannelSourceConnector createEventChannelSourceConnector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();
		
		ProvidedDelegationConnector createProvidedDelegationConnector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
		ProvidedInfrastructureDelegationConnector createProvidedInfrastructureDelegationConnector = CompositionFactory.eINSTANCE.createProvidedInfrastructureDelegationConnector();
		RequiredDelegationConnector createRequiredDelegationConnector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
		RequiredInfrastructureDelegationConnector createRequiredInfrastructureDelegationConnector = CompositionFactory.eINSTANCE.createRequiredInfrastructureDelegationConnector();
		RequiredResourceDelegationConnector createRequiredResourceDelegationConnector = CompositionFactory.eINSTANCE.createRequiredResourceDelegationConnector();
		SinkDelegationConnector createSinkDelegationConnector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();
		SourceDelegationConnector createSourceDelegationConnector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();
		
		
		
		return this;
	}
	
	//TODO: 
	public ComplexComponent withEventChannel(Object o){
		EventChannel eventChannel = CompositionFactory.eINSTANCE.createEventChannel();
		eventChannel.getEventChannelSinkConnector__EventChannel();
		eventChannel.getEventChannelSourceConnector__EventChannel();
		eventChannel.getEventGroup__EventChannel();
		
		return this;
	}
	
	//TODO: das ist ja ultra weird...
	public ComplexComponent resourceRequiredDegelationConnectionTo(Object o) {
		ResourceRequiredDelegationConnector rrdc = CompositionFactory.eINSTANCE.createResourceRequiredDelegationConnector();
		ResourceRequiredRole inner = rrdc.getInnerResourceRequiredRole_ResourceRequiredDelegationConnector();
		ResourceRequiredRole outer = rrdc.getOuterResourceRequiredRole_ResourceRequiredDelegationConnector();
		
		return this;
	}

}
