package systemModel.structure.connector.delegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public interface IContextProvidedRoleCombinator {
	public ProvidedDelegationConnectorCreator combineContextAndProvidedRole(AssemblyContext context, OperationProvidedRole role);
}
