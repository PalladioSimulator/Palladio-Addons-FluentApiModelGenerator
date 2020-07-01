package repositoryStructure;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;
import org.palladiosimulator.pcm.seff.util.SeffAdapterFactory;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.Follow;
import apiControlFlowInterfaces.LoopFollow;

public class SeffCreator extends Entity implements Follow, Action {

//	private Graph<Entity> graph;
	private StartAction start;
	private AbstractAction current;
	private SeffCreator loopSeff;

	@Override
	public Follow withStartAction() {
		// TODO:
		StartAction start = SeffFactory.eINSTANCE.createStartAction();
		this.start = start;
		this.current = start;

		start.getInfrastructureCall__Action();
		start.getPredecessor_AbstractAction();
		start.getResourceCall__Action();
		start.getResourceDemand_Action();
		start.getResourceDemandingBehaviour_AbstractAction();
		start.getSuccessor_AbstractAction();

		return this;
	}

	@Override
	public Follow internalAction() {
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		// TODO Auto-generated method stub

		action.getInternalFailureOccurrenceDescriptions__InternalAction();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	public Follow externalCallAction() {
		ExternalCallAction action = SeffFactory.eINSTANCE.createExternalCallAction();
		// TODO Auto-generated method stub
		action.getCalledService_ExternalService();
		action.getRetryCount();
		action.getRole_ExternalService();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow emitEventAction() {
		EmitEventAction action = SeffFactory.eINSTANCE.createEmitEventAction();
		// TODO Auto-generated method stub
		action.getEventType__EmitEventAction();
		action.getSourceRole__EmitEventAction();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow setVariableAction() {
		SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();
		// TODO Auto-generated method stub
		action.getLocalVariableUsages_SetVariableAction();
		
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow acquireAction() {
		AcquireAction action = SeffFactory.eINSTANCE.createAcquireAction();
		// TODO Auto-generated method stub
		action.getPassiveresource_AcquireAction();
		action.getTimeoutValue();
		
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow releaseAction() {
		ReleaseAction action = SeffFactory.eINSTANCE.createReleaseAction();
		// TODO Auto-generated method stub
		action.getPassiveResource_ReleaseAction();
		
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow loopAction() {
		LoopAction action = SeffFactory.eINSTANCE.createLoopAction();
		// TODO Auto-generated method stub
		action.getIterationCount_LoopAction();
		action.getBodyBehaviour_Loop();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow collectionIteratorAction() {
		CollectionIteratorAction action = SeffFactory.eINSTANCE.createCollectionIteratorAction();
		// TODO Auto-generated method stub
		action.getBodyBehaviour_Loop();
		action.getParameter_CollectionIteratorAction();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow branchAction() {
		BranchAction action = SeffFactory.eINSTANCE.createBranchAction();
		// TODO Auto-generated method stub
		action.getBranches_Branch();

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow forkAction() {
		ForkAction action = SeffFactory.eINSTANCE.createForkAction();
		// TODO Auto-generated method stub
		action.getAsynchronousForkedBehaviours_ForkAction();
		action.getSynchronisingBehaviours_ForkAction();
		
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Follow recoveryAction() {
		RecoveryAction action = SeffReliabilityFactory.eINSTANCE.createRecoveryAction();
		// TODO Auto-generated method stub
		action.getPrimaryBehaviour__RecoveryAction();
		action.getRecoveryActionBehaviours__RecoveryAction();
		
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	public SeffCreator stopAction() {
		StopAction action = SeffFactory.eINSTANCE.createStopAction();
		// TODO Auto-generated method stub

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}

	@Override
	public Action followedBy() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ServiceEffectSpecification build() {

		BasicComponent b = RepositoryFactory.eINSTANCE.createBasicComponent();
		EList<ServiceEffectSpecification> seff = b.getServiceEffectSpecifications__BasicComponent();

		ServiceEffectSpecification s;
//		Signature describedService = s.getDescribedService__SEFF();
//		String seffTypeID = s.getSeffTypeID();

		SeffFactory sf = SeffFactory.eINSTANCE;

		StartAction start = sf.createStartAction();

		return null;

	}
	
}
