package repositoryStructure.components;

import java.util.List;

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

import apiControlFlowInterfaces.Comp;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public abstract class Component implements Comp {

	protected String name;
	protected String id;

	protected RepositoryCreator repository;

	protected List<ProvidedRole> providedRoles;
	protected List<RequiredRole> requiredRoles;

	public abstract RepositoryComponent build();

	// TODO: vielleicht überladen mit String, um der Role einen Namen zu geben; ID
	// sollte nicht nötig sein

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
	// TODO: Resource requiring roles are not part of the RepositoryFactory and the
	// constructor of the implementing class is not visible
	public Component requiresResource(Object o) {
		return this;
	}
}
