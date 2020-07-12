package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.InternalAction
 * InternalAction}. It is used to create the '<em><b>InternalAction</b></em>'
 * object step-by-step, i.e. '<em><b>InternalActionCreator</b></em>' objects are
 * of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.InternalAction
 */
public class InternalActionCreator extends GeneralAction {

	private List<InternalFailureOccurrenceDescription> failures;

	public InternalActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.demands = new ArrayList<>();
		this.failures = new ArrayList<>();
		this.infrastructureCalls = new ArrayList<>();
		this.resourceCalls = new ArrayList<>();
	}

	@Override
	public InternalActionCreator withName(String name) {
		return (InternalActionCreator) super.withName(name);
	}

	public InternalActionCreator withInternalFailureOccurrenceDescription(Double failureProbability,
			SoftwareInducedFailureType failureType) {
		InternalFailureOccurrenceDescription failure = ReliabilityFactory.eINSTANCE
				.createInternalFailureOccurrenceDescription();
		if (failureProbability != null)
			failure.setFailureProbability(failureProbability);
		if (failureType != null)
			failure.setSoftwareInducedFailureType__InternalFailureOccurrenceDescription(failureType);
		this.failures.add(failure);
		return this;
	}

	@Override
	public InternalActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (InternalActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public InternalActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (InternalActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public InternalActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (InternalActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected InternalAction build() {
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		action.getInternalFailureOccurrenceDescriptions__InternalAction().addAll(failures);

		action.getResourceDemand_Action().addAll(demands);
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);

		return action;
	}

}
