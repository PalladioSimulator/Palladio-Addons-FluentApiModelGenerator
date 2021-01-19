package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;


public interface IContextSourceRoleCombinator {
	public EventChannelSourceConnectorCreator CombineContextAndSourceRole(AssemblyContext context, SourceRole role);

}
