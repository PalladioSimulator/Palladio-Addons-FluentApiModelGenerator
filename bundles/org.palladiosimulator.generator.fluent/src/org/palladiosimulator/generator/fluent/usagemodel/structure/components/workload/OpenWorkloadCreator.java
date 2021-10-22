package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.OpenWorkload OpenWorkload}.
 * It is used to create the '<em><b>Open Workload</b></em>' object step-by-step, i.e.
 * '<em><b>OpenWorkloadCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload
 * @see org.palladiosimulator.pcm.usagemodel.Workload
 */
public class OpenWorkloadCreator extends WorkloadCreator {

    /**
     * Instantiates a new open workload creator.
     *
     * <p>
     * OpenWorkload specifies usage intensity with an inter-arrival time (i.e., the time between two
     * user arrivals at the system) as a RandomVariable with an arbitrary probability distribution.
     * It models that an infinite stream of users arrives at a system. The users execute their
     * scenario, and then leave the system. The user population (i.e., the number of users
     * concurrently present in a system) is not fixed in an OpenWorkload.
     * </p>
     *
     * @param usgModelCreator
     *            the usage model creator
     * @param interArrivalTime
     *            the inter arrival time
     *
     * @see org.palladiosimulator.pcm.usagemodel.OpenWorkload
     */
    public OpenWorkloadCreator(final UsageModelCreator usgModelCreator, final String interArrivalTime) {
        super(usgModelCreator, interArrivalTime);
    }

    @Override
    public Workload build() {
        final OpenWorkload work = UsagemodelFactory.eINSTANCE.createOpenWorkload();

        if (this.time != null) {
            work.setInterArrivalTime_OpenWorkload(this.time);
        }
        return work;
    }

}
