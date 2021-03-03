package system.structure.connector.assemblyEventConnector;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class AssemblyEventConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext sourceContext;
    private SourceRole sourceRole;
    private AssemblyContext sinkContext;
    private SinkRole sinkRole;

    public AssemblyEventConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public SourceRoleSelector withSourceAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final var creator = this;
        return new SourceRoleSelector(new IContextSourceRoleCombinator() {

            @Override
            public AssemblyEventConnectorCreator combineContextAndSourceRole(final AssemblyContext context,
                    final SourceRole role) {
                AssemblyEventConnectorCreator.this.sourceContext = context;
                AssemblyEventConnectorCreator.this.sourceRole = role;
                return creator;
            }
        }, context);
    }

    public SourceRoleSelector withSourceAssemblyContext(final String name) {
        final var context = this.system.getAssemblyContextByName(name);
        return this.withSourceAssemblyContext(context);
    }

    public SinkRoleSelector withSinkAssemblyContext(final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final var creator = this;
        return new SinkRoleSelector(new IContextSinkRoleCombinator() {

            @Override
            public AssemblyEventConnectorCreator combineContextAndSinkRole(final AssemblyContext context,
                    final SinkRole role) {
                AssemblyEventConnectorCreator.this.sinkContext = context;
                AssemblyEventConnectorCreator.this.sinkRole = role;
                return creator;
            }
        }, context);
    }

    public SinkRoleSelector withSinkAssemblyContext(final String name) {
        final var context = this.system.getAssemblyContextByName(name);
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
