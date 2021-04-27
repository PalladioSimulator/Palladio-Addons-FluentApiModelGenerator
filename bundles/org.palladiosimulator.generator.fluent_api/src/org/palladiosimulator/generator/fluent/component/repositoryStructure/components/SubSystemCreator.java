package org.palladiosimulator.generator.fluent.component.repositoryStructure.components;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.EventGroupCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.subsystem.SubsystemFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.subsystem.SubSystem
 * SubSystem}. It is used to create the '<em><b>SubSystem</b></em>' object
 * step-by-step, i.e. '<em><b>SubSystemCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.subsystem.SubSystem
 */
public class SubSystemCreator extends ComplexComponent {

    public SubSystemCreator(final RepositoryCreator repo) {
        repository = repo;
    }

    @Override
    public SubSystemCreator withName(final String name) {
        return (SubSystemCreator) super.withName(name);
    }

    // @Override
    // public SubSystemCreator withId(String id) {
    // return (SubSystemCreator) super.withId(id);
    // }

    // ------------ providing roles ------------
    // provides operation interface
    @Override
    public SubSystemCreator provides(final OperationInterfaceCreator interfce) {
        return (SubSystemCreator) super.provides(interfce);
    }

    @Override
    public SubSystemCreator provides(final OperationInterfaceCreator interfce, final String name) {
        return (SubSystemCreator) super.provides(interfce, name);
    }

    @Override
    public SubSystemCreator provides(final OperationInterface interfce) {
        return (SubSystemCreator) super.provides(interfce);
    }

    @Override
    public SubSystemCreator provides(final OperationInterface interfce, final String name) {
        return (SubSystemCreator) super.provides(interfce, name);
    }

    // provides infrastructure interface
    @Override
    public SubSystemCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (SubSystemCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public SubSystemCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce, final String name) {
        return (SubSystemCreator) super.providesInfrastructure(interfce, name);
    }

