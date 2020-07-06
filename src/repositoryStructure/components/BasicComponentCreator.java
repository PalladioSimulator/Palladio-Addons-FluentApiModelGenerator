package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.ComponentType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

import apiControlFlowInterfaces.Action.Seff;
import apiControlFlowInterfaces.VariableUsageCreation.Basic;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class BasicComponentCreator extends Component {

	private ComponentType type;
	private List<CompleteComponentType> conformsCompleteTypes;
	private List<PassiveResource> passiveResources;
	private List<ServiceEffectSpecification> seffs;
	private List<VariableUsage> componentParameterUsages;

	public BasicComponentCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.conformsCompleteTypes = new ArrayList<>();
		this.passiveResources = new ArrayList<>();
		this.seffs = new ArrayList<>();
		this.componentParameterUsages = new ArrayList<>();

	}

	@Override
	public BasicComponentCreator withName(String name) {
		return (BasicComponentCreator) super.withName(name);
	}

//	@Override
//	public BasicComponentCreator withId(String id) {
//		return (BasicComponentCreator) super.withId(id);
//	}

	// business vs infrstructure component
	public BasicComponentCreator ofType(ComponentType type) {
		this.type = type;
		return this;
	}

	// ------------ providing roles ------------
	// provides operation interface
	@Override
	public BasicComponentCreator provides(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.provides(interfce);
	}

	@Override
	public BasicComponentCreator provides(OperationInterfaceCreator interfce, String name) {
		return (BasicComponentCreator) super.provides(interfce, name);
	}

	@Override
	public BasicComponentCreator provides(OperationInterface interfce) {
		return (BasicComponentCreator) super.provides(interfce);
	}

	@Override
	public BasicComponentCreator provides(OperationInterface interfce, String name) {
		return (BasicComponentCreator) super.provides(interfce, name);
	}

	// provides infrastructure interface
	@Override
	public BasicComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public BasicComponentCreator providesInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce, name);
	}

	@Override
	public BasicComponentCreator providesInfrastructure(InfrastructureInterface interfce) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce);
	}

	@Override
	public BasicComponentCreator providesInfrastructure(InfrastructureInterface interfce, String name) {
		return (BasicComponentCreator) super.providesInfrastructure(interfce, name);
	}

	// sink role: handles an event group
	@Override
	public BasicComponentCreator handles(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.handles(eventGroup);
	}

	@Override
	public BasicComponentCreator handles(EventGroupCreator eventGroup, String name) {
		return (BasicComponentCreator) super.handles(eventGroup, name);
	}

	@Override
	public BasicComponentCreator handles(EventGroup eventGroup) {
		return (BasicComponentCreator) super.handles(eventGroup);
	}

	@Override
	public BasicComponentCreator handles(EventGroup eventGroup, String name) {
		return (BasicComponentCreator) super.handles(eventGroup, name);
	}

	// ------------ requiring roles ------------
	// require operation interface
	@Override
	public BasicComponentCreator requires(OperationInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requires(interfce);
	}

	@Override
	public BasicComponentCreator requires(OperationInterfaceCreator interfce, String name) {
		return (BasicComponentCreator) super.requires(interfce, name);
	}

	@Override
	public BasicComponentCreator requires(OperationInterface interfce) {
		return (BasicComponentCreator) super.requires(interfce);
	}

	@Override
	public BasicComponentCreator requires(OperationInterface interfce, String name) {
		return (BasicComponentCreator) super.requires(interfce, name);
	}

	// require infrastructure interface
	@Override
	public BasicComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public BasicComponentCreator requiresInfrastructure(InfrastructureInterfaceCreator interfce, String name) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce, name);
	}

	@Override
	public BasicComponentCreator requiresInfrastructure(InfrastructureInterface interfce) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce);
	}

	@Override
	public BasicComponentCreator requiresInfrastructure(InfrastructureInterface interfce, String name) {
		return (BasicComponentCreator) super.requiresInfrastructure(interfce, name);
	}

	// emits event group (source role)
	@Override
	public BasicComponentCreator emits(EventGroupCreator eventGroup) {
		return (BasicComponentCreator) super.emits(eventGroup);
	}

	@Override
	public BasicComponentCreator emits(EventGroupCreator eventGroup, String name) {
		return (BasicComponentCreator) super.emits(eventGroup, name);
	}

	@Override
	public BasicComponentCreator emits(EventGroup eventGroup) {
		return (BasicComponentCreator) super.emits(eventGroup);
	}

	@Override
	public BasicComponentCreator emits(EventGroup eventGroup, String name) {
		return (BasicComponentCreator) super.emits(eventGroup, name);
	}

	// resource required role
	@Override
	public BasicComponentCreator requiresResource(ResourceInterface resourceInterface) {
		return (BasicComponentCreator) super.requiresResource(resourceInterface);
	}

	// ------------ other listing characteristics ------------
	// parent complete component types
	public BasicComponentCreator conforms(CompleteComponentTypeCreator completeComponentType) {
		CompleteComponentType cct = completeComponentType.build();
		this.conformsCompleteTypes.add(cct);
		this.repository.addComponent(cct);
		return this;
	}

	// Passive resources
	public BasicComponentCreator withPassiveResource(String capacity_stochasticExpression,
			ResourceTimeoutFailureType failureType) {
		PCMRandomVariable randVar = CoreFactory.eINSTANCE.createPCMRandomVariable();
		randVar.setSpecification(capacity_stochasticExpression);

		PassiveResource pass = RepositoryFactory.eINSTANCE.createPassiveResource();
		pass.setCapacity_PassiveResource(randVar);
		pass.setResourceTimeoutFailureType__PassiveResource(failureType);

		this.passiveResources.add(pass);
		return this;
	}

	// SEFFs
	public BasicComponentCreator withServiceEffectSpecification(Seff seff) {
		ServiceEffectSpecification sEfF = seff.build();
		this.seffs.add(sEfF);
		return this;
	}

	public Basic withVariableUsage() {
		VariableUsageCreator vuc = new VariableUsageCreator(this, repository);
		return vuc;
	}

	@Override
	public BasicComponent build() {
		BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		if (name != null)
			basicComponent.setEntityName(name);
//		if (id != null)
//			basicComponent.setId(id);
		if (type != null)
			basicComponent.setComponentType(type);

		basicComponent.getProvidedRoles_InterfaceProvidingEntity().addAll(providedRoles);
		basicComponent.getRequiredRoles_InterfaceRequiringEntity().addAll(requiredRoles);
		basicComponent.getResourceRequiredRoles__ResourceInterfaceRequiringEntity().addAll(resourceRequiredRoles);

		basicComponent.getParentCompleteComponentTypes().addAll(conformsCompleteTypes);
		basicComponent.getComponentParameterUsage_ImplementationComponentType().addAll(componentParameterUsages);

		basicComponent.getPassiveResource_BasicComponent().addAll(passiveResources);
		basicComponent.getServiceEffectSpecifications__BasicComponent().addAll(seffs);

		return basicComponent;
	}

	protected void addVariableUsage(VariableUsage varUsage) {
		this.componentParameterUsages.add(varUsage);
	}

}
