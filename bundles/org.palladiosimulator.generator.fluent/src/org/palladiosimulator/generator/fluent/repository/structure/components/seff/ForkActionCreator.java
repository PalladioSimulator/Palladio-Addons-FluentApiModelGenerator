package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.api.seff.InternalSeff;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.ForkAction
 * BranchAction}. It is used to create the '<em><b>ForkAction</b></em>' object
 * step-by-step, i.e. '<em><b>ForkActionCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.ForkAction
 */
public class ForkActionCreator extends GeneralAction {

    private final List<ForkedBehaviour> asynchronousForkedBehaviours;
    private final List<ForkedBehaviour> synchronousForkedBehaviours;
    private final List<VariableUsage> variableUsages;

    protected ForkActionCreator(final SeffCreator seff) {
        this.seff = seff;
        asynchronousForkedBehaviours = new ArrayList<>();
        synchronousForkedBehaviours = new ArrayList<>();
        variableUsages = new ArrayList<>();
    }

    @Override
    public ForkActionCreator withName(final String name) {
        return (ForkActionCreator) super.withName(name);
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of output parameter
     * usages at the synchronization point.
     *
     * @param variableUsage
     * @return this fork action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public ForkActionCreator withOutputParameterUsageAtSynchronisationPoint(final VariableUsageCreator variableUsage) {
        IllegalArgumentException.throwIfNull(variableUsage, "variableUsage must not be null");
        variableUsages.add(variableUsage.build());
        return this;
    }

    /**
     * Adds the <code>forkedBehaviour</code> to this action's list of synchronous
     * forked behaviours at the synchronization point.
     *
     * @param forkedBehaviour
     * @return this fork action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInternalBehaviour()
     */
    public ForkActionCreator withSynchronousForkedBehaviourAtSynchronisationPoint(final InternalSeff forkedBehaviour) {
        IllegalArgumentException.throwIfNull(forkedBehaviour, "forkedBehaviour must not be null");
        final ForkedBehaviour fork = forkedBehaviour.buildForkedBehaviour();
        synchronousForkedBehaviours.add(fork);
        return this;
    }

    /**
     * Adds the <code>forkedBehaviour</code> to this action's list of asynchronous
     * forked behaviours.
     *
     * @param forkedBehaviour
     * @return this fork action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInternalBehaviour()
     */
    public ForkActionCreator withAsynchronousForkedBehaviour(final InternalSeff forkedBehaviour) {
        IllegalArgumentException.throwIfNull(forkedBehaviour, "forkedBehaviour must not be null");
        final ForkedBehaviour fork = forkedBehaviour.buildForkedBehaviour();
        asynchronousForkedBehaviours.add(fork);
        return this;
    }

    @Override
    public ForkActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (ForkActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public ForkActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (ForkActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public ForkActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (ForkActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected ForkAction build() {
        final ForkAction action = SeffFactory.eINSTANCE.createForkAction();
        action.getAsynchronousForkedBehaviours_ForkAction().addAll(asynchronousForkedBehaviours);

        final SynchronisationPoint synch = SeffFactory.eINSTANCE.createSynchronisationPoint();
        synch.getOutputParameterUsage_SynchronisationPoint().addAll(variableUsages);
        synch.getSynchronousForkedBehaviours_SynchronisationPoint().addAll(synchronousForkedBehaviours);
        action.setSynchronisingBehaviours_ForkAction(synch);

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);

        return action;
    }
}
