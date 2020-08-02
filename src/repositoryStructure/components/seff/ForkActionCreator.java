package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

import apiControlFlowInterfaces.seff.InternalSeff;
import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.internals.ProcessingResource;
import repositoryStructure.internals.ResourceSignature;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.ForkAction
 * BranchAction}. It is used to create the '<em><b>ForkAction</b></em>' object
 * step-by-step, i.e. '<em><b>ForkActionCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.ForkAction
 */
public class ForkActionCreator extends GeneralAction {

	private List<ForkedBehaviour> asynchronousForkedBehaviours;
	private List<ForkedBehaviour> synchronousForkedBehaviours;
	private List<VariableUsage> variableUsages;

	protected ForkActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.asynchronousForkedBehaviours = new ArrayList<>();
		this.synchronousForkedBehaviours = new ArrayList<>();
		this.variableUsages = new ArrayList<>();
	}

	@Override
	public ForkActionCreator withName(String name) {
		return (ForkActionCreator) super.withName(name);
	}

	/**
	 * Adds the <code>variableUsage</code> to this action's list of output parameter
	 * usages at the synchronization point.
	 * 
	 * @param variableUsage
	 * @return this fork action in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public ForkActionCreator withOutputParameterUsageAtSynchronisationPoint(VariableUsageCreator variableUsage) {
		Objects.requireNonNull(variableUsage, "variableUsage must not be null");
		this.variableUsages.add(variableUsage.build());
		return this;
	}

	/**
	 * Adds the <code>forkedBehaviour</code> to this action's list of synchronous
	 * forked behaviours at the synchronization point.
	 * 
	 * @param forkedBehaviour
	 * @return this fork action in the making
	 * @see factory.FluentRepositoryFactory#newInternalBehaviour()
	 */
	public ForkActionCreator withSynchronousForkedBehaviourAtSynchronisationPoint(InternalSeff forkedBehaviour) {
		Objects.requireNonNull(forkedBehaviour, "forkedBehaviour must not be null");
		ForkedBehaviour fork = forkedBehaviour.buildForkedBehaviour();
		this.synchronousForkedBehaviours.add(fork);
		return this;
	}

	/**
	 * Adds the <code>forkedBehaviour</code> to this action's list of asynchronous
	 * forked behaviours.
	 * 
	 * @param forkedBehaviour
	 * @return this fork action in the making
	 * @see factory.FluentRepositoryFactory#newInternalBehaviour()
	 */
	public ForkActionCreator withAsynchronousForkedBehaviour(InternalSeff forkedBehaviour) {
		Objects.requireNonNull(forkedBehaviour, "forkedBehaviour must not be null");
		ForkedBehaviour fork = forkedBehaviour.buildForkedBehaviour();
		this.asynchronousForkedBehaviours.add(fork);
		return this;
	}

	@Override
	public ForkActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (ForkActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public ForkActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (ForkActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public ForkActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (ForkActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

	@Override
	protected ForkAction build() {
		ForkAction action = SeffFactory.eINSTANCE.createForkAction();
		action.getAsynchronousForkedBehaviours_ForkAction().addAll(asynchronousForkedBehaviours);

		SynchronisationPoint synch = SeffFactory.eINSTANCE.createSynchronisationPoint();
		synch.getOutputParameterUsage_SynchronisationPoint().addAll(variableUsages);
		synch.getSynchronousForkedBehaviours_SynchronisationPoint().addAll(synchronousForkedBehaviours);
		action.setSynchronisingBehaviours_ForkAction(synch);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}
}
