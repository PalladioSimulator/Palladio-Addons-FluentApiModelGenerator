package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import apiControlFlowInterfaces.seff.ActionSeff;
import apiControlFlowInterfaces.seff.InternalSeff;
import apiControlFlowInterfaces.seff.Seff;
import apiControlFlowInterfaces.seff.StartSeff;
import repositoryStructure.Entity;

public class SeffCreator extends Entity implements Seff, ActionSeff, StartSeff, InternalSeff {

	private AbstractAction current;
	private Signature signature;
	private String seffTypeID;
	private List<AbstractAction> steps;
	private List<InternalSeff> internalBehaviours;

	public SeffCreator() {
		this.steps = new ArrayList<>();
		this.internalBehaviours = new ArrayList<>();
	}

	@Override
	public StartActionCreator withStartAction() {
		return new StartActionCreator(this);
	}

	@Override
	public StopActionCreator stopAction() {
		return new StopActionCreator(this);
	}
	
	@Override
	public InternalActionCreator internalAction() {
		InternalActionCreator icc = new InternalActionCreator(this);
		return icc;
	}

	@Override
	public InternalCallActionCreator internalCallAction() {
		return new InternalCallActionCreator(this);
	}

	@Override
	public ExternalCallActionCreator externalCallAction() {
		ExternalCallActionCreator ecc = new ExternalCallActionCreator(this);
		return ecc;
	}

	@Override
	public EmitEventActionCreator emitEventAction() {
		EmitEventActionCreator eeac = new EmitEventActionCreator(this);
		return eeac;
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
	public SetVariableActionCreator setVariableAction() {
		return new SetVariableActionCreator(this);
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
	public SeffCreator withInternalBehaviour(InternalSeff internalBehaviour) {
		this.internalBehaviours.add(internalBehaviour);
		return this;
	}

	@Override
	public SeffCreator withName(String name) {
		this.name = name;
		return this;
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
	public SeffCreator withSeffBehaviour() {
		return this;
	}

	@Override
	public ServiceEffectSpecification build() {
		// -> ResourceDemandingSEFF (rdsf) = seff
		SeffFactory fact = SeffFactory.eINSTANCE;
		ResourceDemandingSEFF seff = fact.createResourceDemandingSEFF();

		if (this.signature != null)
			seff.setDescribedService__SEFF(this.signature);
		if (this.seffTypeID != null)
			seff.setSeffTypeID(this.seffTypeID);

		seff.getSteps_Behaviour().addAll(steps);

		seff.getResourceDemandingInternalBehaviours()
				.addAll(internalBehaviours.stream().map(b -> b.buildInternalBehaviour()).collect(Collectors.toList()));
		return seff;

	}

	public ResourceDemandingBehaviour buildBehaviour() {
		ResourceDemandingBehaviour behaviour = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		behaviour.getSteps_Behaviour().addAll(steps);
		return behaviour;
	}

	public ResourceDemandingInternalBehaviour buildInternalBehaviour() {
		ResourceDemandingInternalBehaviour internal = SeffFactory.eINSTANCE.createResourceDemandingInternalBehaviour();
		internal.getSteps_Behaviour().addAll(steps);
		return internal;
	}

	public ForkedBehaviour buildForkedBehaviour() {
		ForkedBehaviour fork = SeffFactory.eINSTANCE.createForkedBehaviour();
		fork.getSteps_Behaviour().addAll(steps);
		return fork;
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

	public enum BodyBehaviour {
		SEFF, RESOURCE_DEMANDING_BEHAVIOUR, INTERNAL_BEHAVIOUR, FORKED_BEHAVIOUR, RECOVERY_ACTION_BEHAVIOUR
	}

}
