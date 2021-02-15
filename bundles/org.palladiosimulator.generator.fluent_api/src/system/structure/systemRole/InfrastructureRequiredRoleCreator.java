package system.structure.systemRole;

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
        this.requiredInterface = infrastructureInterface;
        return this;
    }

    public InfrastructureRequiredRoleCreator withRequiredInterface(final String name) {
        final InfrastructureInterface requiredInterface = (InfrastructureInterface) this.system.getRepositories()
            .stream()
            .flatMap(x -> x.getInterfaces__Repository()
                .stream())
            .filter(i -> i.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withRequiredInterface(requiredInterface);
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
