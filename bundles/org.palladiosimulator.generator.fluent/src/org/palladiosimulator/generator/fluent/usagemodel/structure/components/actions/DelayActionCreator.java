package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.Delay Delay}. It is used to
 * create the '<em><b>Delay</b></em>' object step-by-step, i.e. '<em><b>DelayActionCreator</b></em>'
 * objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.Delay
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class DelayActionCreator extends ActionCreator {

    private PCMRandomVariable time;

    /**
     * Instantiates a new delay action creator.
     *
     * <p>
     * A Delay represents a timing delay as a RandomVariable between two user actions. The Delay is
     * included into the usage model to express that users do not call system services in direct
     * successions,but usually need some time to determine their next action. User delays are for
     * example useful,if a performance analyst wants to determine the execution time for a complete
     * scenario behaviour (instead of a single service), which needs to include user delays.
     * </p>
     *
     * @param timeSpecification
     *            the time specification
     *
     * @see org.palladiosimulator.pcm.usagemodel.Delay
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     */
    public DelayActionCreator(final String timeSpecification) {
        this.addToDelayAction(timeSpecification);
    }

    private DelayActionCreator addToDelayAction(final String timeSpecification) {
        IllegalArgumentException.throwIfNull(timeSpecification, "The given Time Sppecification must not be null");
        this.time = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.time.setSpecification(timeSpecification);
        return this;
    }

    @Override
    public Delay build() {
        final Delay d = UsagemodelFactory.eINSTANCE.createDelay();
        if (this.time != null) {
            d.setTimeSpecification_Delay(this.time);
        }
        if (this.name != null) {
            d.setEntityName(this.name);
        }
        if (this.successor != null) {
            d.setSuccessor(this.successor);
        }
        return d;
    }

    @Override
    public DelayActionCreator withSuccessor(final ActionCreator action) {
        return (DelayActionCreator) super.withSuccessor(action);
    }

    @Override
    public DelayActionCreator withName(final String name) {
        return (DelayActionCreator) super.withName(name);
    }
}
