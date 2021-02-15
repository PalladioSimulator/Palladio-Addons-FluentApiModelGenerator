package system.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
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
            .addAll(this.getAssemblyContexts());
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
        this.assemblyContexts.add(context.build());
        return this;
    }

    @Override
    public ISystem withRepository(final Repository repository) {
        this.repositories.add(repository);
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final AbstractConnectorCreator connector) {
        this.connectors.add(connector.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationRequiredRoleCreator role) {
        this.systemOperationRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final OperationProvidedRoleCreator role) {
        this.systemOperationProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final EventChannelCreator eventChannel) {
        this.eventChannels.add(eventChannel.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SinkRoleCreator role) {
        this.systemSinkRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final SourceRoleCreator role) {
        this.systemSourceRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureRequiredRoleCreator role) {
        this.systemInfrastructureRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final InfrastructureProvidedRoleCreator role) {
        this.systemInfrastructureProvidedRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final QoSAnnotationsCreator annotations) {
        this.qoSAnnotations.add(annotations.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredRoleCreator role) {
        this.systemResourceRequiredRoles.add(role.build());
        return this;
    }

    @Override
    public ISystemAddition addToSystem(final ResourceRequiredDelegationConnectorCreator connector) {
        this.resourceConnectors.add(connector.build());
        return this;
    }

    public List<Repository> getRepositories() {
        return this.repositories;
    }

    public List<AssemblyContext> getAssemblyContexts() {
        return this.assemblyContexts;
    }

    public List<OperationRequiredRole> getSystemOperationRequiredRoles() {
        return this.systemOperationRequiredRoles;
    }

    public List<OperationProvidedRole> getSystemOperationProvidedRoles() {
        return this.systemOperationProvidedRoles;
    }

    public List<SinkRole> getSystemSinkRoles() {
        return this.systemSinkRoles;
    }

    public List<InfrastructureRequiredRole> getSystemInfrastructureRequiredRoles() {
        return this.systemInfrastructureRequiredRoles;
    }

    public List<InfrastructureProvidedRole> getSystemInfrastructureProvidedRoles() {
        return this.systemInfrastructureProvidedRoles;
    }

    public List<SourceRole> getSystemSourceRoles() {
        return this.systemSourceRoles;
    }

    public List<ResourceRequiredRole> getSystemResourceRequiredRoles() {
        return this.systemResourceRequiredRoles;
    }

    public List<EventChannel> getEventChannels() {
        return this.eventChannels;
    }

    public ResourceInterface getResourceInterface(final shared.structure.ResourceInterface resource) {
        return this.resources.getResourceInterfaces__ResourceRepository()
            .stream()
            .filter(x -> x.getEntityName()
                .equals(resource.getResourceName()))
            .findFirst()
            .get();
    }
}
