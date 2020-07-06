package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

public class ForkActionCreator extends GeneralAction {

	private List<ForkedBehaviour> asynchronousForkedBehaviours;
	private List<ForkedBehaviour> synchronousForkedBehaviours;
	private List<VariableUsage> variableUsages;

	public ForkActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.asynchronousForkedBehaviours = new ArrayList<>();
		this.synchronousForkedBehaviours = new ArrayList<>();
		this.variableUsages = new ArrayList<>();
	}

	@Override
	public ForkActionCreator withName(String name) {
		return (ForkActionCreator) super.withName(name);
	}
	
	public ForkActionCreator withOutputParameterUsageAtSynchronisationPoint(VariableUsage variableUsage) {
		if (variableUsage != null)
			this.variableUsages.add(variableUsage);
		return this;
	}

	public ForkActionCreator withSynchronousForkedBehaviourAtSynchronisationPoint(SeffCreator forkedBehaviours) {
		if (forkedBehaviours != null) {
			ForkedBehaviour forkedBehaviour = SeffFactory.eINSTANCE.createForkedBehaviour();
			forkedBehaviour.getSteps_Behaviour().addAll(forkedBehaviours.getSteps());
			this.synchronousForkedBehaviours.add(forkedBehaviour);
		}
		return this;
	}

	public ForkActionCreator withAsynchronousForkedBehaviour(SeffCreator forkedBehaviours) {
		if (forkedBehaviours != null) {
			ForkedBehaviour forkedBehaviour = SeffFactory.eINSTANCE.createForkedBehaviour();
			forkedBehaviour.getSteps_Behaviour().addAll(forkedBehaviours.getSteps());
			this.asynchronousForkedBehaviours.add(forkedBehaviour);
		}
		return this;
	}

	@Override
	public ForkActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (ForkActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public ForkActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (ForkActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public ForkActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
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
