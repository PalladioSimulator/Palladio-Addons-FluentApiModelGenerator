package system.structure.connector;

import org.palladiosimulator.pcm.core.composition.Connector;

import system.structure.SystemEntity;

public abstract class AbstractConnectorCreator extends SystemEntity {

    @Override
    public abstract Connector build();

}
