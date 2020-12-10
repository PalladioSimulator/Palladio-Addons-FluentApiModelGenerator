package systemModel.structure.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

public interface IContextRequiredRoleCombinator {
	public AssemblyConnectorCreator CombineContextAndRequiredRole(AssemblyContext context, OperationRequiredRole role);
}
