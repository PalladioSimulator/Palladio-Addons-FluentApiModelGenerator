package systemModel.structure;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

public class AssemblyContextCreator extends SystemEntity {
	
	private RepositoryComponent encapuslatedComponent;
	
	public AssemblyContextCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}

	public AssemblyContextCreator withEncapsulatedComponent(RepositoryComponent component) {
		this.encapuslatedComponent = component;
		return this;
	}
	
	public AssemblyContextCreator withEncapsulatedComponent(String name) {
		var component = this.system.getRepositories().stream().flatMap(x -> x.getComponents__Repository()
				.stream()).filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withEncapsulatedComponent(component);
	}
	
	@Override
	public AssemblyContext build() {
		AssemblyContext context = CompositionFactory.eINSTANCE.createAssemblyContext();
		if (name != null) {
			context.setEntityName(name);
		}
		if (encapuslatedComponent != null) {
			context.setEncapsulatedComponent__AssemblyContext(encapuslatedComponent);
		}
		return context;
	}
	
	@Override
	public AssemblyContextCreator withName(String name) {
		return (AssemblyContextCreator) super.withName(name);
		
	}

}
