package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class StopActionCreator extends ActionCreator {

    @Override
    public AbstractUserAction build() {
        Stop stop = UsagemodelFactory.eINSTANCE.createStop();

        if (name != null) {
            stop.setEntityName(name);
        }
        if (predecessor != null) {
            stop.setPredecessor(predecessor);
        }
        if (successor != null) {
            stop.setSuccessor(successor);
        }
        return stop;
    }

    @Override
    public StopActionCreator withPredecessor(ActionCreator action) {
        return (StopActionCreator) super.withPredecessor(action);
    }

    @Override
    public StopActionCreator withSuccessor(ActionCreator action) {
        return (StopActionCreator) super.withSuccessor(action);
    }

    @Override
    public StopActionCreator withName(final String name) {
        return (StopActionCreator) super.withName(name);
    }

}
