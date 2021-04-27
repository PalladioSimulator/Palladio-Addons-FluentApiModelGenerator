package org.palladiosimulator.generator.fluent_api.system.structure.connector.operation;

import java.util.Objects;

import org.palladiosimulator.generator.fluent_api.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent_api.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
 * AssemblyConnector}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
 */
public class AssemblyConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext requiringContext;
    private OperationRequiredRole requiredRole;
    private AssemblyContext providingContext;
    private OperationProvidedRole providedRole;

    public AssemblyConnectorCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that requires the role.
     *
     * @param context
     * @return this assembly connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public OperationRequiredRoleSelector<AssemblyConnectorCreator> withRequiringAssemblyContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new OperationRequiredRoleSelector<>((reqContext, role) -> {
            AssemblyConnectorCreator.this.requiringContext = reqContext;
            AssemblyConnectorCreator.this.requiredRole = role;
            return AssemblyConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that requires the role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent_api.system are searched for one that matches the given name.
     *
     * @param name
     * @return this assembly connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public OperationRequiredRoleSelector<AssemblyConnectorCreator> withRequiringAssemblyContext(final String name)
            throws NoSuchElementException {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withRequiringAssemblyContext(context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that provides the role.
     *
     * @param context
     * @return this assembly connector
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public OperationProvidedRoleSelector<AssemblyConnectorCreator> withProvidingAssemblyContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new OperationProvidedRoleSelector<>((provContext, role) -> {
            AssemblyConnectorCreator.this.providingContext = provContext;
            AssemblyConnectorCreator.this.providedRole = role;
            return AssemblyConnectorCreator.this;
        }, context);
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that provides the role. The assembly contexts added to the
     * org.palladiosimulator.generator.fluent_api.system are searched for one that matches the given name.
     *
     * @param name
     * @return this assembly connector
     * @throws NoSuchElementException Thrown if no element matches the given name.
     */
    public OperationProvidedRoleSelector<AssemblyConnectorCreator> withProvidingAssemblyContext(final String name)
            throws NoSuchElementException {
        final AssemblyContext context = system.getAssemblyContextByName(name);
        return this.withProvidingAssemblyContext(context);
    }

    @Override
    public AssemblyConnector build() {
        final AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
        if (name != null) {
            connector.setEntityName(name);
        }
        connector.setRequiringAssemblyContext_AssemblyConnector(requiringContext);
        connector.setRequiredRole_AssemblyConnector(requiredRole);
        connector.setProvidingAssemblyContext_AssemblyConnector(providingContext);
        connector.setProvidedRole_AssemblyConnector(providedRole);
        return connector;
    }

    @Override
    public AssemblyConnectorCreator withName(final String name) {
        return (AssemblyConnectorCreator) super.withName(name);
    }

}
