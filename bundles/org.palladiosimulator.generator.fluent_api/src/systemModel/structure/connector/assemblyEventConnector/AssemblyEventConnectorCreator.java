package systemModel.structure.connector.assemblyEventConnector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.AssemblyEventConnector;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;

import systemModel.structure.SystemCreator;
import systemModel.structure.connector.AbstractConnectorCreator;

public class AssemblyEventConnectorCreator extends AbstractConnectorCreator{
	
	private AssemblyContext sourceContext;
	private SourceRole sourceRole;
	private AssemblyContext sinkContext;
	private SinkRole sinkRole;
	
	public AssemblyEventConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}

	public SourceRoleSelector withSourceAssemblyContext(AssemblyContext context) {
		var creator = this;
		return new SourceRoleSelector(new IContextSourceRoleCombinator() {
			
			@Override
			public AssemblyEventConnectorCreator combineContextAndSourceRole(AssemblyContext context, SourceRole role) {
				sourceContext = context;
				sourceRole = role;
				return creator;
			}
		}, context);
	}
	
	public SourceRoleSelector withSourceAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSourceAssemblyContext(context);
	}
	
	public SinkRoleSelector withSinkAssemblyContext(AssemblyContext context) {
		var creator = this;
		return new SinkRoleSelector(new IContextSinkRoleCombinator() {
			
			@Override
			public AssemblyEventConnectorCreator combineContextAndSinkRole(AssemblyContext context, SinkRole role) {
				sinkContext = context;
				sinkRole = role;
				return creator;
			}
		}, context);
	}

	public SinkRoleSelector withSinkAssemblyContext(String name) {
		var context = this.system.getAssemblyContexts().stream().filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withSinkAssemblyContext(context);
	}

	@Override
	public AssemblyEventConnector build() {
		AssemblyEventConnector connector = CompositionFactory.eINSTANCE.createAssemblyEventConnector();
		if (name != null) {
			connector.setEntityName(name);
		}
		connector.setSourceAssemblyContext__AssemblyEventConnector(sourceContext);
		connector.setSourceRole__AssemblyEventConnector(sourceRole);
		connector.setSinkAssemblyContext__AssemblyEventConnector(sinkContext);
		connector.setSinkRole__AssemblyEventConnector(sinkRole);
		return connector;
	}
	
	@Override
	public AssemblyEventConnectorCreator withName(String name) {
		return (AssemblyEventConnectorCreator) super.withName(name);
	}

}
