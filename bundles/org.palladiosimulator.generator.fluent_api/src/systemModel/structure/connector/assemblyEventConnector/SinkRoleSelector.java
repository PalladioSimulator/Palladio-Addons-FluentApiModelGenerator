package systemModel.structure.connector.assemblyEventConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;

public class SinkRoleSelector {
	private IContextSinkRoleCombinator combinator;
	private AssemblyContext context;
	
	public SinkRoleSelector(IContextSinkRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyEventConnectorCreator withSinkRole(SinkRole role) {
		return combinator.combineContextAndSinkRole(context, role);
	}

	public AssemblyEventConnectorCreator withSinkRole(String name) {
		SinkRole role = (SinkRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSinkRole(role);
	}
}
