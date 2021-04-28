package org.palladiosimulator.generator.fluent.system.structure.connector.resource;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.connector.IContextRoleCombinator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

/**
 * This class ensures, that a Role is only selected after an AssemblyContext.
 *
 * @author Florian Krone
 */
public class ResourceRequiredRoleSelector {
    private final IContextRoleCombinator<ResourceRequiredRole, RequiredResourceDelegationConnectorCreator> combinator;
    private final AssemblyContext context;

    public ResourceRequiredRoleSelector(
            final IContextRoleCombinator<ResourceRequiredRole, RequiredResourceDelegationConnectorCreator> combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @param role
     * @return the assembly connector
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final ResourceRequiredRole role) {
        IllegalArgumentException.requireNonNull(role, "The given Role must not be null.");
        return combinator.combineContextAndRole(context, role);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} required by the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}. The provided roles of the context are searched for a role
     * matching the given name.
     *
     * @param name
     * @return the assembly connector
     * @throws NoSuchElementException Thrown if no role matches the given name.
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public RequiredResourceDelegationConnectorCreator withResourceRequiredRole(final String name)
            throws NoSuchElementException {
        final ResourceRequiredRole role = context.getEncapsulatedComponent__AssemblyContext()
                .getResourceRequiredRoles__ResourceInterfaceRequiringEntity().stream()
                .filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No ResourceRequiredRole with name '%s' found.", name)));
        return this.withResourceRequiredRole(role);
    }
}
