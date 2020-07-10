package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import apiControlFlowInterfaces.seff.RecoverySeff;
import repositoryStructure.components.VariableUsageCreator;

public class RecoveryActionCreator extends GeneralAction {

	private RecoverySeff primary;
	private List<RecoverySeff> otherBehaviours;

	public RecoveryActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.otherBehaviours = new ArrayList<>();
	}

	@Override
	public RecoveryActionCreator withName(String name) {
		return (RecoveryActionCreator) super.withName(name);
	}

	public RecoveryActionCreator withPrimaryBehaviour(RecoverySeff recoveryActionBehaviour) {
		if (recoveryActionBehaviour != null) {
			this.primary = recoveryActionBehaviour;
		}
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
			VariableUsageCreator... variableUsages) {
		return (RecoveryActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public RecoveryActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (RecoveryActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected RecoveryAction build() {
		RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();

		if (name != null)
			action.setEntityName(name);

		if (primary != null)
			action.setPrimaryBehaviour__RecoveryAction(primary.buildRecoveryBehaviour());

		otherBehaviours.stream().map(r -> r.buildRecoveryBehaviour())
				.forEach(r -> action.getRecoveryActionBehaviours__RecoveryAction().add(r));

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}

}
