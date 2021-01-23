package systemModel.structure.connector.assemblyEventConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;

public interface IContextSinkRoleCombinator {
	public AssemblyEventConnectorCreator combineContextAndSinkRole(AssemblyContext context, SinkRole role);
}
