package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

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
import apiControlFlowInterfaces.Follow;

public class RecoveryActionCreator extends GeneralAction implements Follow {

	private SeffCreator seff;

	private RecoveryActionBehaviour primary;
	private List<RecoveryActionBehaviour> otherBehaviours;

	public RecoveryActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.otherBehaviours = new ArrayList<>();
	}

	public Action followedBy() {
		RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();
		if (primary != null)
			action.setPrimaryBehaviour__RecoveryAction(primary);
		action.getRecoveryActionBehaviours__RecoveryAction().addAll(otherBehaviours);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		seff.setNext(action);
		return seff;
	}

	public RecoveryActionCreator withPrimaryBehaviour() {
		RecoveryActionBehaviour recovActionBehaviour = SeffReliabilityFactory.eINSTANCE.createRecoveryActionBehaviour();
		// TODO: primary und other behaviours mit Eigenschaften
		recovActionBehaviour.getFailureHandlingAlternatives__RecoveryActionBehaviour();
		recovActionBehaviour.getFailureTypes_FailureHandlingEntity();
		recovActionBehaviour.getSteps_Behaviour();

		this.primary = recovActionBehaviour;
		return this;
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
