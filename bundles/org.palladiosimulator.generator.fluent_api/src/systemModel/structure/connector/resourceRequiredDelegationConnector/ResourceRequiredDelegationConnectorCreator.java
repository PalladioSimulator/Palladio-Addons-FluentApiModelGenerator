package systemModel.structure.connector.resourceRequiredDelegationConnector;

import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;

import systemModel.structure.SystemCreator;

public class ResourceRequiredDelegationConnectorCreator {
	
	private SystemCreator system;	
	private ResourceRequiredRole outerRequiredRole;
	private ResourceRequiredRole innerRequiredRole;
	
	public ResourceRequiredDelegationConnectorCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(ResourceRequiredRole role) {
		outerRequiredRole = role;
		return this;
	}
	
	public ResourceRequiredDelegationConnectorCreator withOuterRequiredRole(String name) {
		ResourceRequiredRole role = system.getSystemResourceRequiredRoles().stream()
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withOuterRequiredRole(role);
	}
	
	public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(ResourceRequiredRole role) {
		innerRequiredRole = role;
		return this;
	}
	
	public ResourceRequiredDelegationConnectorCreator withInnerRequiredRole(String name) {
		ResourceRequiredRole role = system.getAssemblyContexts().stream()
				.flatMap(x -> x.getEncapsulatedComponent__AssemblyContext().getResourceRequiredRoles__ResourceInterfaceRequiringEntity().stream())
				.filter(x -> x.getEntityName().equals(name)).findFirst().get();
		return withInnerRequiredRole(role);
	}
	
	public ResourceRequiredDelegationConnector build() {
		ResourceRequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createResourceRequiredDelegationConnector();
		connector.setOuterResourceRequiredRole_ResourceRequiredDelegationConnector(outerRequiredRole);
		connector.setInnerResourceRequiredRole_ResourceRequiredDelegationConnector(innerRequiredRole);
		return connector;
	}
}
