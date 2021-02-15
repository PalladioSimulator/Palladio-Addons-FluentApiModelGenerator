package system.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public class OperationRequiredRoleSelector {
    private final IContextRequiredRoleCombinator combinator;
    private final AssemblyContext context;

    public OperationRequiredRoleSelector(final IContextRequiredRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public AssemblyConnectorCreator withOperationRequiredRole(final OperationRequiredRole role) {
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    public AssemblyConnectorCreator withOperationRequiredRole(final String name) {
        final OperationRequiredRole role = (OperationRequiredRole) this.context
            .getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOperationRequiredRole(role);
    }
}
