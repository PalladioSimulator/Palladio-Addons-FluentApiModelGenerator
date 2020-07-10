package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.seff.SeffCreator;

public interface CollectionSeff {
	public CollectionSeff withName(String name);

	public CollectionSeff withParameter(Parameter parameter);

	public CollectionSeff withLoopBody(SeffCreator loopBody);

	public CollectionSeff withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public CollectionSeff withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages);

	public CollectionSeff withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages);

	public ActionSeff followedBy();

}