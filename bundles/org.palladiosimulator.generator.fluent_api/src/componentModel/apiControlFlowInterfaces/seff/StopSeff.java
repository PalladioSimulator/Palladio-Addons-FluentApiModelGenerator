package componentModel.apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;

import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.components.seff.SeffCreator;
import componentModel.repositoryStructure.components.seff.StopActionCreator;
import componentModel.repositoryStructure.internals.ProcessingResource;
import componentModel.repositoryStructure.internals.ResourceSignature;

public interface StopSeff {

	/**
	 * Defines the name of this stop action.
	 * 
	 * @param name
	 * @return this stop action
	 */
	public StopActionCreator withName(String name);

	/**
	 * Adds a
	 * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand
	 * ParametricResourceDemand} to this action.
	 * <p>
	 * Parametric Resource Demand specifies the amount of processing requested from
	 * a certain type of resource in a parameterized way. It assigns the demand
	 * specified as a Random-Variable
	 * (<code>specification_stochasticExpression</code>) to an abstract
	 * ProcessingResourceType <code>processingResource</code>(e.g., CPU, hard disk)
	 * instead of a concrete ProcessingResourceSpecification (e.g., 5 GHz CPU, 20
	 * MByte/s hard disk).
	 * </p>
	 * 
	 * @param specification_stochasticExpression
	 * @param processingResource
	 * @return this action in the making
	 */
	public StopActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource);

	/**
	 * Adds an
	 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall
	 * InfrastructureCall} to this action.
	 * 
	 * @param numberOfCalls_stochasticExpression
	 * @param signature
	 * @param requiredRole
	 * @param variableUsages
	 * @return this stop action in the making
	 */
	public StopActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages);

	/**
	 * Adds a {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall
	 * ResourceCall} to this action.
	 * 
	 * @param numberOfCalls_stochasticExpression
	 * @param signature
	 * @param requiredRole
	 * @param variableUsages
	 * @return this stop action in the making
	 */
	public StopActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages);

	/**
	 * The body behaviour always ends with a stop action and a finishing call on
	 * this method. It turns the stop-action-in-the-making into a
	 * '<em><b>StopAction</b></em>' and adds it to the SEFF'S/behaviour's stepwise
	 * body behaviour
	 *
	 * @return the SEFF/behaviour
	 */
	public SeffCreator createBehaviourNow();

}
