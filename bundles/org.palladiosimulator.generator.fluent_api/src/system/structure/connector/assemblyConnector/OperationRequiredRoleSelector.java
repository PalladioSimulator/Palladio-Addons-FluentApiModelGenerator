package system.structure.connector.assemblyConnector;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 * 
 * @author Florian Krone
 *
 */
public class OperationRequiredRoleSelector {
    private final IContextRequiredRoleCombinator combinator;
    private final AssemblyContext context;

    public OperationRequiredRoleSelector(final IContextRequiredRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}.
     * 
     * @param role
     * @return the assembly connector
     * 
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyConnectorCreator withOperationRequiredRole(final OperationRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        return this.combinator.combineContextAndRequiredRole(this.context, role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}. The
     * provided roles of the context are searched for a role matching the given name.
     * 
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException
     *             Thrown if no role matches the given name.
     * 
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyConnectorCreator withOperationRequiredRole(final String name) {
        final OperationRequiredRole role = (OperationRequiredRole) this.context
            .getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOperationRequiredRole(role);
    }
}
