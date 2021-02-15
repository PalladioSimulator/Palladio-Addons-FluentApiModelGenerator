package system.structure.connector.requiredResourceDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

public class ResourceRequiredRoleSelector {
    private final IContextRequiredRoleCombinator combinator;
    private final AssemblyContext context;

    public ResourceRequiredRoleSelector(final IContextRequiredRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final ResourceRequiredRole role) {
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final String name) {
        final ResourceRequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withResourceRequiredRole(role);
    }
}
