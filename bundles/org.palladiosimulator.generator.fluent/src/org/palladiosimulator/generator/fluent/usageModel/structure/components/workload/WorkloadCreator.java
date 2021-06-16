package org.palladiosimulator.generator.fluent.usageModel.structure.components.workload;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

public abstract class WorkloadCreator extends UsageModelEntity{
    
    //child - 1fach
    protected PCMRandomVariable time;
    
    public WorkloadCreator addToWorkload(String pcmVariable) {
        IllegalArgumentException.throwIfNull(pcmVariable, "The given PCMVariable must not be null");
        time = CoreFactory.eINSTANCE.createPCMRandomVariable();
        time.setSpecification(pcmVariable);
        return this;  
    }

}
