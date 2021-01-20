package systemModel.structure.connector.operationDelegationConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public class OperationProvidedRoleSelector {
	private IContextProvidedRoleCombinator combinator;
	private AssemblyContext context;
	
	public OperationProvidedRoleSelector(IContextProvidedRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public ProvidedDelegationConnectorCreator withOpeartionProvidedRole(OperationProvidedRole role) {
		return combinator.combineContextAndProvidedRole(context, role);
	}
	
	public ProvidedDelegationConnectorCreator withOperationProvidedRole(String name) {
		OperationProvidedRole role = (OperationProvidedRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOpeartionProvidedRole(role);
	}
}
