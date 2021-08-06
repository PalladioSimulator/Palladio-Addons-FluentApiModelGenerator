package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

public abstract class WorkloadCreator extends UsageModelEntity {

    protected PCMRandomVariable time;

    public WorkloadCreator(UsageModelCreator usgModelCreator, String time) {
        usageModelCreator = usgModelCreator;
        addToWorkload(time);
    }

    private WorkloadCreator addToWorkload(String time) {
        IllegalArgumentException.throwIfNull(time, "The given Time must not be null");
        this.time = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.time.setSpecification(time);
        return this;
    }

}
