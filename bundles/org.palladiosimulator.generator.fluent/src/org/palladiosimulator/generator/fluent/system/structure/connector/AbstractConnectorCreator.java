package org.palladiosimulator.generator.fluent.system.structure.connector;

import org.palladiosimulator.generator.fluent.system.structure.SystemEntity;
import org.palladiosimulator.pcm.core.composition.Connector;

/**
 * TODO
 */
public abstract class AbstractConnectorCreator extends SystemEntity {

    @Override
    public abstract Connector build();

}
