package org.palladiosimulator.generator.fluent_api.system.structure.systemRole;

import java.util.Objects;

import org.palladiosimulator.generator.fluent_api.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent_api.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.SystemEntity;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
 * InfrastructureRequiredRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
 */
public class InfrastructureRequiredRoleCreator extends SystemEntity {

    private InfrastructureInterface requiredInterface;

    public InfrastructureRequiredRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} this role requires.
     *
     * @param infrastructureInterface
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureRequiredRoleCreator withRequiredInterface(
            final InfrastructureInterface infrastructureInterface) {
        Objects.requireNonNull(infrastructureInterface, "The given Interface must not be null.");
        requiredInterface = infrastructureInterface;
        return this;
    }

    /**
     * Defines the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} this role requires. Searches the repositories added
     * to the org.palladiosimulator.generator.fluent_api.system for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureRequiredRoleCreator withRequiredInterface(final String name) throws NoSuchElementException {
        InfrastructureInterface infrastructureInterface;
        try {
            infrastructureInterface = (InfrastructureInterface) system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an InfrastructureInterface. "
                            + "Please make sure all names are unique.", name),
                    e);
        }
        return this.withRequiredInterface(infrastructureInterface);
    }

    @Override
    public InfrastructureRequiredRole build() {
        final InfrastructureRequiredRole role = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setRequiredInterface__InfrastructureRequiredRole(requiredInterface);
        return role;
    }

    @Override
    public InfrastructureRequiredRoleCreator withName(final String name) {
        return (InfrastructureRequiredRoleCreator) super.withName(name);
    }

}
