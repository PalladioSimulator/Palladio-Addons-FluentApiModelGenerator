package org.palladiosimulator.generator.fluent.system.structure.role;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.SystemEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
 * OperationProvidedRole}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
 */
public class OperationProvidedRoleCreator extends SystemEntity {

    private OperationInterface providedInterface;

    public OperationProvidedRoleCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationInterface
     * OperationInterface} this role provides.
     *
     * @param operationInterface
     * @return this role creator
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationProvidedRoleCreator withProvidedInterface(final OperationInterface operationInterface) {
        IllegalArgumentException.throwIfNull(operationInterface, "The given Interface must not be null.");
        providedInterface = operationInterface;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationInterface
     * OperationInterface} this role provides. Searches the repositories added to
     * the org.palladiosimulator.generator.fluent.system for an interface that
     * matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationProvidedRoleCreator withProvidedInterface(final String name) throws NoSuchElementException {
        OperationInterface requiredInterface;
        try {
            requiredInterface = (OperationInterface) system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an OperationInterface. "
                            + "Please make sure all names are unique.", name),
                    e);
        }
        return this.withProvidedInterface(requiredInterface);
    }

    @Override
    public OperationProvidedRole build() {
        final OperationProvidedRole role = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        if (name != null) {
            role.setEntityName(name);
        }
        role.setProvidedInterface__OperationProvidedRole(providedInterface);
        return role;
    }

    @Override
    public OperationProvidedRoleCreator withName(final String name) {
        return (OperationProvidedRoleCreator) super.withName(name);
    }

}
