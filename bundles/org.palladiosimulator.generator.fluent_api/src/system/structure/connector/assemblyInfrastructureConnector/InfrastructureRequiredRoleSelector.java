package system.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

public class InfrastructureRequiredRoleSelector {
    private final IContextRequiredRoleCombinator combinator;
    private final AssemblyContext context;

    public InfrastructureRequiredRoleSelector(final IContextRequiredRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public AssemblyInfrastructureConnectorCreator withInfrastructureRequiredRole(
            final InfrastructureRequiredRole role) {
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    public AssemblyInfrastructureConnectorCreator withInfrastructureRequiredRole(final String name) {
        final InfrastructureRequiredRole role = (InfrastructureRequiredRole) this.context
            .getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withInfrastructureRequiredRole(role);
    }
}
