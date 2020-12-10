package systemModel.structure.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public class OperationProvidedRoleCreator {
	private IContextProvidedRoleCombinator combinator;
	private AssemblyContext context;
	
	public OperationProvidedRoleCreator(IContextProvidedRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyConnectorCreator withOpeartionRequiredRole(String name) {
		OperationProvidedRole role = (OperationProvidedRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return combinator.CombineContextAndProvidedRole(context, role);
	}
}
