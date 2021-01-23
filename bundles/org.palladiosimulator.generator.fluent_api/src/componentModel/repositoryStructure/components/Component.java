package componentModel.repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import componentModel.repositoryStructure.RepositoryEntity;
import componentModel.repositoryStructure.interfaces.EventGroupCreator;
import componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import componentModel.repositoryStructure.interfaces.OperationInterfaceCreator;
import shared.structure.ResourceInterface;

/**
 * This class provides the general infrastructure of a component, i.e.
 * BasicComponent CompositeComponent, SubSystem, CompleteComponentTypea and
 * ProvidesComponentType. It provides the implementation of the methods for
 * creating role connections (ProvidedRole, RequiredRole, ResourceRequiredRole).
 * 
 * @author Louisa Lambrecht
 *
 */
public abstract class Component extends RepositoryEntity {

	protected List<ProvidedRole> providedRoles = new ArrayList<>();
	protected List<RequiredRole> requiredRoles = new ArrayList<>();
	protected List<ResourceRequiredRole> resourceRequiredRoles = new ArrayList<>();

	public abstract RepositoryComponent build();

	// ------------ providing roles ------------
	// provides operation interface
	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
	 * OperationProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new operation interface by using the componentModel.factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newOperationInterface()
	 */
	public Component provides(OperationInterfaceCreator interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return provides(interfce, null);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
	 * OperationProvidedRole} connection with the name <code>name</code> between the
	 * component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new operation interface by using the componentModel.factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newOperationInterface()
	 */
	public Component provides(OperationInterfaceCreator interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		OperationInterface i = interfce.build();
		this.repository.addInterface(i);
		return provides(i, name);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
	 * OperationProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component provides(OperationInterface interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return provides(interfce, null);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
	 * OperationProvidedRole} connection with the name <code>name</code> between the
	 * component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component provides(OperationInterface interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__OperationProvidedRole(interfce);
		this.providedRoles.add(providedRole);
		this.repository.addProvidedRole(providedRole);
		return this;
	}

	// provides infrastructure interface
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 * InfrastructureProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new infrastructure interface by using the componentModel.factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newInfrastructureInterface()
	 */
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return providesInfrastructure(interfce, null);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 * InfrastructureProvidedRole} connection with the name <code>name</code>
	 * between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new infrastructure interface by using the componentModel.factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newInfrastructureInterface()
	 */
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		InfrastructureInterface i = interfce.build();
		this.repository.addInterface(i);
		return providesInfrastructure(i, name);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 * InfrastructureProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component providesInfrastructure(InfrastructureInterface interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return providesInfrastructure(interfce, null);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 * InfrastructureProvidedRole} connection with the name <code>name</code>
	 * between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component providesInfrastructure(InfrastructureInterface interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		InfrastructureProvidedRole providedRole = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
		if (name != null)
			providedRole.setEntityName(name);
		providedRole.setProvidedInterface__InfrastructureProvidedRole(interfce);
		this.providedRoles.add(providedRole);
		this.repository.addProvidedRole(providedRole);
		return this;
	}

