package system.structure.connector.infrastructure;

import java.util.NoSuchElementException;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class ProvidedInfrastructureDelegationConnectorCreator extends AbstractConnectorCreator {
    private InfrastructureProvidedRole outerProvidedRole;
    private InfrastructureProvidedRole innerProvidedRole;
    private AssemblyContext providingAssemblyContext;

    public ProvidedInfrastructureDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(
            final InfrastructureProvidedRole role) {
        this.outerProvidedRole = role;
        return this;
    }

    public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(final String name)
            throws NoSuchElementException {
        final InfrastructureProvidedRole role = this.system.getSystemInfrastructureProvidedRoleByName(name);
        return this.withOuterProvidedRole(role);
    }

    public InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator> withProvidingContext(
            final AssemblyContext context) {
        return new InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator>(
                new IContextRoleCombinator<InfrastructureProvidedRole, ProvidedInfrastructureDelegationConnectorCreator>() {

                    @Override
                    public ProvidedInfrastructureDelegationConnectorCreator combineContextAndRole(
                            final AssemblyContext context, final InfrastructureProvidedRole role) {
                        ProvidedInfrastructureDelegationConnectorCreator.this.providingAssemblyContext = context;
                        ProvidedInfrastructureDelegationConnectorCreator.this.innerProvidedRole = role;
                        return ProvidedInfrastructureDelegationConnectorCreator.this;
                    }
                }, context);
    }

    public InfrastructureProvidedRoleSelector<ProvidedInfrastructureDelegationConnectorCreator> withProvidingContext(
            final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withProvidingContext(context);
    }

    @Override
    public ProvidedInfrastructureDelegationConnector build() {
        final ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createProvidedInfrastructureDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext__ProvidedInfrastructureDelegationConnector(this.providingAssemblyContext);
        connector.setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(this.outerProvidedRole);
        connector.setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(this.innerProvidedRole);
        return connector;
    }

    @Override
    public ProvidedInfrastructureDelegationConnectorCreator withName(final String name) {
        return (ProvidedInfrastructureDelegationConnectorCreator) super.withName(name);
    }

}
