package system.structure.connector.event;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.connector.IContextRoleCombinator;

public class SinkRoleSelector<T> {
    private final IContextRoleCombinator<SinkRole, T> combinator;
    private final AssemblyContext context;

    public SinkRoleSelector(final IContextRoleCombinator<SinkRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public T withSinkRole(final SinkRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    public T withSinkRole(final String name) throws NoSuchElementException {
        final ProvidedRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SinkRole with name '%s' found.", name)));
        try {
            return this.withSinkRole((SinkRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not a SinkRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
