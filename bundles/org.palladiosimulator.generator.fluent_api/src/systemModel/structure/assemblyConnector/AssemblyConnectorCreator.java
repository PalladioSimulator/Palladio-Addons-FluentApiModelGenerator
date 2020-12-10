package systemModel.structure.assemblyConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class AssemblyConnectorCreator extends SystemEntity{
	
	private AssemblyContext requiringContext;
	private OperationRequiredRole requiredRole;
	private AssemblyContext providingContext;
	private OperationProvidedRole providedRole;
	
	public AssemblyConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}

	public OperationRequiredRoleCreator withRequiringAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		var creator = this;
		return new OperationRequiredRoleCreator(new IContextRequiredRoleCombinator() {
			
			@Override
			public AssemblyConnectorCreator CombineContextAndRequiredRole(AssemblyContext reqContext, OperationRequiredRole role) {
				requiringContext = reqContext;
				requiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public OperationProvidedRoleCreator withProvidingAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		var creator = this;
		return new OperationProvidedRoleCreator(new IContextProvidedRoleCombinator() {
			
			@Override
			public AssemblyConnectorCreator CombineContextAndProvidedRole(AssemblyContext provContext, OperationProvidedRole role) {
				providingContext = provContext;
				providedRole = role;
				return creator;
			}
		}, context);
	}

	@Override
	public AssemblyConnector build() {
		var connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
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
