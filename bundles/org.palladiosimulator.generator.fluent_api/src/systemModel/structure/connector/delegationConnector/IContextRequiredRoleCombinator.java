package systemModel.structure.connector.delegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

public interface IContextRequiredRoleCombinator {
	public RequiredDelegationConnectorCreator CombineContextAndRequiredRole(AssemblyContext context, OperationRequiredRole role);
}
