package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.seff.InternalSeff;
import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.internals.ProcessingResource;
import repositoryStructure.internals.ResourceSignature;

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

	protected InternalCallActionCreator(SeffCreator seff) {
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

	/**
	 * Adds the <code>variableUsage</code> to this action's list of input variable
	 * usages.
	 * 
	 * @param variableUsage
	 * @return this internal call action in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public InternalCallActionCreator withInputVaribleUsage(VariableUsageCreator variableUsage) {
		Objects.requireNonNull(variableUsage, "variableUsage must not be null");
		this.inputVariableUsages.add(variableUsage.build());
		return this;
	}

	/**
	 * Specifies the internal behaviour that is called on by this action.
	 * 
	 * @param internalBehaviour
	 * @return this internal call action in the making
	 * @see factory.FluentRepositoryFactory#newInternalBehaviour()
	 */
	public InternalCallActionCreator withInternalBehaviour(InternalSeff internalBehaviour) {
		Objects.requireNonNull(internalBehaviour, "internalBehaviour must not be null");
		this.internalSeff = internalBehaviour;
		return this;
	}

	@Override
	public InternalCallActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
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
