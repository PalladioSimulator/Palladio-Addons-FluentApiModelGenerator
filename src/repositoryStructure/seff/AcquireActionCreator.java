package repositoryStructure.seff;

import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class AcquireActionCreator {

	private SeffCreator seff;
	private PassiveResource passiveResource;
	private Double timeoutValue;
	private Boolean isTimeout;

	public AcquireActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
		action.setPassiveresource_AcquireAction(passiveResource);
		action.setTimeoutValue(timeoutValue);
		action.setTimeout(isTimeout);

		// TODO: resource demanding behaviour? + vererbung vom rest -> nötig?
		action.getInfrastructureCall__Action();
		action.getResourceCall__Action();
		action.getResourceDemand_Action();
		action.getResourceDemandingBehaviour_AbstractAction();

		seff.setNext(action);
		return seff;
	}

	public AcquireActionCreator withPassiveResource(PassiveResource passiveResource) {
		this.passiveResource = passiveResource;
		return this;
	}

	public AcquireActionCreator withTimeoutValue(Double timeoutValue) {
		this.timeoutValue = timeoutValue;
		return this;
	}

	public AcquireActionCreator isTimeout(Boolean isTimeout) {
		this.isTimeout = isTimeout;
		return this;
	}
}
