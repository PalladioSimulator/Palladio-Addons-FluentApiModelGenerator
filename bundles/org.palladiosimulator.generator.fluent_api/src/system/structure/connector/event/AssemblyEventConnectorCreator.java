package system.structure.connector.event;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class AssemblyEventConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext sourceContext;
    private SourceRole sourceRole;
    private AssemblyContext sinkContext;
    private SinkRole sinkRole;

    public AssemblyEventConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SourceRoleSelector<AssemblyEventConnectorCreator> withSourceAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SourceRoleSelector<AssemblyEventConnectorCreator>(
                new IContextRoleCombinator<SourceRole, AssemblyEventConnectorCreator>() {

                    @Override
                    public AssemblyEventConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final SourceRole role) {
                        AssemblyEventConnectorCreator.this.sourceContext = context;
                        AssemblyEventConnectorCreator.this.sourceRole = role;
                        return AssemblyEventConnectorCreator.this;
                    }
                }, context);
    }

    public SourceRoleSelector<AssemblyEventConnectorCreator> withSourceAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withSourceAssemblyContext(context);
    }

    public SinkRoleSelector<AssemblyEventConnectorCreator> withSinkAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new SinkRoleSelector<AssemblyEventConnectorCreator>(
                new IContextRoleCombinator<SinkRole, AssemblyEventConnectorCreator>() {

                    @Override
                    public AssemblyEventConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final SinkRole role) {
                        AssemblyEventConnectorCreator.this.sinkContext = context;
                        AssemblyEventConnectorCreator.this.sinkRole = role;
                        return AssemblyEventConnectorCreator.this;
                    }
                }, context);
    }

    public SinkRoleSelector<AssemblyEventConnectorCreator> withSinkAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withSinkAssemblyContext(context);
    }

    @Override
    public AssemblyEventConnector build() {
        final AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setSourceAssemblyContext__AssemblyEventConnector(this.sourceContext);
        connector.setSourceRole__AssemblyEventConnector(this.sourceRole);
        connector.setSinkAssemblyContext__AssemblyEventConnector(this.sinkContext);
        connector.setSinkRole__AssemblyEventConnector(this.sinkRole);
        return connector;
    }

    @Override
    public AssemblyEventConnectorCreator withName(final String name) {
        return (AssemblyEventConnectorCreator) super.withName(name);
    }

}
