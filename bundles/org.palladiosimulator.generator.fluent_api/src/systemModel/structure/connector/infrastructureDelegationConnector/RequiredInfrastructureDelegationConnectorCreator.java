package systemModel.structure.connector.infrastructureDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class RequiredInfrastructureDelegationConnectorCreator extends SystemEntity {
	private InfrastructureRequiredRole outerRequiredRole;
	private InfrastructureRequiredRole innerRequiredRole;
	private AssemblyContext requringAssemblyContext;
	
	
	public RequiredInfrastructureDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(InfrastructureRequiredRole role) {
		outerRequiredRole = role;
		return this;
	}
	
	public RequiredInfrastructureDelegationConnectorCreator withOuterRequiredRole(String name) {
		InfrastructureRequiredRole role = system.getSystemInfrastructureRequiredRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterRequiredRole(role);
	}
	
	public InfrastructureRequiredRoleSelector withRequiringContext(AssemblyContext context) {
		RequiredInfrastructureDelegationConnectorCreator creator = this;
		return new InfrastructureRequiredRoleSelector(new IContextRequiredRoleCombinator() {
			
			@Override
			public RequiredInfrastructureDelegationConnectorCreator combineContextAndRequiredRole(AssemblyContext context,
					InfrastructureRequiredRole role) {
				requringAssemblyContext = context;
				innerRequiredRole = role;
				return creator;
			}
		}, context);
	}
	
	public InfrastructureRequiredRoleSelector withRequiringContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withRequiringContext(context);
	}
	
	@Override
	public RequiredInfrastructureDelegationConnector build() {
		RequiredInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredInfrastructureDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext__RequiredInfrastructureDelegationConnector(requringAssemblyContext);
		connector.setOuterRequiredRole__RequiredInfrastructureDelegationConnector(outerRequiredRole);
		connector.setInnerRequiredRole__RequiredInfrastructureDelegationConnector(innerRequiredRole);
		return connector;
	}
	
	@Override
	public RequiredInfrastructureDelegationConnectorCreator withName(String name) {
		return (RequiredInfrastructureDelegationConnectorCreator) super.withName(name);
	}

}
