package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;

/**
 * This class provides the general infrastructure of an action of the scenario behviour in usage
 * model. All actions inherit from this class.
 *
 * @author Eva-Maria Neumann
 */
public abstract class ActionCreator extends UsageModelEntity {
    protected AbstractUserAction successor;

    @Override
    public abstract AbstractUserAction build();

    /**
     * Adds an {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction Abstract User Action}
     * as successor.
     * <p>
     * Create a new action by using the org.palladiosimulator.generator.fluent.usagemodel.factory,
     * i.e. <code>create.newBranchAction()</code>.
     * </p>
     *
     * @param action
     *            in the making
     * @return the current action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     */
    public ActionCreator withSuccessor(ActionCreator action) {
        IllegalArgumentException.throwIfNull(action, "The given Successor Action must not be null");
        this.successor = action.build();
        return this;
    }

    /**
     * Returns the {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction successor} of the
     * current action.
     * 
     * @return the successor of the current action in the making
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     */
    public AbstractUserAction getSuccessor() {
        return this.successor;
    }

    @Override
    public ActionCreator withName(final String name) {
        return (ActionCreator) super.withName(name);
    }

}
