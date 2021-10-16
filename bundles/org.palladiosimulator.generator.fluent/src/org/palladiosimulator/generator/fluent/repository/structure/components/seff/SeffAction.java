package org.palladiosimulator.generator.fluent.repository.structure.components.seff;

import org.palladiosimulator.generator.fluent.repository.api.seff.ActionSeff;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryEntity;
import org.palladiosimulator.pcm.seff.AbstractAction;

/**
 * This class provides the general infrastructure of a SEFF action: an action belongs to a SEFF and
 * has a successor. All actions except for the StopAction inherit from this class.
 *
 * @author Louisa Lambrecht
 */
public abstract class SeffAction extends RepositoryEntity {

    protected SeffCreator seff;

    @Override
    protected abstract AbstractAction build();

    /**
     * Turns the previous action-in-the-making into an '<em><b>AbstractAction</b></em>' object. The
     * action is added to the SEFF's body behaviour and its predecessor is set so that the actions
     * are linked in the correct order.
     *
     * @return the SEFF's body behaviour
     * @throws IllegalStateException if internal SEFF creator is not set. This is the responsibility of
     *                               the implementing class.
     */
    public ActionSeff followedBy() throws IllegalStateException {
        if (seff == null)
            throw new IllegalStateException("internal SEFF creator is not set");
        final AbstractAction action = build();

        this.seff.setNext(action);
        return this.seff;
    }
}
