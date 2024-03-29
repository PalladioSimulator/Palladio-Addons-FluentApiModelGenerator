package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.api.seff.ActionSeff;
import org.palladiosimulator.generator.fluent.repository.api.seff.InternalSeff;
import org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff;
import org.palladiosimulator.generator.fluent.repository.api.seff.Seff;
import org.palladiosimulator.generator.fluent.repository.api.seff.StartSeff;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Failure;
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

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.ServiceEffectSpecification
 * ServiceEffectSpecification} / {@link org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
 * ResourceDemandingSEFF} / {@link org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour
 * ResourceDemandingBehaviour} /
 * {@link org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
 * ResourceDemandingInternalBehaviour} / {@link org.palladiosimulator.pcm.seff.ForkedBehaviour
 * ForkedBehaviour} / {@link org.palladiosimulator.pcm.seff.RecoveryActionBehaviour
 * RecoveryActionBehaviour}. It is used to create the behaviour objects step-by-step, i.e.
 * '<em><b>SeffCreator</b></em>' objects are of intermediate state.
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
    private final List<AbstractAction> steps;
    private final List<InternalSeff> internalBehaviours;
    private final List<FailureType> failures;
    private final List<RecoveryActionBehaviour> alternatives;

    public SeffCreator(final RepositoryCreator repo) {
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
        return new InternalActionCreator(this);
    }

    @Override
    public InternalCallActionCreator internalCallAction() {
        return new InternalCallActionCreator(this);
    }

    @Override
    public ExternalCallActionCreator externalCallAction() {
        return new ExternalCallActionCreator(this);
    }

    @Override
    public EmitEventActionCreator emitEventAction() {
        return new EmitEventActionCreator(this);
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
        return new RecoveryActionCreator(this, this.repository);
    }

    // ------------ seff methods ------------
    @Override
    public SeffCreator withName(final String name) {
        return (SeffCreator) super.withName(name);
    }

    @Override
    public SeffCreator onSignature(final Signature signature) {
        IllegalArgumentException.throwIfNull(signature, "signature must not be null");
        this.signature = signature;
        return this;
    }

    @Override
    public SeffCreator withSeffTypeID(final String seffTypeID) {
        IllegalArgumentException.throwIfNull(seffTypeID, "seffTypeID must not be null");
        this.seffTypeID = seffTypeID;
        return this;
    }

    @Override
    public SeffCreator withInternalBehaviour(final InternalSeff internalBehaviour) {
        IllegalArgumentException.throwIfNull(internalBehaviour, "internalBehaviour must not be null");
        this.internalBehaviours.add(internalBehaviour);
        return this;
    }

    @Override
    public SeffCreator withSeffBehaviour() {
        return this;
    }

    @Override
    public SeffCreator withFailureType(final Failure failure) {
        IllegalArgumentException.throwIfNull(failure, "failure must not be null");
        final FailureType f = this.repository.getFailureType(failure);
        return this.withFailureType(f);
    }

    @Override
    public SeffCreator withFailureType(final FailureType failureType) {
        IllegalArgumentException.throwIfNull(failureType, "failureType must not be null");
        this.failures.add(failureType);
        return this;
    }

    @Override
    public SeffCreator withAlternativeRecoveryBehaviour(final RecoveryActionBehaviour recoveryBehaviour) {
        IllegalArgumentException.throwIfNull(recoveryBehaviour, "recoveryBehaviour must not be null");
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
        final ResourceDemandingSEFF seff = SeffFactory.eINSTANCE.createResourceDemandingSEFF();

        if (this.signature != null) {
            seff.setDescribedService__SEFF(this.signature);
        }
        if (this.seffTypeID != null) {
            seff.setSeffTypeID(this.seffTypeID);
        }

        seff.getSteps_Behaviour()
            .addAll(this.steps);

        seff.getResourceDemandingInternalBehaviours()
            .addAll(this.internalBehaviours.stream()
                .map(InternalSeff::buildInternalBehaviour)
                .collect(Collectors.toList()));
        return seff;

    }

    @Override
    public ResourceDemandingBehaviour buildBehaviour() {
        final ResourceDemandingBehaviour behaviour = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
        behaviour.getSteps_Behaviour()
            .addAll(this.steps);
        return behaviour;
    }

    @Override
    public ResourceDemandingInternalBehaviour buildInternalBehaviour() {
        final ResourceDemandingInternalBehaviour internal = SeffFactory.eINSTANCE
            .createResourceDemandingInternalBehaviour();
        internal.getSteps_Behaviour()
            .addAll(this.steps);
        return internal;
    }

    @Override
    public ForkedBehaviour buildForkedBehaviour() {
        final ForkedBehaviour fork = SeffFactory.eINSTANCE.createForkedBehaviour();
        fork.getSteps_Behaviour()
            .addAll(this.steps);
        return fork;
    }

    @Override
    public RecoveryActionBehaviour buildRecoveryBehaviour() {
        final RecoveryActionBehaviour recovActionBehaviour = SeffReliabilityFactory.eINSTANCE
            .createRecoveryActionBehaviour();

        if (this.name != null) {
            recovActionBehaviour.setEntityName(this.name);
        }

        recovActionBehaviour.getSteps_Behaviour()
            .addAll(this.steps);
        recovActionBehaviour.getFailureHandlingAlternatives__RecoveryActionBehaviour()
            .addAll(this.alternatives);
        recovActionBehaviour.getFailureTypes_FailureHandlingEntity()
            .addAll(this.failures);

        return recovActionBehaviour;
    }

    // ------------ getter + setter ------------
    protected void setNext(final AbstractAction action) {
        if (this.current != null) {
            this.current.setSuccessor_AbstractAction(action);
            action.setPredecessor_AbstractAction(this.current);
        }
        this.current = action;
        this.steps.add(action);
    }

}
