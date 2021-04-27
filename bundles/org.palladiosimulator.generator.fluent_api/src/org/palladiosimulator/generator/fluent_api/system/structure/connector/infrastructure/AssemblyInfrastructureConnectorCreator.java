package org.palladiosimulator.generator.fluent_api.system.structure.connector.infrastructure;

import java.util.Objects;

import org.palladiosimulator.generator.fluent_api.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent_api.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.connector.AbstractConnectorCreator;
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
        system = systemCreator;
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new InfrastructureRequiredRoleSelector<>((reqContext, role) -> {
            AssemblyInfrastructureConnectorCreator.this.requiringContext = reqContext;
            AssemblyInfrastructureConnectorCreator.this.requiredRole = role;
            return AssemblyInfrastructureConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the required role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent_api.system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator> withRequiringAssemblyContext(
            final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final var creator = this;
        return new InfrastructureProvidedRoleSelector<>((provContext, role) -> {
            AssemblyInfrastructureConnectorCreator.this.providingContext = provContext;
            AssemblyInfrastructureConnectorCreator.this.providedRole = role;
            return creator;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} with the provided role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent_api.system are searched for one that matches the given name.
     *
     * @param name
     * @return this connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator> withProvidingAssemblyContext(
            final String name) {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withProvidingAssemblyContext(context);
    }

    @Override
    public AssemblyInfrastructureConnector build() {
        final var connector = CompositionFactory.eINSTANCE.createAssemblyInfrastructureConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(requiringContext);
        connector.setRequiredRole__AssemblyInfrastructureConnector(requiredRole);
        connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(providingContext);
        connector.setProvidedRole__AssemblyInfrastructureConnector(providedRole);
        return connector;
    }

    @Override
    public AssemblyInfrastructureConnectorCreator withName(final String name) {
        return (AssemblyInfrastructureConnectorCreator) super.withName(name);
    }

}
