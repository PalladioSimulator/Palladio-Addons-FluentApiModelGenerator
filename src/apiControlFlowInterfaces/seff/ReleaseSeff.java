package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

public interface ReleaseSeff {
	public ReleaseSeff withName(String name);

	public ReleaseSeff withPassiveResource(PassiveResource passiveResource);

	public ReleaseSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public ReleaseSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public ReleaseSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}