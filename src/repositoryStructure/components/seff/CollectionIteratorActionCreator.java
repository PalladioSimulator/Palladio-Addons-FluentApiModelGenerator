package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class CollectionIteratorActionCreator extends GeneralAction {

	private Parameter parameter;
	private List<AbstractAction> steps;

	public CollectionIteratorActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.steps = new ArrayList<>();
	}

	@Override
	public CollectionIteratorActionCreator withName(String name) {
		return (CollectionIteratorActionCreator) super.withName(name);
	}

	public CollectionIteratorActionCreator withParameter(Parameter parameter) {
		this.parameter = parameter;
		return this;
	}

	public CollectionIteratorActionCreator withLoopBody(SeffCreator loopBody) {
		if (loopBody != null)
			this.steps.addAll(loopBody.getSteps());
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
			VariableUsage... variableUsages) {
		return (CollectionIteratorActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression,
				signature, requiredRole, variableUsages);
	}

	@Override
	public CollectionIteratorActionCreator withResourceCall(String numberOfCalls_stochasticExpression,
			ResourceSignature signature, ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (CollectionIteratorActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	protected CollectionIteratorAction build() {
		CollectionIteratorAction action = SeffFactory.eINSTANCE.createCollectionIteratorAction();

		if (parameter != null)
			action.setParameter_CollectionIteratorAction(parameter);

		ResourceDemandingBehaviour body = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		body.getSteps_Behaviour().addAll(this.steps);
		action.setBodyBehaviour_Loop(body);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		return action;

	}

}
