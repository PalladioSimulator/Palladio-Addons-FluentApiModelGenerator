package system.structure;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
 * AssemblyContext}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
 */
public class AssemblyContextCreator extends SystemEntity {

    private RepositoryComponent encapuslatedComponent;

    public AssemblyContextCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.RepositoryComponent
     * RepositoryComponent} encapsulated by this assembly context.
     * 
     * @param component
     * @return this assembly context
     * 
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    public AssemblyContextCreator withEncapsulatedComponent(final RepositoryComponent component) {
        Objects.requireNonNull(component, "The given RepositoryComponent must not be null.");
        this.encapuslatedComponent = component;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.repository.RepositoryComponent
     * RepositoryComponent} encapsulated by this assembly context. The repositories added to the
     * system are searched for a component that matches the given name.
     * 
     * @param component
     * @return this assembly context
     * @throws NoSuchElementException
     *             Thrown if no element matches the given name
     * 
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    public AssemblyContextCreator withEncapsulatedComponent(final String name) throws NoSuchElementException {
        final RepositoryComponent component = this.system.getRepositoryComponentByName(name);
        return this.withEncapsulatedComponent(component);
    }

    @Override
    public AssemblyContext build() {
        final AssemblyContext context = CompositionFactory.eINSTANCE.createAssemblyContext();
        if (this.name != null) {
            context.setEntityName(this.name);
        }
        if (this.encapuslatedComponent != null) {
            context.setEncapsulatedComponent__AssemblyContext(this.encapuslatedComponent);
        }
        return context;
    }

    @Override
    public AssemblyContextCreator withName(final String name) {
        return (AssemblyContextCreator) super.withName(name);

    }

}
