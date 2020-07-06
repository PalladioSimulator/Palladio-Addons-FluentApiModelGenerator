package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.seff.AbstractAction;

import repositoryStructure.Entity;

public abstract class SeffAction extends Entity {
	
	protected SeffCreator seff;
	
	protected abstract AbstractAction build();
	
	public SeffCreator followedBy() {
		AbstractAction action = this.build();
		
		seff.setNext(action);
		return seff;
	}

}
