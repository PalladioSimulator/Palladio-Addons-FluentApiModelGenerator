package repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.seff.Seff;
import repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.CollectionIteratorAction
 * CollectionIteratorAction}. It is used to create the
 * '<em><b>CollectionIteratorAction</b></em>' object step-by-step, i.e.
 * '<em><b>CollectionIteratorActionCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.CollectionIteratorAction
 */
public class CollectionIteratorActionCreator extends GeneralAction {

	private Parameter parameter;
	private Seff loopBody;

	protected CollectionIteratorActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	@Override
	public CollectionIteratorActionCreator withName(String name) {
		return (CollectionIteratorActionCreator) super.withName(name);
	}

	/**
	 * Defines the parameter that holds the collection which is iterated over, i.e.
	 * the parameter is of type CollectionDataType.
	 * 
	 * @param parameter
	 * @return this collection iterator action in the making
	 */
	public CollectionIteratorActionCreator withParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

	/**
	 * Defines the inner resource demanding behaviour that is executed for each
	 * element of a collection.
	 * 
	 * @param loopBody a nested resource demanding behaviour (seff)
	 * @return this collection iterator action in the making
	 */
	public CollectionIteratorActionCreator withLoopBody(Seff loopBody) {
		if (loopBody != null)
			this.loopBody = loopBody;
		return this;
	}

	@Override
	public CollectionIteratorActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (CollectionIteratorActionCreator) super.withResourceDemand(specification_stochasticExpression,
				processingResource);
	}

	@Override
	public CollectionIteratorActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsageCreator... variableUsages) {
		return (CollectionIteratorActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression,
				signature, requiredRole, variableUsages);
	}

	@Override
	public CollectionIteratorActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages) {
		return (CollectionIteratorActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected CollectionIteratorAction build() {
		CollectionIteratorAction action = SeffFactory.eINSTANCE.createCollectionIteratorAction();

		if (parameter != null)
			action.setParameter_CollectionIteratorAction(parameter);

		if (loopBody != null) {
			ResourceDemandingSEFF build = loopBody.buildRDSeff();
			if (build.getDescribedService__SEFF() == null && build.getSeffTypeID() == null
					&& build.getResourceDemandingInternalBehaviours().isEmpty())
				action.setBodyBehaviour_Loop(loopBody.buildBehaviour());
			else
				action.setBodyBehaviour_Loop(build);
			;
		}

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;

	}

}
