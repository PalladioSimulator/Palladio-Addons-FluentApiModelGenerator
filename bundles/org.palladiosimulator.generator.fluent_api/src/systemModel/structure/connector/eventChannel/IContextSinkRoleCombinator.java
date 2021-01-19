package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;


public interface IContextSinkRoleCombinator {
	public EventChannelSinkConnectorCreator combineContextAndSinkRole(AssemblyContext context, SinkRole role);

}
