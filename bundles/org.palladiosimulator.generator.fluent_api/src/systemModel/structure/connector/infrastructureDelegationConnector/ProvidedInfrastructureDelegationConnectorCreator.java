package systemModel.structure.connector.infrastructureDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class ProvidedInfrastructureDelegationConnectorCreator extends SystemEntity {
	private InfrastructureProvidedRole outerProvidedRole;
	private InfrastructureProvidedRole innerProvidedRole;
	private AssemblyContext providingAssemblyContext;
	
	
	public ProvidedInfrastructureDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(InfrastructureProvidedRole role) {
		outerProvidedRole = role;
		return this;
	}
	
	public ProvidedInfrastructureDelegationConnectorCreator withOuterProvidedRole(String name) {
		InfrastructureProvidedRole role = system.getSystemInfrastructureProvidedRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterProvidedRole(role);
	}
	
	public InfrastructureProvidedRoleSelector withProvidingContext(AssemblyContext context) {
		ProvidedInfrastructureDelegationConnectorCreator creator = this;
		return new InfrastructureProvidedRoleSelector(new IContextProvidedRoleCombinator() {
			
			@Override
			public ProvidedInfrastructureDelegationConnectorCreator combineContextAndProvidedRole(AssemblyContext context,
					InfrastructureProvidedRole role) {
				providingAssemblyContext = context;
				innerProvidedRole = role;
				return creator;
			}
		}, context);
	}
	
	public InfrastructureProvidedRoleSelector withProvidingContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withProvidingContext(context);
	}
	
	@Override
	public ProvidedInfrastructureDelegationConnector build() {
		ProvidedInfrastructureDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedInfrastructureDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext__ProvidedInfrastructureDelegationConnector(providingAssemblyContext);
		connector.setOuterProvidedRole__ProvidedInfrastructureDelegationConnector(outerProvidedRole);
		connector.setInnerProvidedRole__ProvidedInfrastructureDelegationConnector(innerProvidedRole);
		return connector;
	}
	
	@Override
	public ProvidedInfrastructureDelegationConnectorCreator withName(String name) {
		return (ProvidedInfrastructureDelegationConnectorCreator) super.withName(name);
	}

}
