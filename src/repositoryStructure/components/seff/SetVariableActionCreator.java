package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SetVariableAction;

import apiControlFlowInterfaces.seff.FollowSeff;

public class SetVariableActionCreator extends GeneralAction implements FollowSeff {

	private List<VariableUsage> localVariableUsages;

	public SetVariableActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.localVariableUsages = new ArrayList<>();
	}

	@Override
	public SetVariableActionCreator withName(String name) {
		return (SetVariableActionCreator) super.withName(name);
	}

	@Override
	public SetVariableActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (SetVariableActionCreator) super.withResourceDemand(specification_stochasticExpression,
				processingResource);
	}

	@Override
	public SetVariableActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (SetVariableActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public SetVariableActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (SetVariableActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected SetVariableAction build() {
		// TODO: iwelche Voraussetzungen? + localVariableUsages
		SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();
		action.getLocalVariableUsages_SetVariableAction().addAll(localVariableUsages);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		return action;
	}
}
