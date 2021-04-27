package component.repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import component.repositoryStructure.RepositoryCreator;
import component.repositoryStructure.interfaces.EventGroupCreator;
import component.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import component.repositoryStructure.interfaces.OperationInterfaceCreator;
import shared.structure.ResourceInterface;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.CompleteComponentType
 * CompleteComponentType}. It is used to create the
 * '<em><b>CompleteComponentType</b></em>' object step-by-step, i.e.
 * '<em><b>CompleteComponentTypeCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.CompleteComponentType
 */
public class CompleteComponentTypeCreator extends Component {

    private final List<ProvidesComponentType> conformsProvidedTypes;

    public CompleteComponentTypeCreator(final RepositoryCreator repo) {
        this.repository = repo;
        this.conformsProvidedTypes = new ArrayList<>();
    }

    @Override
    public CompleteComponentTypeCreator withName(final String name) {
        return (CompleteComponentTypeCreator) super.withName(name);
    }

    // @Override
    // public CompleteComponentTypeCreator withId(String id) {
    // return (CompleteComponentTypeCreator) super.withId(id);
    // }

    // ------------ providing roles ------------
    // provides operation interface
    @Override
    public CompleteComponentTypeCreator provides(final OperationInterfaceCreator interfce) {
        return (CompleteComponentTypeCreator) super.provides(interfce);
    }

    @Override
    public CompleteComponentTypeCreator provides(final OperationInterfaceCreator interfce, final String name) {
        return (CompleteComponentTypeCreator) super.provides(interfce, name);
    }

    @Override
    public CompleteComponentTypeCreator provides(final OperationInterface interfce) {
        return (CompleteComponentTypeCreator) super.provides(interfce);
    }

    @Override
    public CompleteComponentTypeCreator provides(final OperationInterface interfce, final String name) {
        return (CompleteComponentTypeCreator) super.provides(interfce, name);
    }

