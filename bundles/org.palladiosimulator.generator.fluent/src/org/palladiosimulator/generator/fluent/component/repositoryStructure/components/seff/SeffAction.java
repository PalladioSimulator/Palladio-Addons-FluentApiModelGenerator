package org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff;

import org.palladiosimulator.generator.fluent.component.api.seff.ActionSeff;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryEntity;
import org.palladiosimulator.pcm.seff.AbstractAction;

/**
 * This class provides the general infrastructure of a SEFF action: an action
 * belongs to a SEFF and has a successor. All actions except for the StopAction
 * inherit from this class.
 *
 * @author Louisa Lambrecht
 */
public abstract class SeffAction extends RepositoryEntity {

    protected SeffCreator seff;

    @Override
    protected abstract AbstractAction build();

    /**
     * Turns the previous action-in-the-making into an
     * '<em><b>AbstractAction</b></em>' object. The action is added to the SEFF's
     * body behaviour and its predecessor is set so that the actions are linked in
     * the correct order.
     *
     * @return the SEFF's body behaviour
     */
    public ActionSeff followedBy() {
        final AbstractAction action = build();

        seff.setNext(action);
        return seff;
    }
}
