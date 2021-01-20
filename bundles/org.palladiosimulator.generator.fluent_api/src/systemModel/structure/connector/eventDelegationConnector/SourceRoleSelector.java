package systemModel.structure.connector.eventDelegationConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;

public class SourceRoleSelector {
	private IContextSourceRoleCombinator combinator;
	private AssemblyContext context;
	
	public SourceRoleSelector(IContextSourceRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public SourceDelegationConnectorCreator withSourceRole(SourceRole role) {
		return combinator.combineContextAndSourceRole(context, role);
	}
	
	public SourceDelegationConnectorCreator withSourceRole(String name) {
		SourceRole role = (SourceRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSourceRole(role);
	}
}
