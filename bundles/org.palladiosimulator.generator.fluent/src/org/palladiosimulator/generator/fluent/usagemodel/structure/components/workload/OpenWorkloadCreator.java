package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

public class OpenWorkloadCreator extends WorkloadCreator {

    public OpenWorkloadCreator(UsageModelCreator usgModelCreator, String interArrivalTime) {
        super(usgModelCreator, interArrivalTime);
    }


    @Override
    public Workload build() {
        OpenWorkload work = UsagemodelFactory.eINSTANCE.createOpenWorkload();

        if (time != null) {
            work.setInterArrivalTime_OpenWorkload(time);
        }
        return work;
    }

}
