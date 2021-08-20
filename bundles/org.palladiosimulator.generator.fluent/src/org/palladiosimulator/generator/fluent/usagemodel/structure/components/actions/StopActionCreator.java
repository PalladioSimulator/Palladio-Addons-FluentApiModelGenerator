package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.Stop Stop}. It is used to
 * create the '<em><b>Stop</b></em>' object step-by-step, i.e. '<em><b>StopActionCreator</b></em>'
 * objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.Stop
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class StopActionCreator extends ActionCreator {

    @Override
    public AbstractUserAction build() {
        Stop stop = UsagemodelFactory.eINSTANCE.createStop();

        if (this.name != null) {
            stop.setEntityName(this.name);
        }
        return stop;
    }

    @Override
    public StopActionCreator withName(final String name) {
        return (StopActionCreator) super.withName(name);
    }

}
