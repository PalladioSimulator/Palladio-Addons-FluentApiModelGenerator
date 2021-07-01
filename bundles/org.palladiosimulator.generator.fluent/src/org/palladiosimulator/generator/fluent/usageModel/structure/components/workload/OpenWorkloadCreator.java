package org.palladiosimulator.generator.fluent.usageModel.structure.components.workload;

import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

public class OpenWorkloadCreator extends WorkloadCreator {

    @Override
    public Workload build() {
        OpenWorkload work = (OpenWorkload) UsagemodelFactory.eINSTANCE.createUsageScenario()
                .getWorkload_UsageScenario();

        if (time != null) {
            work.setInterArrivalTime_OpenWorkload(time);
        }
        return work;
    }

    @Override
    public OpenWorkloadCreator addToWorkload(String time) {
        return (OpenWorkloadCreator) super.addToWorkload(time);
    }

}
