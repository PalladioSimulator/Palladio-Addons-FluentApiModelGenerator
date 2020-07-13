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
	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
	 * OperationProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new operation interface by using the factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newOperationInterface()
	 */
	public Component provides(OperationInterfaceCreator interfce) {
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
	 * Create a new operation interface by using the factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newOperationInterface()
	 */
	public Component provides(OperationInterfaceCreator interfce, String name) {
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
	 * the factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component provides(OperationInterface interfce) {
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
	 * the factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfOperationInterface(String)
	 */
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
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 * InfrastructureProvidedRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new infrastructure interface by using the factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newInfrastructureInterface()
	 */
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce) {
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
	 * Create a new infrastructure interface by using the factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newInfrastructureInterface()
	 */
	public Component providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
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
	 * the factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component providesInfrastructure(InfrastructureInterface interfce) {
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
	 * the factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
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
	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * Create a new event group by using the factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newEventGroup()
	 */
	public Component handles(EventGroupCreator eventGroup) {
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
	 * Create a new event group by using the factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newEventGroup()
	 */
	public Component handles(EventGroupCreator eventGroup, String name) {
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
	 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component handles(EventGroup eventGroup) {
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
	 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
	 */
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
	/**
	 * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
	 * OperationRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.OperationInterface
	 * OperationInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new operation interface by using the factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newOperationInterface()
	 */
	public Component requires(OperationInterfaceCreator interfce) {
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
	 * Create a new operation interface by using the factory, i.e.
	 * <code>create.newOperationInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newOperationInterface()
	 */
	public Component requires(OperationInterfaceCreator interfce, String name) {
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
	 * the factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfOperationInterface(String)
	 */
	public Component requires(OperationInterface interfce) {
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
	 * the factory, i.e. <code>create.fetchOfOperationInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfOperationInterface(String)
	 */
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
	/**
	 * Creates an
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 * InfrastructureRequiredRole} connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
	 * InfrastructureInterface} <code>interfce</code> and adds it to the component.
	 * <p>
	 * Create a new infrastructure interface by using the factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newInfrastructureInterface()
	 */
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
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
	 * Create a new infrastructure interface by using the factory, i.e.
	 * <code>create.newInfrastructureInterface()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newInfrastructureInterface()
	 */
	public Component requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
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
	 * the factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
	public Component requiresInfrastructure(InfrastructureInterface interfce) {
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
	 * the factory, i.e. <code>create.fetchOfInfrastructureInterface(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfInfrastructureInterface(String)
	 */
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
	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}
	 * connection between the component and the
	 * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
	 * <code>eventGroup</code> and adds it to the component.
	 * <p>
	 * Create a new event group by using the factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#newEventGroup()
	 */
	public Component emits(EventGroupCreator eventGroup) {
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
	 * Create a new event group by using the factory, i.e.
	 * <code>create.newEventGroup()</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#newEventGroup()
	 */
	public Component emits(EventGroupCreator eventGroup, String name) {
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
	 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component emits(EventGroup eventGroup) {
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
	 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
	 * </p>
	 * 
	 * @param interfce
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
	 */
	public Component emits(EventGroup eventGroup, String name) {
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
	 * using the factory, i.e. <code>create.fetchOfResourceInterface(name)</code>.
	 * </p>
	 * 
	 * @param resourceInterface
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfResourceInterface(String)
	 */
	public Component requiresResource(ResourceInterface resourceInterface) {
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
	 * using the factory, i.e. <code>create.fetchOfResourceInterface(name)</code>.
	 * </p>
	 * 
	 * @param resourceInterface
	 * @param name
	 * @return the component
	 * @see factory.MyRepositoryFactory#fetchOfResourceInterface(String)
	 */
	public Component requiresResource(ResourceInterface resourceInterface, String name) {
		ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
		if (name != null)
			rrr.setEntityName(name);
		rrr.setRequiredResourceInterface__ResourceRequiredRole(resourceInterface);
		this.resourceRequiredRoles.add(rrr);
		this.repository.addResourceRequiredRole(rrr);
		return this;
	}
}
