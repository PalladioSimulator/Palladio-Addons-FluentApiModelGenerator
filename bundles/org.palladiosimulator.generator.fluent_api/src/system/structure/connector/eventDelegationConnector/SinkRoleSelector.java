package system.structure.connector.eventDelegationConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.SinkRole;

public class SinkRoleSelector {
    private final IContextSinkRoleCombinator combinator;
    private final AssemblyContext context;

    public SinkRoleSelector(final IContextSinkRoleCombinator combinator, final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public SinkDelegationConnectorCreator withSinkRole(final SinkRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndSinkRole(this.context, role);
    }

    public SinkDelegationConnectorCreator withSinkRole(final String name) throws NoSuchElementException {
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
