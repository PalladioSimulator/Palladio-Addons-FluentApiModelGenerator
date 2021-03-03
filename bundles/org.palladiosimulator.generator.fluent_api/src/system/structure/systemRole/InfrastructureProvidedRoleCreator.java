package system.structure.systemRole;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class InfrastructureProvidedRoleCreator extends SystemEntity {

    private InfrastructureInterface providedInterface;

    public InfrastructureProvidedRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public InfrastructureProvidedRoleCreator withProvidedInterface(
            final InfrastructureInterface infrastructureInterface) {
        Objects.requireNonNull(infrastructureInterface, "The given Interface must not be null.");
        this.providedInterface = infrastructureInterface;
        return this;
    }

    public InfrastructureProvidedRoleCreator withProvidedInterface(final String name) throws NoSuchElementException {
        InfrastructureInterface requiredInterface;
        try {
            requiredInterface = (InfrastructureInterface) this.system.getInterfaceByName(name);
        } catch (ClassCastException e) {
            throw new NoSuchElementException(
                    String.format("An Interface with name '%s' was found, but it was not an InfrastructureInterface. "
                            + "Please make sure all names are unique.", name));
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
