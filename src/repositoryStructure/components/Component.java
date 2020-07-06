package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.EntityFactory;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;

import repositoryStructure.Entity;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public abstract class Component extends Entity {

	protected List<ProvidedRole> providedRoles = new ArrayList<>();
	protected List<RequiredRole> requiredRoles = new ArrayList<>();
	protected List<ResourceRequiredRole> resourceRequiredRoles = new ArrayList<>();

	public abstract RepositoryComponent build();

	// ------------ providing roles ------------
	// provides operation interface
	public Component provides(OperationInterfaceCreator interfce) {
		return provides(interfce, null);
	}

	public Component provides(OperationInterfaceCreator interfce, String name) {
		OperationInterface i = interfce.build();
		this.repository.addInterface(i);
		return provides(i, name);
	}

	public Component provides(OperationInterface interfce) {
		return provides(interfce, null);
	}

	public Component provides(OperationInterface interfce, String name) {
		OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__OperationProvidedRole(interfce);
		this.providedRoles.add(providedRole);
		this.repository.addProvidedRole(providedRole);
		return this;
	}

	// provides infrastructure interface
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return providesInfrastructure(interfce, null);
	}

	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		InfrastructureInterface i = interfce.build();
		this.repository.addInterface(i);
		return providesInfrastructure(i, name);
	}

	public Component providesInfrastructure(InfrastructureInterface interfce) {
		return providesInfrastructure(interfce, null);
	}

	public Component providesInfrastructure(InfrastructureInterface interfce, String name) {
		InfrastructureProvidedRole providedRole = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__InfrastructureProvidedRole(interfce);
		this.providedRoles.add(providedRole);
		this.repository.addProvidedRole(providedRole);
		return this;
	}

	// handles event group (sink role)
	public Component handles(EventGroupCreator eventGroup) {
		return handles(eventGroup, null);
	}

	public Component handles(EventGroupCreator eventGroup, String name) {
		EventGroup eg = eventGroup.build();
		this.repository.addInterface(eg);
		return handles(eg, name);
	}

	public Component handles(EventGroup eventGroup) {
		return handles(eventGroup, null);
	}

	public Component handles(EventGroup eventGroup, String name) {
		SinkRole providedRole = RepositoryFactory.eINSTANCE.createSinkRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setEventGroup__SinkRole(eventGroup);
		this.providedRoles.add(providedRole);
		this.repository.addProvidedRole(providedRole);
		return this;
	}

	// ------------ requiring roles ------------
	// require operation interface
	public Component requires(OperationInterfaceCreator interfce) {
		return requires(interfce, null);
	}

	public Component requires(OperationInterfaceCreator interfce, String name) {
		OperationInterface i = interfce.build();
		this.repository.addInterface(i);
		return requires(i, name);
	}

	public Component requires(OperationInterface interfce) {
		return requires(interfce, null);
	}

	public Component requires(OperationInterface interfce, String name) {
		OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__OperationRequiredRole(interfce);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	// require infrastructure interface
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return requiresInfrastructure(interfce, null);
	}

	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		InfrastructureInterface i = interfce.build();
		this.repository.addInterface(i);
		return requiresInfrastructure(i, name);
	}

	public Component requiresInfrastructure(InfrastructureInterface interfce) {
		return requiresInfrastructure(interfce, null);
	}

	public Component requiresInfrastructure(InfrastructureInterface interfce, String name) {
		InfrastructureRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__InfrastructureRequiredRole(interfce);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	// emits event group (source role)
	public Component emits(EventGroupCreator eventGroup) {
		return emits(eventGroup, null);
	}

	public Component emits(EventGroupCreator eventGroup, String name) {
		EventGroup eg = eventGroup.build();
		this.repository.addInterface(eg);
		return emits(eg, name);
	}

	public Component emits(EventGroup eventGroup) {
		return emits(eventGroup, null);
	}

	public Component emits(EventGroup eventGroup, String name) {
		SourceRole requiredRole = RepositoryFactory.eINSTANCE.createSourceRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setEventGroup__SourceRole(eventGroup);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	public Component requiresResource(ResourceInterface resourceInterface) {
		ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
		rrr.setRequiredResourceInterface__ResourceRequiredRole(resourceInterface);
		this.resourceRequiredRoles.add(rrr);
		this.repository.addResourceRequiredRole(rrr);
		return this;
	}

}
