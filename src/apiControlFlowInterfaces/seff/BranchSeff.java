package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.components.seff.SeffCreator.BodyBehaviour;

public interface BranchSeff {
	public BranchSeff withName(String name);

	public BranchSeff withGuardedBranchTransition(String branchCondition_stochasticExpression,
			SeffCreator branchActions, BodyBehaviour bodyBehaviourType);

	public BranchSeff withProbabilisticBranchTransition(Double branchProbability, SeffCreator branchActions,
			BodyBehaviour bodyBehaviourType);

	public BranchSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public BranchSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public BranchSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}