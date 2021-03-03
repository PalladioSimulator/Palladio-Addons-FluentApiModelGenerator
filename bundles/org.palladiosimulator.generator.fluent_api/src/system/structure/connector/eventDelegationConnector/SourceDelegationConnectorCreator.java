package system.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class SourceDelegationConnectorCreator extends AbstractConnectorCreator {
    private SourceRole outerRole;
    private SourceRole innerRole;
    private AssemblyContext assemblyContext;

    public SourceDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SourceDelegationConnectorCreator withOuterSourceRole(final SourceRole role) {
        this.outerRole = role;
        return this;
    }

    public SourceDelegationConnectorCreator withOuterSourceRole(final String name) {
        final SourceRole role = this.system.getSystemSourceRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterSourceRole(role);
    }

    public SourceRoleSelector withAssemblyContext(final AssemblyContext context) {
        final SourceDelegationConnectorCreator creator = this;
        return new SourceRoleSelector(new IContextSourceRoleCombinator() {

            @Override
            public SourceDelegationConnectorCreator combineContextAndSourceRole(final AssemblyContext context,
                    final SourceRole role) {
                SourceDelegationConnectorCreator.this.assemblyContext = context;
                SourceDelegationConnectorCreator.this.innerRole = role;
                return creator;
            }
        }, context);
    }

    public SourceRoleSelector withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withAssemblyContext(context);
    }

    @Override
    public SourceDelegationConnector build() {
        final SourceDelegationConnector connector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__SourceDelegationConnector(this.assemblyContext);
        connector.setOuterSourceRole__SourceRole(this.outerRole);
        connector.setInnerSourceRole__SourceRole(this.innerRole);
        return connector;
    }

    @Override
    public SourceDelegationConnectorCreator withName(final String name) {
        return (SourceDelegationConnectorCreator) super.withName(name);
    }

}