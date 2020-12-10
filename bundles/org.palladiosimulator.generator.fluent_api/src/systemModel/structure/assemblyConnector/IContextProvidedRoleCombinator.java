package systemModel.structure.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public interface IContextProvidedRoleCombinator {
	public AssemblyConnectorCreator CombineContextAndProvidedRole(AssemblyContext context, OperationProvidedRole role);
}
