package systemModel.structure.connector.requiredResourceDelegationConnector;


import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

public class ResourceRequiredRoleSelector {
	private IContextRequiredRoleCombinator combinator;
	private AssemblyContext context;
	
	public ResourceRequiredRoleSelector(IContextRequiredRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(ResourceRequiredRole role) {
		return combinator.combineContextAndRequiredRole(context, role);
	}
	
	public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(String name) {
		ResourceRequiredRole role =  context.getEncapsulatedComponent__AssemblyContext()
				.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withResourceRequiredRole(role);
	}
}
