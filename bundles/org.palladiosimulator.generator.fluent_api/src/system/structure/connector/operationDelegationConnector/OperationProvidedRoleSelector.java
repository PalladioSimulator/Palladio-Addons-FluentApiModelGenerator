package system.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public class OperationProvidedRoleSelector {
    private final IContextProvidedRoleCombinator combinator;
    private final AssemblyContext context;

    public OperationProvidedRoleSelector(final IContextProvidedRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public ProvidedDelegationConnectorCreator withOpeartionProvidedRole(final OperationProvidedRole role) {
        return this.combinator.combineContextAndProvidedRole(this.context, role);
    }

    public ProvidedDelegationConnectorCreator withOperationProvidedRole(final String name) {
        final OperationProvidedRole role = (OperationProvidedRole) this.context
            .getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOpeartionProvidedRole(role);
    }
}
