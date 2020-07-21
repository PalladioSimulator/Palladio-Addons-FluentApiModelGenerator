package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.seff.Seff;
import repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.LoopAction
 * LoopAction}. It is used to create the '<em><b>LoopAction</b></em>' object
 * step-by-step, i.e. '<em><b>LoopActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.LoopAction
 */
public class LoopActionCreator extends GeneralAction {

	private PCMRandomVariable iterationCount;
	private Seff loopBody;

	protected LoopActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public LoopActionCreator withName(String name) {
		return (LoopActionCreator) super.withName(name);
	}

	/**
	 * Specifies the number of repetitions the inner ResourceDemandingBehaviour is
	 * executed.
	 * <p>
	 * The number of repetitions is specified by a random variable evaluating to
	 * integer or an IntPMF. The number of iterations specified by the random
	 * variable always needs to be bounded, i.e., the probabilities in an IntPMF for
	 * iteration numbers above a certain threshold must be zero. Otherwise, it would
	 * be possible that certain requests do not terminate, which would complicate
	 * performance analyses. The stochastic expression defining the iteration random
	 * variable may include references to input or component parameters to model
	 * dependencies between the usage profile and the number of loop iterations.
	 * </p>
	 * 
	 * @param iterationCount_stochasticExpression
	 * @return this loop action in the making
	 * @see org.palladiosimulator.pcm.seff.LoopAction
	 */
	public LoopActionCreator withIterationCount(String iterationCount_stochasticExpression) {
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(iterationCount_stochasticExpression);
		this.iterationCount = rand;
		return this;
	}

	/**
	 * Specifies the inner ResourceDemandingBehaviour representing the loop body.
	 * <p>
	 * Notice, that loop actions should only be modeled if the loop body contains
	 * either external service calls or resource demands directed at special
	 * resources. Otherwise, control flow loops in component behaviour should be
	 * abstracted by subsuming them in InternalAction, which combine a number of
	 * instructions.
	 * </p>
	 * 
	 * @param loopBody
	 * @return this loop action in the making
	 */
	public LoopActionCreator withLoopBody(Seff loopBody) {
		if (loopBody != null)
			this.loopBody = loopBody;
		return this;
	}

	@Override
	public LoopActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (LoopActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public LoopActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (LoopActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public LoopActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (LoopActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

	@Override
	protected LoopAction build() {
		LoopAction action = SeffFactory.eINSTANCE.createLoopAction();
		action.setIterationCount_LoopAction(iterationCount);

		if (loopBody != null) {
			ResourceDemandingSEFF build = loopBody.buildRDSeff();
			if (build.getDescribedService__SEFF() == null && build.getSeffTypeID() == null
					&& build.getResourceDemandingInternalBehaviours().isEmpty())
				action.setBodyBehaviour_Loop(loopBody.buildBehaviour());
			else
				action.setBodyBehaviour_Loop(build);
		}

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}
}
