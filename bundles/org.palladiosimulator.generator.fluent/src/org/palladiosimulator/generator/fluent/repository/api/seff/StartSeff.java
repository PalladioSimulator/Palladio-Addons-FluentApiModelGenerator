package org.palladiosimulator.generator.fluent.repository.api.seff;

import org.palladiosimulator.generator.fluent.repository.structure.components.seff.StartActionCreator;

/**
 * This interface is merely used for control flow purposes making sure that a body behaviour always
 * starts with a start action.
 *
 * @author Louisa Lambrecht
 */
public interface StartSeff {

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.StartAction StartAction}.
     *
     * @return the start action in the making
     */
    StartActionCreator withStartAction();

}