	// handles event group (sink role)
	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * Create a new event group by using the componentModel.factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newEventGroup()
	 */
	public Component handles(EventGroupCreator eventGroup) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		return handles(eventGroup, null);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}
	 * connection with the name <code>name</code> between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component. Setting the
	 * <code>name</code> is important for referencing if the role is used in a
	 * connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new event group by using the componentModel.factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newEventGroup()
	 */
	public Component handles(EventGroupCreator eventGroup, String name) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		EventGroup eg = eventGroup.build();
		this.repository.addInterface(eg);
		return handles(eg, name);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * An existing <code>eventGroup</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component handles(EventGroup eventGroup) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		return handles(eventGroup, null);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}
	 * connection with the name <code>name</code> between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component. Setting the
	 * <code>name</code> is important for referencing if the role is used in a
	 * connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>eventGroup</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component handles(EventGroup eventGroup, String name) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
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
	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
	 * OperationRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new operation interface by using the componentModel.factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newOperationInterface()
	 */
	public Component requires(OperationInterfaceCreator interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return requires(interfce, null);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
	 * OperationRequiredRole} connection with the name <code>name</code> between the
	 * component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new operation interface by using the componentModel.factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newOperationInterface()
	 */
	public Component requires(OperationInterfaceCreator interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		OperationInterface i = interfce.build();
		this.repository.addInterface(i);
		return requires(i, name);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
	 * OperationRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component requires(OperationInterface interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return requires(interfce, null);
	}

	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
	 * OperationRequiredRole} connection with the name <code>name</code> between the
	 * component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component requires(OperationInterface interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__OperationRequiredRole(interfce);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	// require infrastructure interface
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 * InfrastructureRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new infrastructure interface by using the componentModel.factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newInfrastructureInterface()
	 */
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return requiresInfrastructure(interfce, null);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 * InfrastructureRequiredRole} connection with the name <code>name</code>
	 * between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new infrastructure interface by using the componentModel.factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newInfrastructureInterface()
	 */
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		InfrastructureInterface i = interfce.build();
		this.repository.addInterface(i);
		return requiresInfrastructure(i, name);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 * InfrastructureRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component requiresInfrastructure(InfrastructureInterface interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		return requiresInfrastructure(interfce, null);
	}

	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 * InfrastructureRequiredRole} connection with the name <code>name</code>
	 * between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * Setting the <code>name</code> is important for referencing if the role is
	 * used in a connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>interfce</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component requiresInfrastructure(InfrastructureInterface interfce, String name) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		InfrastructureRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setRequiredInterface__InfrastructureRequiredRole(interfce);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	// emits event group (source role)
	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * Create a new event group by using the componentModel.factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newEventGroup()
	 */
	public Component emits(EventGroupCreator eventGroup) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		return emits(eventGroup, null);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}
	 * connection with the name <code>name</code> between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component. Setting the
	 * <code>name</code> is important for referencing if the role is used in a
	 * connector of a composite component and/or subsystem later on.
	 * <p>
	 * Create a new event group by using the componentModel.factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#newEventGroup()
	 */
	public Component emits(EventGroupCreator eventGroup, String name) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		EventGroup eg = eventGroup.build();
		this.repository.addInterface(eg);
		return emits(eg, name);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * An existing <code>eventGroup</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component emits(EventGroup eventGroup) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		return emits(eventGroup, null);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}
	 * connection with the name <code>name</code> between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component. Setting the
	 * <code>name</code> is important for referencing if the role is used in a
	 * connector of a composite component and/or subsystem later on.
	 * <p>
	 * An existing <code>eventGroup</code> can be fetched from the repository using
	 * the componentModel.factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param eventGroup
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component emits(EventGroup eventGroup, String name) {
		Objects.requireNonNull(eventGroup, "eventGroup must not be null");
		SourceRole requiredRole = RepositoryFactory.eINSTANCE.createSourceRole();
		if (name != null)
			requiredRole.setEntityName(name);
		requiredRole.setEventGroup__SourceRole(eventGroup);
		this.requiredRoles.add(requiredRole);
		this.repository.addRequiredRole(requiredRole);
		return this;
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
	 * ResourceRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface
	 * ResourceInterface} <code>resourceInterface</code> and adds it to the
	 * component.
	 * <p>
	 * An existing <code>resourceInterface</code> can be fetched from the repository
	 * using the componentModel.factory, i.e. <code>create.fetchOfResourceInterface(name)</code>.
	 * </p>
	 * 
	 * @param resourceInterface
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfResourceInterface(String)
	 */
	public Component requiresResource(ResourceInterface resourceInterface) {
		Objects.requireNonNull(resourceInterface, "resourceInterface must not be null");
		return requiresResource(resourceInterface, null);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
	 * ResourceRequiredRole} connection with the name <code>name</code> between the
	 * component and the
	 * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface
	 * ResourceInterface} <code>resourceInterface</code> and adds it to the
	 * component. Setting the <code>name</code> is important for referencing if the
	 * role is used in a connector of a composite component and/or subsystem later
	 * on.
	 * <p>
	 * An existing <code>resourceInterface</code> can be fetched from the repository
	 * using the componentModel.factory, i.e. <code>create.fetchOfResourceInterface(name)</code>.
	 * </p>
	 * 
	 * @param resourceInterface
	 * @param name
	 * @return this component
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfResourceInterface(String)
	 */
	public Component requiresResource(ResourceInterface resourceInterface, String name) {
		Objects.requireNonNull(resourceInterface, "resourceInterface must not be null");
		ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
		if (name != null)
			rrr.setEntityName(name);
		org.palladiosimulator.pcm.resourcetype.ResourceInterface resInt = this.repository
				.getResourceInterface(resourceInterface);
		rrr.setRequiredResourceInterface__ResourceRequiredRole(resInt);
		this.resourceRequiredRoles.add(rrr);
		this.repository.addResourceRequiredRole(rrr);
		return this;
	}
}
