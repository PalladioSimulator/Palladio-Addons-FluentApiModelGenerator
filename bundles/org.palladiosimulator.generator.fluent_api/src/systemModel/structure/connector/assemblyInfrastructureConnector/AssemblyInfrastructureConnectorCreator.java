package systemModel.structure.connector.assemblyInfrastructureConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class AssemblyInfrastructureConnectorCreator extends SystemEntity{
	
	private AssemblyContext requiringContext;
	private InfrastructureRequiredRole requiredRole;
	private AssemblyContext providingContext;
	private InfrastructureProvidedRole providedRole;
	
	public AssemblyInfrastructureConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}

	public InfrastructureRequiredRoleSelector withRequiringAssemblyContext(AssemblyContext context) {
		var creator = this;
		return new InfrastructureRequiredRoleSelector(new IContextRequiredRoleCombinator() {
			
			@Override
			public AssemblyInfrastructureConnectorCreator combineContextAndRequiredRole(AssemblyContext reqContext, InfrastructureRequiredRole role) {
				requiringContext = reqContext;
				requiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public InfrastructureRequiredRoleSelector withRequiringAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withRequiringAssemblyContext(context);
	}
	
	public InfrastructureProvidedRoleSelector withProvidingAssemblyContext(AssemblyContext context) {
		var creator = this;
		return new InfrastructureProvidedRoleSelector(new IContextProvidedRoleCombinator() {
			
			@Override
			public AssemblyInfrastructureConnectorCreator combineContextAndProvidedRole(AssemblyContext provContext, InfrastructureProvidedRole role) {
				providingContext = provContext;
				providedRole = role;
				return creator;
			}
		}, context);
	}

	public InfrastructureProvidedRoleSelector withProvidingAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withProvidingAssemblyContext(context);
	}

	@Override
	public AssemblyInfrastructureConnector build() {
		var connector = CompositionFactory.eINSTANCE.createAssemblyInfrastructureConnector();
		if (name != null) {
			connector.setEntityName(name);
		}
		connector.setRequiringAssemblyContext__AssemblyInfrastructureConnector(requiringContext);
		connector.setRequiredRole__AssemblyInfrastructureConnector(requiredRole);
		connector.setProvidingAssemblyContext__AssemblyInfrastructureConnector(providingContext);
		connector.setProvidedRole__AssemblyInfrastructureConnector(providedRole);
		return connector;
	}
	
	@Override
	public AssemblyInfrastructureConnectorCreator withName(String name) {
		return (AssemblyInfrastructureConnectorCreator) super.withName(name);
	}

}
