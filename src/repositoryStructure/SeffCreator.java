package repositoryStructure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.Follow;
import apiControlFlowInterfaces.Follow.Start;
import repositoryStructure.seff.AcquireActionCreator;
import repositoryStructure.seff.EmitEventActionCreator;
import repositoryStructure.seff.ExternalCallCreator;
import repositoryStructure.seff.InternalCallCreator;
import repositoryStructure.seff.LoopActionCreator;
import repositoryStructure.seff.ReleaseActionCreator;
import repositoryStructure.seff.SetVariableActionCreator;

public class SeffCreator extends Entity implements Start, Follow, Action {

	private LoopActionCreator loopParent;

	private AbstractAction current;
	private Signature signature;
	private String seffTypeID;
	private List<AbstractAction> steps;

	public SeffCreator() {
		this.steps = new ArrayList<>();
	}

	public SeffCreator(LoopActionCreator loopAction) {
		this.loopParent = loopAction;
		this.steps = new ArrayList<>();
	}

	@Override
	public Follow withStartAction() {
		StartAction start = SeffFactory.eINSTANCE.createStartAction();
//		TODO: überall mit name, id?!
		this.current = start;
		steps.add(start);
		return this;
	}

	@Override
	public InternalCallCreator internalAction() {
		InternalCallCreator icc = new InternalCallCreator(this);
		return icc;
	}

	@Override
	public ExternalCallCreator externalCallAction() {
		ExternalCallCreator ecc = new ExternalCallCreator(this);
		return ecc;
	}

	@Override
	public EmitEventActionCreator emitEventAction() {
		EmitEventActionCreator eeac = new EmitEventActionCreator(this);
		return eeac;
	}

	@Override
	public SetVariableActionCreator setVariableAction() {
		return new SetVariableActionCreator(this);
	}

	@Override
	public AcquireActionCreator acquireAction() {
		return new AcquireActionCreator(this);
	}

	@Override
	public ReleaseActionCreator releaseAction() {
		return new ReleaseActionCreator(this);
	}

	public void setNext(AbstractAction action) {
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;
		steps.add(action);
	}

	@Override
	public LoopActionCreator loopAction() {
		return new LoopActionCreator(this);
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
		if (this.seffTypeID != null)
			seff.setSeffTypeID(this.seffTypeID);
		seff.getSteps_Behaviour().addAll(steps);

		// TODO:
		seff.setAbstractBranchTransition_ResourceDemandingBehaviour(null);
		seff.setAbstractLoopAction_ResourceDemandingBehaviour(null);
		seff.getResourceDemandingInternalBehaviours();

		return seff;

	}

}
