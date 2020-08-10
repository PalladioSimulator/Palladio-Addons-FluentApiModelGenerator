package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;

import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.internals.ProcessingResource;
import repositoryStructure.internals.ResourceSignature;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StartAction
 * StartAction}. It is used to create the '<em><b>StartAction</b></em>' object
 * step-by-step, i.e. '<em><b>StartActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StartAction
 */
public class StartActionCreator extends GeneralAction {

	protected StartActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public StartActionCreator withName(String name) {
		return (StartActionCreator) super.withName(name);
	}

	@Override
	public StartActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (StartActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public StartActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (StartActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public StartActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (StartActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

	@Override
	protected StartAction build() {
		StartAction action = SeffFactory.eINSTANCE.createStartAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		return action;
	}

}
