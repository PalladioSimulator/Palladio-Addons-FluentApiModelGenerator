package system.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;

public interface IContextSinkRoleCombinator {
    public SinkDelegationConnectorCreator combineContextAndSinkRole(AssemblyContext context, SinkRole role);
}
