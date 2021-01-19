package systemModel.factory;


import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.structure.AssemblyContextCreator;
import systemModel.structure.EventChannelCreator;
import systemModel.structure.OperationProvidedRoleCreator;
import systemModel.structure.OperationRequiredRoleCreator;
import systemModel.structure.SystemCreator;
import systemModel.structure.connector.assemblyConnector.AssemblyConnectorCreator;
import systemModel.structure.connector.delegationConnector.ProvidedDelegationConnectorCreator;
import systemModel.structure.connector.delegationConnector.RequiredDelegationConnectorCreator;
import systemModel.structure.connector.eventChannel.EventChannelSinkConnectorCreator;
import systemModel.structure.connector.eventChannel.EventChannelSourceConnectorCreator;

public class FluentSystemFactory {
	private SystemCreator systemCreator;
	
	public ISystem newSystem() {
		systemCreator = new SystemCreator();
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
}
