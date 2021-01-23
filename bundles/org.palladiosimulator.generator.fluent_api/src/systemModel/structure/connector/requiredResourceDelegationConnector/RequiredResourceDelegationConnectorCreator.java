package systemModel.structure.connector.requiredResourceDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.connector.AbstractConnectorCreator;

public class RequiredResourceDelegationConnectorCreator extends AbstractConnectorCreator {
	private ResourceRequiredRole outerRequiredRole;
	private ResourceRequiredRole innerRequiredRole;
	private AssemblyContext requringAssemblyContext;
	
	
	public RequiredResourceDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(ResourceRequiredRole role) {
		outerRequiredRole = role;
		return this;
	}
	
	public RequiredResourceDelegationConnectorCreator withOuterRequiredRole(String name) {
		ResourceRequiredRole role = system.getSystemResourceRequiredRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterRequiredRole(role);
	}
	
	public ResourceRequiredRoleSelector withRequiringContext(AssemblyContext context) {
		RequiredResourceDelegationConnectorCreator creator = this;
		return new ResourceRequiredRoleSelector(new IContextRequiredRoleCombinator() {
			
			@Override
			public RequiredResourceDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
					ResourceRequiredRole role) {
				requringAssemblyContext = context;
				innerRequiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public ResourceRequiredRoleSelector withRequiringContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withRequiringContext(context);
	}
	
	@Override
	public RequiredResourceDelegationConnector build() {
		RequiredResourceDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredResourceDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext__RequiredResourceDelegationConnector(requringAssemblyContext);
		connector.setOuterRequiredRole__RequiredResourceDelegationConnector(outerRequiredRole);
		connector.setInnerRequiredRole__RequiredResourceDelegationConnector(innerRequiredRole);
		return connector;
	}
	
	@Override
	public RequiredResourceDelegationConnectorCreator withName(String name) {
		return (RequiredResourceDelegationConnectorCreator) super.withName(name);
	}

}
