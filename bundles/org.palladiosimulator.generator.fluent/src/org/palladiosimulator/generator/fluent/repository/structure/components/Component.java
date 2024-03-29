package org.palladiosimulator.generator.fluent.repository.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface;
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

/**
 * This class provides the general infrastructure of a component, i.e. BasicComponent
 * CompositeComponent, SubSystem, CompleteComponentTypea and ProvidesComponentType. It provides the
 * implementation of the methods for creating role connections (ProvidedRole, RequiredRole,
 * ResourceRequiredRole).
 *
 * @author Louisa Lambrecht
 */
public abstract class Component extends RepositoryEntity {

    protected List<ProvidedRole> providedRoles = new ArrayList<>();
    protected List<RequiredRole> requiredRoles = new ArrayList<>();
    protected List<ResourceRequiredRole> resourceRequiredRoles = new ArrayList<>();

    @Override
    public abstract RepositoryComponent build();

    // ------------ providing roles ------------
    // provides operation interface
    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * Create a new operation interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newOperationInterface()</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newOperationInterface()
     */
    public Component provides(final OperationInterfaceCreator interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.provides(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} connection with the name <code>name</code> between the component and
     * the {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component. Setting the <code>name</code> is
     * important for referencing if the role is used in a connector of a composite component and/or
     * subsystem later on.
     * <p>
     * Create a new operation interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newOperationInterface()</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newOperationInterface()
     */
    public Component provides(final OperationInterfaceCreator interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final OperationInterface i = interfce.build();
        this.repository.addInterface(i);
        return this.provides(i, name);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfOperationInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
     */
    public Component provides(final OperationInterface interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.provides(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} connection with the name <code>name</code> between the component and
     * the {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component. Setting the <code>name</code> is
     * important for referencing if the role is used in a connector of a composite component and/or
     * subsystem later on.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfOperationInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
     */
    public Component provides(final OperationInterface interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        if (name != null) {
            providedRole.setEntityName(name);
        }
        providedRole.setProvidedInterface__OperationProvidedRole(interfce);
        this.providedRoles.add(providedRole);
        this.repository.addProvidedRole(providedRole);
        return this;
    }

    // provides infrastructure interface
    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface InfrastructureInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * Create a new infrastructure interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newInfrastructureInterface()</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInfrastructureInterface()
     */
    public Component providesInfrastructure(final InfrastructureInterfaceCreator interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.providesInfrastructure(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} connection with the name <code>name</code> between the component
     * and the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} <code>interfce</code> and adds it to the component. Setting the
     * <code>name</code> is important for referencing if the role is used in a connector of a
     * composite component and/or subsystem later on.
     * <p>
     * Create a new infrastructure interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newInfrastructureInterface()</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInfrastructureInterface()
     */
    public Component providesInfrastructure(final InfrastructureInterfaceCreator interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final InfrastructureInterface i = interfce.build();
        this.repository.addInterface(i);
        return this.providesInfrastructure(i, name);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface InfrastructureInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfInfrastructureInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
     */
    public Component providesInfrastructure(final InfrastructureInterface interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.providesInfrastructure(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} connection with the name <code>name</code> between the component
     * and the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} <code>interfce</code> and adds it to the component. Setting the
     * <code>name</code> is important for referencing if the role is used in a connector of a
     * composite component and/or subsystem later on.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfInfrastructureInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
     */
    public Component providesInfrastructure(final InfrastructureInterface interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final InfrastructureProvidedRole providedRole = RepositoryFactory.eINSTANCE.createInfrastructureProvidedRole();
        if (name != null) {
            providedRole.setEntityName(name);
        }
        providedRole.setProvidedInterface__InfrastructureProvidedRole(interfce);
        this.providedRoles.add(providedRole);
        this.repository.addProvidedRole(providedRole);
        return this;
    }

    // handles event group (sink role)
    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} connection between
     * the component and the {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
     * <code>eventGroup</code> and adds it to the component.
     * <p>
     * Create a new event group by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newEventGroup()</code>.
     * </p>
     *
     * @param eventGroup
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newEventGroup()
     */
    public Component handles(final EventGroupCreator eventGroup) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        return this.handles(eventGroup, null);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} connection with the
     * name <code>name</code> between the component and the
     * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup} <code>eventGroup</code>
     * and adds it to the component. Setting the <code>name</code> is important for referencing if
     * the role is used in a connector of a composite component and/or subsystem later on.
     * <p>
     * Create a new event group by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newEventGroup()</code>.
     * </p>
     *
     * @param eventGroup
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newEventGroup()
     */
    public Component handles(final EventGroupCreator eventGroup, final String name) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        final EventGroup eg = eventGroup.build();
        this.repository.addInterface(eg);
        return this.handles(eg, name);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} connection between
     * the component and the {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup}
     * <code>eventGroup</code> and adds it to the component.
     * <p>
     * An existing <code>eventGroup</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfEventGroup(name)</code>.
     * </p>
     *
     * @param eventGroup
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public Component handles(final EventGroup eventGroup) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        return this.handles(eventGroup, null);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} connection with the
     * name <code>name</code> between the component and the
     * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup} <code>eventGroup</code>
     * and adds it to the component. Setting the <code>name</code> is important for referencing if
     * the role is used in a connector of a composite component and/or subsystem later on.
     * <p>
     * An existing <code>eventGroup</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfEventGroup(name)</code>.
     * </p>
     *
     * @param eventGroup
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public Component handles(final EventGroup eventGroup, final String name) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        final SinkRole providedRole = RepositoryFactory.eINSTANCE.createSinkRole();
        if (name != null) {
            providedRole.setEntityName(name);
        }
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
     * {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * Create a new operation interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newOperationInterface()</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newOperationInterface()
     */
    public Component requires(final OperationInterfaceCreator interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.requires(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} connection with the name <code>name</code> between the component and
     * the {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component. Setting the <code>name</code> is
     * important for referencing if the role is used in a connector of a composite component and/or
     * subsystem later on.
     * <p>
     * Create a new operation interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newOperationInterface()</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newOperationInterface()
     */
    public Component requires(final OperationInterfaceCreator interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final OperationInterface i = interfce.build();
        this.repository.addInterface(i);
        return this.requires(i, name);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfOperationInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
     */
    public Component requires(final OperationInterface interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.requires(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} connection with the name <code>name</code> between the component and
     * the {@link org.palladiosimulator.pcm.repository.OperationInterface OperationInterface}
     * <code>interfce</code> and adds it to the component. Setting the <code>name</code> is
     * important for referencing if the role is used in a connector of a composite component and/or
     * subsystem later on.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfOperationInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfOperationInterface(String)
     */
    public Component requires(final OperationInterface interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        if (name != null) {
            requiredRole.setEntityName(name);
        }
        requiredRole.setRequiredInterface__OperationRequiredRole(interfce);
        this.requiredRoles.add(requiredRole);
        this.repository.addRequiredRole(requiredRole);
        return this;
    }

    // require infrastructure interface
    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface InfrastructureInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * Create a new infrastructure interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newInfrastructureInterface()</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInfrastructureInterface()
     */
    public Component requiresInfrastructure(final InfrastructureInterfaceCreator interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.requiresInfrastructure(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} connection with the name <code>name</code> between the component
     * and the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} <code>interfce</code> and adds it to the component. Setting the
     * <code>name</code> is important for referencing if the role is used in a connector of a
     * composite component and/or subsystem later on.
     * <p>
     * Create a new infrastructure interface by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newInfrastructureInterface()</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newInfrastructureInterface()
     */
    public Component requiresInfrastructure(final InfrastructureInterfaceCreator interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final InfrastructureInterface i = interfce.build();
        this.repository.addInterface(i);
        return this.requiresInfrastructure(i, name);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface InfrastructureInterface}
     * <code>interfce</code> and adds it to the component.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfInfrastructureInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
     */
    public Component requiresInfrastructure(final InfrastructureInterface interfce) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        return this.requiresInfrastructure(interfce, null);
    }

    /**
     * Creates an {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} connection with the name <code>name</code> between the component
     * and the {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
     * InfrastructureInterface} <code>interfce</code> and adds it to the component. Setting the
     * <code>name</code> is important for referencing if the role is used in a connector of a
     * composite component and/or subsystem later on.
     * <p>
     * An existing <code>interfce</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfInfrastructureInterface(name)</code>.
     * </p>
     *
     * @param interfce
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfInfrastructureInterface(String)
     */
    public Component requiresInfrastructure(final InfrastructureInterface interfce, final String name) {
        IllegalArgumentException.throwIfNull(interfce, "interfce must not be null");
        final InfrastructureRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createInfrastructureRequiredRole();
        if (name != null) {
            requiredRole.setEntityName(name);
        }
        requiredRole.setRequiredInterface__InfrastructureRequiredRole(interfce);
        this.requiredRoles.add(requiredRole);
        this.repository.addRequiredRole(requiredRole);
        return this;
    }

    // emits event group (source role)
    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} connection
     * between the component and the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} <code>eventGroup</code> and adds it to the component.
     * <p>
     * Create a new event group by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newEventGroup()</code>.
     * </p>
     *
     * @param eventGroup
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newEventGroup()
     */
    public Component emits(final EventGroupCreator eventGroup) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        return this.emits(eventGroup, null);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} connection with
     * the name <code>name</code> between the component and the
     * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup} <code>eventGroup</code>
     * and adds it to the component. Setting the <code>name</code> is important for referencing if
     * the role is used in a connector of a composite component and/or subsystem later on.
     * <p>
     * Create a new event group by using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newEventGroup()</code>.
     * </p>
     *
     * @param eventGroup
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#newEventGroup()
     */
    public Component emits(final EventGroupCreator eventGroup, final String name) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        final EventGroup eg = eventGroup.build();
        this.repository.addInterface(eg);
        return this.emits(eg, name);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} connection
     * between the component and the {@link org.palladiosimulator.pcm.repository.EventGroup
     * EventGroup} <code>eventGroup</code> and adds it to the component.
     * <p>
     * An existing <code>eventGroup</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfEventGroup(name)</code>.
     * </p>
     *
     * @param eventGroup
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public Component emits(final EventGroup eventGroup) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        return this.emits(eventGroup, null);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} connection with
     * the name <code>name</code> between the component and the
     * {@link org.palladiosimulator.pcm.repository.EventGroup EventGroup} <code>eventGroup</code>
     * and adds it to the component. Setting the <code>name</code> is important for referencing if
     * the role is used in a connector of a composite component and/or subsystem later on.
     * <p>
     * An existing <code>eventGroup</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfEventGroup(name)</code>.
     * </p>
     *
     * @param eventGroup
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfEventGroup(String)
     */
    public Component emits(final EventGroup eventGroup, final String name) {
        IllegalArgumentException.throwIfNull(eventGroup, "eventGroup must not be null");
        final SourceRole requiredRole = RepositoryFactory.eINSTANCE.createSourceRole();
        if (name != null) {
            requiredRole.setEntityName(name);
        }
        requiredRole.setEventGroup__SourceRole(eventGroup);
        this.requiredRoles.add(requiredRole);
        this.repository.addRequiredRole(requiredRole);
        return this;
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} connection between the component and the
     * {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface ResourceInterface}
     * <code>resourceInterface</code> and adds it to the component.
     * <p>
     * An existing <code>resourceInterface</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfResourceInterface(name)</code>.
     * </p>
     *
     * @param resourceInterface
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfResourceInterface(String)
     */
    public Component requiresResource(final ResourceInterface resourceInterface) {
        IllegalArgumentException.throwIfNull(resourceInterface, "resourceInterface must not be null");
        return this.requiresResource(resourceInterface, null);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} connection with the name <code>name</code> between the component and
     * the {@link org.palladiosimulator.pcm.resourcetype.ResourceInterface ResourceInterface}
     * <code>resourceInterface</code> and adds it to the component. Setting the <code>name</code> is
     * important for referencing if the role is used in a connector of a composite component and/or
     * subsystem later on.
     * <p>
     * An existing <code>resourceInterface</code> can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfResourceInterface(name)</code>.
     * </p>
     *
     * @param resourceInterface
     * @param name
     * @return this component
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfResourceInterface(String)
     */
    public Component requiresResource(final ResourceInterface resourceInterface, final String name) {
        IllegalArgumentException.throwIfNull(resourceInterface, "resourceInterface must not be null");
        final ResourceRequiredRole rrr = EntityFactory.eINSTANCE.createResourceRequiredRole();
        if (name != null) {
            rrr.setEntityName(name);
        }
        final org.palladiosimulator.pcm.resourcetype.ResourceInterface resInt = this.repository
            .getResourceInterface(resourceInterface);
        rrr.setRequiredResourceInterface__ResourceRequiredRole(resInt);
        this.resourceRequiredRoles.add(rrr);
        this.repository.addResourceRequiredRole(rrr);
        return this;
    }
}
