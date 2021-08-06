package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class StartActionCreator extends ActionCreator {

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

    @Override
    public StartActionCreator withSuccessor(ActionCreator action) {
        return (StartActionCreator) super.withSuccessor(action);
    }

    @Override
    public StartActionCreator withName(final String name) {
        return (StartActionCreator) super.withName(name);
    }
}
