package systemModel.structure.connector.delegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class ProvidedDelegationConnectorCreator extends SystemEntity {
	private OperationProvidedRole outerProvidedRole;
	private OperationProvidedRole innerProvidedRole;
	private AssemblyContext providingAssemblyContext;
	
	
	public ProvidedDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public ProvidedDelegationConnectorCreator withOuterProvidedRole(OperationProvidedRole role) {
		outerProvidedRole = role;
		return this;
	}
	
	public ProvidedDelegationConnectorCreator withOuterProvidedRole(String name) {
		OperationProvidedRole role = system.getSystemProvidedRoles().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterProvidedRole(role);
	}
	
	public OperationProvidedRoleSelector withProvidingContext(AssemblyContext context) {
		ProvidedDelegationConnectorCreator creator = this;
		return new OperationProvidedRoleSelector(new IContextProvidedRoleCombinator() {
			
			@Override
			public ProvidedDelegationConnectorCreator combineContextAndProvidedRole(AssemblyContext context,
					OperationProvidedRole role) {
				providingAssemblyContext = context;
				innerProvidedRole = role;
				return creator;
			}
		}, context);
	}
	
	public OperationProvidedRoleSelector withProvidingContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withProvidingContext(context);
	}
	
	@Override
	public ProvidedDelegationConnector build() {
		ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext_ProvidedDelegationConnector(providingAssemblyContext);
		connector.setOuterProvidedRole_ProvidedDelegationConnector(outerProvidedRole);
		connector.setInnerProvidedRole_ProvidedDelegationConnector(innerProvidedRole);
		return connector;
	}
	
	@Override
	public ProvidedDelegationConnectorCreator withName(String name) {
		return (ProvidedDelegationConnectorCreator) super.withName(name);
	}

}
