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

import apiControlFlowInterfaces.seff.FollowSeff;
import repositoryStructure.components.seff.SeffCreator.BodyBehaviour;

public class BranchActionCreator extends GeneralAction implements FollowSeff {

	private List<AbstractBranchTransition> branches;

	public BranchActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.branches = new ArrayList<>();
	}

	@Override
	public BranchActionCreator withName(String name) {
		return (BranchActionCreator) super.withName(name);
	}

	public BranchActionCreator withGuardedBranchTransition(String branchCondition_stochasticExpression,
			SeffCreator branchActions, BodyBehaviour bodyBehaviourType) {

		GuardedBranchTransition branch = SeffFactory.eINSTANCE.createGuardedBranchTransition();

		if (branchActions != null) {
			switch (bodyBehaviourType) {
			case SEFF:
//TODO
			}
			ResourceDemandingBehaviour branchBody = branchActions.buildBehaviour(); // TODO
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

	public BranchActionCreator withProbabilisticBranchTransition(Double branchProbability, SeffCreator branchActions,
			BodyBehaviour bodyBehaviourType) {

		ProbabilisticBranchTransition branch = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();

		if (branchActions != null) {
			ResourceDemandingBehaviour branchBody = branchActions.buildBehaviour(); // TODO
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
