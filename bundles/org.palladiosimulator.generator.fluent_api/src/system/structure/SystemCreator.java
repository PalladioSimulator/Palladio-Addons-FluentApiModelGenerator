package system.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import exceptions.NoSuchElementException;
import shared.validate.IModelValidator;
import system.apiControlFlowInterfaces.ISystem;
import system.apiControlFlowInterfaces.ISystemAddition;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.resource.ResourceRequiredDelegationConnectorCreator;
import system.structure.qosAnnotations.QoSAnnotationsCreator;
import system.structure.systemRole.InfrastructureProvidedRoleCreator;
import system.structure.systemRole.InfrastructureRequiredRoleCreator;
import system.structure.systemRole.OperationProvidedRoleCreator;
import system.structure.systemRole.OperationRequiredRoleCreator;
import system.structure.systemRole.ResourceRequiredRoleCreator;
import system.structure.systemRole.SinkRoleCreator;
import system.structure.systemRole.SourceRoleCreator;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.system.System
 * System}.
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
        if (name != null) {
            system.setEntityName(name);
        }
        system.getAssemblyContexts__ComposedStructure().addAll(assemblyContexts);
        system.getConnectors__ComposedStructure().addAll(connectors);
        system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemOperationRequiredRoles);
        system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemOperationProvidedRoles);
        system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemSourceRoles);
        system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemSinkRoles);
        system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemInfrastructureRequiredRoles);
        system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemInfrastructureProvidedRoles);
        system.getEventChannel__ComposedStructure().addAll(eventChannels);
        system.getQosAnnotations_System().addAll(qoSAnnotations);
        system.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(systemResourceRequiredRoles);
        system.getResourceRequiredDelegationConnectors_ComposedStructure().addAll(resourceConnectors);
        return system;
    }

    @Override
    public System createSystemNow() {
        final System system = build();
        validator.validate(system, name);
        return system;
    }

    @Override
    public ISystemAddition addToSystem(final AssemblyContextCreator context) {
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        assemblyContexts.add(context.build());
        return this;
    }

    @Override
    public ISystem addRepository(final Repository repository) {
        Objects.requireNonNull(repository, "The given Repository must not be null.");
        repositories.add(repository);
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final AbstractConnectorCreator connector) {
        Objects.requireNonNull(connector, "The given Connector must not be null.");
        connectors.add(connector.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemOperationRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationProvidedRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemOperationProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final EventChannelCreator eventChannel) {
        Objects.requireNonNull(eventChannel, "The given EventChannel must not be null.");
        eventChannels.add(eventChannel.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SinkRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemSinkRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SourceRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemSourceRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemInfrastructureRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureProvidedRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemInfrastructureProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final QoSAnnotationsCreator annotations) {
        Objects.requireNonNull(annotations, "The given QoSAnnotations must not be null.");
        qoSAnnotations.add(annotations.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        systemResourceRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredDelegationConnectorCreator connector) {
        Objects.requireNonNull(connector, "The given Connector must not be null.");
        resourceConnectors.add(connector.build());
        return this;
    }

    /**
     * Searches the repositories added to the system for an
     * {@link org.palladiosimulator.pcm.repository.Interface Interface} that matches
     * the given name.
     *
     * @param name
     * @return the matching interface
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.Interface
     */
    public Interface getInterfaceByName(String name) throws NoSuchElementException {
        return repositories.stream().flatMap(x -> x.getInterfaces__Repository().stream())
                .filter(i -> i.getEntityName().equals(name)).findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No Interface with name '%s' was found.", name)));
    }

    /**
     * Searches the repositories added to the system for an
     * {@link org.palladiosimulator.pcm.repository.RepositoryComponent
     * RepositoryComponent} that matches the given name.
     *
     * @param name
     * @return the matching component
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    public RepositoryComponent getRepositoryComponentByName(String name) throws NoSuchElementException {
        return repositories.stream().flatMap(x -> x.getComponents__Repository().stream())
                .filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No RepositoryComponent with name '%s' found.", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}s added to the system for one that matches the given name.
     *
     * @param name
     * @return the matching context
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContext getAssemblyContextByName(String name) throws NoSuchElementException {
        return assemblyContexts.stream().filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(
                () -> new NoSuchElementException(String.format("No AssemblyContext with name '%s' found", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}s added to the system for one that matches the given
     * name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRole getResourceRequiredRoleByName(String name) throws NoSuchElementException {
        return assemblyContexts.stream()
                .flatMap(x -> x.getEncapsulatedComponent__AssemblyContext()
                        .getResourceRequiredRoles__ResourceInterfaceRequiringEntity().stream())
                .filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(() -> new NoSuchElementException(
                        String.format("No ResourceRequiredRole with name '%s' found", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole}s added to the system for one that matches the given
     * name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRole getSystemOperationRequiredRoleByName(String name) throws NoSuchElementException {
        return systemOperationRequiredRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No OperationRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole}s added to the system for one that matches the given
     * name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRole getSystemOperationProvidedRoleByName(String name) {
        return systemOperationProvidedRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No OperationProvidedRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}s
     * added to the system for one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRole getSystemSinkRoleByName(String name) throws NoSuchElementException {
        return systemSinkRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("No SinkRole with name '%s' found", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole}s added to the system for one that matches the
     * given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRole getSystemInfrastructureRequiredRoleByName(String name)
            throws NoSuchElementException {
        return systemInfrastructureRequiredRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No InfrastructureRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole}s added to the system for one that matches the
     * given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRole getSystemInfrastructureProvidedRoleByName(String name)
            throws NoSuchElementException {
        return systemInfrastructureProvidedRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("No InfrastructureProvidedRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.SourceRole
     * SourceRole}s added to the system for one that matches the given name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRole getSystemSourceRoleByName(String name) throws NoSuchElementException {
        return systemSourceRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(
                () -> new NoSuchElementException(String.format("No SourceRole with name '%s' found.", name)));
    }

    /**
     * Searches the
     * {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}s added to the system for one that matches the given
     * name.
     *
     * @param name
     * @return the matching role
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRole getSystemResourceRequiredRoleByName(String name) throws NoSuchElementException {
        return systemResourceRequiredRoles.stream().filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(
                () -> new NoSuchElementException(String.format("No ResourceRequiredRole with name '%s' found.", name)));
    }

    /**
     * Searches the {@link org.palladiosimulator.pcm.repository.EventChannel
     * EventChannel}s added to the system for one that matches the given name.
     *
     * @param name
     * @return the matching event channel
     * @throws NoSuchElementException Thrown if no element matches the given name
     * @see org.palladiosimulator.pcm.repository.EventChannel
     */
    public EventChannel getEventChannelByName(String name) throws NoSuchElementException {
        return eventChannels.stream().filter(x -> x.getEntityName().equals(name)).findFirst().orElseThrow(
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
    public ResourceInterface getResourceInterface(final shared.structure.ResourceInterface resource) {
        Objects.requireNonNull(resource, "The given resource must not be null.");
        return resources.getResourceInterfaces__ResourceRepository().stream()
                .filter(x -> x.getEntityName().equals(resource.getResourceName())).findFirst().get();
    }
}
