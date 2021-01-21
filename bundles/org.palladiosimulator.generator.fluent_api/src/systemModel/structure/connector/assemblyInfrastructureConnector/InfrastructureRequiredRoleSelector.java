package systemModel.structure.connector.assemblyInfrastructureConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

public class InfrastructureRequiredRoleSelector {
	private IContextRequiredRoleCombinator combinator;
	private AssemblyContext context;
	
	public InfrastructureRequiredRoleSelector(IContextRequiredRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyInfrastructureConnectorCreator withInfrastructureRequiredRole(InfrastructureRequiredRole role) {
		return combinator.combineContextAndRequiredRole(context, role);
	}
	
	public AssemblyInfrastructureConnectorCreator withInfrastructureRequiredRole(String name) {
		InfrastructureRequiredRole role = (InfrastructureRequiredRole) context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withInfrastructureRequiredRole(role);
	}
}
