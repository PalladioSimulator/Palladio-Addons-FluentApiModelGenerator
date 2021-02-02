package componentModel.repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.seff.AcquireAction
 * AcquireAction}. It is used to create the '<em><b>AcquireAction</b></em>'
 * object step-by-step, i.e. '<em><b>AcquireActionCreator</b></em>' objects are
 * of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.AcquireAction
 *
 */
public class AcquireActionCreator extends GeneralAction {

	private PassiveResource passiveResource;
	private Double timeoutValue;
	private Boolean isTimeout;

	protected AcquireActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public AcquireActionCreator withName(String name) {
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
	public AcquireActionCreator withPassiveResource(PassiveResource passiveResource) {
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
	public AcquireActionCreator withTimeoutValue(double timeoutValue) {
		this.timeoutValue = timeoutValue;
		return this;
	}

	/**
	 * Specifies if this acquire action is timeout.
	 * 
	 * @param isTimeout
	 * @return this acquire action in the making
	 */
	public AcquireActionCreator isTimeout(Boolean isTimeout) {
		Objects.requireNonNull(isTimeout, "isTimeout must not be null");
		this.isTimeout = isTimeout;
		return this;
	}

	@Override
	public AcquireActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (AcquireActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public AcquireActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (AcquireActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public AcquireActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (AcquireActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected AcquireAction build() {
		AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
		if (passiveResource != null)
			action.setPassiveresource_AcquireAction(passiveResource);
		if (timeoutValue != null)
			action.setTimeoutValue(timeoutValue);
		if (isTimeout != null)
			action.setTimeout(isTimeout);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}

}
