package systemModel.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

public class InfrastructureProvidedRoleSelector {
	private IContextProvidedRoleCombinator combinator;
	private AssemblyContext context;
	
	public InfrastructureProvidedRoleSelector(IContextProvidedRoleCombinator combinator, AssemblyContext context) {
		this.combinator = combinator;
		this.context = context;
	}
	
	
	public AssemblyInfrastructureConnectorCreator withInfrastructureProvidedRole(InfrastructureProvidedRole role) {
		return combinator.combineContextAndProvidedRole(context, role);
	}

	public AssemblyInfrastructureConnectorCreator withInfrastructureProvidedRole(String name) {
		InfrastructureProvidedRole role = (InfrastructureProvidedRole) context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withInfrastructureProvidedRole(role);
	}
}
