package system.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

public class InfrastructureProvidedRoleSelector {
    private final IContextProvidedRoleCombinator combinator;
    private final AssemblyContext context;

    public InfrastructureProvidedRoleSelector(final IContextProvidedRoleCombinator combinator,
            final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public AssemblyInfrastructureConnectorCreator withInfrastructureProvidedRole(
            final InfrastructureProvidedRole role) {
        return this.combinator.combineContextAndProvidedRole(this.context, role);
    }

    public AssemblyInfrastructureConnectorCreator withInfrastructureProvidedRole(final String name) {
        final InfrastructureProvidedRole role = (InfrastructureProvidedRole) this.context
            .getEncapsulatedComponent__AssemblyContext()
            .getProvidedRoles_InterfaceProvidingEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withInfrastructureProvidedRole(role);
    }
}
