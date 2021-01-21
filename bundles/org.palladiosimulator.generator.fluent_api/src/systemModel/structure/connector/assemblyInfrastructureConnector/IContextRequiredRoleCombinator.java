package systemModel.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

public interface IContextRequiredRoleCombinator {
	public AssemblyInfrastructureConnectorCreator combineContextAndRequiredRole(AssemblyContext context, InfrastructureRequiredRole role);
}
