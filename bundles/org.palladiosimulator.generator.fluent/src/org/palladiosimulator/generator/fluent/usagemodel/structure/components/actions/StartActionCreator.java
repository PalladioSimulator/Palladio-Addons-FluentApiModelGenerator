package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.Start Start}. It is used to
 * create the '<em><b>Start</b></em>' object step-by-step, i.e. '<em><b>StartActionCreator</b></em>'
 * objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.Start
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class StartActionCreator extends ActionCreator {

    @Override
    public AbstractUserAction build() {
        final Start start = UsagemodelFactory.eINSTANCE.createStart();

        if (this.name != null) {
            start.setEntityName(this.name);
        }
        if (this.successor != null) {
            start.setSuccessor(this.successor);
        }
        return start;
    }

    @Override
    public StartActionCreator withSuccessor(final ActionCreator action) {
        return (StartActionCreator) super.withSuccessor(action);
    }

    @Override
    public StartActionCreator withName(final String name) {
        return (StartActionCreator) super.withName(name);
    }
}
