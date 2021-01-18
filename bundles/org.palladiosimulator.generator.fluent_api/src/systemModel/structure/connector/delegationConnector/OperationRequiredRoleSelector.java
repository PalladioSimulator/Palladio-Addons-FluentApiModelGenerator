package systemModel.structure.connector.delegationConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public class OperationRequiredRoleSelector {
	private IContextRequiredRoleCombinator combinator;
	private AssemblyContext context;
	
	public OperationRequiredRoleSelector(IContextRequiredRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public RequiredDelegationConnectorCreator withOpeartionRequiredRole(OperationRequiredRole role) {
		return combinator.CombineContextAndRequiredRole(context, role);
	}
	
	public RequiredDelegationConnectorCreator withOperationRequiredRoleByName(String name) {
		OperationRequiredRole role = (OperationRequiredRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOpeartionRequiredRole(role);
	}
}
