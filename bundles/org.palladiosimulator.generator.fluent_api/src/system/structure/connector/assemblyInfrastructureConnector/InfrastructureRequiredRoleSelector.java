package system.structure.connector.assemblyInfrastructureConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

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
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    public AssemblyInfrastructureConnectorCreator withInfrastructureRequiredRole(final String name)
            throws NoSuchElementException {
        final RequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureRequiredRole with name '%s' found.", name)));
        try {
            return this.withInfrastructureRequiredRole((InfrastructureRequiredRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an InfrastructureRequiredRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
