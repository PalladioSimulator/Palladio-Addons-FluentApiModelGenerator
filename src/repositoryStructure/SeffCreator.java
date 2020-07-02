package repositoryStructure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypeFactory;
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
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;
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
	private Signature signature;
	private String seffTypeID;
	private List<AbstractAction> steps;
	
	public SeffCreator() {
		 this.steps = new ArrayList<>();
	}

	@Override
	public Follow withStartAction() {
		StartAction start = SeffFactory.eINSTANCE.createStartAction();
		
//		name, id
		this.start = start;
		this.current = start;
		steps.add(start);
		
		return this;
	}

	@Override
	public Follow internalAction() {
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		// TODO Auto-generated method stub

		// name, id, resource demand, failure occurence description, infrastructure calls compartment
		EList<ParametricResourceDemand> demand = action.getResourceDemand_Action();
		action.getInternalFailureOccurrenceDescriptions__InternalAction();
		action.getInfrastructureCall__Action();
		

		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;

		return this;
	}
	
	public Follow withResourceDemand(String specification_stochasticExpression, ProcessingResourceType processingResource) {
		ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();
		demand.setRequiredResource_ParametricResourceDemand(processingResource);
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(specification_stochasticExpression);
		demand.setSpecification_ParametericResourceDemand(rand);
		//TODO: wo fügt man den hinzu?
		return this;
	}
	
	public Follow withInternalFailureOccurrenceDescription(Double failureProbability, SoftwareInducedFailureType failureType) {
		InternalFailureOccurrenceDescription failure = ReliabilityFactory.eINSTANCE.createInternalFailureOccurrenceDescription();
		failure.setFailureProbability(failureProbability);
		failure.setSoftwareInducedFailureType__InternalFailureOccurrenceDescription(failureType);
		//TODO: wo fügt man den hinzu?
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
		// -> ResourceDemandingSEFF (rdsf) = seff
		ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
		
		if (this.signature != null)
			seff.setDescribedService__SEFF(this.signature);
		if(this.seffTypeID != null)
			seff.setSeffTypeID(this.seffTypeID);
		seff.setAbstractBranchTransition_ResourceDemandingBehaviour(null);
		seff.setAbstractLoopAction_ResourceDemandingBehaviour(null);
		EList<ResourceDemandingInternalBehaviour> behaviours = seff.getResourceDemandingInternalBehaviours();
		EList<AbstractAction> steps = seff.getSteps_Behaviour();
		
		

//		Signature describedService = s.getDescribedService__SEFF();
//		String seffTypeID = s.getSeffTypeID();

		SeffFactory sf = SeffFactory.eINSTANCE;

		StartAction start = sf.createStartAction();

		return seff;

	}

}
