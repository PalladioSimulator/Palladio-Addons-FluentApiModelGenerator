package systemModel.factory;


import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

import shared.util.RepositoryLoader;
import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.structure.AssemblyContextCreator;
import systemModel.structure.EventChannelCreator;
import systemModel.structure.SystemCreator;
import systemModel.structure.connector.assemblyConnector.AssemblyConnectorCreator;
import systemModel.structure.connector.assemblyInfrastructureConnector.AssemblyInfrastructureConnectorCreator;
import systemModel.structure.connector.eventChannel.EventChannelSinkConnectorCreator;
import systemModel.structure.connector.eventChannel.EventChannelSourceConnectorCreator;
import systemModel.structure.connector.eventDelegationConnector.SinkDelegationConnectorCreator;
import systemModel.structure.connector.eventDelegationConnector.SourceDelegationConnectorCreator;
import systemModel.structure.connector.infrastructureDelegationConnector.ProvidedInfrastructureDelegationConnectorCreator;
import systemModel.structure.connector.infrastructureDelegationConnector.RequiredInfrastructureDelegationConnectorCreator;
import systemModel.structure.connector.operationDelegationConnector.ProvidedDelegationConnectorCreator;
import systemModel.structure.connector.operationDelegationConnector.RequiredDelegationConnectorCreator;
import systemModel.structure.connector.requiredResourceDelegationConnector.RequiredResourceDelegationConnectorCreator;
import systemModel.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
import systemModel.structure.qosAnnotations.QoSAnnotationsCreator;
import systemModel.structure.systemRole.InfrastructureProvidedRoleCreator;
import systemModel.structure.systemRole.InfrastructureRequiredRoleCreator;
import systemModel.structure.systemRole.OperationProvidedRoleCreator;
import systemModel.structure.systemRole.OperationRequiredRoleCreator;
import systemModel.structure.systemRole.ResourceRequiredRoleCreator;
import systemModel.structure.systemRole.SinkRoleCreator;
import systemModel.structure.systemRole.SourceRoleCreator;

public class FluentSystemFactory {
	private SystemCreator systemCreator;
	
	public ISystem newSystem() {
		EcorePlugin.ExtensionProcessor.process(null);
		ResourceRepository resources = RepositoryLoader.loadResourceTypeRepository("pathmap://PCM_MODELS/Palladio.resourcetype");
		systemCreator = new SystemCreator(resources);
		return systemCreator;
	}
	
	public AssemblyContextCreator newAssemblyContext() {
		return new AssemblyContextCreator(systemCreator);
	}
	
	public AssemblyConnectorCreator newAssemblyConnector() {
		return new AssemblyConnectorCreator(systemCreator);
	}
	
	public OperationRequiredRoleCreator newOperationRequiredRole() {
		return new OperationRequiredRoleCreator(systemCreator);
	}
	
	public RequiredDelegationConnectorCreator newRequiredDelegationConnectorCreator() {
		return new RequiredDelegationConnectorCreator(systemCreator);
	}
	
	public OperationProvidedRoleCreator newOperationProvidedRole() {
		return new OperationProvidedRoleCreator(systemCreator);
	}

	public ProvidedDelegationConnectorCreator newProvidedDelegationConnectorCreator() {
		return new ProvidedDelegationConnectorCreator(systemCreator);
	}
	
	public EventChannelCreator newEventChannelCreator() {
		return new EventChannelCreator(systemCreator);
	}
	
	public EventChannelSinkConnectorCreator newEventChannelSinkConnector() {
		return new EventChannelSinkConnectorCreator(systemCreator);
	}
	
	public EventChannelSourceConnectorCreator newEventChannelSourceConnector() {
		return new EventChannelSourceConnectorCreator(systemCreator);
	}
	
	public SinkRoleCreator newSinkRole() {
		return new SinkRoleCreator(systemCreator);
	}
	
	public SinkDelegationConnectorCreator newSinkDelegationConnector() {
		return new SinkDelegationConnectorCreator(systemCreator);
	}
	
	public SourceRoleCreator newSourceRole() {
		return new SourceRoleCreator(systemCreator);
	}
	
	public SourceDelegationConnectorCreator newSourceDelegationConnector() {
		return new SourceDelegationConnectorCreator(systemCreator);
	}
	
	public AssemblyInfrastructureConnectorCreator newAssemblyInfrastructureConnector() {
		return new AssemblyInfrastructureConnectorCreator(systemCreator);
	}
	
	public InfrastructureRequiredRoleCreator newInfrastructureRequiredRole() {
		return new InfrastructureRequiredRoleCreator(systemCreator);
	}
	
	public RequiredInfrastructureDelegationConnectorCreator newRequiredInfrastructureDelegationConnectorCreator() {
		return new RequiredInfrastructureDelegationConnectorCreator(systemCreator);
	}
	
	public InfrastructureProvidedRoleCreator newInfrastructurenProvidedRole() {
		return new InfrastructureProvidedRoleCreator(systemCreator);
	}

	public ProvidedInfrastructureDelegationConnectorCreator newProvidedInfrastructureDelegationConnectorCreator() {
		return new ProvidedInfrastructureDelegationConnectorCreator(systemCreator);
	}
	
	public QoSAnnotationsCreator newQoSAnnotations() {
		return new QoSAnnotationsCreator(systemCreator);
	}
	
	public ResourceRequiredRoleCreator newResourceRequiredRole() {
		return new ResourceRequiredRoleCreator(systemCreator);
	}
	
	public ResourceRequiredDelegationConnectorCreator newResourceRequiredDelegationConnector() {
		return new ResourceRequiredDelegationConnectorCreator(systemCreator);
	}
	
	public RequiredResourceDelegationConnectorCreator newRequiredResourceDelegationConnector() {
		return new RequiredResourceDelegationConnectorCreator(systemCreator);
	}
}
