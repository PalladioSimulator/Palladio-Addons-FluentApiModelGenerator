package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

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
        this.system = systemCreator;
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
        Objects.requireNonNull(operationInterface, "The given Interface must not be null.");
        this.providedInterface = operationInterface;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.OperationInterface
     * OperationInterface} this role provides. Searches the repositories added to
     * the system for an interface that matches the given name.
     *
     * @param name
     * @return this role creator
     * @throws NoSuchElementException Thrown if no element matches the given name.
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationProvidedRoleCreator withProvidedInterface(final String name) throws NoSuchElementException {
        OperationInterface requiredInterface;
        try {
            requiredInterface = (OperationInterface) this.system.getInterfaceByName(name);
        } catch (final ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an OperationInterface. "
                            + "Please make sure all names are unique.", name));
        }
        return this.withProvidedInterface(requiredInterface);
    }

    @Override
    public OperationProvidedRole build() {
        final OperationProvidedRole role = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setProvidedInterface__OperationProvidedRole(this.providedInterface);
        return role;
    }

    @Override
    public OperationProvidedRoleCreator withName(final String name) {
        return (OperationProvidedRoleCreator) super.withName(name);
    }

}
