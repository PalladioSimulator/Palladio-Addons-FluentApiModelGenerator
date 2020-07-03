package repositoryStructure.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class CollectionIteratorActionCreator extends AbstractAction {

	private SeffCreator seff;

	private Parameter parameter;

	public CollectionIteratorActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		CollectionIteratorAction action = SeffFactory.eINSTANCE.createCollectionIteratorAction();

		action.setParameter_CollectionIteratorAction(parameter);
		// TODO: what shall we do with the internal body?
		ResourceDemandingBehaviour body = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		action.setBodyBehaviour_Loop(body);

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		seff.setNext(action);
		return seff;
	}

	public CollectionIteratorActionCreator withParameter(Parameter parameter) {
		this.parameter = parameter;
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

}
