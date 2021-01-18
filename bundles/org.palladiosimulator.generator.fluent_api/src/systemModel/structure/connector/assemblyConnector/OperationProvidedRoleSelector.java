package systemModel.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public class OperationProvidedRoleSelector {
	private IContextProvidedRoleCombinator combinator;
	private AssemblyContext context;
	
	public OperationProvidedRoleSelector(IContextProvidedRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyConnectorCreator withOperationProvidedRole(String name) {
		OperationProvidedRole role = (OperationProvidedRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return combinator.CombineContextAndProvidedRole(context, role);
	}
}
