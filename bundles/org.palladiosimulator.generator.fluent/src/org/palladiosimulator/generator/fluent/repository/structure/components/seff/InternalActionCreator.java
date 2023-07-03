package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.InternalAction InternalAction}. It
 * is used to create the '<em><b>InternalAction</b></em>' object step-by-step, i.e.
 * '<em><b>InternalActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.InternalAction
 */
public class InternalActionCreator extends GeneralAction {

    private final List<InternalFailureOccurrenceDescription> failures;

    protected InternalActionCreator(final SeffCreator seff) {
        this.seff = seff;
        this.demands = new ArrayList<>();
        this.failures = new ArrayList<>();
        this.infrastructureCalls = new ArrayList<>();
        this.resourceCalls = new ArrayList<>();
    }

    @Override
    public InternalActionCreator withName(final String name) {
        return (InternalActionCreator) super.withName(name);
    }

    /**
     * Creates an internal failure occurrence description with the failure probability
     * <code>failureProbability</code> of the software induced failure type <code>failureType</code>
     * and adds it to this action's list of internal failure occurrence descriptions.
     *
     * @param failureProbability
     * @param failureType
     * @return this internal action in the making
     * @see org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription
     */
    public InternalActionCreator withInternalFailureOccurrenceDescription(final double failureProbability,
            final SoftwareInducedFailureType failureType) {
        IllegalArgumentException.throwIfNull(failureType, "failureType must not be null");
        final InternalFailureOccurrenceDescription failure = ReliabilityFactory.eINSTANCE
            .createInternalFailureOccurrenceDescription();
        failure.setFailureProbability(failureProbability);
        failure.setSoftwareInducedFailureType__InternalFailureOccurrenceDescription(failureType);
        this.failures.add(failure);
        return this;
    }

    @Override
    public InternalActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (InternalActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public InternalActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public InternalActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected InternalAction build() {
        final InternalAction action = SeffFactory.eINSTANCE.createInternalAction();

        if (this.name != null) {
            action.setEntityName(this.name);
        }

        action.getInternalFailureOccurrenceDescriptions__InternalAction()
            .addAll(this.failures);
        action.getResourceDemand_Action()
            .addAll(this.demands);
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);

        return action;
    }

}
