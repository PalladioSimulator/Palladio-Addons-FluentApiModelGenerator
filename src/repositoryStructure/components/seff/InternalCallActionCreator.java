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

import apiControlFlowInterfaces.seff.InternalSeff;
import repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.InternalCallAction InternalCallAction}.
 * It is used to create the '<em><b>InternalCallAction</b></em>' object
 * step-by-step, i.e. '<em><b>InternalCallActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.InternalCallAction
 */
public class InternalCallActionCreator extends GeneralAction {

	private List<VariableUsage> inputVariableUsages;
	private InternalSeff internalSeff;

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

	public InternalCallActionCreator withVaribleUsage(VariableUsageCreator inputVariableUsage) {
		this.inputVariableUsages.add(inputVariableUsage.build());
		return this;
	}

	public InternalCallActionCreator withInternalBehaviour(InternalSeff seff) {
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
			VariableUsageCreator... variableUsages) {
		return (InternalCallActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public InternalCallActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (InternalCallActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected InternalCallAction build() {
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
