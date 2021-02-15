package system.structure.systemRole;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class OperationRequiredRoleCreator extends SystemEntity {

    private OperationInterface requiredInterface;

    public OperationRequiredRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public OperationRequiredRoleCreator withRequiredInterface(final OperationInterface operationInterface) {
        this.requiredInterface = operationInterface;
        return this;
    }

    public OperationRequiredRoleCreator withRequiredInterface(final String name) {
        final OperationInterface requiredInterface = (OperationInterface) this.system.getRepositories()
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
    public OperationRequiredRole build() {
        final OperationRequiredRole role = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        if (this.name != null) {
            role.setEntityName(this.name);
        }
        role.setRequiredInterface__OperationRequiredRole(this.requiredInterface);
        return role;
    }

    @Override
    public OperationRequiredRoleCreator withName(final String name) {
        return (OperationRequiredRoleCreator) super.withName(name);
    }

}
