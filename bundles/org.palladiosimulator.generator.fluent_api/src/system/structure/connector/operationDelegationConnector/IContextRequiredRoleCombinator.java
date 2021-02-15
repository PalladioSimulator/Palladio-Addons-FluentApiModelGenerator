package system.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public interface IContextRequiredRoleCombinator {
    public RequiredDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
            OperationRequiredRole role);
}
