package org.palladiosimulator.generator.fluent.repository.api.seff;

import org.palladiosimulator.generator.fluent.repository.structure.components.seff.StartActionCreator;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

public interface InternalSeff {

    /**
     * Creates a {@link org.palladiosimulator.pcm.seff.StartAction StartAction}.
     *
     * @return the start action in the making
     */
    StartActionCreator withStartAction();

    ResourceDemandingInternalBehaviour buildInternalBehaviour();

    ResourceDemandingBehaviour buildBehaviour();

    ForkedBehaviour buildForkedBehaviour();
}
