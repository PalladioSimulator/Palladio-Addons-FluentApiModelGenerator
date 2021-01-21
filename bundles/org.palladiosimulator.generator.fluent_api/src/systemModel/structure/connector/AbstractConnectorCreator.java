package systemModel.structure.connector;

import org.palladiosimulator.pcm.core.composition.Connector;

import systemModel.structure.SystemEntity;

public abstract class AbstractConnectorCreator extends SystemEntity {

	@Override
	public abstract Connector build();

}
