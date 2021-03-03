package system.structure.connector.infrastructureDelegationConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

public class InfrastructureProvidedRoleSelector {
    private final IContextProvidedRoleCombinator combinator;
    private final AssemblyContext context;

    public InfrastructureProvidedRoleSelector(final IContextProvidedRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public ProvidedInfrastructureDelegationConnectorCreator withInfrastructureProvidedRole(
            final InfrastructureProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndProvidedRole(this.context, role);
    }

    public ProvidedInfrastructureDelegationConnectorCreator withInfrastructureProvidedRole(final String name)
            throws NoSuchElementException {
        final ProvidedRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureProvidedRole with name '%s' found.", name)));
        try {
            return this.withInfrastructureProvidedRole((InfrastructureProvidedRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an InfrastructureProvidedRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
