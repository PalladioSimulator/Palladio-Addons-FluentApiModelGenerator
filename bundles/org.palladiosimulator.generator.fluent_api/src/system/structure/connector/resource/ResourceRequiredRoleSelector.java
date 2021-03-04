package system.structure.connector.resource;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import system.structure.connector.IContextRoleCombinator;

public class ResourceRequiredRoleSelector {
    private final IContextRoleCombinator<ResourceRequiredRole, RequiredResourceDelegationConnectorCreator> combinator;
    private final AssemblyContext context;

    public ResourceRequiredRoleSelector(
            final IContextRoleCombinator<ResourceRequiredRole, RequiredResourceDelegationConnectorCreator> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final ResourceRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No ResourceRequiredRole with name '%s' found.", name)));
        return this.withResourceRequiredRole(role);
    }
}
