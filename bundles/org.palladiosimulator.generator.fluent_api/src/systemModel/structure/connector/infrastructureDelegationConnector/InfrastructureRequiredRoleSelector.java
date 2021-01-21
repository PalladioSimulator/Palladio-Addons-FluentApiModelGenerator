package systemModel.structure.connector.infrastructureDelegationConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

public class InfrastructureRequiredRoleSelector {
	private IContextRequiredRoleCombinator combinator;
	private AssemblyContext context;
	
	public InfrastructureRequiredRoleSelector(IContextRequiredRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public RequiredInfrastructureDelegationConnectorCreator withInfrastructureRequiredRole(InfrastructureRequiredRole role) {
		return combinator.combineContextAndRequiredRole(context, role);
	}
	
	public RequiredInfrastructureDelegationConnectorCreator withInfrastructuRequiredRole(String name) {
		InfrastructureRequiredRole role = (InfrastructureRequiredRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withInfrastructureRequiredRole(role);
	}
}
