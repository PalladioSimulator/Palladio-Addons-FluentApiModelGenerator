package repositoryStructure.seff;

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

public abstract class AbstractAction {

	protected List<ParametricResourceDemand> demands = new ArrayList<>();
	protected List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
	protected List<ResourceCall> resourceCalls = new ArrayList<>();

	public AbstractAction withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();
		demand.setRequiredResource_ParametricResourceDemand(processingResource);
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(specification_stochasticExpression);
		demand.setSpecification_ParametericResourceDemand(rand);
		this.demands.add(demand);
		return this;
	}

	public AbstractAction withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();
		// TODO: machen die Variable usages hier sinn?
		// TODO: muss man die required Role setzen, wenn die signature gesetzt wird? ->
		// muss man prüfen, ob das zsm passt? -> siehe auch "withResourceCall"
		call.getInputVariableUsages__CallAction().addAll(Arrays.asList(variableUsages));
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(numberOfCalls_stochasticExpression);
		call.setNumberOfCalls__InfrastructureCall(rand);
		call.setRequiredRole__InfrastructureCall(requiredRole);
		call.setSignature__InfrastructureCall(signature);
		this.infrastructureCalls.add(call);
		return this;
	}

	public AbstractAction withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		ResourceCall call = SeffPerformanceFactory.eINSTANCE.createResourceCall();
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(numberOfCalls_stochasticExpression);
		call.setNumberOfCalls__ResourceCall(rand);
		call.setSignature__ResourceCall(signature);
		call.setResourceRequiredRole__ResourceCall(requiredRole);
		call.getInputVariableUsages__CallAction().addAll(Arrays.asList(variableUsages));

		this.resourceCalls.add(call);
		return this;
	}
}
