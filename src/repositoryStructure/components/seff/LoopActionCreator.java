package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.components.VariableUsageCreator;

public class LoopActionCreator extends GeneralAction {

	private PCMRandomVariable iterationCount;
	private SeffCreator loopBody;

	public LoopActionCreator(SeffCreator seff) {
		this.seff = seff;
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
			if (loopBody.getSignature() == null && loopBody.getSeffTypeID() == null
					&& loopBody.getInternalBehaviours().isEmpty())
				action.setBodyBehaviour_Loop(loopBody.buildBehaviour());
			else
				action.setBodyBehaviour_Loop(loopBody.buildSeff());
		}

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}
}
