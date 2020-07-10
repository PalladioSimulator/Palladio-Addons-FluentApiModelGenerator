package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.seff.SeffCreator;

public interface StopSeff {

	public StopSeff withName(String name);

	public SeffCreator createBehaviourNow();

	public StopSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public StopSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public StopSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

}