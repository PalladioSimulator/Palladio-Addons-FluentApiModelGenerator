package org.palladiosimulator.generator.fluent.system.structure.connector.operation;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.connector.IContextRoleCombinator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 *
 * @author Florian Krone
 * @param <T> The ConnectorCreator, creating this selector.
 */
public class OperationRequiredRoleSelector<T> {
    private final IContextRoleCombinator<OperationRequiredRole, T> combinator;
    private final AssemblyContext context;

    public OperationRequiredRoleSelector(final IContextRoleCombinator<OperationRequiredRole, T> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @param role
     * @return the assembly connector
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withOperationRequiredRole(final OperationRequiredRole role) {
        IllegalArgumentException.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRole(this.context, role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}. The provided roles of the context are searched for a role
     * matching the given name.
     *
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException Thrown if no role matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public T withOperationRequiredRole(final String name) {
        final OperationRequiredRole role = (OperationRequiredRole) this.context
                .getEncapsulatedComponent__AssemblyContext().getRequiredRoles_InterfaceRequiringEntity().stream()
                .filter(x -> x.getEntityName().equals(name)).findFirst().get();
        return this.withOperationRequiredRole(role);
    }
}
