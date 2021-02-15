package component.repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.seff.AcquireAction AcquireAction}. It
 * is used to create the '<em><b>AcquireAction</b></em>' object step-by-step, i.e.
 * '<em><b>AcquireActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.AcquireAction
 *
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
     * An existing <code>passiveResource</code> can be fetched from the repository using the
     * factory, i.e. <code>create.fetchOfPassiveResource(name)</code>.
     * </p>
     *
     * @param passiveResource
     * @return this acquire action in the making
     * @see factory.FluentRepositoryFactory#fetchOfPassiveResource(String)
     */
    public AcquireActionCreator withPassiveResource(final PassiveResource passiveResource) {
        Objects.requireNonNull(passiveResource, "passiveResource must not be null");
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
        Objects.requireNonNull(isTimeout, "isTimeout must not be null");
        this.isTimeout = isTimeout;
        return this;
    }

    @Override
    public AcquireActionCreator withResourceDemand(final String specification_stochasticExpression,
            final ProcessingResource processingResource) {
        return (AcquireActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
    }

    @Override
    public AcquireActionCreator withInfrastructureCall(final String numberOfCalls_stochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (AcquireActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public AcquireActionCreator withResourceCall(final String numberOfCalls_stochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (AcquireActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected AcquireAction build() {
        final AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
        if (this.passiveResource != null) {
            action.setPassiveresource_AcquireAction(this.passiveResource);
        }
        if (this.timeoutValue != null) {
            action.setTimeoutValue(this.timeoutValue);
        }
        if (this.isTimeout != null) {
            action.setTimeout(this.isTimeout);
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
