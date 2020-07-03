package repositoryStructure.seff;

import java.util.List;

import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class BranchActionCreator extends AbstractAction {

	private SeffCreator seff;
	private List<AbstractBranchTransition> branches;

	public BranchActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		BranchAction action = SeffFactory.eINSTANCE.createBranchAction();
		ProbabilisticBranchTransition prob = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();
		GuardedBranchTransition guard = SeffFactory.eINSTANCE.createGuardedBranchTransition();

		ResourceDemandingBehaviour body = prob.getBranchBehaviour_BranchTransition();
		double probab = prob.getBranchProbability();

		guard.getBranchBehaviour_BranchTransition();
		PCMRandomVariable cond = guard.getBranchCondition_GuardedBranchTransition();

		action.getBranches_Branch();

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		seff.setNext(action);
		return seff;
	}

	@Override
	public BranchActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (BranchActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public BranchActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (BranchActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public BranchActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (BranchActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}
}
