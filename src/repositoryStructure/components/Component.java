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
		OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__OperationProvidedRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// provides infrastructure interface
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return providesInfrastructure(interfce, null);
	}

	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		InfrastructureInterface i = interfce.build();
		InfrastructureProvidedRole providedRole = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__InfrastructureProvidedRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// handles event group (sink role)
	public Component handles(EventGroupCreator eventGroup) {
		return handles(eventGroup, null);
	}

	public Component handles(EventGroupCreator eventGroup, String name) {
		EventGroup i = eventGroup.build();
		SinkRole providedRole = RepositoryFactory.eINSTANCE.createSinkRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setEventGroup__SinkRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// ------------ requiring roles ------------
	// require operation interface
	public Component requires(OperationInterfaceCreator interfce) {
		return requires(interfce, null);
	}

	public Component requires(OperationInterfaceCreator interfce, String name) {
		OperationInterface i = interfce.build();
		OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__OperationRequiredRole(i);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// require infrastructure interface
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return requiresInfrastructure(interfce, null);
	}

	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		InfrastructureInterface i = interfce.build();
		InfrastructureRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__InfrastructureRequiredRole(i);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// emits event group (source role)
	public Component emits(EventGroupCreator eventGroup) {
		return emits(eventGroup, null);
	}

	public Component emits(EventGroupCreator eventGroup, String name) {
		EventGroup eg = eventGroup.build();
		SourceRole requiredRole = RepositoryFactory.eINSTANCE.createSourceRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setEventGroup__SourceRole(eg);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// resource required role
	public Component requiresResource(ResourceInterface resourceInterface) {
		// TODO: later; how to get the resourceInterface

		ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
		rrr.setRequiredResourceInterface__ResourceRequiredRole(resourceInterface);
		this.resourceRequiredRoles.add(rrr);
		return this;
	}

}
