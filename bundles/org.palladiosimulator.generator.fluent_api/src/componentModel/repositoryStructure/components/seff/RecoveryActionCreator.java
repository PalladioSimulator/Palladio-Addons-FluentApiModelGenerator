package componentModel.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import componentModel.apiControlFlowInterfaces.seff.RecoverySeff;
import componentModel.repositoryStructure.RepositoryCreator;
import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.RecoveryAction
 * RecoveryAction}. It is used to create the '<em><b>RecoveryAction</b></em>'
 * object step-by-step, i.e. '<em><b>RecoveryActionCreator</b></em>' objects are
 * of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.RecoveryAction
 */
public class RecoveryActionCreator extends GeneralAction {

	private RecoveryActionBehaviour primary;
	private List<RecoveryActionBehaviour> otherBehaviours;

	protected RecoveryActionCreator(SeffCreator seff, RepositoryCreator repo) {
		this.repository = repo;
		this.seff = seff;
		this.otherBehaviours = new ArrayList<>();
	}

	@Override
	public RecoveryActionCreator withName(String name) {
		return (RecoveryActionCreator) super.withName(name);
	}

	/**
	 * Specifies the primary recovery behaviour of this recovery action.
	 * 
	 * @param recoveryActionBehaviour
	 * @return this recovery action in the making
	 * @see componentModel.factory.FluentRepositoryFactory#newRecoveryBehaviour()
	 */
	public RecoveryActionCreator withPrimaryBehaviour(RecoverySeff recoveryActionBehaviour) {
		Objects.requireNonNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
		RecoveryActionBehaviour build = recoveryActionBehaviour.buildRecoveryBehaviour();
		this.primary = build;
		this.repository.addRecoveryActionBehaviour(build);
		return this;
	}

	/**
	 * Adds the <code>recoveryActionBehaviour</code> to this action's list of
	 * alternate recovery behaviours.
	 * 
	 * @param recoveryActionBehaviour
	 * @return this recovery action in the making
	 * @see componentModel.factory.FluentRepositoryFactory#newRecoveryBehaviour()
	 */
	public RecoveryActionCreator withAlternativeBehaviour(RecoverySeff recoveryActionBehaviour) {
		Objects.requireNonNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
		RecoveryActionBehaviour buildRecoveryBehaviour = recoveryActionBehaviour.buildRecoveryBehaviour();
		this.otherBehaviours.add(buildRecoveryBehaviour);
		this.repository.addRecoveryActionBehaviour(buildRecoveryBehaviour);
		return this;
	}

	@Override
	public RecoveryActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
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
			action.setPrimaryBehaviour__RecoveryAction(primary);

		action.getRecoveryActionBehaviours__RecoveryAction().addAll(otherBehaviours);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}

}
