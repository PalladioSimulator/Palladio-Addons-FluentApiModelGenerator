package org.palladiosimulator.generator.fluent.system.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.system.api.ISystem;
import org.palladiosimulator.generator.fluent.system.api.ISystemAddition;
import org.palladiosimulator.generator.fluent.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.resource.ResourceRequiredDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.qos.QoSAnnotationsCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.InfrastructureProvidedRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.InfrastructureRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.OperationProvidedRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.OperationRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.ResourceRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.SinkRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.SourceRoleCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.system.System System}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.system.System
 */
public class SystemCreator extends SystemEntity implements ISystem {
    private final IModelValidator validator;
    private final ResourceRepository resources;
    private final List<AssemblyContext> assemblyContexts = new ArrayList<>();
    private final List<Repository> repositories = new ArrayList<>();
    private final List<Connector> connectors = new ArrayList<>();
    private final List<OperationRequiredRole> systemOperationRequiredRoles = new ArrayList<>();
    private final List<OperationProvidedRole> systemOperationProvidedRoles = new ArrayList<>();
    private final List<InfrastructureRequiredRole> systemInfrastructureRequiredRoles = new ArrayList<>();
    private final List<InfrastructureProvidedRole> systemInfrastructureProvidedRoles = new ArrayList<>();
    private final List<SinkRole> systemSinkRoles = new ArrayList<>();
    private final List<SourceRole> systemSourceRoles = new ArrayList<>();
    private final List<ResourceRequiredRole> systemResourceRequiredRoles = new ArrayList<>();
    private final List<EventChannel> eventChannels = new ArrayList<>();
    private final List<QoSAnnotations> qoSAnnotations = new ArrayList<>();
    private final List<ResourceRequiredDelegationConnector> resourceConnectors = new ArrayList<>();

    public SystemCreator(final ResourceRepository resources, final IModelValidator validator) {
        this.resources = resources;
        this.validator = validator;
    }

    @Override
    public SystemCreator withName(final String name) {
        return (SystemCreator) super.withName(name);
    }

    @Override
    protected System build() {
        final System system = SystemFactory.eINSTANCE.createSystem();
        if (this.name != null) {
            system.setEntityName(this.name);
        }
        system.getAssemblyContexts__ComposedStructure()
            .addAll(this.assemblyContexts);
        system.getConnectors__ComposedStructure()
            .addAll(this.connectors);
        system.getRequiredRoles_InterfaceRequiringEntity()
            .addAll(this.systemOperationRequiredRoles);
        system.getProvidedRoles_InterfaceProvidingEntity()
            .addAll(this.systemOperationProvidedRoles);
        system.getRequiredRoles_InterfaceRequiringEntity()
            .addAll(this.systemSourceRoles);
        system.getProvidedRoles_InterfaceProvidingEntity()
            .addAll(this.systemSinkRoles);
        system.getRequiredRoles_InterfaceRequiringEntity()
            .addAll(this.systemInfrastructureRequiredRoles);
        system.getProvidedRoles_InterfaceProvidingEntity()
            .addAll(this.systemInfrastructureProvidedRoles);
        system.getEventChannel__ComposedStructure()
            .addAll(this.eventChannels);
        system.getQosAnnotations_System()
            .addAll(this.qoSAnnotations);
        system.getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
            .addAll(this.systemResourceRequiredRoles);
        system.getResourceRequiredDelegationConnectors_ComposedStructure()
            .addAll(this.resourceConnectors);
        return system;
    }

    @Override
    public System createSystemNow() {
        final System system = this.build();
        this.validator.validate(system, this.name);
        return system;
    }

    @Override
    public ISystemAddition addToSystem(final AssemblyContextCreator context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        this.assemblyContexts.add(context.build());
        return this;
    }

