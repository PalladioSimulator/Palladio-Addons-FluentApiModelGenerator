package org.palladiosimulator.generator.fluent.repository.api.seff;

import org.palladiosimulator.generator.fluent.repository.structure.internals.Failure;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;

/**
 * TODO
 */
public interface RecoverySeff {

    /**
     * Defines the <i>unique</i> name of this recovery behaviour. Once created recovery behaviours
     * are referenced by this name. Only recovery behaviours with a unique name can be fetched from
     * the repository.
     *
     * @param name
     * @return this recovery behaviour in the making
     */
    RecoverySeff withName(String name);

    /**
     * Specifies the body behaviour of this RecoverActionBehaviour. Every body behaviour starts with
     * a
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.StartSeff#withStartAction()
     * start action} followed by an arbitrary amount of other
     * {@link org.palladiosimulator.pcm.seff.AbstractAction actions}. The body behaviour always ends
     * with a stop action and a finishing call on the method
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.StopSeff#createBehaviourNow()
     * createBehaviourNow()}.
     *
     * @return this recovery behaviour's starting body behaviour
     */
    StartSeff withSeffBehaviour();

    /**
     * Adds the failure type <code>failure</code> to this recovery behaviour's occurring failure
     * types.
     * <p>
     * A recovery behaviour is failure handling entity i.e. it can handle failures that occur in
     * previous alternatives. If one alternative fails, the next alternative is executed that can
     * handle the failure type. The failure type <code>failure</code> is (one of) the failure
     * type(s) this recovery behaviour can handle.
     * </p>
     * <p>
     * A failure can be one of '<em><b>HARDWARE_CPU</b></em>', '<em><b>HARDWARE_HDD</b></em>',
     * '<em><b>HARDWARE_DELAY</b></em>', '<em><b>NETWORK_LAN</b></em>', '<em><b>SOFTWARE</b></em>'.
     *
     * @param failure
     * @return this recovery behaviour in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    RecoverySeff withFailureType(Failure failure);

    /**
     * Adds the failure type <code>failure</code> to this recovery behaviour's occurring failure
     * types.
     * <p>
     * A recovery behaviour is failure handling entity i.e. it can handle failures that occur in
     * previous alternatives. If one alternative fails, the next alternative is executed that can
     * handle the failure type. The failure type <code>failure</code> is (one of) the failure
     * type(s) this recovery behaviour can handle.
     * </p>
     * <p>
     * An existing <code>failureType</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfFailureType(name)</code>.
     *
     * @param failureType
     * @return this recovery behaviour in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    RecoverySeff withFailureType(FailureType failureType);

    /**
     * Adds an alternative recovery behaviour to this recovery behaviour.
     * <p>
     * A recovery action behaviour provides a behaviour and alternatives of recovery behaviours.
     * They are resource demanding behaviours, thus any behaviour can be defined as an alternative.
     * The alternatives of a recovery block form a chain. They are failure handling entities, i.e.
     * they can handle failures that occur in previous alternatives. If one alternative fails, the
     * next alternative is executed that can handle the failure type. The alternatives are
     * referenced by name and have to be previously defined. Thus the chain of alternatives has to
     * be created inversely. The last alternative that has no alternatives itself is created first,
     * so that the second last can reference it as its alternative.
     * </p>
     * <p>
     * Previously defined RecoveryActionBehaviours can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfRecoveryActionBehaviour(name)</code>.
     * </p>
     *
     * @param recoveryBehaviour
     * @return this recovery behaviour in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfRecoveryActionBehaviour(String)
     */
    RecoverySeff withAlternativeRecoveryBehaviour(RecoveryActionBehaviour recoveryBehaviour);

    RecoveryActionBehaviour buildRecoveryBehaviour();
}
