package org.palladiosimulator.generator.fluent.system.structure.connector.infrastructure;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
 * AssemblyInfrastructureConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
 */
public class AssemblyInfrastructureConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext requiringContext;
    private InfrastructureRequiredRole requiredRole;
    private AssemblyContext providingContext;
    private InfrastructureProvidedRole providedRole;

    public AssemblyInfrastructureConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator> withRequiringAssemblyContext(
            final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new InfrastructureRequiredRoleSelector<>((reqContext, role) -> {
            this.requiringContext = reqContext;
            this.requiredRole = role;
            return this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator> withRequiringAssemblyContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withRequiringAssemblyContext(context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role.
     *
     * @param context
     * @return this connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator> withProvidingAssemblyContext(
            final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        return new InfrastructureProvidedRoleSelector<>((provContext, role) -> {
            this.providingContext = provContext;
            this.providedRole = role;
            return this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent.system are searched for one that matches the given
     * name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator> withProvidingAssemblyContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withProvidingAssemblyContext(context);
    }

    @Override
    public AssemblyInfrastructureConnector build() {
        final var connector = CompositionFactory.eINSTANCE.createAssemblyInfrastructureConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(this.requiringContext);
        connector.setRequiredRole__AssemblyInfrastructureConnector(this.requiredRole);
        connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(this.providingContext);
        connector.setProvidedRole__AssemblyInfrastructureConnector(this.providedRole);
        return connector;
    }

    @Override
    public AssemblyInfrastructureConnectorCreator withName(final String name) {
        return (AssemblyInfrastructureConnectorCreator) super.withName(name);
    }

}
