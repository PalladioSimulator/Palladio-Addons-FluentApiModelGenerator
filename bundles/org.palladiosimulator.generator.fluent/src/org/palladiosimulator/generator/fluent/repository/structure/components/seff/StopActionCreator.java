package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StopAction StopAction}. It is used
 * to create the '<em><b>StopAction</b></em>' object step-by-step, i.e.
 * '<em><b>StopActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StopAction
 */
public class StopActionCreator extends GeneralAction implements StopSeff {

    private final List<ParametricResourceDemand> demands = new ArrayList<>();
    private final List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
    private final List<ResourceCall> resourceCalls = new ArrayList<>();

    protected StopActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public StopActionCreator withName(final String name) {
        return (StopActionCreator) super.withName(name);
    }

    @Override
    public SeffCreator createBehaviourNow() {
        final AbstractAction action = this.build();
        this.seff.setNext(action);
        return this.seff;
    }

    @Override
    public StopActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (StopActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public StopActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StopActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public StopActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StopActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected StopAction build() {
        final StopAction action = SeffFactory.eINSTANCE.createStopAction();
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);
        return action;
    }
}
