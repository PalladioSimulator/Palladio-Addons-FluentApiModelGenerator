package repositoryStructure.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.LoopAction.InternalLoop;
import repositoryStructure.SeffCreator;

public class InternalCallCreator extends AbstractAction implements InternalLoop{

	private SeffCreator seff;

	private List<InternalFailureOccurrenceDescription> failures;

	public InternalCallCreator(SeffCreator seff) {
		this.seff = seff;
		this.demands = new ArrayList<>();
		this.failures = new ArrayList<>();
		this.infrastructureCalls = new ArrayList<>();
		this.resourceCalls = new ArrayList<>();
	}

	public SeffCreator followedBy() {
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		action.getResourceDemand_Action().addAll(demands);
		action.getInternalFailureOccurrenceDescriptions__InternalAction().addAll(failures);
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		seff.setNext(action);
		return seff;
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

	@Override
	public InternalCallCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (InternalCallCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public InternalCallCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (InternalCallCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public InternalCallCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (InternalCallCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}

}
