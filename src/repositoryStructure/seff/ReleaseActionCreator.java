package repositoryStructure.seff;

import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class ReleaseActionCreator {

	private SeffCreator seff;
	private PassiveResource passiveResource;

	public ReleaseActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		ReleaseAction action = SeffFactory.eINSTANCE.createReleaseAction();
		action.setPassiveResource_ReleaseAction(passiveResource);

		// TODO: resource demanding behaviour? + vererbung vom rest -> nötig?
		action.getInfrastructureCall__Action();
		action.getResourceCall__Action();
		action.getResourceDemand_Action();
		action.getResourceDemandingBehaviour_AbstractAction();

		seff.setNext(action);
		return seff;
	}

	public ReleaseActionCreator withPassiveResource(PassiveResource passiveResource) {
		this.passiveResource = passiveResource;
		return this;
	}

}
