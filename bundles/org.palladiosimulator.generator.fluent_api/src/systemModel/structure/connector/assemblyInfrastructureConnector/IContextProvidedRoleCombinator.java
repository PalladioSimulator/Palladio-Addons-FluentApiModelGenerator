package systemModel.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

public interface IContextProvidedRoleCombinator {
	public AssemblyInfrastructureConnectorCreator combineContextAndProvidedRole(AssemblyContext context, InfrastructureProvidedRole role);
}
