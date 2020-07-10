package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;

import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.components.seff.StopActionCreator;

public interface StopSeff {

	/**
	 * Defines the name of the current entity.
	 * 
	 * @param name
	 * @return current entity
	 */
	public StopActionCreator withName(String name);

	public StopActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource);

	public StopActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages);

	public StopActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages);

	public SeffCreator createBehaviourNow();

}
