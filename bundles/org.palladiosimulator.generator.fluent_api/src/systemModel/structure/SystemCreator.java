package systemModel.structure;

import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

import systemModel.apiControlFlowInterfaces.ISystem;

public class SystemCreator extends SystemEntity implements ISystem {

	@Override
	public SystemCreator withName(String name) {
		return (SystemCreator) super.withName(name);
	}

	@Override
	protected System build() {
		System system = SystemFactory.eINSTANCE.createSystem();
		if (name != null) {
			system.setEntityName(name);
		}
		
		return system;
	}

	@Override
	public System createSystemNow() {
		return build();
	}
	
}
