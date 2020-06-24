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

	// todo: vielleicht überladen mit String, um der Rolle einen Namen/ID zu geben?
	// ------------ providing roles ------------
	// provides operation interface
	public Component provides(OperationInterfaceCreator interfce) {
		OperationInterface i = interfce.build();
		OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		providedRole.setProvidedInterface__OperationProvidedRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// provides infrastructure interface
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		InfrastructureInterface i = interfce.build();
		InfrastructureProvidedRole providedRole = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
		providedRole.setProvidedInterface__InfrastructureProvidedRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// handles event group (sink role)
	public Component handles(EventGroupCreator eventGroup) {
		EventGroup i = eventGroup.build();
		SinkRole providedRole = RepositoryFactory.eINSTANCE.createSinkRole();
		providedRole.setEventGroup__SinkRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}

	// ------------ requiring roles ------------
	// require operation interface
	public Component requires(OperationInterfaceCreator interfce) {
		OperationInterface i = interfce.build();
		OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		requiredRole.setRequiredInterface__OperationRequiredRole(i);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// require infrastructure interface
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		InfrastructureInterface i = interfce.build();
		InfrastructureRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
		requiredRole.setRequiredInterface__InfrastructureRequiredRole(i);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// emits event group (source role)
	public Component emits(EventGroupCreator eventGroup) {
		EventGroup eg = eventGroup.build();
		SourceRole requiredRole = RepositoryFactory.eINSTANCE.createSourceRole();
		requiredRole.setEventGroup__SourceRole(eg);
		this.requiredRoles.add(requiredRole);
		return this;
	}

	// resource required role
	// TODO: how to get the resourceInterface
	public Component requiresResource(ResourceInterface resourceInterface) {

		ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
		rrr.setRequiredResourceInterface__ResourceRequiredRole(resourceInterface);
		this.resourceRequiredRoles.add(rrr);
		return this;
	}

}
