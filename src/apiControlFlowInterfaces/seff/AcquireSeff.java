package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

public interface AcquireSeff extends SeffInterfaces1{
	public AcquireSeff withName(String name);

	public AcquireSeff withPassiveResource(PassiveResource passiveResource);

	public AcquireSeff withTimeoutValue(Double timeoutValue);

	public AcquireSeff isTimeout(Boolean isTimeout);

	public AcquireSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public AcquireSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public AcquireSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}