package system.structure.connector.requiredResourceDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

public interface IContextRequiredRoleCombinator {
    public RequiredResourceDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
            ResourceRequiredRole role);
}
