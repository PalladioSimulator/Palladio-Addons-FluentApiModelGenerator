package system.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

import shared.validate.IModelValidator;
import system.apiControlFlowInterfaces.ISystem;
import system.apiControlFlowInterfaces.ISystemAddition;
import system.structure.connector.AbstractConnectorCreator;
import system.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
import system.structure.qosAnnotations.QoSAnnotationsCreator;
import system.structure.systemRole.InfrastructureProvidedRoleCreator;
import system.structure.systemRole.InfrastructureRequiredRoleCreator;
import system.structure.systemRole.OperationProvidedRoleCreator;
import system.structure.systemRole.OperationRequiredRoleCreator;
import system.structure.systemRole.ResourceRequiredRoleCreator;
import system.structure.systemRole.SinkRoleCreator;
import system.structure.systemRole.SourceRoleCreator;

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
        Objects.requireNonNull(context, "The given AssemblyContext must not be null.");
        this.assemblyContexts.add(context.build());
        return this;
    }

    @Override
    public ISystem withRepository(final Repository repository) {
        Objects.requireNonNull(repository, "The given Repository must not be null.");
        this.repositories.add(repository);
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final AbstractConnectorCreator connector) {
        Objects.requireNonNull(connector, "The given Connector must not be null.");
        this.connectors.add(connector.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemOperationRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationProvidedRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemOperationProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final EventChannelCreator eventChannel) {
        Objects.requireNonNull(eventChannel, "The given EventChannel must not be null.");
        this.eventChannels.add(eventChannel.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SinkRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemSinkRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SourceRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemSourceRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemInfrastructureRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureProvidedRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemInfrastructureProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final QoSAnnotationsCreator annotations) {
        Objects.requireNonNull(annotations, "The given QoSAnnotations must not be null.");
        this.qoSAnnotations.add(annotations.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredRoleCreator role) {
        Objects.requireNonNull(role, "The given Role must not be null.");
        this.systemResourceRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredDelegationConnectorCreator connector) {
        Objects.requireNonNull(connector, "The given Connector must not be null.");
        this.resourceConnectors.add(connector.build());
        return this;
    }

    public Interface getInterfaceByName(String name) throws NoSuchElementException {
        return this.repositories.stream()
            .flatMap(x -> x.getInterfaces__Repository()
                .stream())
            .filter(i -> i.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No Interface with name '%s' was found.", name)));
    }

    public RepositoryComponent getRepositoryComponentByName(String name) throws NoSuchElementException {
        return this.repositories.stream()
            .flatMap(x -> x.getComponents__Repository()
                .stream())
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No RepositoryComponent with name '%s' found.", name)));
    }

    public AssemblyContext getAssemblyContextByName(String name) throws NoSuchElementException {
        return this.assemblyContexts.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No AssemblyContext with name '%s' found", name)));
    }

    public ResourceRequiredRole getResourceRequiredRoleByName(String name) throws NoSuchElementException {
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

    public OperationRequiredRole getSystemOperationRequiredRoleByName(String name) throws NoSuchElementException {
        return this.systemOperationRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationRequiredRole with name '%s' found.", name)));
    }

    public OperationProvidedRole getSystemOperationProvidedRoleByName(String name) {
        return this.systemOperationProvidedRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No OperationProvidedRole with name '%s' found.", name)));
    }

    public SinkRole getSystemSinkRoleByName(String name) throws NoSuchElementException {
        return this.systemSinkRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SinkRole with name '%s' found", name)));
    }

    public InfrastructureRequiredRole getSystemInfrastructureRequiredRoleByName(String name)
            throws NoSuchElementException {
        return this.systemInfrastructureRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureRequiredRole with name '%s' found.", name)));
    }

    public InfrastructureProvidedRole getSystemInfrastructureProvidedRoleByName(String name)
            throws NoSuchElementException {
        return this.systemInfrastructureProvidedRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No InfrastructureProvidedRole with name '%s' found.", name)));
    }

    public SourceRole getSystemSourceRoleByName(String name) throws NoSuchElementException {
        return this.systemSourceRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("No SourceRole with name '%s' found.", name)));
    }

    public ResourceRequiredRole getSystemResourceRequiredRoleByName(String name) throws NoSuchElementException {
        return this.systemResourceRequiredRoles.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    String.format("No ResourceRequiredRole with name '%s' found.", name)));
    }

    public EventChannel getEventChannelByName(String name) throws NoSuchElementException {
        return this.eventChannels.stream()
            .filter(x -> x.getEntityName()
                .equals(name))
            .findFirst()
            .orElseThrow(
                    () -> new NoSuchElementException(String.format("No EventChannel with name '%s' found.", name)));
    }

    public ResourceInterface getResourceInterface(final shared.structure.ResourceInterface resource) {
        Objects.requireNonNull(resource, "The given resource must not be null.");
        return this.resources.getResourceInterfaces__ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }
}
