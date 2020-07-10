package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.seff.AbstractAction;

import apiControlFlowInterfaces.seff.ActionSeff;
import repositoryStructure.Entity;

public abstract class SeffAction extends Entity {
	
	protected SeffCreator seff;
	
	protected abstract AbstractAction build();
	
	public ActionSeff followedBy() {
		AbstractAction action = this.build();
		
		seff.setNext(action);
		return seff;
	}
}
