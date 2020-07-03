package repositoryStructure.seff;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class ForkActionCreator extends AbstractAction {

	private SeffCreator seff;

	public ForkActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	public Action followedBy() {
		ForkAction action = SeffFactory.eINSTANCE.createForkAction();

		// TODO:
		EList<ForkedBehaviour> asynch = action.getAsynchronousForkedBehaviours_ForkAction();
		SynchronisationPoint synch = action.getSynchronisingBehaviours_ForkAction();
		SeffFactory.eINSTANCE.createSynchronisationPoint();
		synch.getOutputParameterUsage_SynchronisationPoint();
		EList<ForkedBehaviour> fb = synch.getSynchronousForkedBehaviours_SynchronisationPoint();
		ForkedBehaviour forkBeh = SeffFactory.eINSTANCE.createForkedBehaviour();

		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(demands);

		seff.setNext(action);
		return seff;
	}

	@Override
	public ForkActionCreator withResourceDemand(String specification_stochasticExpression,
			ProcessingResourceType processingResource) {
		return (ForkActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
	}

	@Override
	public ForkActionCreator withInfrastructureCall(String numberOfCalls_stochasticExpression,
			InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
			VariableUsage... variableUsages) {
		return (ForkActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
				requiredRole, variableUsages);
	}

	@Override
	public ForkActionCreator withResourceCall(String numberOfCalls_stochasticExpression, ResourceSignature signature,
			ResourceRequiredRole requiredRole, VariableUsage... variableUsages) {
		return (ForkActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
				variableUsages);
	}
}
