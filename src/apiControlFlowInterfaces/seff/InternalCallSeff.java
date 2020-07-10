package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.seff.SeffCreator;

public interface InternalCallSeff {
	public InternalCallSeff withName(String name);

	public InternalCallSeff withVaribleUsage(VariableUsage inputVariableUsage);

	public InternalCallSeff withInternalBehaviour(SeffCreator seff);

	public InternalCallSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public InternalCallSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public InternalCallSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}