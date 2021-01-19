package systemModel.structure.connector.delegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public interface IContextRequiredRoleCombinator {
	public RequiredDelegationConnectorCreator CombineContextAndRequiredRole(AssemblyContext context, OperationRequiredRole role);
}