    // provides infrastructure interface
    @Override
    public CompleteComponentTypeCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public CompleteComponentTypeCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce, name);
    }

    @Override
    public CompleteComponentTypeCreator providesInfrastructure(final InfrastructureInterface interfce) {
        return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public CompleteComponentTypeCreator providesInfrastructure(final InfrastructureInterface interfce,
            final String name) {
        return (CompleteComponentTypeCreator) super.providesInfrastructure(interfce, name);
    }

    // sink role: handles an event group
    @Override
    public CompleteComponentTypeCreator handles(final EventGroupCreator eventGroup) {
        return (CompleteComponentTypeCreator) super.handles(eventGroup);
    }

    @Override
    public CompleteComponentTypeCreator handles(final EventGroupCreator eventGroup, final String name) {
        return (CompleteComponentTypeCreator) super.handles(eventGroup, name);
    }

    @Override
    public CompleteComponentTypeCreator handles(final EventGroup eventGroup) {
        return (CompleteComponentTypeCreator) super.handles(eventGroup);
    }

    @Override
    public CompleteComponentTypeCreator handles(final EventGroup eventGroup, final String name) {
        return (CompleteComponentTypeCreator) super.handles(eventGroup, name);
    }

    // ------------ requiring roles ------------
    // require operation interface
    @Override
    public CompleteComponentTypeCreator requires(final OperationInterfaceCreator interfce) {
        return (CompleteComponentTypeCreator) super.requires(interfce);
    }

    @Override
    public CompleteComponentTypeCreator requires(final OperationInterfaceCreator interfce, final String name) {
        return (CompleteComponentTypeCreator) super.requires(interfce, name);
    }

    @Override
    public CompleteComponentTypeCreator requires(final OperationInterface interfce) {
        return (CompleteComponentTypeCreator) super.requires(interfce);
    }

    @Override
    public CompleteComponentTypeCreator requires(final OperationInterface interfce, final String name) {
        return (CompleteComponentTypeCreator) super.requires(interfce, name);
    }

    // require infrastructure interface
    @Override
    public CompleteComponentTypeCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public CompleteComponentTypeCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce, name);
    }

    @Override
    public CompleteComponentTypeCreator requiresInfrastructure(final InfrastructureInterface interfce) {
        return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public CompleteComponentTypeCreator requiresInfrastructure(final InfrastructureInterface interfce,
            final String name) {
        return (CompleteComponentTypeCreator) super.requiresInfrastructure(interfce, name);
    }

    // emits event group (source role)
    @Override
    public CompleteComponentTypeCreator emits(final EventGroupCreator eventGroup) {
        return (CompleteComponentTypeCreator) super.emits(eventGroup);
    }

    @Override
    public CompleteComponentTypeCreator emits(final EventGroupCreator eventGroup, final String name) {
        return (CompleteComponentTypeCreator) super.emits(eventGroup, name);
    }

    @Override
    public CompleteComponentTypeCreator emits(final EventGroup eventGroup) {
        return (CompleteComponentTypeCreator) super.emits(eventGroup);
    }

    @Override
    public CompleteComponentTypeCreator emits(final EventGroup eventGroup, final String name) {
        return (CompleteComponentTypeCreator) super.emits(eventGroup, name);
    }

    // resource required role
    @Override
    public CompleteComponentTypeCreator requiresResource(final ResourceInterface resourceInterface) {
        return (CompleteComponentTypeCreator) super.requiresResource(resourceInterface);
    }

    @Override
    public CompleteComponentTypeCreator requiresResource(final ResourceInterface resourceInterface, final String name) {
        return (CompleteComponentTypeCreator) super.requiresResource(resourceInterface, name);
    }

    // ------------ type roles ------------
    /**
     * Creates a conforming (parental) connection to the
     * <code>providesComponentType</code> and adds it to the complete component
     * type.
     * <p>
     * Provided (Component) Types abstract a component to its provided interfaces,
     * leaving its requirements and implementation details open. So, provided types
     * subsume components which offer the same functionality, but with different
     * implementations.
     * </p>
     * <p>
     * The <code>providesComponentType</code> can be created using the
     * component.factory, i.e. <code>create.newProvidesComponentType()</code>.
     * </p>
     *
     * @param providesComponentType
     * @return the complete component type in the making
     * @see component.factory.FluentRepositoryFactory#newProvidesComponentType()
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType#getParentProvidesComponentTypes()
     * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
     */
    public CompleteComponentTypeCreator conforms(final ProvidesComponentTypeCreator providesComponentType) {
        Objects.requireNonNull(providesComponentType, "providesComponentType must not be null");
        final ProvidesComponentType pct = providesComponentType.build();
        this.repository.addComponent(pct);
        return this.conforms(pct);
    }

    /**
     * Creates a conforming (parental) connection to the
     * <code>providesComponentType</code> and adds it to the complete component
     * type.
     * <p>
     * Provided (Component) Types abstract a component to its provided interfaces,
     * leaving its requirements and implementation details open. So, provided types
     * subsume components which offer the same functionality, but with different
     * implementations.
     * </p>
     * <p>
     * An existing <code>providesComponentType</code> can be fetched from the
     * repository using the component.factory, i.e.
     * <code>create.fetchOfProvidesComponentType(name)</code>.
     * </p>
     *
     * @param providesComponentType
     * @return the complete component type in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfProvidesComponentType(String)
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType#getParentProvidesComponentTypes()
     * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
     */
    public CompleteComponentTypeCreator conforms(final ProvidesComponentType providesComponentType) {
        Objects.requireNonNull(providesComponentType, "providesComponentType must not be null");
        this.conformsProvidedTypes.add(providesComponentType);
        return this;
    }

    @Override
    public CompleteComponentType build() {
        final CompleteComponentType cct = RepositoryFactory.eINSTANCE.createCompleteComponentType();
        if (this.name != null) {
            cct.setEntityName(this.name);
            // if (id != null)
            // cct.setId(id);
        }

        cct.getProvidedRoles_InterfaceProvidingEntity().addAll(this.providedRoles);
        cct.getRequiredRoles_InterfaceRequiringEntity().addAll(this.requiredRoles);
        cct.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(this.resourceRequiredRoles);
        cct.getParentProvidesComponentTypes().addAll(this.conformsProvidedTypes);

        return cct;
    }

}
