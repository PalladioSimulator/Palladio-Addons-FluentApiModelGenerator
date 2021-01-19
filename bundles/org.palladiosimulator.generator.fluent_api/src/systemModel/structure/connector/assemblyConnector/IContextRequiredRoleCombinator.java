package systemModel.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public interface IContextRequiredRoleCombinator {
	public AssemblyConnectorCreator combineContextAndRequiredRole(AssemblyContext context, OperationRequiredRole role);
}