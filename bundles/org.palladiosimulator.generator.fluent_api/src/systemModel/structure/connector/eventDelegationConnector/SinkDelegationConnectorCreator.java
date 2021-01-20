package systemModel.structure.connector.eventDelegationConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.SinkDelegationConnector;
import org.palladiosimulator.pcm.repository.SinkRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class SinkDelegationConnectorCreator extends SystemEntity {
	private SinkRole outerRole;
	private SinkRole innerRole;
	private AssemblyContext assemblyContext;
	
	
	public SinkDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public SinkDelegationConnectorCreator withOuterSinkRole(SinkRole role) {
		outerRole = role;
		return this;
	}
	
	public SinkDelegationConnectorCreator withOuterSinkRole(String name) {
		SinkRole role = system.getSystemSinkRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterSinkRole(role);
	}
	
	public SinkRoleSelector withAssemblyContext(AssemblyContext context) {
		SinkDelegationConnectorCreator creator = this;
		return new SinkRoleSelector(new IContextSinkRoleCombinator() {
			
			@Override
			public SinkDelegationConnectorCreator combineContextAndSinkRole(AssemblyContext context,
					SinkRole role) {
				assemblyContext = context;
				innerRole = role;
				return creator;
			}
		}, context);
	}
	
	public SinkRoleSelector withAssemblyContext(String name) {
		AssemblyContext context = system.getAssemblyContexts().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withAssemblyContext(context);
	}
	
	@Override
	public SinkDelegationConnector build() {
		SinkDelegationConnector connector = CompositionFactory.eINSTANCE.createSinkDelegationConnector();
		if (this.name != null ) {
			connector.setEntityName(name);
		}
		connector.setAssemblyContext__SinkDelegationConnector(assemblyContext);
		connector.setOuterSinkRole__SinkRole(outerRole);
		connector.setInnerSinkRole__SinkRole(innerRole);
		return connector;
	}
	
	@Override
	public SinkDelegationConnectorCreator withName(String name) {
		return (SinkDelegationConnectorCreator) super.withName(name);
	}

}
