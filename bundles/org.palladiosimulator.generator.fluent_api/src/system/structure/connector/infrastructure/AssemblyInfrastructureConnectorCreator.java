package system.structure.connector.infrastructure;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class AssemblyInfrastructureConnectorCreator extends AbstractConnectorCreator {

    private AssemblyContext requiringContext;
    private InfrastructureRequiredRole requiredRole;
    private AssemblyContext providingContext;
    private InfrastructureProvidedRole providedRole;

    public AssemblyInfrastructureConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator> withRequiringAssemblyContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator>(
                new IContextRoleCombinator<InfrastructureRequiredRole, AssemblyInfrastructureConnectorCreator>() {

                    @Override
                    public AssemblyInfrastructureConnectorCreator combineContextAndRole(
                            final AssemblyContext reqContext, final InfrastructureRequiredRole role) {
                        AssemblyInfrastructureConnectorCreator.this.requiringContext = reqContext;
                        AssemblyInfrastructureConnectorCreator.this.requiredRole = role;
                        return AssemblyInfrastructureConnectorCreator.this;
                    }
                }, context);
    }

    public InfrastructureRequiredRoleSelector<AssemblyInfrastructureConnectorCreator> withRequiringAssemblyContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withRequiringAssemblyContext(context);
    }

    public InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator> withProvidingAssemblyContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        final var creator = this;
        return new InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator>(
                new IContextRoleCombinator<InfrastructureProvidedRole, AssemblyInfrastructureConnectorCreator>() {

                    @Override
                    public AssemblyInfrastructureConnectorCreator combineContextAndRole(
                            final AssemblyContext provContext, final InfrastructureProvidedRole role) {
                        AssemblyInfrastructureConnectorCreator.this.providingContext = provContext;
                        AssemblyInfrastructureConnectorCreator.this.providedRole = role;
                        return creator;
                    }
                }, context);
    }

    public InfrastructureProvidedRoleSelector<AssemblyInfrastructureConnectorCreator> withProvidingAssemblyContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withProvidingAssemblyContext(context);
    }

    @Override
    public AssemblyInfrastructureConnector build() {
        final var connector = CompositionFactory.eINSTANCE.createAssemblyInfrastructureConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(this.requiringContext);
        connector.setRequiredRole__AssemblyInfrastructureConnector(this.requiredRole);
        connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(this.providingContext);
        connector.setProvidedRole__AssemblyInfrastructureConnector(this.providedRole);
        return connector;
    }

    @Override
    public AssemblyInfrastructureConnectorCreator withName(final String name) {
        return (AssemblyInfrastructureConnectorCreator) super.withName(name);
    }

}
