package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.seff.ExternalCallAction ExternalCallAction}.
 * It is used to create the '<em><b>ExternalCallAction</b></em>' object
 * step-by-step, i.e. '<em><b>ExternalCallActionCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.ExternalCallAction
 */
public class ExternalCallActionCreator extends SeffAction {

    private Integer retryCount;
    private OperationSignature signature;
    private OperationRequiredRole requiredRole;
    private final List<VariableUsage> inputVariableUsages;
    private final List<VariableUsage> returnVariableUsages;
    private final List<FailureType> failures;

    protected ExternalCallActionCreator(final SeffCreator seff) {
        this.seff = seff;
        this.inputVariableUsages = new ArrayList<>();
        this.returnVariableUsages = new ArrayList<>();
        this.failures = new ArrayList<>();
    }

    @Override
    public ExternalCallActionCreator withName(final String name) {
        return (ExternalCallActionCreator) super.withName(name);
    }

    /**
     * Specifies the <code>retryCount</code> of this external call action.
     *
     * @param retryCount
     * @return this external call action in the making
     */
    public ExternalCallActionCreator withRetryCount(final int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * Specifies the <code>signature</code> of the service that is called on by this
     * action.
     * <p>
     * An existing <code>signature</code> can be fetched from the repository using
     * the component.factory, i.e.
     * <code>create.fetchOfOperationSignature(name)</code>.
     * </p>
     *
     * @param signature
     * @return this external call action in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfOperationSignature(String)
     */
    public ExternalCallActionCreator withCalledService(final OperationSignature signature) {
        Objects.requireNonNull(signature, "signature must not be null");
        this.signature = signature;
        return this;
    }

    /**
     * Specifies the <code>requiredRole</code> corresponding to the service that is
     * called on by this action.
     * <p>
     * An existing <code>requiredRole</code> can be fetched from the repository
     * using the component.factory, i.e.
     * <code>create.fetchOfOperationRequiredRole(name)</code>.
     * </p>
     *
     * @param requiredRole
     * @return this external call action in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfOperationRequiredRole(String)
     */
    public ExternalCallActionCreator withRequiredRole(final OperationRequiredRole requiredRole) {
        Objects.requireNonNull(requiredRole, "requiredRole must not be null");
        this.requiredRole = requiredRole;
        return this;
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of input variable
     * usages.
     *
     * @param variableUsage
     * @return this external call action in the making
     * @see component.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public ExternalCallActionCreator withInputVariableUsage(final VariableUsageCreator variableUsage) {
        Objects.requireNonNull(variableUsage, "variableUsage must not be null");
        this.inputVariableUsages.add(variableUsage.build());
        return this;
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of return variable
     * usages.
     *
     * @param variableUsage
     * @return this external call action in the making
     * @see component.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public ExternalCallActionCreator withReturnVariableUsage(final VariableUsageCreator variableUsage) {
        Objects.requireNonNull(variableUsage, "variableUsage must not be null");
        this.returnVariableUsages.add(variableUsage.build());
        return this;
    }

    /**
     * Adds the failure type <code>failure</code> to this action's list of failure
     * types.
     * <p>
     * An existing <code>failure</code> can be fetched from the repository using the
     * component.factory, e.g. <code>create.fetchOfFailureType(name)</code>.
     * </p>
     *
     * @param failure
     * @return this external call action in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfFailureType(component.repositoryStructure.datatypes.Failure)
     * @see component.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    public ExternalCallActionCreator withFailureType(final FailureType failure) {
        Objects.requireNonNull(failure, "failure must not be null");
        this.failures.add(failure);
        return this;
    }

    @Override
    protected ExternalCallAction build() {
        final ExternalCallAction action = SeffFactory.eINSTANCE.createExternalCallAction();
        if (this.retryCount != null) {
            action.setRetryCount(this.retryCount);
        }
        if (this.signature != null) {
            action.setCalledService_ExternalService(this.signature);
        }
        if (this.requiredRole != null) {
            action.setRole_ExternalService(this.requiredRole);
        }
        action.getInputVariableUsages__CallAction().addAll(this.inputVariableUsages);
        action.getReturnVariableUsage__CallReturnAction().addAll(this.returnVariableUsages);
        action.getFailureTypes_FailureHandlingEntity().addAll(this.failures);

        return action;
    }
}
