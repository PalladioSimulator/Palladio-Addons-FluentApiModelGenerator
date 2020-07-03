package repositoryStructure.seff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class InternalCallCreator {

	private SeffCreator seff;

	private List<ParametricResourceDemand> demands;
	private List<InternalFailureOccurrenceDescription> failures;
	private List<InfrastructureCall> infrastructureCalls;
	private List<ResourceCall> resourceCalls;

	public InternalCallCreator(SeffCreator seff) {
		this.seff = seff;
		this.demands = new ArrayList<>();
		this.failures = new ArrayList<>();
		this.infrastructureCalls = new ArrayList<>();
		this.resourceCalls = new ArrayList<>();
	}

	public Action followedBy() {
		// TODO: resource Demanding behaviour?
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		action.getResourceDemand_Action().addAll(demands);
		action.getInternalFailureOccurrenceDescriptions__InternalAction().addAll(failures);
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		seff.setNext(action);
		return seff;
	}

	public InternalCallCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();
		demand.setRequiredResource_ParametricResourceDemand(processingResource);
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(specification_stochasticExpression);
		demand.setSpecification_ParametericResourceDemand(rand);
		this.demands.add(demand);
		return this;
	}

	public InternalCallCreator withInternalFailureOccurrenceDescription(Double failureProbability,
			SoftwareInducedFailureType failureType) {
		InternalFailureOccurrenceDescription failure = ReliabilityFactory.eINSTANCE
				.createInternalFailureOccurrenceDescription();
		failure.setFailureProbability(failureProbability);
		failure.setSoftwareInducedFailureType__InternalFailureOccurrenceDescription(failureType);
		this.failures.add(failure);
		return this;
	}

	public InternalCallCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();
		// TODO: machen die Variable usages hier sinn?
		// TODO: muss man die required Role setzen, wenn die signature gesetzt wird? ->
		// muss man prüfen, ob das zsm passt?
		call.getInputVariableUsages__CallAction().addAll(Arrays.asList(variableUsages));
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(numberOfCalls_stochasticExpression);
		call.setNumberOfCalls__InfrastructureCall(rand);
		call.setRequiredRole__InfrastructureCall(requiredRole);
		call.setSignature__InfrastructureCall(signature);
		this.infrastructureCalls.add(call);
		return this;
	}

	public InternalCallCreator withResourceCall() {
		ResourceCall call = SeffPerformanceFactory.eINSTANCE.createResourceCall();
		// TODO: might be deprecated? oder im ResourceDemand enthalten?
		call.getResourceRequiredRole__ResourceCall();
		call.getSignature__ResourceCall();
		this.resourceCalls.add(call);
		return this;
	}
}
