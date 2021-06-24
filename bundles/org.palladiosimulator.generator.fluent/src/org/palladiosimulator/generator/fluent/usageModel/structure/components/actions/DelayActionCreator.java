package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class DelayActionCreator extends ActionCreator {

    PCMRandomVariable time;

    @Override
    public Delay build() {
        Delay d = UsagemodelFactory.eINSTANCE.createDelay();
        if (time != null) {
            d.setTimeSpecification_Delay(this.time);
        }
        if (name != null) {
            d.setEntityName(name);
        }
        if (predecessor != null) {
            d.setPredecessor(predecessor);
        }
        if (successor != null) {
            d.setSuccessor(successor);
        }
        return d;
    }

    public DelayActionCreator addToDelayAction(String timeSpecification) {
        IllegalArgumentException.throwIfNull(timeSpecification, "The given Time Sppecification must not be null");
        time = CoreFactory.eINSTANCE.createPCMRandomVariable();
        time.setSpecification(timeSpecification);
        return this;
    }
}
