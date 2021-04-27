package org.palladiosimulator.generator.fluent.component.repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.EventGroupCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.CompositeComponent
 * CompositeComponent}. It is used to create the
 * '<em><b>CompositeComponent</b></em>' object step-by-step, i.e.
 * '<em><b>CompositeComponentCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.CompositeComponent
 */
public class CompositeComponentCreator extends ComplexComponent {

    private ComponentType type;
    private final List<CompleteComponentType> conformsCompleteTypes;
    private final List<VariableUsage> componentParameterUsages;

    public CompositeComponentCreator(final RepositoryCreator repo) {
        repository = repo;
        conformsCompleteTypes = new ArrayList<>();
        componentParameterUsages = new ArrayList<>();
    }

    @Override
    public CompositeComponentCreator withName(final String name) {
        return (CompositeComponentCreator) super.withName(name);
    }

    // @Override
    // public CompositeComponentCreator withId(String id) {
    // return (CompositeComponentCreator) super.withId(id);
    // }

    /**
     * Sets the type of the composite component.<br>
     * <br>
     * Possible values are '<em><b>BUSINESS_COMPONENT</b></em>' (default) and
     * '<em><b>INFRASTRUCTURE_COMPONENT</b></em>'.
     *
     * @param type
     * @return the composite component in the making
     */
    public CompositeComponentCreator ofType(final ComponentType type) {
        Objects.requireNonNull(type, "type must not be null");
        this.type = type;
        return this;
    }

    // ------------ providing roles ------------
    // provides operation interface
    @Override
    public CompositeComponentCreator provides(final OperationInterfaceCreator interfce) {
        return (CompositeComponentCreator) super.provides(interfce);
    }

    @Override
    public CompositeComponentCreator provides(final OperationInterfaceCreator interfce, final String name) {
        return (CompositeComponentCreator) super.provides(interfce, name);
    }

    @Override
    public CompositeComponentCreator provides(final OperationInterface interfce) {
        return (CompositeComponentCreator) super.provides(interfce);

    }

    @Override
    public CompositeComponentCreator provides(final OperationInterface interfce, final String name) {
        return (CompositeComponentCreator) super.provides(interfce, name);
    }

    // provides infrastructure interface
    @Override
    public CompositeComponentCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (CompositeComponentCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public CompositeComponentCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (CompositeComponentCreator) super.providesInfrastructure(interfce, name);
    }

