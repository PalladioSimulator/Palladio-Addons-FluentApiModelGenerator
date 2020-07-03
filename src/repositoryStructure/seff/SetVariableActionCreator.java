package repositoryStructure.seff;

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

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.LoopAction.SetVariableLoop;
import repositoryStructure.SeffCreator;

public class SetVariableActionCreator extends AbstractAction implements SetVariableLoop{

	private SeffCreator seff;

	private List<VariableUsage> localVariableUsages;

	public SetVariableActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.localVariableUsages = new ArrayList<>();
	}

	public SeffCreator followedBy() {
		// TODO: iwelche Voraussetzungen? + localVariableUsages
		SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		action.getLocalVariableUsages_SetVariableAction().addAll(localVariableUsages);

		seff.setNext(action);
		return seff;
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
}
