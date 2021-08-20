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
     * TODO
     * Instantiates a new open workload creator.
     *
     * @param usgModelCreator the usg model creator
     * @param interArrivalTime the inter arrival time
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
