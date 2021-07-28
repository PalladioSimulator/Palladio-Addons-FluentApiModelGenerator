package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;

public class ClosedWorkloadCreator extends WorkloadCreator {

    private int population;

    public ClosedWorkloadCreator(UsageModelCreator usgModelCreator, String thinkTime) {
        super(usgModelCreator, thinkTime);
        this.population = 0; // default value
    }
    
    @Deprecated
    public ClosedWorkloadCreator(UsageModelCreator usgModelCreator) {
        super(usgModelCreator);
        this.population = 0; // default value
    }

    public ClosedWorkloadCreator withPopulation(int population) {
        this.population = population;
        return this;
    }

    @Override
    public Workload build() {
        final ClosedWorkload work = UsagemodelFactory.eINSTANCE.createClosedWorkload();

        work.setPopulation(population);

        if (time != null) {
            work.setThinkTime_ClosedWorkload(time);
        }
        return work;
    }

   @Override
   @Deprecated
    public ClosedWorkloadCreator addToWorkload(String time) {
        return (ClosedWorkloadCreator) super.addToWorkload(time);
    }

}
