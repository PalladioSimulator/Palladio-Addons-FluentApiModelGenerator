package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Seff.FollowSeff;
import apiControlFlowInterfaces.Seff.LoopSeff;

public class LoopActionCreator extends GeneralAction implements LoopSeff, FollowSeff {

	private PCMRandomVariable iterationCount;
	private List<AbstractAction> steps;

	public LoopActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.steps = new ArrayList<>();
	}

	@Override
	public LoopActionCreator withName(String name) {
		return (LoopActionCreator) super.withName(name);
	}
	
	public LoopActionCreator withIterationCount(String iterationCount_stochasticExpression) {
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(iterationCount_stochasticExpression);
		this.iterationCount = rand;
		return this;
	}

	public LoopActionCreator withLoopBody(SeffCreator loopBody) {
		if (loopBody != null)
			this.steps = loopBody.getSteps();
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
			VariableUsage... variableUsages) {
		return (LoopActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public LoopActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (LoopActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

	@Override
	public LoopAction build() {
		LoopAction action = SeffFactory.eINSTANCE.createLoopAction();
		action.setIterationCount_LoopAction(iterationCount);

		ResourceDemandingBehaviour loop = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		loop.getSteps_Behaviour().addAll(this.steps);
		action.setBodyBehaviour_Loop(loop);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}
}
