package systemModel.structure.connector.assemblyEventConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;

public interface IContextSourceRoleCombinator {
	public AssemblyEventConnectorCreator combineContextAndSourceRole(AssemblyContext context, SourceRole role);
}
