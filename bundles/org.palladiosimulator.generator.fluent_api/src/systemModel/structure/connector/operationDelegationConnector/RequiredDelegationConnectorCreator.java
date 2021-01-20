package systemModel.structure.connector.operationDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class RequiredDelegationConnectorCreator extends SystemEntity {
	private OperationRequiredRole outerRequiredRole;
	private OperationRequiredRole innerRequiredRole;
	private AssemblyContext requringAssemblyContext;
	
	
	public RequiredDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public RequiredDelegationConnectorCreator withOuterRequiredRole(OperationRequiredRole role) {
		outerRequiredRole = role;
		return this;
	}
	
	public RequiredDelegationConnectorCreator withOuterRequiredRole(String name) {
		OperationRequiredRole role = system.getSystemRequiredRoles().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterRequiredRole(role);
	}
	
	public OperationRequiredRoleSelector withRequiringContext(AssemblyContext context) {
		RequiredDelegationConnectorCreator creator = this;
		return new OperationRequiredRoleSelector(new IContextRequiredRoleCombinator() {
			
			@Override
			public RequiredDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
					OperationRequiredRole role) {
				requringAssemblyContext = context;
				innerRequiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public OperationRequiredRoleSelector withRequiringContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withRequiringContext(context);
	}
	
	@Override
	public RequiredDelegationConnector build() {
		RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext_RequiredDelegationConnector(requringAssemblyContext);
		connector.setOuterRequiredRole_RequiredDelegationConnector(outerRequiredRole);
		connector.setInnerRequiredRole_RequiredDelegationConnector(innerRequiredRole);
		return connector;
	}
	
	@Override
	public RequiredDelegationConnectorCreator withName(String name) {
		return (RequiredDelegationConnectorCreator) super.withName(name);
	}

}
