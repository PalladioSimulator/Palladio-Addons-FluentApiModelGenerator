package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.seff.AbstractAction;

import apiControlFlowInterfaces.seff.ActionSeff;
import repositoryStructure.Entity;

public abstract class SeffAction extends Entity {

	protected SeffCreator seff;

	@Override
	protected abstract AbstractAction build();

	/**
	 * Turns the previous action-in-the-making into an
	 * '<em><b>AbstractAction</b></em>' object. The action is added to the SEFF's
	 * body behaviour and its predecessor is set so that the actions are linked in
	 * the correct order.
	 * 
	 * @return the SEFF's body behaviour
	 */
	public ActionSeff followedBy() {
		AbstractAction action = this.build();

		seff.setNext(action);
		return seff;
	}
}