    @Override
    public SubSystemCreator providesInfrastructure(final InfrastructureInterface interfce) {
        return (SubSystemCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public SubSystemCreator providesInfrastructure(final InfrastructureInterface interfce, final String name) {
        return (SubSystemCreator) super.providesInfrastructure(interfce, name);
    }

    // sink role: handles an event group
    @Override
    public SubSystemCreator handles(final EventGroupCreator eventGroup) {
        return (SubSystemCreator) super.handles(eventGroup);
    }

    @Override
    public SubSystemCreator handles(final EventGroupCreator eventGroup, final String name) {
        return (SubSystemCreator) super.handles(eventGroup, name);
    }

    @Override
    public SubSystemCreator handles(final EventGroup eventGroup) {
        return (SubSystemCreator) super.handles(eventGroup);
    }

    @Override
    public SubSystemCreator handles(final EventGroup eventGroup, final String name) {
        return (SubSystemCreator) super.handles(eventGroup, name);
    }

    // ------------ requiring roles ------------
    // require operation interface
    @Override
    public SubSystemCreator requires(final OperationInterfaceCreator interfce) {
        return (SubSystemCreator) super.requires(interfce);
    }

    @Override
    public SubSystemCreator requires(final OperationInterfaceCreator interfce, final String name) {
        return (SubSystemCreator) super.requires(interfce, name);
    }

    @Override
    public SubSystemCreator requires(final OperationInterface interfce) {
        return (SubSystemCreator) super.requires(interfce);
    }

    @Override
    public SubSystemCreator requires(final OperationInterface interfce, final String name) {
        return (SubSystemCreator) super.requires(interfce, name);
    }

    // require infrastructure interface
    @Override
    public SubSystemCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (SubSystemCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public SubSystemCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce, final String name) {
        return (SubSystemCreator) super.requiresInfrastructure(interfce, name);
    }

    @Override
    public SubSystemCreator requiresInfrastructure(final InfrastructureInterface interfce) {
        return (SubSystemCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public SubSystemCreator requiresInfrastructure(final InfrastructureInterface interfce, final String name) {
        return (SubSystemCreator) super.requiresInfrastructure(interfce, name);
    }

    // emits event group (source role)
    @Override
    public SubSystemCreator emits(final EventGroupCreator eventGroup) {
        return (SubSystemCreator) super.emits(eventGroup);
    }

    @Override
    public SubSystemCreator emits(final EventGroupCreator eventGroup, final String name) {
        return (SubSystemCreator) super.emits(eventGroup, name);
    }

    @Override
    public SubSystemCreator emits(final EventGroup eventGroup) {
        return (SubSystemCreator) super.emits(eventGroup);
    }

    @Override
    public SubSystemCreator emits(final EventGroup eventGroup, final String name) {
        return (SubSystemCreator) super.emits(eventGroup, name);
    }

    // resource required role
    @Override
    public SubSystemCreator requiresResource(final ResourceInterface resourceInterface) {
        return (SubSystemCreator) super.requiresResource(resourceInterface);
    }

    @Override
    public SubSystemCreator requiresResource(final ResourceInterface resourceInterface, final String name) {
        return (SubSystemCreator) super.requiresResource(resourceInterface, name);
    }

    // ------------ other listing characteristics ------------
    @Override
    public SubSystemCreator withAssemblyContext(final RepositoryComponent component, final String name,
            final VariableUsageCreator... configParameterUsage) {
        return (SubSystemCreator) super.withAssemblyContext(component, name, configParameterUsage);
    }

    @Override
    public SubSystemCreator withAssemblyContext(final RepositoryComponent component,
            final VariableUsageCreator... configParameterUsage) {
        return (SubSystemCreator) super.withAssemblyContext(component, configParameterUsage);
    }

    @Override
    public SubSystemCreator withEventChannel(final EventGroup eventGroup, final String name) {
        return (SubSystemCreator) super.withEventChannel(eventGroup, name);
    }

    @Override
    public SubSystemCreator withEventChannel(final EventGroup eventGroup) {
        return (SubSystemCreator) super.withEventChannel(eventGroup);
    }

    // ------------ connectors ------------
    @Override
    public SubSystemCreator withAssemblyConnection(final OperationProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final OperationRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        return (SubSystemCreator) super.withAssemblyConnection(providedRole, providingAssemblyContext, requiredRole,
                requiringAssemblyContext);
    }

    @Override
    public SubSystemCreator withProvidedDelegationConnection(final AssemblyContext assemblyContext,
            final OperationProvidedRole innerProvidedRole, final OperationProvidedRole outerProvidedRole) {
        return (SubSystemCreator) super.withProvidedDelegationConnection(assemblyContext, innerProvidedRole,
                outerProvidedRole);
    }

    @Override
    public SubSystemCreator withRequiredDelegationConnection(final AssemblyContext assemblyContext,
            final OperationRequiredRole innerRequiredRole, final OperationRequiredRole outerRequiredRole) {
        return (SubSystemCreator) super.withRequiredDelegationConnection(assemblyContext, innerRequiredRole,
                outerRequiredRole);
    }

    @Override
    public SubSystemCreator withAssemblyEventConnection(final SinkRole sinkRole,
            final AssemblyContext sinkAssemblyContext, final SourceRole sourceRole,
            final AssemblyContext sourceAssemblyContext, final String filterConditionStochasticExpression) {
        return (SubSystemCreator) super.withAssemblyEventConnection(sinkRole, sinkAssemblyContext, sourceRole,
                sourceAssemblyContext, filterConditionStochasticExpression);
    }

    @Override
    public SubSystemCreator withEventChannelSinkConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SinkRole sinkRole,
            final String filterConditionStochasticExpression) {
        return (SubSystemCreator) super.withEventChannelSinkConnection(assemblyContext, eventChannel, sinkRole,
                filterConditionStochasticExpression);
    }

    @Override
    public SubSystemCreator withEventChannelSourceConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SourceRole sourceRole) {
        return (SubSystemCreator) super.withEventChannelSourceConnection(assemblyContext, eventChannel, sourceRole);
    }

    @Override
    public SubSystemCreator withSinkDelegationConnection(final AssemblyContext assemblyContext,
            final SinkRole innerSinkRole, final SinkRole outerSinkRole) {
        return (SubSystemCreator) super.withSinkDelegationConnection(assemblyContext, innerSinkRole, outerSinkRole);
    }

    @Override
    public SubSystemCreator withSourceDelegationConnection(final AssemblyContext assemblyContext,
            final SourceRole innerSourceRole, final SourceRole outerSourceRole) {
        return (SubSystemCreator) super.withSourceDelegationConnection(assemblyContext, innerSourceRole,
                outerSourceRole);
    }

    @Override
    public SubSystemCreator withAssemblyInfrastructureConnection(final InfrastructureProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final InfrastructureRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        return (SubSystemCreator) super.withAssemblyInfrastructureConnection(providedRole, providingAssemblyContext,
                requiredRole, requiringAssemblyContext);
    }

    @Override
    public SubSystemCreator withProvidedInfrastructureDelegationConnection(final AssemblyContext assemblyContext,
            final InfrastructureProvidedRole innerProvidedRole, final InfrastructureProvidedRole outerProvidedRole) {
        return (SubSystemCreator) super.withProvidedInfrastructureDelegationConnection(assemblyContext,
                innerProvidedRole, outerProvidedRole);
    }

    @Override
    public SubSystemCreator withRequiredInfrastructureDelegationConnection(final AssemblyContext assemblyContext,
            final InfrastructureRequiredRole innerRequiredRole, final InfrastructureRequiredRole outerRequiredRole) {
        return (SubSystemCreator) super.withRequiredInfrastructureDelegationConnection(assemblyContext,
                innerRequiredRole, outerRequiredRole);
    }

    @Override
    public SubSystemCreator withRequiredResourceDelegationConnection(final AssemblyContext assemblyContext,
            final ResourceRequiredRole innerRequiredRole, final ResourceRequiredRole outerRequiredRole) {
        return (SubSystemCreator) super.withRequiredResourceDelegationConnection(assemblyContext, innerRequiredRole,
                outerRequiredRole);
    }

    @Override
    public SubSystemCreator resourceRequiredDegelationConnection(final ResourceRequiredRole innerRequiredRole,
            final ResourceRequiredRole outerRequiredRole) {
        return (SubSystemCreator) super.resourceRequiredDegelationConnection(innerRequiredRole, outerRequiredRole);
    }

    @Override
    public SubSystem build() {
        final SubSystem subSystem = SubsystemFactory.eINSTANCE.createSubSystem();
        if (name != null) {
            subSystem.setEntityName(name);
            // if (this.id != null)
            // subSystem.setId(this.id);
        }

        subSystem.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
        subSystem.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
        subSystem.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

        subSystem.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
        subSystem.getConnectors__ComposedStructure().addAll(connectors);
        subSystem.getEventChannel__ComposedStructure().addAll(eventChannels);
        subSystem.getResourceRequiredDelegationConnectors_ComposedStructure()
                .addAll(resourceRequiredDelegationConnectors);

        return subSystem;
    }

}
