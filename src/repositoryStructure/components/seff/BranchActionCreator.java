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
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;


public class BranchActionCreator extends GeneralAction {

	private List<AbstractBranchTransition> branches;

	public BranchActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.branches = new ArrayList<>();
	}
	
	@Override
	public BranchActionCreator withName(String name) {
		return (BranchActionCreator) super.withName(name);
	}

	public BranchActionCreator withGuardedBranch(String branchCondition_stochasticExpression,
			SeffCreator branchActions) {

		GuardedBranchTransition branch = SeffFactory.eINSTANCE.createGuardedBranchTransition();

		if (branchActions != null) {
			ResourceDemandingBehaviour branchBody = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
			branchBody.getSteps_Behaviour().addAll(branchActions.getSteps());
			branch.setBranchBehaviour_BranchTransition(branchBody);
		}

		if (branchCondition_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(branchCondition_stochasticExpression);
			branch.setBranchCondition_GuardedBranchTransition(rand);
		}

		this.branches.add(branch);
		// TODO: name + id ?
		return this;
	}

	public BranchActionCreator withProbabilisticBranch(Double branchProbability, SeffCreator branchActions) {

		ProbabilisticBranchTransition branch = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();

		if (branchActions != null) {
			ResourceDemandingBehaviour branchBody = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
			branchBody.getSteps_Behaviour().addAll(branchActions.getSteps());
			branch.setBranchBehaviour_BranchTransition(branchBody);
		}

		if (branchProbability != null)
			branch.setBranchProbability(branchProbability);

		this.branches.add(branch);
		// TODO: name + id ?
		return this;
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

	@Override
	protected BranchAction build() {
		BranchAction action = SeffFactory.eINSTANCE.createBranchAction();
		action.getBranches_Branch().addAll(branches);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}
}
