package system.structure.systemRole;

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
        this.providedInterface = infrastructureInterface;
        return this;
    }

    public InfrastructureProvidedRoleCreator withProvidedInterface(final String name) {
        final InfrastructureInterface requiredInterface = (InfrastructureInterface) this.system.getRepositories()
            .stream()
            .flatMap(x -> x.getInterfaces__Repository()
                .stream())
            .filter(i -> i.getEntityName()
                .equals(name))
            .findFirst()
            .get();
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
