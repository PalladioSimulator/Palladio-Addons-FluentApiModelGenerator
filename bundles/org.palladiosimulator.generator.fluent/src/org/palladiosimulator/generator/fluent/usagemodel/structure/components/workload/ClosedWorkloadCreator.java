package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.ClosedWorkload
 * ClosedWorkload}. It is used to create the '<em><b>Closed Workload</b></em>' object step-by-step,
 * i.e. '<em><b>ClosedWorkloadCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
 * @see org.palladiosimulator.pcm.usagemodel.Workload
 */
public class ClosedWorkloadCreator extends WorkloadCreator {

    private int population;

    /**
     * Instantiates a new closed workload creator.
     *
     * <p>
     * ClosedWorkload specifies directly the (constant) user population and a think time. It
     * modelst hat a fixed number of users execute their scenario, then wait (or think) for the
     * specified amount of think time as a RandomVariable, and then reenter the system executing
     * their scenario again.Performance analysts use closed workloads to model scenarios, where the
     * number of users is known(e.g., a fixed number of users in a company).
     * </p>
     * 
     * @param usgModelCreator
     *            the usage model creator
     * @param thinkTime
     *            the think time
     *            
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     */
    public ClosedWorkloadCreator(final UsageModelCreator usgModelCreator, final String thinkTime) {
        super(usgModelCreator, thinkTime);
        this.population = 0; // default value
    }

    /**
     * Adds a population to the Closed Workload.
     *
     * @param population
     *            the population
     * @return the current closed workload in the making
     * @see org.palladiosimulator.pcm.usagemodel.ClosedWorkload
     */
    public ClosedWorkloadCreator withPopulation(final int population) {
        this.population = population;
        return this;
    }

    @Override
    public Workload build() {
        final ClosedWorkload work = UsagemodelFactory.eINSTANCE.createClosedWorkload();

        work.setPopulation(this.population);

        if (this.time != null) {
            work.setThinkTime_ClosedWorkload(this.time);
        }
        return work;
    }

}
