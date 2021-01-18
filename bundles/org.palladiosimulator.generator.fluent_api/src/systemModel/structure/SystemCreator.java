package systemModel.structure;

import java.util.ArrayList;
import java.util.List;

import javax.management.remote.JMXConnectorServer;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;

import systemModel.apiControlFlowInterfaces.ISystem;
import systemModel.apiControlFlowInterfaces.ISystemAddition;

public class SystemCreator extends SystemEntity implements ISystem {
	private List<AssemblyContext> assemblyContexts = new ArrayList<>();
	private List<Repository> repositories = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<OperationRequiredRole> systemRequiredRoles = new ArrayList<>();
	private List<OperationProvidedRole> systemProvidedRoles = new ArrayList<>();

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
		system.getRequiredRoles_InterfaceRequiringEntity().addAll(systemRequiredRoles);
		system.getProvidedRoles_InterfaceProvidingEntity().addAll(systemProvidedRoles);
		//TODO: validate
		return system;
	}

	@Override
	public System createSystemNow() {
		return build();
	}

	@Override
	public ISystem withAssembyContext(AssemblyContext context) {
		this.assemblyContexts.add(context);
		return this;
	}

	@Override
	public ISystem withRepository(Repository repository) {
		this.repositories.add(repository);
		return this;
	}

	@Override
	public ISystemAddition withAssemblyConnector(AssemblyConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	@Override
	public ISystemAddition withOperationRequiredRole(OperationRequiredRole role) {
		this.systemRequiredRoles.add(role);
		return this;
	}

	@Override
	public ISystemAddition withRequiredDelegationConnector(RequiredDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}
	
	@Override
	public ISystemAddition withOperationProvidedRole(OperationProvidedRole role) {
		this.systemProvidedRoles.add(role);
		return this;
	}
	
	@Override
	public ISystemAddition withProvidedDelegationConnector(ProvidedDelegationConnector connector) {
		this.connectors.add(connector);
		return this;
	}

	public List<Repository> getRepositories() {
		return repositories;
	}

	public List<AssemblyContext> getAssemblyContexts() {
		return assemblyContexts;
	}
	
	public List<OperationRequiredRole> getSystemRequiredRoles() {
		return systemRequiredRoles;
	}

	public List<OperationProvidedRole> getSystemProvidedRoles() {
		return systemProvidedRoles;
	}
}
