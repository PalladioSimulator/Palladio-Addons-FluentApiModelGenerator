package system.structure.connector.event;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.connector.IContextRoleCombinator;

public class SourceRoleSelector<T> {
    private final IContextRoleCombinator<SourceRole, T> combinator;
    private final AssemblyContext context;

    public SourceRoleSelector(final IContextRoleCombinator<SourceRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public T withSourceRole(final SourceRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    public T withSourceRole(final String name) throws NoSuchElementException {
        final RequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SourceRole with name '%s' found.", name)));
        try {
            return this.withSourceRole((SourceRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not a SourceRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
