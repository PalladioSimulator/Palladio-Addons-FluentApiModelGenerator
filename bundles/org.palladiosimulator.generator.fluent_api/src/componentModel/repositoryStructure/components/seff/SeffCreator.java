package componentModel.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;

import componentModel.apiControlFlowInterfaces.seff.ActionSeff;
import componentModel.apiControlFlowInterfaces.seff.InternalSeff;
import componentModel.apiControlFlowInterfaces.seff.RecoverySeff;
import componentModel.apiControlFlowInterfaces.seff.Seff;
import componentModel.apiControlFlowInterfaces.seff.StartSeff;
import componentModel.repositoryStructure.RepositoryEntity;
import componentModel.repositoryStructure.RepositoryCreator;
import componentModel.repositoryStructure.internals.Failure;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification
 * ServiceEffectSpecification} /
 * {@link org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
 * ResourceDemandingSEFF} /
 * {@link org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour
 * ResourceDemandingBehaviour} /
 * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
 * ResourceDemandingInternalBehaviour} /
 * {@link org.palladiosimulator.pcm.seff.ForkedBehaviour ForkedBehaviour} /
 * {@link org.palladiosimulator.pcm.seff.RecoveryActionBehaviour
 * RecoveryActionBehaviour}. It is used to create the behaviour objects
 * step-by-step, i.e. '<em><b>SeffCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.ServiceEffectSpecification
 * @see org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
 * @see org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour
 * @see org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
 * @see org.palladiosimulator.pcm.seff.ForkedBehaviour
 * @see org.palladiosimulator.pcm.seff.RecoveryActionBehaviour
 */
public class SeffCreator extends RepositoryEntity implements Seff, ActionSeff, StartSeff, InternalSeff, RecoverySeff {

	private AbstractAction current;
	private Signature signature;
	private String seffTypeID;
	private List<AbstractAction> steps;
	private List<InternalSeff> internalBehaviours;
	private List<FailureType> failures;
	private List<RecoveryActionBehaviour> alternatives;

	public SeffCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.steps = new ArrayList<>();
		this.internalBehaviours = new ArrayList<>();
		this.failures = new ArrayList<>();
		this.alternatives = new ArrayList<>();
	}

	// ------------ action methods ------------
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
		return new RecoveryActionCreator(this, repository);
	}

	// ------------ seff methods ------------
	@Override
	public SeffCreator withName(String name) {
		return (SeffCreator) super.withName(name);
	}

	@Override
	public SeffCreator onSignature(Signature signature) {
		Objects.requireNonNull(signature, "signature must not be null");
		this.signature = signature;
		return this;
	}

	@Override
	public SeffCreator withSeffTypeID(String seffTypeID) {
		Objects.requireNonNull(seffTypeID, "seffTypeID must not be null");
		this.seffTypeID = seffTypeID;
		return this;
	}

	@Override
	public SeffCreator withInternalBehaviour(InternalSeff internalBehaviour) {
		Objects.requireNonNull(internalBehaviour, "internalBehaviour must not be null");
		this.internalBehaviours.add(internalBehaviour);
		return this;
	}

	@Override
	public SeffCreator withSeffBehaviour() {
		return this;
	}

	@Override
	public SeffCreator withFailureType(Failure failure) {
		Objects.requireNonNull(failure, "failure must not be null");
		FailureType f = this.repository.getFailureType(failure);
		return withFailureType(f);
	}

	@Override
	public SeffCreator withFailureType(FailureType failureType) {
		Objects.requireNonNull(failureType, "failureType must not be null");
		this.failures.add(failureType);
		return this;
	}

	@Override
	public SeffCreator withAlternativeRecoveryBehaviour(RecoveryActionBehaviour recoveryBehaviour) {
		Objects.requireNonNull(recoveryBehaviour, "recoveryBehaviour must not be null");
		this.alternatives.add(recoveryBehaviour);
		return this;
	}

	// ------------ build methods ------------
	@Override
	public ServiceEffectSpecification build() {
		// -> ResourceDemandingSEFF (rdsf) = seff
		return this.buildRDSeff();
	}

	@Override
	public ResourceDemandingSEFF buildRDSeff() {
		ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();

		if (this.signature != null)
			seff.setDescribedService__SEFF(this.signature);
		if (this.seffTypeID != null)
			seff.setSeffTypeID(this.seffTypeID);

		seff.getSteps_Behaviour().addAll(steps);

		seff.getResourceDemandingInternalBehaviours()
				.addAll(internalBehaviours.stream().map(b -> b.buildInternalBehaviour()).collect(Collectors.toList()));
		return seff;

	}

	@Override
	public ResourceDemandingBehaviour buildBehaviour() {
		ResourceDemandingBehaviour behaviour = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
		behaviour.getSteps_Behaviour().addAll(steps);
		return behaviour;
	}

	@Override
	public ResourceDemandingInternalBehaviour buildInternalBehaviour() {
		ResourceDemandingInternalBehaviour internal = SeffFactory.eINSTANCE.createResourceDemandingInternalBehaviour();
		internal.getSteps_Behaviour().addAll(steps);
		return internal;
	}

	@Override
	public ForkedBehaviour buildForkedBehaviour() {
		ForkedBehaviour fork = SeffFactory.eINSTANCE.createForkedBehaviour();
		fork.getSteps_Behaviour().addAll(steps);
		return fork;
	}

	@Override
	public RecoveryActionBehaviour buildRecoveryBehaviour() {
		RecoveryActionBehaviour recovActionBehaviour = SeffReliabilityFactory.eINSTANCE.createRecoveryActionBehaviour();

		if (this.name != null)
			recovActionBehaviour.setEntityName(this.name);

		recovActionBehaviour.getSteps_Behaviour().addAll(steps);
		recovActionBehaviour.getFailureHandlingAlternatives__RecoveryActionBehaviour().addAll(alternatives);
		recovActionBehaviour.getFailureTypes_FailureHandlingEntity().addAll(failures);

		return recovActionBehaviour;
	}

	// ------------ getter + setter ------------
	protected void setNext(AbstractAction action) {
		if (current != null) {
			current.setSuccessor_AbstractAction(action);
			action.setPredecessor_AbstractAction(current);
		}
		current = action;
		steps.add(action);
	}

}
