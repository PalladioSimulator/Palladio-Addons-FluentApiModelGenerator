package org.palladiosimulator.generator.fluent.repository.structure.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

/**
 * This class provides the general infrastructure of a compositional component, i.e.
 * CompositeComponent and SubSystem. It provides the implementation of the methods for creating
 * assembly contexts, connectors and event channels.
 *
 * @author Louisa Lambrecht
 */
public abstract class ComplexComponent extends Component {

    protected List<AssemblyContext> assemblyContexts = new ArrayList<>();
    protected List<Connector> connectors = new ArrayList<>();
    protected List<EventChannel> eventChannels = new ArrayList<>();
    protected List<ResourceRequiredDelegationConnector> resourceRequiredDelegationConnectors = new ArrayList<>();



    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}
     * with the name <code>name</code> and optionally many <code>configParameterUsages</code> and
     * adds it to the component.
     * <p>
     * An existing {@link org.palladiosimulator.pcm.repository.RepositoryComponent
     * RepositoryComponent} can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfComponent(name)</code>.
     * </p>
     *
     * @param encapsulatedComponent
     * @param name
     * @param configParameterUsages
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfComponent(String)
     */
    public ComplexComponent withAssemblyContext(final RepositoryComponent encapsulatedComponent, final String name,
            final VariableUsageCreator... configParameterUsages) {
        // Null checks
        IllegalArgumentException.throwIfNull(encapsulatedComponent, "encapsulatedComponent must not be null");
        IllegalArgumentException.throwIfNull(configParameterUsages, "config parameter usages must not be null");
        if (configParameterUsages.length > 0) {
            for (final VariableUsageCreator configParameterUsage : configParameterUsages) {
                IllegalArgumentException.throwIfNull(configParameterUsage, "config parameter usages must not be null");
            }
        }

        final AssemblyContext ac = CompositionFactory.eINSTANCE.createAssemblyContext();
        if (name != null) {
            ac.setEntityName(name);
        }
        ac.setEncapsulatedComponent__AssemblyContext(encapsulatedComponent);
        Arrays.asList(configParameterUsages)
            .stream()
            .map(VariableUsageCreator::build)
            .forEach(v -> ac.getConfigParameterUsages__AssemblyContext()
                .add(v));
        this.assemblyContexts.add(ac);
        this.repository.addAssemblyContext(ac);

        return this;
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext AssemblyContext}
     * and optionally many <code>configParameterUsages</code> and adds it to the component.
     * <p>
     * An existing {@link org.palladiosimulator.pcm.repository.RepositoryComponent
     * RepositoryComponent} can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfComponent(name)</code>.
     * </p>
     *
     * @param encapsulatedComponent
     * @param name
     * @param configParameterUsages
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfComponent(String)
     */
    public ComplexComponent withAssemblyContext(final RepositoryComponent encapsulatedComponent,
            final VariableUsageCreator... configParameterUsages) {
        return this.withAssemblyContext(encapsulatedComponent, null, configParameterUsages);
    }

    /**
     * Creates a new {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel}
     * with name <code>name</code>.
     * <p>
     * Event channels consist of an {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} and arbitrarily many
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}s and
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}s that are added when creating the connectors.
     * </p>
     *
     * @param eventGroup
     * @param name
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public ComplexComponent withEventChannel(final EventGroup eventGroup, final String name) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        final EventChannel eventChannel = CompositionFactory.eINSTANCE.createEventChannel();
        if (name != null) {
            eventChannel.setEntityName(name);
        }
        eventChannel.setEventGroup__EventChannel(eventGroup);
        this.eventChannels.add(eventChannel);
        return this;
    }

    /**
     * Creates a new {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel}.
     * <p>
     * Event channels consist of an {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} and arbitrarily many
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}s and
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}s that are added when creating the connectors. However, that
     * required the specification of a name for the event channel.
     * </p>
     *
     * @param eventGroup
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public ComplexComponent withEventChannel(final EventGroup eventGroup) {
        return this.withEventChannel(eventGroup, null);
    }

    // ------------ operation role connectors ------------
    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * AssemblyConnector} and adds it to the component.
     * <p>
     * An AssemblyConnector is a bidirectional link of two assembly contexts. Intuitively, an
     * AssemblyConnector connects a provided and a required interface of two different components.
     * AssemblyContext must refer to the tuple (Role, AssemblyContext) in order to uniquely identify
     * which component roles communicate with each other.
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param providedRole
     * @param providingAssemblyContext
     * @param requiredRole
     * @param requiringAssemblyContext
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationProvidedRole(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationRequiredRole(String)
     */
    public ComplexComponent withAssemblyConnection(final OperationProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final OperationRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        IllegalArgumentException.throwIfNull(providedRole, "providedRole must not be null");
        IllegalArgumentException.throwIfNull(providingAssemblyContext, "providingAssemblyContext must not be null");
        IllegalArgumentException.throwIfNull(requiredRole, "requiredRole must not be null");
        IllegalArgumentException.throwIfNull(requiringAssemblyContext, "requiringAssemblyContext must not be null");
        final AssemblyConnector assemblyConnector = CompositionFactory.eINSTANCE.createAssemblyConnector();

        assemblyConnector.setProvidedRole_AssemblyConnector(providedRole);
        assemblyConnector.setProvidingAssemblyContext_AssemblyConnector(providingAssemblyContext);
        assemblyConnector.setRequiredRole_AssemblyConnector(requiredRole);
        assemblyConnector.setRequiringAssemblyContext_AssemblyConnector(requiringAssemblyContext);

        this.connectors.add(assemblyConnector);
        return this;
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * ProvidedDelegationConnector} and adds it to the component.
     * <p>
     * A ProvidedDelegationConnector delegates incoming calls of provided roles to inner provided
     * roles of encapsulated assembly contexts.
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerProvidedRole
     * @param outerProvidedRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationProvidedRole(String)
     */
    public ComplexComponent withProvidedDelegationConnection(final AssemblyContext assemblyContext,
            final OperationProvidedRole innerProvidedRole, final OperationProvidedRole outerProvidedRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerProvidedRole, "innerProvidedRole must not be null");
        IllegalArgumentException.throwIfNull(outerProvidedRole, "outerProvidedRole must not be null");
        final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();

        connector.setAssemblyContext_ProvidedDelegationConnector(assemblyContext);
        connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvidedRole);
        connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvidedRole);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * RequiredDelegationConnector} and adds it to the component.
     * <p>
     * A RequiredDelegationConnector delegates required roles of encapsulated assembly contexts to
     * outer required roles .
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerRequiredRole
     * @param outerRequiredRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationRequiredRole(String)
     */
    public ComplexComponent withRequiredDelegationConnection(final AssemblyContext assemblyContext,
            final OperationRequiredRole innerRequiredRole, final OperationRequiredRole outerRequiredRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerRequiredRole, "innerRequiredRole must not be null");
        IllegalArgumentException.throwIfNull(outerRequiredRole, "outerRequiredRole must not be null");
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();

        connector.setAssemblyContext_RequiredDelegationConnector(assemblyContext);
        connector.setInnerRequiredRole_RequiredDelegationConnector(innerRequiredRole);
        connector.setOuterRequiredRole_RequiredDelegationConnector(outerRequiredRole);

        this.connectors.add(connector);
        return this;
    }

    // ------------ event channel role connectors ------------
    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * AssemblyEventConnector} and adds it to the component.
     * <p>
     * An AssemblyConnector is a bidirectional link of two assembly contexts. Intuitively, an
     * AssemblyEventConnector connects a sink and a source. AssemblyContext must refer to the tuple
     * (Role,AssemblyContext) in order to uniquely identify which component sink and source roles
     * communicate with each other.
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param sinkRole
     * @param sinkAssemblyContext
     * @param sourceRole
     * @param sourceAssemblyContext
     * @param filterConditionStochasticExpression
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSinkRole(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSourceRole(String)
     */
    public ComplexComponent withAssemblyEventConnection(final SinkRole sinkRole,
            final AssemblyContext sinkAssemblyContext, final SourceRole sourceRole,
            final AssemblyContext sourceAssemblyContext, final String filterConditionStochasticExpression) {
        IllegalArgumentException.throwIfNull(sinkRole, "sinkRole must not be null");
        IllegalArgumentException.throwIfNull(sinkAssemblyContext, "sinkAssemblyContext must not be null");
        IllegalArgumentException.throwIfNull(sourceRole, "sourceRole must not be null");
        IllegalArgumentException.throwIfNull(sourceAssemblyContext, "sourceAssemblyContext must not be null");
        IllegalArgumentException.throwIfNull(filterConditionStochasticExpression,
                "filterCondition_stochasticExpression must not be null");
        final AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();

        connector.setSinkRole__AssemblyEventConnector(sinkRole);
        connector.setSinkAssemblyContext__AssemblyEventConnector(sinkAssemblyContext);
        connector.setSourceRole__AssemblyEventConnector(sourceRole);
        connector.setSourceAssemblyContext__AssemblyEventConnector(sourceAssemblyContext);

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(filterConditionStochasticExpression);
        connector.setFilterCondition__AssemblyEventConnector(rand);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector} and adds it to the component.
     * <p>
     * Existing roles, assembly contexts and event channels can be fetched from the repository using
     * the org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param eventChannel
     * @param sinkRole
     * @param filterConditionStochasticExpression
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventChannel(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSinkRole(String)
     */
    public ComplexComponent withEventChannelSinkConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SinkRole sinkRole,
            final String filterConditionStochasticExpression) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(eventChannel, "eventChannel must not be null");
        IllegalArgumentException.throwIfNull(sinkRole, "sinkRole must not be null");
        IllegalArgumentException.throwIfNull(filterConditionStochasticExpression,
                "filterCondition_stochasticExpression must not be null");
        final EventChannelSinkConnector connector = CompositionFactory.eINSTANCE.createEventChannelSinkConnector();

        connector.setAssemblyContext__EventChannelSinkConnector(assemblyContext);
        connector.setEventChannel__EventChannelSinkConnector(eventChannel);
        connector.setSinkRole__EventChannelSinkConnector(sinkRole);
        connector.setEntityName(this.name);

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(filterConditionStochasticExpression);
        connector.setFilterCondition__EventChannelSinkConnector(rand);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * EventChannelSourceConnector} and adds it to the component.
     * <p>
     * Existing roles, assembly contexts and event channels can be fetched from the repository using
     * the org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param eventChannel
     * @param sourceRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventChannel(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSourceRole(String)
     */
    public ComplexComponent withEventChannelSourceConnection(final AssemblyContext assemblyContext,
            final EventChannel eventChannel, final SourceRole sourceRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(eventChannel, "eventChannel must not be null");
        IllegalArgumentException.throwIfNull(sourceRole, "sourceRole must not be null");
        final EventChannelSourceConnector connector = CompositionFactory.eINSTANCE.createEventChannelSourceConnector();

        connector.setAssemblyContext__EventChannelSourceConnector(assemblyContext);
        connector.setEventChannel__EventChannelSourceConnector(eventChannel);
        connector.setSourceRole__EventChannelSourceRole(sourceRole);
        connector.setEntityName(this.name);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * SinkDelegationConnector} and adds it to the component.
     * <p>
     * A SinkDelegationConnector delegates an incoming event to the encapsulated assembly contexts
     * to inner sink roles.
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerSinkRole
     * @param outerSinkRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSinkRole(String)
     */
    public ComplexComponent withSinkDelegationConnection(final AssemblyContext assemblyContext,
            final SinkRole innerSinkRole, final SinkRole outerSinkRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerSinkRole, "innerSinkRole must not be null");
        IllegalArgumentException.throwIfNull(outerSinkRole, "outerSinkRole must not be null");
        final SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();

        connector.setAssemblyContext__SinkDelegationConnector(assemblyContext);
        connector.setInnerSinkRole__SinkRole(innerSinkRole);
        connector.setOuterSinkRole__SinkRole(outerSinkRole);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * SourceDelegationConnector} and adds it to the component.
     * <p>
     * A SourceDelegationConnector delegates outgoing events of encapsulated assembly contexts to an
     * external souce role of the enclosing assembly context.
     * </p>
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerSourceRole
     * @param outerSourceRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfSourceRole(String)
     */
    public ComplexComponent withSourceDelegationConnection(final AssemblyContext assemblyContext,
            final SourceRole innerSourceRole, final SourceRole outerSourceRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerSourceRole, "innerSourceRole must not be null");
        IllegalArgumentException.throwIfNull(outerSourceRole, "outerSourceRole must not be null");
        final SourceDelegationConnector connector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();

        connector.setAssemblyContext__SourceDelegationConnector(assemblyContext);
        connector.setInnerSourceRole__SourceRole(innerSourceRole);
        connector.setOuterSourceRole__SourceRole(outerSourceRole);

        this.connectors.add(connector);
        return this;
    }

    // ------------ infrastructure role connectors ------------
    /**
     * Creates an {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * AssemblyInfrastructureConnector} and adds it to the component.
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param providedRole
     * @param providingAssemblyContext
     * @param requiredRole
     * @param requiringAssemblyContext
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureProvidedRole(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureRequiredRole(String)
     */
    public ComplexComponent withAssemblyInfrastructureConnection(final InfrastructureProvidedRole providedRole,
            final AssemblyContext providingAssemblyContext, final InfrastructureRequiredRole requiredRole,
            final AssemblyContext requiringAssemblyContext) {
        IllegalArgumentException.throwIfNull(providedRole, "providedRole must not be null");
        IllegalArgumentException.throwIfNull(providingAssemblyContext, "providingAssemblyContext must not be null");
        IllegalArgumentException.throwIfNull(requiredRole, "requiredRole must not be null");
        IllegalArgumentException.throwIfNull(requiringAssemblyContext, "requiringAssemblyContext must not be null");
        final AssemblyInfrastructureConnector connector = CompositionFactory.eINSTANCE
            .createAssemblyInfrastructureConnector();

        connector.setProvidedRole__AssemblyInfrastructureConnector(providedRole);
        connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(providingAssemblyContext);
        connector.setRequiredRole__AssemblyInfrastructureConnector(requiredRole);
        connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(requiringAssemblyContext);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * ProvidedInfrastructureDelegationConnector} and adds it to the component.
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerProvidedRole
     * @param outerProvidedRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureProvidedRole(String)
     */
    public ComplexComponent withProvidedInfrastructureDelegationConnection(final AssemblyContext assemblyContext,
            final InfrastructureProvidedRole innerProvidedRole, final InfrastructureProvidedRole outerProvidedRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerProvidedRole, "innerProvidedRole must not be null");
        IllegalArgumentException.throwIfNull(outerProvidedRole, "outerProvidedRole must not be null");
        final ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createProvidedInfrastructureDelegationConnector();
        connector.setAssemblyContext__ProvidedInfrastructureDelegationConnector(assemblyContext);
        connector.setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(innerProvidedRole);
        connector.setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(outerProvidedRole);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * RequiredInfrastructureDelegationConnector} and adds it to the component.
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerRequiredRole
     * @param outerRequiredRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureRequiredRole(String)
     */
    public ComplexComponent withRequiredInfrastructureDelegationConnection(final AssemblyContext assemblyContext,
            final InfrastructureRequiredRole innerRequiredRole, final InfrastructureRequiredRole outerRequiredRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerRequiredRole, "innerRequiredRole must not be null");
        IllegalArgumentException.throwIfNull(outerRequiredRole, "outerRequiredRole must not be null");
        final RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE
            .createRequiredInfrastructureDelegationConnector();
        connector.setAssemblyContext__RequiredInfrastructureDelegationConnector(assemblyContext);
        connector.setInnerRequiredRole__RequiredInfrastructureDelegationConnector(innerRequiredRole);
        connector.setOuterRequiredRole__RequiredInfrastructureDelegationConnector(outerRequiredRole);

        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * RequiredResourceDelegationConnector} and adds it to the component.
     * <p>
     * Existing roles and assembly contexts can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory.
     * </p>
     *
     * @param assemblyContext
     * @param innerRequiredRole
     * @param outerRequiredRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfAssemblyContext(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfResourceRequiredRole(String)
     */
    public ComplexComponent withRequiredResourceDelegationConnection(final AssemblyContext assemblyContext,
            final ResourceRequiredRole innerRequiredRole, final ResourceRequiredRole outerRequiredRole) {
        IllegalArgumentException.throwIfNull(assemblyContext, "assemblyContext must not be null");
        IllegalArgumentException.throwIfNull(innerRequiredRole, "innerRequiredRole must not be null");
        IllegalArgumentException.throwIfNull(outerRequiredRole, "outerRequiredRole must not be null");
        final RequiredResourceDelegationConnector connector = CompositionFactory.eINSTANCE
            .createRequiredResourceDelegationConnector();
        connector.setAssemblyContext__RequiredResourceDelegationConnector(assemblyContext);
        connector.setInnerRequiredRole__RequiredResourceDelegationConnector(innerRequiredRole);
        connector.setOuterRequiredRole__RequiredResourceDelegationConnector(outerRequiredRole);
        this.connectors.add(connector);
        return this;
    }

    /**
     * Creates a
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * ResourceRequiredDelegationConnector} and adds it to the component.
     * <p>
     * Existing required roles can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfResourceRequiredRole(name)</code>.
     * </p>
     *
     * @param innerRequiredRole
     * @param outerRequiredRole
     * @return the component in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfResourceRequiredRole(String)
     */
    public ComplexComponent resourceRequiredDegelationConnection(final ResourceRequiredRole innerRequiredRole,
            final ResourceRequiredRole outerRequiredRole) {
        IllegalArgumentException.throwIfNull(innerRequiredRole, "innerRequiredRole must not be null");
        IllegalArgumentException.throwIfNull(outerRequiredRole, "outerRequiredRole must not be null");
        final ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE
            .createResourceRequiredDelegationConnector();
        connector.setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(innerRequiredRole);
        connector.setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(outerRequiredRole);

        this.resourceRequiredDelegationConnectors.add(connector);
        return this;

    }

    protected void addEventChannel(final EventChannel eventChannel) {
        this.eventChannels.add(eventChannel);
    }

}
