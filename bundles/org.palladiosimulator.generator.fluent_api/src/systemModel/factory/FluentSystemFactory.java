package systemModel.factory;

import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.structure.SystemCreator;

public class FluentSystemFactory {
	private SystemCreator systemCreator;
	
	public ISystem newSystem() {
		systemCreator = new SystemCreator();
		return systemCreator;
	}
}
