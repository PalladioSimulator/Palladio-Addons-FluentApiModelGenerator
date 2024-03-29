package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.RecoveryAction RecoveryAction}. It
 * is used to create the '<em><b>RecoveryAction</b></em>' object step-by-step, i.e.
 * '<em><b>RecoveryActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.RecoveryAction
 */
public class RecoveryActionCreator extends GeneralAction {

    private RecoveryActionBehaviour primary;
    private final List<RecoveryActionBehaviour> otherBehaviours;

    protected RecoveryActionCreator(final SeffCreator seff, final RepositoryCreator repo) {
        this.repository = repo;
        this.seff = seff;
        this.otherBehaviours = new ArrayList<>();
    }

    @Override
    public RecoveryActionCreator withName(final String name) {
        return (RecoveryActionCreator) super.withName(name);
    }

    /**
     * Specifies the primary recovery behaviour of this recovery action.
     *
     * @param recoveryActionBehaviour
     * @return this recovery action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newRecoveryBehaviour()
     */
    public RecoveryActionCreator withPrimaryBehaviour(final RecoverySeff recoveryActionBehaviour) {
        IllegalArgumentException.throwIfNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
        final RecoveryActionBehaviour build = recoveryActionBehaviour.buildRecoveryBehaviour();
        this.primary = build;
        this.repository.addRecoveryActionBehaviour(build);
        return this;
    }

    /**
     * Adds the <code>recoveryActionBehaviour</code> to this action's list of alternate recovery
     * behaviours.
     *
     * @param recoveryActionBehaviour
     * @return this recovery action in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newRecoveryBehaviour()
     */
    public RecoveryActionCreator withAlternativeBehaviour(final RecoverySeff recoveryActionBehaviour) {
        IllegalArgumentException.throwIfNull(recoveryActionBehaviour, "recoveryActionBehaviour must not be null");
        final RecoveryActionBehaviour buildRecoveryBehaviour = recoveryActionBehaviour.buildRecoveryBehaviour();
        this.otherBehaviours.add(buildRecoveryBehaviour);
        this.repository.addRecoveryActionBehaviour(buildRecoveryBehaviour);
        return this;
    }

    @Override
    public RecoveryActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (RecoveryActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public RecoveryActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (RecoveryActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public RecoveryActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (RecoveryActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected RecoveryAction build() {
        final RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();
        if (this.name != null) {
            action.setEntityName(this.name);
        }
        if (this.primary != null) {
            action.setPrimaryBehaviour__RecoveryAction(this.primary);
        }

        action.getRecoveryActionBehaviours__RecoveryAction()
            .addAll(this.otherBehaviours);

        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);

        return action;
    }

}
