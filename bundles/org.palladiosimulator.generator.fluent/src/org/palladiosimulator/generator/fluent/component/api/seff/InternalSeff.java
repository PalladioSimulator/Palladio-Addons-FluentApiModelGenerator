package org.palladiosimulator.generator.fluent.component.api.seff;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff.StartActionCreator;
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
