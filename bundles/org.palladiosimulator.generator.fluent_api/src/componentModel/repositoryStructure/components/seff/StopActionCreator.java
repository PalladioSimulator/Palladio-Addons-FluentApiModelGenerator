package componentModel.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;

import componentModel.apiControlFlowInterfaces.seff.StopSeff;
import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StopAction
 * StopAction}. It is used to create the '<em><b>StopAction</b></em>' object
 * step-by-step, i.e. '<em><b>StopActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StopAction
 */
public class StopActionCreator extends GeneralAction implements StopSeff {

	private List<ParametricResourceDemand> demands = new ArrayList<>();
	private List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
	private List<ResourceCall> resourceCalls = new ArrayList<>();

	protected StopActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public StopActionCreator withName(String name) {
		return (StopActionCreator) super.withName(name);
	}

	@Override
	public SeffCreator createBehaviourNow() {
		AbstractAction action = this.build();
		seff.setNext(action);
		return seff;
	}

	@Override
	public StopActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (StopActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public StopActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (StopActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public StopActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (StopActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

	@Override
	protected StopAction build() {
		StopAction action = SeffFactory.eINSTANCE.createStopAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);
		return action;
	}
}
