package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;

import repositoryStructure.components.VariableUsageCreator;

public abstract class GeneralAction extends SeffAction {

	protected List<ParametricResourceDemand> demands = new ArrayList<>();
	protected List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
	protected List<ResourceCall> resourceCalls = new ArrayList<>();

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
	 * TODO: woher kommt die Processing resource type -> see mit fetch?
	 * 
	 * @param specification_stochasticExpression
	 * @param processingResource
	 * @return this action in the making
	 */
	public GeneralAction withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {

		ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();

		if (processingResource != null)
			demand.setRequiredResource_ParametricResourceDemand(processingResource);

		if (specification_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(specification_stochasticExpression);
			demand.setSpecification_ParametericResourceDemand(rand);
		}
		this.demands.add(demand);
		return this;
	}

	/**
	 * Adds an
	 * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall
	 * InfrastructureCall} to this action.
	 * 
	 * TODO:
	 * 
	 * @param numberOfCalls_stochasticExpression
	 * @param signature
	 * @param requiredRole
	 * @param variableUsages
	 * @return this action in the making
	 */
	public GeneralAction withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {

		InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();

		if (numberOfCalls_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(numberOfCalls_stochasticExpression);
			call.setNumberOfCalls__InfrastructureCall(rand);
		}
		if (requiredRole != null)
			call.setRequiredRole__InfrastructureCall(requiredRole);
		if (signature != null)
			call.setSignature__InfrastructureCall(signature);
		if (variableUsages != null && variableUsages.length != 0)
			Arrays.asList(variableUsages).stream().map(v -> v.build())
					.forEach(v -> call.getInputVariableUsages__CallAction().add(v));

		this.infrastructureCalls.add(call);
		return this;
	}

	/**
	 * Adds a {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall
	 * ResourceCall} to this action. TODO
	 * 
	 * @param numberOfCalls_stochasticExpression
	 * @param signature
	 * @param requiredRole
	 * @param variableUsages
	 * @return this action in the making
	 */
	public GeneralAction withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {

		ResourceCall call = SeffPerformanceFactory.eINSTANCE.createResourceCall();

		if (numberOfCalls_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(numberOfCalls_stochasticExpression);
			call.setNumberOfCalls__ResourceCall(rand);
		}
		if (signature != null)
			call.setSignature__ResourceCall(signature);
		if (requiredRole != null)
			call.setResourceRequiredRole__ResourceCall(requiredRole);
		if (variableUsages != null && variableUsages.length != 0)
			Arrays.asList(variableUsages).stream().map(v -> v.build())
					.forEach(v -> call.getInputVariableUsages__CallAction().add(v));

		this.resourceCalls.add(call);
		return this;
	}

}
