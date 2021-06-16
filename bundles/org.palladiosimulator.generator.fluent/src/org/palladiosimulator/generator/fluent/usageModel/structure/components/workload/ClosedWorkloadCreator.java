package org.palladiosimulator.generator.fluent.usageModel.structure.components.workload;

import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;

public class ClosedWorkloadCreator extends WorkloadCreator {

    private int population;

    @Override
    public Workload build() {
        final ClosedWorkload work = (ClosedWorkload) UsagemodelFactory.eINSTANCE.createUsageScenario().getWorkload_UsageScenario();
  
        work.setPopulation(population);
        if (time != null) {
            work.setThinkTime_ClosedWorkload(time);
        }
        return work;
    }

    public ClosedWorkloadCreator withPopulation(int population) {
        this.population = population;
        return this;
    }

}
