package system.structure.connector.operation;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

import system.structure.connector.IContextRoleCombinator;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 *
 * @author Florian Krone
 * @param <T> The ConnectorCreator, creating this selector.
 */
public class OperationProvidedRoleSelector<T> {
    private final IContextRoleCombinator<OperationProvidedRole, T> combinator;
    private final AssemblyContext context;

    public OperationProvidedRoleSelector(final IContextRoleCombinator<OperationProvidedRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} provided by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @param role
     * @return the assembly connector
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withOperationProvidedRole(final OperationProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} provided by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}. The provided roles of the context are searched for a role
     * matching the given name.
     *
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException Thrown if no role matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withOperationProvidedRole(final String name) throws NoSuchElementException {
        final ProvidedRole role = this.context.getEncapsulatedComponent__AssemblyContext()
                .getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No OperationProvidedRole with name '%s' found.", name)));
        try {
            return this.withOperationProvidedRole((OperationProvidedRole) role);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an OperationProvidedRole. "
                            + "Please make sure all names are unique.", name));
        }
    }
}
