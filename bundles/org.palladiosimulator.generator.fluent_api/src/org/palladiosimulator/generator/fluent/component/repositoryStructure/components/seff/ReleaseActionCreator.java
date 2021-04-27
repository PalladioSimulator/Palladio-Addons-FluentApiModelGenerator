package org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.ReleaseAction
 * ReleaseAction}. It is used to create the '<em><b>ReleaseAction</b></em>'
 * object step-by-step, i.e. '<em><b>ReleaseActionCreator</b></em>' objects are
 * of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.ReleaseAction
 */
public class ReleaseActionCreator extends GeneralAction {

    private PassiveResource passiveResource;

    protected ReleaseActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public ReleaseActionCreator withName(final String name) {
        return (ReleaseActionCreator) super.withName(name);
    }

    /**
     * Specifies the passive resource of this release action.
     * <p>
     * An existing <code>passiveResource</code> can be fetched from the repository
     * using the org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfPassiveResource(name)</code>.
     * </p>
     *
     * @param passiveResource
     * @return this release action in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#fetchOfPassiveResource(String)
     */
    public ReleaseActionCreator withPassiveResource(final PassiveResource passiveResource) {
        Objects.requireNonNull(passiveResource, "passiveResource must not be null");
        this.passiveResource = passiveResource;
        return this;
    }

    @Override
    public ReleaseActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (ReleaseActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public ReleaseActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (ReleaseActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public ReleaseActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (ReleaseActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected ReleaseAction build() {
        final ReleaseAction action = SeffFactory.eINSTANCE.createReleaseAction();
        if (passiveResource != null) {
            action.setPassiveResource_ReleaseAction(passiveResource);
        }

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);

        return action;
    }

}
