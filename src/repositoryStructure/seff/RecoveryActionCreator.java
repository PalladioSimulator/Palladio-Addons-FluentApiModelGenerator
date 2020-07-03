package repositoryStructure.seff;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class RecoveryActionCreator extends AbstractAction {

	private SeffCreator seff;

	public RecoveryActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();

		// TODO:
		RecoveryActionBehaviour beh = action.getPrimaryBehaviour__RecoveryAction();
		EList<RecoveryActionBehaviour> rec = action.getRecoveryActionBehaviours__RecoveryAction();

		beh.getFailureHandlingAlternatives__RecoveryActionBehaviour();
		beh.getFailureTypes_FailureHandlingEntity();
		beh.getSteps_Behaviour();

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		seff.setNext(action);
		return seff;
	}

	@Override
	public RecoveryActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (RecoveryActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public RecoveryActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (RecoveryActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public RecoveryActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (RecoveryActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

}
