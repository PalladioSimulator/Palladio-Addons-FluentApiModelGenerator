package org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * This class provides the general infrastructure of an workload of usage scenario in usage model.
 * All workloads inherit from this class.
 *
 * @author Eva-Maria Neumann
 */
public abstract class WorkloadCreator extends UsageModelEntity {

    protected PCMRandomVariable time;

    public WorkloadCreator(final UsageModelCreator usgModelCreator, final String time) {
        this.usageModelCreator = usgModelCreator;
        addToWorkload(time);
    }

    private WorkloadCreator addToWorkload(final String time) {
        IllegalArgumentException.throwIfNull(time, "The given Time must not be null");
        this.time = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.time.setSpecification(time);
        return this;
    }

}
