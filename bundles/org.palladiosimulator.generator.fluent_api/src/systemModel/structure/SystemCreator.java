package systemModel.structure;

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

import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.apiControlFlowInterfaces.ISystemAddition;
import systemModel.structure.connector.AbstractConnectorCreator;
import systemModel.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
import systemModel.structure.qosAnnotations.QoSAnnotationsCreator;
import systemModel.structure.systemRole.InfrastructureProvidedRoleCreator;
import systemModel.structure.systemRole.InfrastructureRequiredRoleCreator;
import systemModel.structure.systemRole.OperationProvidedRoleCreator;
import systemModel.structure.systemRole.OperationRequiredRoleCreator;
import systemModel.structure.systemRole.ResourceRequiredRoleCreator;
import systemModel.structure.systemRole.SinkRoleCreator;
import systemModel.structure.systemRole.SourceRoleCreator;

public class SystemCreator extends SystemEntity implements ISystem {
	private ResourceRepository resources;
	private List<AssemblyContext> assemblyContexts = new ArrayList<>();
	private List<Repository> repositories = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<OperationRequiredRole> systemOperationRequiredRoles = new ArrayList<>();
	private List<OperationProvidedRole> systemOperationProvidedRoles = new ArrayList<>();
	private List<InfrastructureRequiredRole> systemInfrastructureRequiredRoles = new ArrayList<>();
	private List<InfrastructureProvidedRole> systemInfrastructureProvidedRoles = new ArrayList<>();
	private List<SinkRole> systemSinkRoles = new ArrayList<>();
	private List<SourceRole> systemSourceRoles = new ArrayList<>();
	private List<ResourceRequiredRole> systemResourceRequiredRoles = new ArrayList<>();
	private List<EventChannel> eventChannels = new ArrayList<>();
	private List<QoSAnnotations> qoSAnnotations = new ArrayList<>();
	private List<ResourceRequiredDelegationConnector> resourceConnectors = new ArrayList<>();

	public SystemCreator(ResourceRepository resources) {
		this.resources = resources;
	}
	
	@Override
	public SystemCreator withName(String name) {
		return (SystemCreator) super.withName(name);
	}

	@Override
	protected System build() {
		System system = SystemFactory.eINSTANCE.createSystem();
		if (name != null) {
			system.setEntityName(name);
		}
		system.getAssemblyContexts__ComposedStructure().addAll(getAssemblyContexts());
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
		//TODO: validate
		return system;
	}

	@Override
	public System createSystemNow() {
		return build();
	}

	@Override
	public ISystemAddition addToSystem(AssemblyContextCreator context) {
		this.assemblyContexts.add(context.build());
		return this;
	}

	@Override
	public ISystem withRepository(Repository repository) {
		this.repositories.add(repository);
		return this;
	}

	@Override
	public ISystemAddition addToSystem(AbstractConnectorCreator connector) {
		this.connectors.add(connector.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(OperationRequiredRoleCreator role) {
		this.systemOperationRequiredRoles.add(role.build());
		return this;
	}
	
	@Override
	public ISystemAddition addToSystem(OperationProvidedRoleCreator role) {
		this.systemOperationProvidedRoles.add(role.build());
		return this;
	}
	
	@Override
	public ISystemAddition addToSystem(EventChannelCreator eventChannel) {
		this.eventChannels.add(eventChannel.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(SinkRoleCreator role) {
		this.systemSinkRoles.add(role.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(SourceRoleCreator role) {
		this.systemSourceRoles.add(role.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(InfrastructureRequiredRoleCreator role) {
		this.systemInfrastructureRequiredRoles.add(role.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(InfrastructureProvidedRoleCreator role) {
		this.systemInfrastructureProvidedRoles.add(role.build());
		return this;
	}

	@Override
	public ISystemAddition addToSystem(QoSAnnotationsCreator annotations) {
		this.qoSAnnotations.add(annotations.build());
		return this;
	}
	
	@Override
	public ISystemAddition addToSystem(ResourceRequiredRoleCreator role) {
		this.systemResourceRequiredRoles.add(role.build());
		return this;
	}
	
	@Override
	public ISystemAddition addToSystem(ResourceRequiredDelegationConnectorCreator connector) {
		this.resourceConnectors.add(connector.build());
		return this;
	}

	public List<Repository> getRepositories() {
		return repositories;
	}

	public List<AssemblyContext> getAssemblyContexts() {
		return assemblyContexts;
	}
	
	public List<OperationRequiredRole> getSystemOperationRequiredRoles() {
		return systemOperationRequiredRoles;
	}

	public List<OperationProvidedRole> getSystemOperationProvidedRoles() {
		return systemOperationProvidedRoles;
	}
	
	public List<SinkRole> getSystemSinkRoles() {
		return systemSinkRoles;
	}
	
	public List<InfrastructureRequiredRole> getSystemInfrastructureRequiredRoles() {
		return systemInfrastructureRequiredRoles;
	}
	
	public List<InfrastructureProvidedRole> getSystemInfrastructureProvidedRoles() {
		return systemInfrastructureProvidedRoles;
	}
	
	public List<SourceRole> getSystemSourceRoles() {
		return systemSourceRoles;
	}
	
	public List<ResourceRequiredRole> getSystemResourceRequiredRoles() {
		return systemResourceRequiredRoles;
	}
	
	public List<EventChannel> getEventChannels() {
		return eventChannels;
	}
	
	public ResourceInterface getResourceInterface(shared.structure.ResourceInterface resource) {
		return resources.getResourceInterfaces__ResourceRepository().stream()
				.filter(x -> x.getEntityName().equals(resource.resourceName)).findFirst().get();
	}
}
