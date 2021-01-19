package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;


public interface IContextSourceRoleCombinator {
	public EventChannelSourceConnectorCreator combineContextAndSourceRole(AssemblyContext context, SourceRole role);

}
