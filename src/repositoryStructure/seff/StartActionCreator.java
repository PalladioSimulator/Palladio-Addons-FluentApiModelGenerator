package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class StartActionCreator extends AbstractAction {

	private SeffCreator seff;

	public StartActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		StartAction action = SeffFactory.eINSTANCE.createStartAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		seff.setNext(action);
		return seff;
	}
	
	@Override
	public StartActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (StartActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public StartActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (StartActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public StartActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (StartActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}
}
