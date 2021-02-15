package system.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public interface IContextProvidedRoleCombinator {
    public AssemblyConnectorCreator combineContextAndProvidedRole(AssemblyContext context, OperationProvidedRole role);
}
