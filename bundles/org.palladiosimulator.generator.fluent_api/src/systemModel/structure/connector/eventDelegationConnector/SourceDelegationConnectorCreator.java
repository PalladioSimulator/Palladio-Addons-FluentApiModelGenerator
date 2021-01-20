package systemModel.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SourceDelegationConnector;
import org.palladiosimulator.pcm.repository.SourceRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class SourceDelegationConnectorCreator extends SystemEntity {
	private SourceRole outerRole;
	private SourceRole innerRole;
	private AssemblyContext assemblyContext;
	
	
	public SourceDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public SourceDelegationConnectorCreator withOuterSourceRole(SourceRole role) {
		outerRole = role;
		return this;
	}
	
	public SourceDelegationConnectorCreator withOuterSourceRole(String name) {
		SourceRole role = system.getSystemSourceRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterSourceRole(role);
	}
	
	public SourceRoleSelector withAssemblyContext(AssemblyContext context) {
		SourceDelegationConnectorCreator creator = this;
		return new SourceRoleSelector(new IContextSourceRoleCombinator() {
			
			@Override
			public SourceDelegationConnectorCreator combineContextAndSourceRole(AssemblyContext context,
					SourceRole role) {
				assemblyContext = context;
				innerRole = role;
				return creator;
			}
		}, context);
	}
	
	public SourceRoleSelector withAssemblyContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withAssemblyContext(context);
	}
	
	@Override
	public SourceDelegationConnector build() {
		SourceDelegationConnector connector = CompositionFactory.eINSTANCE.createSourceDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext__SourceDelegationConnector(assemblyContext);
		connector.setOuterSourceRole__SourceRole(outerRole);
		connector.setInnerSourceRole__SourceRole(innerRole);
		return connector;
	}
	
	@Override
	public SourceDelegationConnectorCreator withName(String name) {
		return (SourceDelegationConnectorCreator) super.withName(name);
	}

}
