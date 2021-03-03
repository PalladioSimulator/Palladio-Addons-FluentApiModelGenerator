package system.structure.connector.assemblyConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

public class OperationProvidedRoleSelector {
    private final IContextProvidedRoleCombinator combinator;
    private final AssemblyContext context;

    public OperationProvidedRoleSelector(final IContextProvidedRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public AssemblyConnectorCreator withOperationProvidedRole(final OperationProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndProvidedRole(this.context, role);
    }

    public AssemblyConnectorCreator withOperationProvidedRole(final String name) throws NoSuchElementException {
        final ProvidedRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationProvidedRole with name '%s' found.", name)));
        try {
            return this.withOperationProvidedRole((OperationProvidedRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an OperationProvidedRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
