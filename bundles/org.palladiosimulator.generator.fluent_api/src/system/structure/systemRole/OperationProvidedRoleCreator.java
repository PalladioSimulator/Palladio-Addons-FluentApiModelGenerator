package system.structure.systemRole;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class OperationProvidedRoleCreator extends SystemEntity {

    private OperationInterface providedInterface;

    public OperationProvidedRoleCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public OperationProvidedRoleCreator withProvidedInterface(final OperationInterface operationInterface) {
        this.providedInterface = operationInterface;
        return this;
    }

    public OperationProvidedRoleCreator withProvidedInterface(final String name) {
        final OperationInterface requiredInterface = (OperationInterface) this.system.getRepositories()
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
