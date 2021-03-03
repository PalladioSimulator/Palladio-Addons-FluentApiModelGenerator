package system.structure;

import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

public class AssemblyContextCreator extends SystemEntity {

    private RepositoryComponent encapuslatedComponent;

    public AssemblyContextCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public AssemblyContextCreator withEncapsulatedComponent(final RepositoryComponent component) {
        Objects.requireNonNull(component, "The given RepositoryComponent must not be null.");
        this.encapuslatedComponent = component;
        return this;
    }

    public AssemblyContextCreator withEncapsulatedComponent(final String name) {
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
