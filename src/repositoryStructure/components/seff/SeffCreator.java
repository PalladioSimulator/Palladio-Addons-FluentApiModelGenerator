package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.Action.Start;
import repositoryStructure.Entity;
import apiControlFlowInterfaces.Action.Seff;

public class SeffCreator extends Entity implements Start, Action, Seff {

	private AbstractAction current;
	private Signature signature;
	private String seffTypeID;
	private List<AbstractAction> steps;

	public SeffCreator() {
		this.steps = new ArrayList<>();
	}

	@Override
	public StartActionCreator withStartAction() {
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

	@Override
	public StopActionCreator stopAction() {
		return new StopActionCreator(this);
	}

	@Override
	public SeffCreator onSignature(Signature signature) {
		this.signature = signature;
		return this;
	}

	@Override
	public SeffCreator withSeffTypeID(String seffTypeID) {
		this.seffTypeID = seffTypeID;
		return this;
	}

	@Override
	public Start withSeffBehaviour() {
		return this;
	}

	@Override
	public ServiceEffectSpecification build() {
		// -> ResourceDemandingSEFF (rdsf) = seff
		ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();

		// TODO: methoden für signature erstellen
		if (this.signature != null)
			seff.setDescribedService__SEFF(this.signature);
		if (this.seffTypeID != null)
			seff.setSeffTypeID(this.seffTypeID);

		seff.getSteps_Behaviour().addAll(steps);

		return seff;

	}

	protected void setNext(AbstractAction action) {

		if (current != null) {
			current.setSuccessor_AbstractAction(action);
			action.setPredecessor_AbstractAction(current);
		}
		current = action;
		steps.add(action);
	}

	protected List<AbstractAction> getSteps() {
		return this.steps;
	}

}
