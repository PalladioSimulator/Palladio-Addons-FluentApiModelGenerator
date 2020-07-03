package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.LoopFollow.LoopExitFollow.LoopStart;
import repositoryStructure.SeffCreator;

public class LoopActionCreator extends AbstractAction {

	private SeffCreator seff;
	private PCMRandomVariable iterationCount;
	private SeffCreator loopBody;

	public LoopActionCreator(SeffCreator seff) {

		this.seff = seff;

	}

	public Action followedBy() {
		LoopAction action = SeffFactory.eINSTANCE.createLoopAction();
		action.setIterationCount_LoopAction(iterationCount);
		ResourceDemandingBehaviour bodyBehaviour_Loop = action.getBodyBehaviour_Loop();

		ResourceDemandingBehaviour loop = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		loop.getSteps_Behaviour();
		//TODO: what shall we do with the internal body?

		action.getInfrastructureCall__Action();
		action.getResourceCall__Action();
		action.getResourceDemand_Action();
		action.getResourceDemandingBehaviour_AbstractAction();

		seff.setNext(action);
		return seff;
	}

	public LoopActionCreator withIterationCount(String iterationCount_stochasticExpression) {
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(iterationCount_stochasticExpression);
		this.iterationCount = rand;
		return this;
	}

//	public LoopStart withLoopBody() {
//		loopBody = new SeffCreator(this);
//		return loopBody;
//	}

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
}
