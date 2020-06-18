package repositoryStructure.components;

import java.util.List;

import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.SinkRole;

import apiControlFlowInterfaces.Comp;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.roles.ProvidesOperationInterfaceCreator;

public abstract class Component implements Comp {

	protected String name;
	protected String id;

	protected RepositoryCreator repository;

	protected List<ProvidedRole> providedRoles;

	public abstract RepositoryComponent build();

	// TODO: vielleicht überladen mit String, um der Role einen Namen zu geben; ID
	// sollte nicht nötig sein
	
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

	public Component handles(EventGroupCreator interfce) {
		EventGroup i = interfce.build();
		SinkRole providedRole = RepositoryFactory.eINSTANCE.createSinkRole();
		providedRole.setEventGroup__SinkRole(i);
		this.providedRoles.add(providedRole);
		return this;
	}
}
