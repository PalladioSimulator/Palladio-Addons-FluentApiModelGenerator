package system.structure.connector.infrastructure;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

import system.structure.connector.IContextRoleCombinator;

public class InfrastructureProvidedRoleSelector<T> {
    private final IContextRoleCombinator<InfrastructureProvidedRole, T> combinator;
    private final AssemblyContext context;

    public InfrastructureProvidedRoleSelector(
            final IContextRoleCombinator<InfrastructureProvidedRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public T withInfrastructureProvidedRole(
            final InfrastructureProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    public T withInfrastructureProvidedRole(final String name)
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
