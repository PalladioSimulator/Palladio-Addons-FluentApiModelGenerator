package system.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class SinkDelegationConnectorCreator extends AbstractConnectorCreator {
    private SinkRole outerRole;
    private SinkRole innerRole;
    private AssemblyContext assemblyContext;

    public SinkDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SinkDelegationConnectorCreator withOuterSinkRole(final SinkRole role) {
        this.outerRole = role;
        return this;
    }

    public SinkDelegationConnectorCreator withOuterSinkRole(final String name) {
        final SinkRole role = this.system.getSystemSinkRoles()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withOuterSinkRole(role);
    }

    public SinkRoleSelector withAssemblyContext(final AssemblyContext context) {
        final SinkDelegationConnectorCreator creator = this;
        return new SinkRoleSelector(new IContextSinkRoleCombinator() {

            @Override
            public SinkDelegationConnectorCreator combineContextAndSinkRole(final AssemblyContext context,
                    final SinkRole role) {
                SinkDelegationConnectorCreator.this.assemblyContext = context;
                SinkDelegationConnectorCreator.this.innerRole = role;
                return creator;
            }
        }, context);
    }

    public SinkRoleSelector withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withAssemblyContext(context);
    }

    @Override
    public SinkDelegationConnector build() {
        final SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__SinkDelegationConnector(this.assemblyContext);
        connector.setOuterSinkRole__SinkRole(this.outerRole);
        connector.setInnerSinkRole__SinkRole(this.innerRole);
        return connector;
    }

    @Override
    public SinkDelegationConnectorCreator withName(final String name) {
        return (SinkDelegationConnectorCreator) super.withName(name);
    }

}
