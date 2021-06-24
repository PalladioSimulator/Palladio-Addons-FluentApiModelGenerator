package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class StartActionCreator extends ActionCreator{

    @Override
    public AbstractUserAction build() {
        Start start = UsagemodelFactory.eINSTANCE.createStart();
        if (name != null) {
            start.setEntityName(name);
        }
        if (predecessor != null) {
            start.setPredecessor(predecessor);
        }
        if (successor != null) {
            start.setSuccessor(successor);
        }
        return start;
    }

}
