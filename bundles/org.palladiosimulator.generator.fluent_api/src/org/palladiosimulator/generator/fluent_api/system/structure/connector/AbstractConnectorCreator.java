package org.palladiosimulator.generator.fluent_api.system.structure.connector;

import org.palladiosimulator.generator.fluent_api.system.structure.SystemEntity;
import org.palladiosimulator.pcm.core.composition.Connector;

public abstract class AbstractConnectorCreator extends SystemEntity {

    @Override
    public abstract Connector build();

}
