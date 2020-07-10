package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.seff.SeffCreator;

public interface ForkSeff {
	public ForkSeff withName(String name);

	public ForkSeff withOutputParameterUsageAtSynchronisationPoint(VariableUsage variableUsage);

	public ForkSeff withSynchronousForkedBehaviourAtSynchronisationPoint(SeffCreator forkedBehaviours);

	public ForkSeff withAsynchronousForkedBehaviour(SeffCreator forkedBehaviours);

	public ForkSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public ForkSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public ForkSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}