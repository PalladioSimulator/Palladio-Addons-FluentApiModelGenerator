package componentModel.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SetVariableAction;

import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.SetVariableAction SetVariableAction}.
 * It is used to create the '<em><b>SetVariableAction</b></em>' object
 * step-by-step, i.e. '<em><b>SetVariableActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.SetVariableAction
 */
public class SetVariableActionCreator extends GeneralAction {

	private List<VariableUsage> localVariableUsages;

	protected SetVariableActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.localVariableUsages = new ArrayList<>();
	}

	@Override
	public SetVariableActionCreator withName(String name) {
		return (SetVariableActionCreator) super.withName(name);
	}

	/**
	 * Adds the <code>variableUsage</code> to this action's list of local variable
	 * usages.
	 * 
	 * @param variableUsage
	 * @return this set variable action in the making
	 * @see componentModel.factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public SetVariableActionCreator withLocalVariableUsage(VariableUsageCreator variableUsage) {
		Objects.requireNonNull(variableUsage, "variableUsage must not be null");
		this.localVariableUsages.add(variableUsage.build());
		return this;
	}

	@Override
	public SetVariableActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (SetVariableActionCreator) super.withResourceDemand(specification_stochasticExpression,
				processingResource);
	}

	@Override
	public SetVariableActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (SetVariableActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public SetVariableActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (SetVariableActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected SetVariableAction build() {
		SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();

		if (name != null)
			action.getEntityName();

		action.getLocalVariableUsages_SetVariableAction().addAll(localVariableUsages);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		return action;
	}
}
