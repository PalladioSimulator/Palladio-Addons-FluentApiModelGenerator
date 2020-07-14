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

import apiControlFlowInterfaces.seff.Seff;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.BasicComponent BasicComponent}.
 * It is used to create the '<em><b>BasicComponent</b></em>' object
 * step-by-step, i.e. '<em><b>BasicComponentCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.BasicComponent
 */
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
	/**
	 * Sets the type of the basic component.<br>
	 * <br>
	 * Possible values are '<em><b>BUSINESS_COMPONENT</b></em>' (default) and
	 * '<em><b>INFRASTRUCTURE_COMPONENT</b></em>'.
	 * 
	 * @param type
	 * @return the basic component in the making
	 */
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

	@Override
	public BasicComponentCreator requiresResource(ResourceInterface resourceInterface, String name) {
		return (BasicComponentCreator) super.requiresResource(resourceInterface, name);
	}

	// ------------ other listing characteristics ------------
	// parent complete component types
	/**
	 * Creates a conforming (parental) connection to the
	 * <code>completeComponentType</code> and adds it to the basic component.
	 * <p>
	 * Complete (Component) types abstract from the realization of components. They
	 * only contain provided and required roles omitting the components’ internal
	 * structure, i.e., the service effect specifications or assemblies.
	 * </p>
	 * <p>
	 * The <code>completeComponentType</code> can be created using the factory, i.e.
	 * <code>create.newCompleteComponentType()</code>.
	 * </p>
	 * 
	 * @param completeComponentType
	 * @return the basic component in the making
	 * @see factory.FluentRepositoryFactory#newCompleteComponentType()
	 * @see org.palladiosimulator.pcm.repository.BasicComponent#getParentCompleteComponentTypes()
	 * @see org.palladiosimulator.pcm.repository.CompleteComponentType
	 */
	public BasicComponentCreator conforms(CompleteComponentTypeCreator completeComponentType) {
		CompleteComponentType cct = completeComponentType.build();
		this.repository.addComponent(cct);
		return conforms(cct);
	}

	/**
	 * Creates a conforming (parental) connection to the
	 * <code>completeComponentType</code> and adds it to the basic component.
	 * <p>
	 * Complete (Component) types abstract from the realization of components. They
	 * only contain provided and required roles omitting the components’ internal
	 * structure, i.e., the service effect specifications or assemblies.
	 * </p>
	 * <p>
	 * An existing <code>completeComponentType</code> can be fetched from the
	 * repository using the factory, i.e.
	 * <code>create.fetchOfCompleteComponentType(name)</code>.
	 * </p>
	 * 
	 * @param completeComponentType
	 * @return the basic component in the making
	 * @see factory.FluentRepositoryFactory#fetchOfCompleteComponentType(String)
	 * @see org.palladiosimulator.pcm.repository.BasicComponent#getParentCompleteComponentTypes()
	 * @see org.palladiosimulator.pcm.repository.CompleteComponentType
	 */
	public BasicComponentCreator conforms(CompleteComponentType completeComponentType) {
		this.conformsCompleteTypes.add(completeComponentType);
		return this;
	}

	/**
	 * Adds a passive resource (e.g. a semaphore) to the basic component.
	 * <p>
	 * The stochastic expression in <code>capacity_stochasticExpression</code>
	 * belongs to a {@link org.palladiosimulator.pcm.core.PCMRandomVariable
	 * PCMRandomVariable} and describes the capacity of the passive resource. The
	 * given <code>failureType</code> is the failure type that represents a
	 * timeout&nbsp;failure of an acquiring action for this passive resource.
	 * </p>
	 * 
	 * @param capacity_stochasticExpression stochastic expression as a string
	 * @param failureType                   a resource timeout failure
	 * @return the basic component in the making
	 * @see org.palladiosimulator.pcm.repository.PassiveResource
	 * @see org.palladiosimulator.pcm.core.PCMRandomVariable
	 * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
	 */
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

	/**
	 * Adds a service effect specification (SEFF) to the basic component.
	 * <p>
	 * Service Effect Specification Models the effect of invoking a specific service
	 * of a basic component. Therefore, it references a signature from an Interface,
	 * for which the component takes a ProvidedRole, to identify the described
	 * service.
	 * </p>
	 * <p>
	 * Create a new SEFF by using the factory, i.e. <code>create.newSeff()</code>.
	 * </p>
	 * 
	 * @param seff
	 * @return the basic component in the making
	 * @see factory.FluentRepositoryFactory#newSeff()
	 * @see org.palladiosimulator.pcm.seff.ServiceEffectSpecification
	 * @see org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
	 */
	public BasicComponentCreator withServiceEffectSpecification(Seff seff) {
		if (seff != null) {
			ServiceEffectSpecification sEfF = seff.build();
			this.seffs.add(sEfF);
		}
		return this;
	}

	/**
	 * Adds a {@link org.palladiosimulator.pcm.parameter.VariableUsage
	 * VariableUsage} to the basic component.
	 *
	 * <p>
	 * Variable usages are used to characterize variables like input and output
	 * variables or component parameters. They contain the specification of the
	 * variable as VariableCharacterisation and also refer to the name of the
	 * characterized variable in its namedReference association.
	 * </p>
	 * 
	 * <p>
	 * Create a new variable usage by using the factory, i.e.
	 * <code>create.newVariableUsage()</code>.
	 * </p>
	 * 
	 * @param variableUsage in the making
	 * @return the basic component in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 * @see org.palladiosimulator.pcm.parameter.VariableUsage
	 */
	public BasicComponentCreator withVariableUsage(VariableUsageCreator variableUsage) {
		this.componentParameterUsages.add(variableUsage.build());
		return this;
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
