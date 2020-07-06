package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Seff.FollowSeff;
import apiControlFlowInterfaces.Seff.InternalCallSeff;

public class InternalCallActionCreator extends GeneralAction implements InternalCallSeff, FollowSeff {

	private List<VariableUsage> inputVariableUsages;
	private SeffCreator internalSeff;

	public InternalCallActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.demands = new ArrayList<>();
		this.infrastructureCalls = new ArrayList<>();
		this.resourceCalls = new ArrayList<>();
		this.inputVariableUsages = new ArrayList<>();
	}

	@Override
	public InternalCallActionCreator withName(String name) {
		return (InternalCallActionCreator) super.withName(name);
	}

	public InternalCallActionCreator withVaribleUsage(VariableUsage inputVariableUsage) {
		this.inputVariableUsages.add(inputVariableUsage);
		return this;
	}

	public InternalCallActionCreator withInternalBehaviour(SeffCreator seff) {
		this.internalSeff = seff;
		return this;
	}

	@Override
	public InternalCallActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (InternalCallActionCreator) super.withResourceDemand(specification_stochasticExpression,
				processingResource);
	}

	@Override
	public InternalCallActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (InternalCallActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public InternalCallActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (InternalCallActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public InternalCallAction build() {
		InternalCallAction action = SeffFactory.eINSTANCE.createInternalCallAction();

		if (internalSeff != null) {
			ResourceDemandingInternalBehaviour internal = internalSeff.buildInternalBehaviour();
			action.setCalledResourceDemandingInternalBehaviour(internal);
		}

		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);

		action.getResourceDemand_Action().addAll(demands);
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);

		return action;
	}

}
