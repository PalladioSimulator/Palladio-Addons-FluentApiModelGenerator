package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.LoopFollow.LoopExitFollow.LoopStart;
import repositoryStructure.SeffCreator;

public class LoopActionCreator {

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

		// TODO: resource demanding behaviour?
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
}
