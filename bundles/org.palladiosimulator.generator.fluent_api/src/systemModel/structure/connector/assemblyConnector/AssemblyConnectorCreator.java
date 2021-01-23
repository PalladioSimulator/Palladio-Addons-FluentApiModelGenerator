package systemModel.structure.connector.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.connector.AbstractConnectorCreator;

public class AssemblyConnectorCreator extends AbstractConnectorCreator{
	
	private AssemblyContext requiringContext;
	private OperationRequiredRole requiredRole;
	private AssemblyContext providingContext;
	private OperationProvidedRole providedRole;
	
	public AssemblyConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}

	public OperationRequiredRoleSelector withRequiringAssemblyContext(AssemblyContext context) {
		AssemblyConnectorCreator creator = this;
		return new OperationRequiredRoleSelector(new IContextRequiredRoleCombinator() {
			
			@Override
			public AssemblyConnectorCreator combineContextAndRequiredRole(AssemblyContext reqContext, OperationRequiredRole role) {
				requiringContext = reqContext;
				requiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public OperationRequiredRoleSelector withRequiringAssemblyContext(String name) {
		AssemblyContext context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withRequiringAssemblyContext(context);
	}
	
	public OperationProvidedRoleSelector withProvidingAssemblyContext(AssemblyContext context) {
		AssemblyConnectorCreator creator = this;
		return new OperationProvidedRoleSelector(new IContextProvidedRoleCombinator() {
			
			@Override
			public AssemblyConnectorCreator combineContextAndProvidedRole(AssemblyContext provContext, OperationProvidedRole role) {
				providingContext = provContext;
				providedRole = role;
				return creator;
			}
		}, context);
	}

	public OperationProvidedRoleSelector withProvidingAssemblyContext(String name) {
		AssemblyContext context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withProvidingAssemblyContext(context);
	}

	@Override
	public AssemblyConnector build() {
		AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
		if (name != null) {
			connector.setEntityName(name);
		}
		connector.setRequiringAssemblyContext_AssemblyConnector(requiringContext);
		connector.setRequiredRole_AssemblyConnector(requiredRole);
		connector.setProvidingAssemblyContext_AssemblyConnector(providingContext);
		connector.setProvidedRole_AssemblyConnector(providedRole);
		return connector;
	}
	
	@Override
	public AssemblyConnectorCreator withName(String name) {
		return (AssemblyConnectorCreator) super.withName(name);
	}

}
