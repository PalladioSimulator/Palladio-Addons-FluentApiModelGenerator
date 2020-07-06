package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;

public abstract class GeneralAction extends SeffAction{

	protected List<ParametricResourceDemand> demands = new ArrayList<>();
	protected List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
	protected List<ResourceCall> resourceCalls = new ArrayList<>();

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

	public GeneralAction withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {

		InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();
		// TODO: machen die Variable usages hier sinn?
		// TODO: muss man die required Role setzen, wenn die signature gesetzt wird? ->
		// muss man prüfen, ob das zsm passt? -> siehe auch "withResourceCall"

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
			call.getInputVariableUsages__CallAction().addAll(Arrays.asList(variableUsages));

		this.infrastructureCalls.add(call);
		return this;
	}

	public GeneralAction withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {

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
			call.getInputVariableUsages__CallAction().addAll(Arrays.asList(variableUsages));

		this.resourceCalls.add(call);
		return this;
	}

}
