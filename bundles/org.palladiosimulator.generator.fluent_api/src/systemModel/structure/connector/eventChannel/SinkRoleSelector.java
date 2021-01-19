package systemModel.structure.connector.eventChannel;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SinkRole;


public class SinkRoleSelector {
	private IContextSinkRoleCombinator combinator;
	private AssemblyContext context;
	
	public SinkRoleSelector(IContextSinkRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public EventChannelSinkConnectorCreator withSinkRole(SinkRole role) {
		return combinator.combineContextAndSinkRole(context, role);
	}
	
	public EventChannelSinkConnectorCreator withSinkRole(String name) {
		SinkRole role = (SinkRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSinkRole(role);
	}

}
