package org.palladiosimulator.generator.fluent.usageModel.structure.components.workload;

import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;

public class ClosedWorkloadCreator extends WorkloadCreator {

    private int population;

    public ClosedWorkloadCreator() {
        this.population = 0; // default value
    }

    public ClosedWorkloadCreator withPopulation(int population) {
        this.population = population;
        return this;
    }

    @Override
    public Workload build() {
        final ClosedWorkload work = (ClosedWorkload) UsagemodelFactory.eINSTANCE.createUsageScenario()
                .getWorkload_UsageScenario();

        work.setPopulation(population);

        if (time != null) {
            work.setThinkTime_ClosedWorkload(time);
        }
        return work;
    }

    @Override
    public ClosedWorkloadCreator addToWorkload(String time) {
        return (ClosedWorkloadCreator) super.addToWorkload(time);
    }

}
