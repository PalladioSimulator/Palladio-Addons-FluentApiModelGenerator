package system.structure.connector.operation;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import system.structure.SystemCreator;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.IContextRoleCombinator;

public class RequiredDelegationConnectorCreator extends AbstractConnectorCreator {
    private OperationRequiredRole outerRequiredRole;
    private OperationRequiredRole innerRequiredRole;
    private AssemblyContext requringAssemblyContext;

    public RequiredDelegationConnectorCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public RequiredDelegationConnectorCreator withOuterRequiredRole(final OperationRequiredRole role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.outerRequiredRole = role;
        return this;
    }

    public RequiredDelegationConnectorCreator withOuterRequiredRole(final String name) throws NoSuchElementException {
        final OperationRequiredRole role = this.system.getSystemOperationRequiredRoleByName(name);
        return this.withOuterRequiredRole(role);
    }

    public OperationRequiredRoleSelector<RequiredDelegationConnectorCreator> withRequiringContext(
            final AssemblyContext context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        return new OperationRequiredRoleSelector<RequiredDelegationConnectorCreator>(
                new IContextRoleCombinator<OperationRequiredRole, RequiredDelegationConnectorCreator>() {

                    @Override
                    public RequiredDelegationConnectorCreator combineContextAndRole(final AssemblyContext context,
                            final OperationRequiredRole role) {
                        RequiredDelegationConnectorCreator.this.requringAssemblyContext = context;
                        RequiredDelegationConnectorCreator.this.innerRequiredRole = role;
                        return RequiredDelegationConnectorCreator.this;
                    }
                }, context);
    }

    public OperationRequiredRoleSelector<RequiredDelegationConnectorCreator> withRequiringContext(final String name) {
        final AssemblyContext context = this.system.getAssemblyContextByName(name);
        return this.withRequiringContext(context);
    }

    @Override
    public RequiredDelegationConnector build() {
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        if (this.name != null) {
            connector.setEntityName(this.name);
        }
        connector.setAssemblyContext_RequiredDelegationConnector(this.requringAssemblyContext);
        connector.setOuterRequiredRole_RequiredDelegationConnector(this.outerRequiredRole);
        connector.setInnerRequiredRole_RequiredDelegationConnector(this.innerRequiredRole);
        return connector;
    }

    @Override
    public RequiredDelegationConnectorCreator withName(final String name) {
        return (RequiredDelegationConnectorCreator) super.withName(name);
    }

}
