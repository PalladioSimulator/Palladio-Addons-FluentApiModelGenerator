package systemModel.structure.assemblyConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public class OperationRequiredRoleCreator {
	private IContextRequiredRoleCombinator combinator;
	private AssemblyContext context;
	
	public OperationRequiredRoleCreator(IContextRequiredRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyConnectorCreator withOpeartionRequiredRole(String name) {
		OperationRequiredRole role = (OperationRequiredRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return combinator.CombineContextAndRequiredRole(context, role);
	}
}
