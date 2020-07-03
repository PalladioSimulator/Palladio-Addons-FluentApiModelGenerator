package repositoryStructure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.Follow.Start;
import apiControlFlowInterfaces.LoopAction;
import apiControlFlowInterfaces.LoopFollow.LoopExitFollow.LoopStart;
import repositoryStructure.seff.AcquireActionCreator;
import repositoryStructure.seff.BranchActionCreator;
import repositoryStructure.seff.CollectionIteratorActionCreator;
import repositoryStructure.seff.EmitEventActionCreator;
import repositoryStructure.seff.ExternalCallCreator;
import repositoryStructure.seff.ForkActionCreator;
import repositoryStructure.seff.InternalCallCreator;
import repositoryStructure.seff.LoopActionCreator;
import repositoryStructure.seff.RecoveryActionCreator;
import repositoryStructure.seff.ReleaseActionCreator;
import repositoryStructure.seff.SetVariableActionCreator;
import repositoryStructure.seff.StartActionCreator;
import repositoryStructure.seff.StopActionCreator;

public class SeffCreator extends Entity implements Start, Action, LoopStart, LoopAction {

	public LoopActionCreator loopParent; //TODO: private

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
	public StartActionCreator withStartAction() {
		return new StartActionCreator(this);
	}
	
	@Override
	public StartActionCreator startAction() {
		return new StartActionCreator(this);
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

	@Override
	public LoopActionCreator loopAction() {
		return new LoopActionCreator(this);
	}

	@Override
	public CollectionIteratorActionCreator collectionIteratorAction() {
		return new CollectionIteratorActionCreator(this);
	}

	@Override
	public BranchActionCreator branchAction() {
		return new BranchActionCreator(this);
	}

	@Override
	public ForkActionCreator forkAction() {
		return new ForkActionCreator(this);
	}

	@Override
	public RecoveryActionCreator recoveryAction() {
		return new RecoveryActionCreator(this);
	}

	public StopActionCreator stopAction() {
		return new StopActionCreator(this);
	}

	public void setNext(AbstractAction action) {
		current.setSuccessor_AbstractAction(action);
		action.setPredecessor_AbstractAction(current);
		current = action;
		steps.add(action);
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

		return seff;

	}

}
