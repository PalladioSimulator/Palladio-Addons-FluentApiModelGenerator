package systemModel.factory;


import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.structure.AssemblyContextCreator;
import systemModel.structure.SystemCreator;
import systemModel.structure.assemblyConnector.AssemblyConnectorCreator;

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
}
