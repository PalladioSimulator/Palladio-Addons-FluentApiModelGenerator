package system.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.SourceRole;

public class SourceRoleSelector {
    private final IContextSourceRoleCombinator combinator;
    private final AssemblyContext context;

    public SourceRoleSelector(final IContextSourceRoleCombinator combinator, final AssemblyContext context) {
        this.combinator = combinator;
        this.context = context;
    }

    public SourceDelegationConnectorCreator withSourceRole(final SourceRole role) {
        return this.combinator.combineContextAndSourceRole(this.context, role);
    }

    public SourceDelegationConnectorCreator withSourceRole(final String name) {
        final SourceRole role = (SourceRole) this.context.getEncapsulatedComponent__AssemblyContext()
            .getRequiredRoles_InterfaceRequiringEntity()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withSourceRole(role);
    }
}
