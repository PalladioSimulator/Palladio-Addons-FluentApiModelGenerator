package system.structure.connector.infrastructure;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

import exceptions.NoSuchElementException;
import system.structure.connector.IContextRoleCombinator;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 *
 * @author Florian Krone
 * @param <T> The ConnectorCreator, creating this selector.
 */
public class InfrastructureRequiredRoleSelector<T> {
    private final IContextRoleCombinator<InfrastructureRequiredRole, T> combinator;
    private final AssemblyContext context;

    public InfrastructureRequiredRoleSelector(final IContextRoleCombinator<InfrastructureRequiredRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @param role
     * @return the assembly connector
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withInfrastructureRequiredRole(final InfrastructureRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}. The required roles of the context are searched for a role
     * matching the given name.
     *
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException Thrown if no role matches the given name.
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withInfrastructureRequiredRole(final String name) throws NoSuchElementException {
        final RequiredRole role = this.context.getEncapsulatedComponent__AssemblyContext()
                .getRequiredRoles_InterfaceRequiringEntity().stream().filter(x -> x.getEntityName().equals(name))
                .findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No InfrastructureRequiredRole with name '%s' found.", name)));
        try {
            return this.withInfrastructureRequiredRole((InfrastructureRequiredRole) role);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("A Role with name '%s' was found, but it was not an InfrastructureRequiredRole. "
                            + "Please make sure all names are unique.", name),
                    e);
        }
    }
}
