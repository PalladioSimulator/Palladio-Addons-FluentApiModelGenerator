package system.structure.connector.infrastructure;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

import system.structure.connector.IContextRoleCombinator;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 *
 * @author Florian Krone
 * @param <T> The ConnectorCreator, creating this selector.
 */
public class InfrastructureProvidedRoleSelector<T> {
    private final IContextRoleCombinator<InfrastructureProvidedRole, T> combinator;
    private final AssemblyContext context;

    public InfrastructureProvidedRoleSelector(final IContextRoleCombinator<InfrastructureProvidedRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} provided by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @param role
     * @return the assembly connector
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withInfrastructureProvidedRole(final InfrastructureProvidedRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} provided by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}. The provided roles of the context are searched for a role
     * matching the given name.
     *
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException Thrown if no role matches the given name.
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withInfrastructureProvidedRole(final String name) throws NoSuchElementException {
        final ProvidedRole role = this.context.getEncapsulatedComponent__AssemblyContext()
                .getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(
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
