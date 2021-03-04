package system.structure.connector.event;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class SinkDelegationConnectorCreator extends AbstractConnectorCreator {
    private SinkRole outerRole;
    private SinkRole innerRole;
    private AssemblyContext assemblyContext;

    public SinkDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SinkDelegationConnectorCreator withOuterSinkRole(final SinkRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRole = role;
        return this;
    }

    public SinkDelegationConnectorCreator withOuterSinkRole(final String name) throws NoSuchElementException {
        final SinkRole role = this.system.getSystemSinkRoleByName(name);
        return this.withOuterSinkRole(role);
    }

    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final SinkDelegationConnectorCreator creator = this;
        return new SinkRoleSelector<SinkDelegationConnectorCreator>(
                new IContextRoleCombinator<SinkRole, SinkDelegationConnectorCreator>() {

                    @Override
                    public SinkDelegationConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final SinkRole role) {
                        SinkDelegationConnectorCreator.this.assemblyContext = context;
                        SinkDelegationConnectorCreator.this.innerRole = role;
                        return creator;
                    }
                }, context);
    }

    public SinkRoleSelector<SinkDelegationConnectorCreator> withAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
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
