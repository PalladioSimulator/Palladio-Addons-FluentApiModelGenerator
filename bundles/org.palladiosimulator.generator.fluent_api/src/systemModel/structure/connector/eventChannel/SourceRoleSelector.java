package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;


public class SourceRoleSelector {
	private IContextSourceRoleCombinator combinator;
	private AssemblyContext context;
	
	public SourceRoleSelector(IContextSourceRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public EventChannelSourceConnectorCreator withSourceRole(SourceRole role) {
		return combinator.CombineContextAndSourceRole(context, role);
	}
	
	public EventChannelSourceConnectorCreator withSourceRole(String name) {
		SourceRole role = (SourceRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSourceRole(role);
	}

}
