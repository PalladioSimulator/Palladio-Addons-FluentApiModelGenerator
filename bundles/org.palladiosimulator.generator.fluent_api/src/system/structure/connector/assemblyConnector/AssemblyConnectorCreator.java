package system.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;

public class AssemblyConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext requiringContext;
    private OperationRequiredRole requiredRole;
    private AssemblyContext providingContext;
    private OperationProvidedRole providedRole;

    public AssemblyConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public OperationRequiredRoleSelector withRequiringAssemblyContext(final AssemblyContext context) {
        final AssemblyConnectorCreator creator = this;
        return new OperationRequiredRoleSelector(new IContextRequiredRoleCombinator() {

            @Override
            public AssemblyConnectorCreator combineContextAndRequiredRole(final AssemblyContext reqContext,
                    final OperationRequiredRole role) {
                AssemblyConnectorCreator.this.requiringContext = reqContext;
                AssemblyConnectorCreator.this.requiredRole = role;
                return creator;
            }
        }, context);
    }

    public OperationRequiredRoleSelector withRequiringAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withRequiringAssemblyContext(context);
    }

    public OperationProvidedRoleSelector withProvidingAssemblyContext(final AssemblyContext context) {
        final AssemblyConnectorCreator creator = this;
        return new OperationProvidedRoleSelector(new IContextProvidedRoleCombinator() {

            @Override
            public AssemblyConnectorCreator combineContextAndProvidedRole(final AssemblyContext provContext,
                    final OperationProvidedRole role) {
                AssemblyConnectorCreator.this.providingContext = provContext;
                AssemblyConnectorCreator.this.providedRole = role;
                return creator;
            }
        }, context);
    }

    public OperationProvidedRoleSelector withProvidingAssemblyContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContexts()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .get();
        return this.withProvidingAssemblyContext(context);
    }

    @Override
    public AssemblyConnector build() {
        final AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setRequiringAssemblyContext_AssemblyConnector(this.requiringContext);
        connector.setRequiredRole_AssemblyConnector(this.requiredRole);
        connector.setProvidingAssemblyContext_AssemblyConnector(this.providingContext);
        connector.setProvidedRole_AssemblyConnector(this.providedRole);
        return connector;
    }

    @Override
    public AssemblyConnectorCreator withName(final String name) {
        return (AssemblyConnectorCreator) super.withName(name);
    }

}
