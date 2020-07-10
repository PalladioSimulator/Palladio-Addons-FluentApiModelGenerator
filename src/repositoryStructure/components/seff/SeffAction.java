package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.seff.AbstractAction;

import apiControlFlowInterfaces.seff.SeffInterfaces;
import apiControlFlowInterfaces.seff.SeffInterfaces1;
import repositoryStructure.Entity;

public abstract class SeffAction extends Entity implements SeffInterfaces, SeffInterfaces1{
	
	protected SeffCreator seff;
	
	protected abstract AbstractAction build();
	
	public SeffCreator followedBy() {
		AbstractAction action = this.build();
		
		seff.setNext(action);
		return seff;
	}

}
