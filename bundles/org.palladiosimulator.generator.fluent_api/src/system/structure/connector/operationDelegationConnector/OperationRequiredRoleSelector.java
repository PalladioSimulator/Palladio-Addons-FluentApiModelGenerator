package system.structure.connector.operationDelegationConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

public class OperationRequiredRoleSelector {
    private final IContextRequiredRoleCombinator combinator;
    private final AssemblyContext context;

    public OperationRequiredRoleSelector(final IContextRequiredRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public RequiredDelegationConnectorCreator withOpeartionRequiredRole(final OperationRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    public RequiredDelegationConnectorCreator withOperationRequiredRole(final String name)
            throws NoSuchElementException {
        final RequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationRequiredRole with name '%s' found.", name)));
        try {
            return this.withOpeartionRequiredRole((OperationRequiredRole) role);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an OperationRequiredRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
