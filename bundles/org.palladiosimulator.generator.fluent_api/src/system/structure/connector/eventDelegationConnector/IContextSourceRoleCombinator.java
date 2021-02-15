package system.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;

public interface IContextSourceRoleCombinator {
    public SourceDelegationConnectorCreator combineContextAndSourceRole(AssemblyContext context, SourceRole role);
}
