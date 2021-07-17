package org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SetVariableAction;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.SetVariableAction SetVariableAction}.
 * It is used to create the '<em><b>SetVariableAction</b></em>' object
 * step-by-step, i.e. '<em><b>SetVariableActionCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.SetVariableAction
 */
public class SetVariableActionCreator extends GeneralAction {

    private final List<VariableUsage> localVariableUsages;

    protected SetVariableActionCreator(final SeffCreator seff) {
        this.seff = seff;
        localVariableUsages = new ArrayList<>();
    }

    @Override
    public SetVariableActionCreator withName(final String name) {
        return (SetVariableActionCreator) super.withName(name);
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of local variable
     * usages.
     *
     * @param variableUsage
     * @return this set variable action in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public SetVariableActionCreator withLocalVariableUsage(final VariableUsageCreator variableUsage) {
        IllegalArgumentException.throwIfNull(variableUsage, "variableUsage must not be null");
        localVariableUsages.add(variableUsage.build());
        return this;
    }

    @Override
    public SetVariableActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (SetVariableActionCreator) super.withResourceDemand(specificationStochasticExpression,
                processingResource);
    }

    @Override
    public SetVariableActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (SetVariableActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public SetVariableActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (SetVariableActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected SetVariableAction build() {
        final SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();

        if (name != null) {
            action.getEntityName();
        }

        action.getLocalVariableUsages_SetVariableAction().addAll(localVariableUsages);

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);
        return action;
    }
}
