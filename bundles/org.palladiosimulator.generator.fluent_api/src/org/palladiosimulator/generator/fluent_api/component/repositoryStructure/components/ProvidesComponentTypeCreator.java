package org.palladiosimulator.generator.fluent_api.component.repositoryStructure.components;

import org.palladiosimulator.generator.fluent_api.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent_api.component.repositoryStructure.interfaces.EventGroupCreator;
import org.palladiosimulator.generator.fluent_api.component.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import org.palladiosimulator.generator.fluent_api.component.repositoryStructure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent_api.shared.structure.ResourceInterface;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.ProvidesComponentType
 * ProvidesComponentType}. It is used to create the
 * '<em><b>ProvidesComponentType</b></em>' object step-by-step, i.e.
 * '<em><b>ProvidesComponentTypeCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
 */
public class ProvidesComponentTypeCreator extends Component {

    public ProvidesComponentTypeCreator(final RepositoryCreator repo) {
        repository = repo;
    }

    @Override
    public ProvidesComponentTypeCreator withName(final String name) {
        return (ProvidesComponentTypeCreator) super.withName(name);
    }

    // @Override
    // public ProvidesComponentTypeCreator withId(String id) {
    // return (ProvidesComponentTypeCreator) super.withId(id);
    // }

    // ------------ providing roles ------------
    // provides operation interface
    @Override
    public ProvidesComponentTypeCreator provides(final OperationInterfaceCreator interfce) {
        return (ProvidesComponentTypeCreator) super.provides(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator provides(final OperationInterfaceCreator interfce, final String name) {
        return (ProvidesComponentTypeCreator) super.provides(interfce, name);
    }

    @Override
    public ProvidesComponentTypeCreator provides(final OperationInterface interfce) {
        return (ProvidesComponentTypeCreator) super.provides(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator provides(final OperationInterface interfce, final String name) {
        return (ProvidesComponentTypeCreator) super.provides(interfce, name);
    }

    // provides infrastructure interface
    @Override
    public ProvidesComponentTypeCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator providesInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce, name);
    }

    @Override
    public ProvidesComponentTypeCreator providesInfrastructure(final InfrastructureInterface interfce) {
        return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator providesInfrastructure(final InfrastructureInterface interfce,
            final String name) {
        return (ProvidesComponentTypeCreator) super.providesInfrastructure(interfce, name);
    }

    // sink role: handles an event group
    @Override
    public ProvidesComponentTypeCreator handles(final EventGroupCreator eventGroup) {
        return (ProvidesComponentTypeCreator) super.handles(eventGroup);
    }

    @Override
    public ProvidesComponentTypeCreator handles(final EventGroupCreator eventGroup, final String name) {
        return (ProvidesComponentTypeCreator) super.handles(eventGroup, name);
    }

    @Override
    public ProvidesComponentTypeCreator handles(final EventGroup eventGroup) {
        return (ProvidesComponentTypeCreator) super.handles(eventGroup);
    }

    @Override
    public ProvidesComponentTypeCreator handles(final EventGroup eventGroup, final String name) {
        return (ProvidesComponentTypeCreator) super.handles(eventGroup, name);
    }

    // ------------ requiring roles ------------
    // require operation interface
    @Override
    public ProvidesComponentTypeCreator requires(final OperationInterfaceCreator interfce) {
        return (ProvidesComponentTypeCreator) super.requires(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator requires(final OperationInterfaceCreator interfce, final String name) {
        return (ProvidesComponentTypeCreator) super.requires(interfce, name);
    }

    @Override
    public ProvidesComponentTypeCreator requires(final OperationInterface interfce) {
        return (ProvidesComponentTypeCreator) super.requires(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator requires(final OperationInterface interfce, final String name) {
        return (ProvidesComponentTypeCreator) super.requires(interfce, name);
    }

    // require infrastructure interface
    @Override
    public ProvidesComponentTypeCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce) {
        return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator requiresInfrastructure(final InfrastructureInterfaceCreator interfce,
            final String name) {
        return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce, name);
    }

    @Override
    public ProvidesComponentTypeCreator requiresInfrastructure(final InfrastructureInterface interfce) {
        return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce);
    }

    @Override
    public ProvidesComponentTypeCreator requiresInfrastructure(final InfrastructureInterface interfce,
            final String name) {
        return (ProvidesComponentTypeCreator) super.requiresInfrastructure(interfce, name);
    }

    // emits event group (source role)
    @Override
    public ProvidesComponentTypeCreator emits(final EventGroupCreator eventGroup) {
        return (ProvidesComponentTypeCreator) super.emits(eventGroup);
    }

    @Override
    public ProvidesComponentTypeCreator emits(final EventGroupCreator eventGroup, final String name) {
        return (ProvidesComponentTypeCreator) super.emits(eventGroup, name);
    }

    @Override
    public ProvidesComponentTypeCreator emits(final EventGroup eventGroup) {
        return (ProvidesComponentTypeCreator) super.emits(eventGroup);
    }

    @Override
    public ProvidesComponentTypeCreator emits(final EventGroup eventGroup, final String name) {
        return (ProvidesComponentTypeCreator) super.emits(eventGroup, name);
    }

    // resource required role
    @Override
    public ProvidesComponentTypeCreator requiresResource(final ResourceInterface resourceInterface) {
        return (ProvidesComponentTypeCreator) super.requiresResource(resourceInterface);
    }

    @Override
    public ProvidesComponentTypeCreator requiresResource(final ResourceInterface resourceInterface, final String name) {
        return (ProvidesComponentTypeCreator) super.requiresResource(resourceInterface, name);
    }

    @Override
    public ProvidesComponentType build() {
        final ProvidesComponentType pct = RepositoryFactory.eINSTANCE.createProvidesComponentType();
        if (name != null) {
            pct.setEntityName(name);
            // if (id != null)
            // pct.setId(id);
        }

        pct.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
        pct.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
        pct.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

        return pct;
    }

}
