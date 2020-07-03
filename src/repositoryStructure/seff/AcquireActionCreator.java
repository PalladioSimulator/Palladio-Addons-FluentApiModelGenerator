package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.LoopAction.AcquireLoop;
import repositoryStructure.SeffCreator;

public class AcquireActionCreator extends AbstractAction implements AcquireLoop {

	private SeffCreator seff;
	private PassiveResource passiveResource;
	private Double timeoutValue;
	private Boolean isTimeout;

	public AcquireActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public SeffCreator followedBy() {
		AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
		action.setPassiveresource_AcquireAction(passiveResource);
		action.setTimeoutValue(timeoutValue);
		action.setTimeout(isTimeout);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

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

	@Override
	public AcquireActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (AcquireActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public AcquireActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (AcquireActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public AcquireActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (AcquireActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}
}
