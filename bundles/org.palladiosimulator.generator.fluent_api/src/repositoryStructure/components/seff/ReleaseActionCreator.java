package repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.internals.ProcessingResource;
import repositoryStructure.internals.ResourceSignature;

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

	protected ReleaseActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public ReleaseActionCreator withName(String name) {
		return (ReleaseActionCreator) super.withName(name);
	}

	/**
	 * Specifies the passive resource of this release action.
	 * <p>
	 * An existing <code>passiveResource</code> can be fetched from the repository
	 * using the factory, i.e. <code>create.fetchOfPassiveResource(name)</code>.
	 * </p>
	 * 
	 * @param passiveResource
	 * @return this release action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfPassiveResource(String)
	 */
	public ReleaseActionCreator withPassiveResource(PassiveResource passiveResource) {
		Objects.requireNonNull(passiveResource, "passiveResource must not be null");
		this.passiveResource = passiveResource;
		return this;
	}

	@Override
	public ReleaseActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResource processingResource) {
		return (ReleaseActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public ReleaseActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (ReleaseActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public ReleaseActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (ReleaseActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected ReleaseAction build() {
		ReleaseAction action = SeffFactory.eINSTANCE.createReleaseAction();
		if (passiveResource != null)
			action.setPassiveResource_ReleaseAction(passiveResource);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;
	}

}