    @Override
    public ISystem addRepository(final Repository repository) {
        IllegalArgumentException.throwIfNull(repository, "The given Repository must not be null.");
        this.repositories.add(repository);
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final AbstractConnectorCreator connector) {
        IllegalArgumentException.throwIfNull(connector, "The given Connector must not be null.");
        this.connectors.add(connector.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationRequiredRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemOperationRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationProvidedRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemOperationProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final EventChannelCreator eventChannel) {
        IllegalArgumentException.throwIfNull(eventChannel, "The given EventChannel must not be null.");
        this.eventChannels.add(eventChannel.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SinkRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemSinkRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SourceRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemSourceRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureRequiredRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemInfrastructureRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureProvidedRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemInfrastructureProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final QoSAnnotationsCreator annotations) {
        IllegalArgumentException.throwIfNull(annotations, "The given QoSAnnotations must not be null.");
        this.qoSAnnotations.add(annotations.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredRoleCreator role) {
        IllegalArgumentException.throwIfNull(role, "The given Role must not be null.");
        this.systemResourceRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredDelegationConnectorCreator connector) {
        IllegalArgumentException.throwIfNull(connector, "The given Connector must not be null.");
        this.resourceConnectors.add(connector.build());
        return this;
    }

    /**
     * Searches the repositories added to the org.palladiosimulator.generator.fluent.system for an
     * {@link org.palladiosimulator.pcm.repository.Interface Interface} that matches the given name.
     *
     * @param name
     * @return the matching interface
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.Interface
     */
    public Interface getInterfaceByName(final String name) throws NoSuchElementException {
        return this.repositories.stream()
            .flatMap(x -> x.getInterfaces__Repository()
                .stream())
            .filter(i -> i.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No Interface with name '%s' was found.", name)));
    }

    /**
     * Searches the repositories added to the org.palladiosimulator.generator.fluent.system for an
     * {@link org.palladiosimulator.pcm.repository.RepositoryComponent RepositoryComponent} that
     * matches the given name.
     *
     * @param name
     * @return the matching component
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    public RepositoryComponent getRepositoryComponentByName(final String name) throws NoSuchElementException {
        return this.repositories.stream()
            .flatMap(x -> x.getComponents__Repository()
                .stream())
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No RepositoryComponent with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}s added to the org.palladiosimulator.generator.fluent.system for one that
     * matches the given name.
     *
     * @param name
     * @return the matching context
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContext getAssemblyContextByName(final String name) throws NoSuchElementException {
        return this.assemblyContexts.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No AssemblyContext with name '%s' found", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}s added to the org.palladiosimulator.generator.fluent.system for one
     * that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRole getResourceRequiredRoleByName(final String name) throws NoSuchElementException {
        return this.assemblyContexts.stream()
            .flatMap(x -> x.getEncapsulatedComponent__AssemblyContext()
                .getResourceRequiredRoles__ResourceInterfaceRequiringEntity()
                .stream())
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No ResourceRequiredRole with name '%s' found", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole}s added to the org.palladiosimulator.generator.fluent.system for one
     * that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRole getSystemOperationRequiredRoleByName(final String name) throws NoSuchElementException {
        return this.systemOperationRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole}s added to the org.palladiosimulator.generator.fluent.system for one
     * that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRole getSystemOperationProvidedRoleByName(final String name) {
        return this.systemOperationProvidedRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationProvidedRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}s added to the
     * org.palladiosimulator.generator.fluent.system for one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRole getSystemSinkRoleByName(final String name) throws NoSuchElementException {
        return this.systemSinkRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SinkRole with name '%s' found", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole}s added to the org.palladiosimulator.generator.fluent.system for
     * one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRole getSystemInfrastructureRequiredRoleByName(final String name)
            throws NoSuchElementException {
        return this.systemInfrastructureRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole}s added to the org.palladiosimulator.generator.fluent.system for
     * one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRole getSystemInfrastructureProvidedRoleByName(final String name)
            throws NoSuchElementException {
        return this.systemInfrastructureProvidedRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureProvidedRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}s added to the
     * org.palladiosimulator.generator.fluent.system for one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRole getSystemSourceRoleByName(final String name) throws NoSuchElementException {
        return this.systemSourceRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SourceRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}s added to the org.palladiosimulator.generator.fluent.system for one
     * that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRole getSystemResourceRequiredRoleByName(final String name) throws NoSuchElementException {
        return this.systemResourceRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No ResourceRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.EventChannel EventChannel}s added to
     * the org.palladiosimulator.generator.fluent.system for one that matches the given name.
     *
     * @param name
     * @return the matching event channel
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.EventChannel
     */
    public EventChannel getEventChannelByName(final String name) throws NoSuchElementException {
        return this.eventChannels.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No EventChannel with name '%s' found.", name)));
    }

    /**
     * Fetches the {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface
     * ResourceInterface} matching the given resource.
     *
     * @param resource
     * @return the matching resource interface
     * @see org.palladiosimulator.pcm.resourcetype.ResourceInterface
     */
    public ResourceInterface getResourceInterface(
            final org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface resource) {
        IllegalArgumentException.throwIfNull(resource, "The given resource must not be null.");
        return this.resources.getResourceInterfaces__ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }
}
