package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;

import apiControlFlowInterfaces.seff.Internal.StartActionInternal;
import apiControlFlowInterfaces.seff.StartActionSeff;

public class StartActionCreator extends GeneralAction implements StartActionSeff{

	public StartActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public StartActionCreator withName(String name) {
		return (StartActionCreator) super.withName(name);
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

	public SeffCreator followedBy() {
		AbstractAction action = this.build();
		
		seff.setNext(action);
		return seff;
	}
	
	@Override
	public StartAction build() {
		StartAction action = SeffFactory.eINSTANCE.createStartAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		return action;
	}

}