    @Override
    public CompositeComponentCreator providesInfrastructure(final InfrastructureInterface interfce) {
        return (CompositeComponentCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public CompositeComponentCreator providesInfrastructure(final InfrastructureInterface interfce, final String name) {
        return (CompositeComponentCreator) super.providesInfrastructure(interfce, name);
    }

    // sink role: handles an event group
    @Override
    public CompositeComponentCreator handles(final EventGroupCreator eventGroup) {
        return (CompositeComponentCreator) super.handles(eventGroup);
    }

    @Override
    public CompositeComponentCreator handles(final EventGroupCreator eventGroup, final String name) {
        return (CompositeComponentCreator) super.handles(eventGroup, name);
    }

    @Override
    public CompositeComponentCreator handles(final EventGroup eventGroup) {
        return (CompositeComponentCreator) super.handles(eventGroup);
    }

    @Override
    public CompositeComponentCreator handles(final EventGroup eventGroup, final String name) {
        return (CompositeComponentCreator) super.handles(eventGroup, name);
    }

    // ------------ requiring roles ------------
    // require operation interface
    @Override
    public CompositeComponentCreator requires(final OperationInterfaceCreator interfce) {
        return (CompositeComponentCreator) super.requires(interfce);
    }

    @Override
    public CompositeComponentCreator requires(final OperationInterfaceCreator interfce, final String name) {
        return (CompositeComponentCreator) super.requires(interfce, name);
    }

    @Override
    public CompositeComponentCreator requires(final OperationInterface interfce) {
        return (CompositeComponentCreator) super.requires(interfce);
    }

    @Override
    public CompositeComponentCreator requires(final OperationInterface interfce, final String name) {
        return (CompositeComponentCreator) super.requires(interfce, name);
    }

    // require infrastructure interface
    @Override
    public CompositeComponentCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (CompositeComponentCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public CompositeComponentCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (CompositeComponentCreator) super.requiresInfrastructure(interfce, name);
    }

    @Override
    public CompositeComponentCreator requiresInfrastructure(final InfrastructureInterface interfce) {
        return (CompositeComponentCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public CompositeComponentCreator requiresInfrastructure(final InfrastructureInterface interfce, final String name) {
        return (CompositeComponentCreator) super.requiresInfrastructure(interfce, name);
    }

    // emits event group (source role)
    @Override
    public CompositeComponentCreator emits(final EventGroupCreator eventGroup) {
        return (CompositeComponentCreator) super.emits(eventGroup);
    }

    @Override
    public CompositeComponentCreator emits(final EventGroupCreator eventGroup, final String name) {
        return (CompositeComponentCreator) super.emits(eventGroup, name);
    }

    @Override
    public CompositeComponentCreator emits(final EventGroup eventGroup) {
        return (CompositeComponentCreator) super.emits(eventGroup);
    }

    @Override
    public CompositeComponentCreator emits(final EventGroup eventGroup, final String name) {
        return (CompositeComponentCreator) super.emits(eventGroup, name);
    }

    // resource required role
    @Override
    public CompositeComponentCreator requiresResource(final ResourceInterface resourceInterface) {
        return (CompositeComponentCreator) super.requiresResource(resourceInterface);
    }

    @Override
    public CompositeComponentCreator requiresResource(final ResourceInterface resourceInterface, final String name) {
        return (CompositeComponentCreator) super.requiresResource(resourceInterface, name);
    }

    // ------------ other listing characteristics ------------
    /**
     * Creates a conforming (parental) connection to the
     * <code>completeComponentType</code> and adds it to the composite component.
     * <p>
     * Complete (Component) types abstract from the realization of components. They
     * only contain provided and required roles omitting the components’ internal
     * structure, i.e., the service effect specifications or assemblies.
     * </p>
     * <p>
     * The <code>completeComponentType</code> can be created using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newCompleteComponentType()</code>.
     * </p>
     *
     * @param completeComponentType
     * @return the composite component in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#newCompleteComponentType()
     * @see org.palladiosimulator.pcm.repository.CompositeComponent#getParentCompleteComponentTypes()
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType
     */
    public CompositeComponentCreator conforms(final CompleteComponentTypeCreator completeComponentType) {
        Objects.requireNonNull(completeComponentType, "completeComponentType must not be null");
        final CompleteComponentType cct = completeComponentType.build();
        repository.addComponent(cct);
        return this.conforms(cct);
    }

    /**
     * Creates a conforming (parental) connection to the
     * <code>completeComponentType</code> and adds it to the composite component.
     * <p>
     * Complete (Component) types abstract from the realization of components. They
     * only contain provided and required roles omitting the components’ internal
     * structure, i.e., the service effect specifications or assemblies.
     * </p>
     * <p>
     * An existing <code>completeComponentType</code> can be fetched from the
     * repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfCompleteComponentType(name)</code>.
     * </p>
     *
     * @param completeComponentType
     * @return the composite component in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#fetchOfCompleteComponentType(String)
     * @see org.palladiosimulator.pcm.repository.CompositeComponent#getParentCompleteComponentTypes()
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType
     */
    public CompositeComponentCreator conforms(final CompleteComponentType completeComponentType) {
        Objects.requireNonNull(completeComponentType, "completeComponentType must not be null");
        conformsCompleteTypes.add(completeComponentType);
        return this;
    }

    /**
     * Adds a {@link org.palladiosimulator.pcm.parameter.VariableUsage
     * VariableUsage} to the composite component.
     * <p>
     * Variable usages are used to characterize variables like input and output
     * variables or component parameters. They contain the specification of the
     * variable as VariableCharacterisation and also refer to the name of the
     * characterized variable in its namedReference association.
     * </p>
     * <p>
     * Create a new variable usage by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newVariableUsage()</code>.
     * </p>
     *
     * @param variableUsage in the making
     * @return the composite component in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#newVariableUsage()
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public CompositeComponentCreator withVariableUsage(final VariableUsageCreator variableUsage) {
        Objects.requireNonNull(variableUsage, "variableUsage must not be null");
        componentParameterUsages.add(variableUsage.build());
        return this;
    }

    @Override
    public CompositeComponentCreator withAssemblyContext(final RepositoryComponent component, final String name,
            final VariableUsageCreator... configParameterUsage) {
        return (CompositeComponentCreator) super.withAssemblyContext(component, name, configParameterUsage);
    }

    @Override
    public CompositeComponentCreator withAssemblyContext(final RepositoryComponent component,
            final VariableUsageCreator... configParameterUsage) {
        return (CompositeComponentCreator) super.withAssemblyContext(component, configParameterUsage);
    }

    @Override
    public CompositeComponentCreator withEventChannel(final EventGroup eventGroup, final String name) {
        return (CompositeComponentCreator) super.withEventChannel(eventGroup, name);
    }

    @Override
    public CompositeComponentCreator withEventChannel(final EventGroup eventGroup) {
        return (CompositeComponentCreator) super.withEventChannel(eventGroup);
    }

    // ------------ connectors ------------
    @Override
    public CompositeComponentCreator withAssemblyConnection(final OperationProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final OperationRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        return (CompositeComponentCreator) super.withAssemblyConnection(providedRole, providingAssemblyContext,
                requiredRole, requiringAssemblyContext);
    }

    @Override
    public CompositeComponentCreator withProvidedDelegationConnection(final AssemblyContext assemblyContext,
            final OperationProvidedRole innerProvidedRole, final OperationProvidedRole outerProvidedRole) {
        return (CompositeComponentCreator) super.withProvidedDelegationConnection(assemblyContext, innerProvidedRole,
                outerProvidedRole);
    }

    @Override
    public CompositeComponentCreator withRequiredDelegationConnection(final AssemblyContext assemblyContext,
            final OperationRequiredRole innerRequiredRole, final OperationRequiredRole outerRequiredRole) {
        return (CompositeComponentCreator) super.withRequiredDelegationConnection(assemblyContext, innerRequiredRole,
                outerRequiredRole);
    }

    @Override
    public CompositeComponentCreator withAssemblyEventConnection(final SinkRole sinkRole,
            final AssemblyContext sinkAssemblyContext, final SourceRole sourceRole,
            final AssemblyContext sourceAssemblyContext, final String filterConditionStochasticExpression) {
        return (CompositeComponentCreator) super.withAssemblyEventConnection(sinkRole, sinkAssemblyContext, sourceRole,
                sourceAssemblyContext, filterConditionStochasticExpression);
    }

    @Override
    public CompositeComponentCreator withEventChannelSinkConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SinkRole sinkRole,
            final String filterConditionStochasticExpression) {
        return (CompositeComponentCreator) super.withEventChannelSinkConnection(assemblyContext, eventChannel, sinkRole,
                filterConditionStochasticExpression);
    }

    @Override
    public CompositeComponentCreator withEventChannelSourceConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SourceRole sourceRole) {
        return (CompositeComponentCreator) super.withEventChannelSourceConnection(assemblyContext, eventChannel,
                sourceRole);
    }

    @Override
    public CompositeComponentCreator withSinkDelegationConnection(final AssemblyContext assemblyContext,
            final SinkRole innerSinkRole, final SinkRole outerSinkRole) {
        return (CompositeComponentCreator) super.withSinkDelegationConnection(assemblyContext, innerSinkRole,
                outerSinkRole);
    }

    @Override
    public CompositeComponentCreator withSourceDelegationConnection(final AssemblyContext assemblyContext,
            final SourceRole innerSourceRole, final SourceRole outerSourceRole) {
        return (CompositeComponentCreator) super.withSourceDelegationConnection(assemblyContext, innerSourceRole,
                outerSourceRole);
    }

    // ------------ infrastructure role connectors ------------
    @Override
    public CompositeComponentCreator withAssemblyInfrastructureConnection(final InfrastructureProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final InfrastructureRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        return (CompositeComponentCreator) super.withAssemblyInfrastructureConnection(providedRole,
                providingAssemblyContext, requiredRole, requiringAssemblyContext);
    }

    @Override
    public CompositeComponentCreator withProvidedInfrastructureDelegationConnection(
            final AssemblyContext assemblyContext, final InfrastructureProvidedRole innerProvidedRole,
            final InfrastructureProvidedRole outerProvidedRole) {
        return (CompositeComponentCreator) super.withProvidedInfrastructureDelegationConnection(assemblyContext,
                innerProvidedRole, outerProvidedRole);
    }

    @Override
    public CompositeComponentCreator withRequiredInfrastructureDelegationConnection(
            final AssemblyContext assemblyContext, final InfrastructureRequiredRole innerRequiredRole,
            final InfrastructureRequiredRole outerRequiredRole) {
        return (CompositeComponentCreator) super.withRequiredInfrastructureDelegationConnection(assemblyContext,
                innerRequiredRole, outerRequiredRole);
    }

    @Override
    public CompositeComponentCreator withRequiredResourceDelegationConnection(final AssemblyContext assemblyContext,
            final ResourceRequiredRole innerRequiredRole, final ResourceRequiredRole outerRequiredRole) {
        return (CompositeComponentCreator) super.withRequiredResourceDelegationConnection(assemblyContext,
                innerRequiredRole, outerRequiredRole);
    }

    @Override
    public CompositeComponentCreator resourceRequiredDegelationConnection(final ResourceRequiredRole innerRequiredRole,
            final ResourceRequiredRole outerRequiredRole) {
        return (CompositeComponentCreator) super.resourceRequiredDegelationConnection(innerRequiredRole,
                outerRequiredRole);
    }

    @Override
    public RepositoryComponent build() {
        final CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
        if (name != null) {
            compositeComponent.setEntityName(name);
        }
        // if (id != null)
        // compositeComponent.setId(id);
        if (type != null) {
            compositeComponent.setComponentType(type);
        }

        compositeComponent.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
        compositeComponent.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
        compositeComponent.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

        compositeComponent.getParentCompleteComponentTypes().addAll(conformsCompleteTypes);
        compositeComponent.getComponentParameterUsage_ImplementationComponentType().addAll(componentParameterUsages);

        compositeComponent.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
        compositeComponent.getConnectors__ComposedStructure().addAll(connectors);
        compositeComponent.getEventChannel__ComposedStructure().addAll(eventChannels);
        compositeComponent.getResourceRequiredDelegationConnectors_ComposedStructure()
                .addAll(resourceRequiredDelegationConnectors);

        return compositeComponent;
    }

}
