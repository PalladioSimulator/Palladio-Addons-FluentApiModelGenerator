package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.components.VariableUsageCreator;

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
	private List<VariableUsage> inputVariableUsages;
	private List<VariableUsage> returnVariableUsages;
	private List<FailureType> failures;

	protected ExternalCallActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
		this.returnVariableUsages = new ArrayList<>();
		this.failures = new ArrayList<>();
	}

	@Override
	public ExternalCallActionCreator withName(String name) {
		return (ExternalCallActionCreator) super.withName(name);
	}

	/**
	 * Specifies the <code>retryCount</code> of this external call action.
	 * 
	 * @param retryCount
	 * @return this external call action in the making
	 */
	public ExternalCallActionCreator withRetryCount(int retryCount) {
		this.retryCount = retryCount;
		return this;
	}

	/**
	 * Specifies the <code>signature</code> of the service that is called on by this
	 * action.
	 * <p>
	 * An existing <code>signature</code> can be fetched from the repository using
	 * the factory, i.e. <code>create.fetchOfOperationSignature(name)</code>.
	 * </p>
	 * 
	 * @param signature
	 * @return this external call action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfOperationSignature(String)
	 */
	public ExternalCallActionCreator withCalledService(OperationSignature signature) {
		this.signature = signature;
		return this;
	}

	/**
	 * Specifies the <code>requiredRole</code> corresponding to the service that is
	 * called on by this action.
	 * <p>
	 * An existing <code>requiredRole</code> can be fetched from the repository
	 * using the factory, i.e.
	 * <code>create.fetchOfOperationRequiredRole(name)</code>.
	 * </p>
	 * 
	 * @param requiredRole
	 * @return this external call action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfOperationRequiredRole(String)
	 */
	public ExternalCallActionCreator withRequiredRole(OperationRequiredRole requiredRole) {
		this.requiredRole = requiredRole;
		return this;
	}

	/**
	 * Adds the <code>variableUsage</code> to this action's list of input variable
	 * usages.
	 * 
	 * @param variableUsage
	 * @return this external call action in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public ExternalCallActionCreator withInputVariableUsage(VariableUsageCreator variableUsage) {
		if (variableUsage != null)
			this.inputVariableUsages.add(variableUsage.build());
		return this;
	}

	/**
	 * Adds the <code>variableUsage</code> to this action's list of return variable
	 * usages.
	 * 
	 * @param variableUsage
	 * @return this external call action in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public ExternalCallActionCreator withReturnVariableUsage(VariableUsageCreator variableUsage) {
		if (variableUsage != null)
			this.returnVariableUsages.add(variableUsage.build());
		return this;
	}

	/**
	 * Adds the failure type <code>failure</code> to this action's list of failure
	 * types.
	 * <p>
	 * An existing <code>failure</code> can be fetched from the repository using the
	 * factory, e.g. <code>create.fetchOfFailureType(name)</code>.
	 * </p>
	 * 
	 * @param failure
	 * @return this external call action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfFailureType(repositoryStructure.datatypes.Failure)
	 * @see factory.FluentRepositoryFactory#fetchOfFailureType(String)
	 */
	public ExternalCallActionCreator withFailureType(FailureType failure) {
		if (failure != null)
			this.failures.add(failure);
		return this;
	}

	@Override
	protected ExternalCallAction build() {
		ExternalCallAction action = SeffFactory.eINSTANCE.createExternalCallAction();
		if (retryCount != null)
			action.setRetryCount(retryCount);
		if (signature != null)
			action.setCalledService_ExternalService(signature);
		if (requiredRole != null)
			action.setRole_ExternalService(requiredRole);
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		action.getReturnVariableUsage__CallReturnAction().addAll(returnVariableUsages);
		action.getFailureTypes_FailureHandlingEntity().addAll(failures);

		return action;
	}
}
