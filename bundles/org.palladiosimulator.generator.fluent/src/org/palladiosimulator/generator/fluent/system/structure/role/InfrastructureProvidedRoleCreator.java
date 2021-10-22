package org.palladiosimulator.generator.fluent.system.structure.role;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.SystemEntity;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
 * InfrastructureProvidedRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
 */
public class InfrastructureProvidedRoleCreator extends SystemEntity {

    private InfrastructureInterface providedInterface;

    public InfrastructureProvidedRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} this role provides.
     *
     * @param infrastructureInterface
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureProvidedRoleCreator withProvidedInterface(
            final InfrastructureInterface infrastructureInterface) {
        IllegalArgumentException.throwIfNull(infrastructureInterface, "The given Interface must not be null.");
        this.providedInterface = infrastructureInterface;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} this role provides. Searches the repositories added to the
     * org.palladiosimulator.generator.fluent.system for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureProvidedRoleCreator withProvidedInterface(final String name) throws NoSuchElementException {
        InfrastructureInterface requiredInterface;
        try {
            requiredInterface = (InfrastructureInterface) this.system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an InfrastructureInterface. "
                            + "Please make sure all names are unique.", name),
                    e);
        }
        return this.withProvidedInterface(requiredInterface);
    }

    @Override
    public InfrastructureProvidedRole build() {
        final InfrastructureProvidedRole role = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setProvidedInterface__InfrastructureProvidedRole(this.providedInterface);
        return role;
    }

    @Override
    public InfrastructureProvidedRoleCreator withName(final String name) {
        return (InfrastructureProvidedRoleCreator) super.withName(name);
    }

}
