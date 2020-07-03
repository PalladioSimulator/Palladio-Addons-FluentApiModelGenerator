package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.StopAction;

import repositoryStructure.SeffCreator;

public class StopActionCreator extends AbstractAction {

	private SeffCreator seff;

	public StopActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public SeffCreator createSeffNow() {
		StopAction action = SeffFactory.eINSTANCE.createStopAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		seff.setNext(action);
		return seff;
	}

	@Override
	public StopActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (StopActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public StopActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (StopActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public StopActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (StopActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}
}
