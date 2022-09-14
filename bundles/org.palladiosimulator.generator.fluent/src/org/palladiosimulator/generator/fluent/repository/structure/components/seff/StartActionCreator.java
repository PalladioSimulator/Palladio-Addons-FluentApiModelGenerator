package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StartAction StartAction}. It is
 * used to create the '<em><b>StartAction</b></em>' object step-by-step, i.e.
 * '<em><b>StartActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StartAction
 */
public class StartActionCreator extends GeneralAction {

    protected StartActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public StartActionCreator withName(final String name) {
        return (StartActionCreator) super.withName(name);
    }

    @Override
    public StartActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (StartActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public StartActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StartActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public StartActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StartActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected StartAction build() {
        final StartAction action = SeffFactory.eINSTANCE.createStartAction();
        if (this.name != null) {
            action.setEntityName(this.name);
        }
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);
        return action;
    }

}
