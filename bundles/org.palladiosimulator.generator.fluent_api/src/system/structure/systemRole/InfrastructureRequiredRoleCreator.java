package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class InfrastructureRequiredRoleCreator extends SystemEntity {

    private InfrastructureInterface requiredInterface;

    public InfrastructureRequiredRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public InfrastructureRequiredRoleCreator withRequiredInterface(
            final InfrastructureInterface infrastructureInterface) {
        Objects.requireNonNull(infrastructureInterface, "The given Interface must not be null.");
        this.requiredInterface = infrastructureInterface;
        return this;
    }

    public InfrastructureRequiredRoleCreator withRequiredInterface(final String name) throws NoSuchElementException {
        InfrastructureInterface infrastructureInterface;
        try {
            infrastructureInterface = (InfrastructureInterface) this.system.getInterfaceByName(name);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an InfrastructureInterface. "
                            + "Please make sure all names are unique.", name));
        }
        return this.withRequiredInterface(infrastructureInterface);
    }

    @Override
    public InfrastructureRequiredRole build() {
        final InfrastructureRequiredRole role = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setRequiredInterface__InfrastructureRequiredRole(this.requiredInterface);
        return role;
    }

    @Override
    public InfrastructureRequiredRoleCreator withName(final String name) {
        return (InfrastructureRequiredRoleCreator) super.withName(name);
    }

}
