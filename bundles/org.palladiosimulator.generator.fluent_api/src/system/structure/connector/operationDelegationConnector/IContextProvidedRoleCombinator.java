package system.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public interface IContextProvidedRoleCombinator {
    public ProvidedDelegationConnectorCreator combineContextAndProvidedRole(AssemblyContext context,
            OperationProvidedRole role);
}
