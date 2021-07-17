package org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.seff.AcquireAction
 * AcquireAction}. It is used to create the '<em><b>AcquireAction</b></em>'
 * object step-by-step, i.e. '<em><b>AcquireActionCreator</b></em>' objects are
 * of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.AcquireAction
 */
public class AcquireActionCreator extends GeneralAction {

    private PassiveResource passiveResource;
    private Double timeoutValue;
    private Boolean isTimeout;

    protected AcquireActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public AcquireActionCreator withName(final String name) {
        return (AcquireActionCreator) super.withName(name);
    }

    /**
     * Specifies the passive resource of this acquire action.
     * <p>
     * An existing <code>passiveResource</code> can be fetched from the repository
     * using the factory, i.e. <code>create.fetchOfPassiveResource(name)</code>.
     * </p>
     *
     * @param passiveResource
     * @return this acquire action in the making
     * @see factory.FluentRepositoryFactory#fetchOfPassiveResource(String)
     */
    public AcquireActionCreator withPassiveResource(final PassiveResource passiveResource) {
        IllegalArgumentException.throwIfNull(passiveResource, "passiveResource must not be null");
        this.passiveResource = passiveResource;
        return this;
    }

    /**
     * Specifies the timeout value of this acquire action.
     *
     * @param timeoutValue
     * @return this acquire action in the making
     */
    public AcquireActionCreator withTimeoutValue(final double timeoutValue) {
        this.timeoutValue = timeoutValue;
        return this;
    }

    /**
     * Specifies if this acquire action is timeout.
     *
     * @param isTimeout
     * @return this acquire action in the making
     */
    public AcquireActionCreator isTimeout(final Boolean isTimeout) {
        IllegalArgumentException.throwIfNull(isTimeout, "isTimeout must not be null");
        this.isTimeout = isTimeout;
        return this;
    }

    @Override
    public AcquireActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (AcquireActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public AcquireActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (AcquireActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public AcquireActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (AcquireActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected AcquireAction build() {
        final AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
        if (passiveResource != null) {
            action.setPassiveresource_AcquireAction(passiveResource);
        }
        if (timeoutValue != null) {
            action.setTimeoutValue(timeoutValue);
        }
        if (isTimeout != null) {
            action.setTimeout(isTimeout);
        }

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);

        return action;
    }

}
