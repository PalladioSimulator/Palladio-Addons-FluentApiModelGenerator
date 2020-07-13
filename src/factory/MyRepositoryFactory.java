package factory;

import java.util.Map;

import org.eclipse.emf.cdo.spi.common.revision.InternalCDOFeatureDelta.WithIndex;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.subsystem.SubSystem;

import apiControlFlowInterfaces.Repo;
import apiControlFlowInterfaces.seff.InternalSeff;
import apiControlFlowInterfaces.seff.RecoverySeff;
import apiControlFlowInterfaces.seff.Seff;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
import repositoryStructure.components.VariableUsageCreator;
import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.datatypes.Failure;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class MyRepositoryFactory {
	// TODO: welchen passenderen Namen könnte diese Klasse bekommen?

	private RepositoryCreator repo;
	private Repository primitives;
	private ResourceRepository resourceTypes;
	private Repository failures;

	public MyRepositoryFactory() {
//		this.primitives = loadPrimitiveTypesRepository();
//		this.resourceTypes = loadResourceTypeRepository();
//		this.failures = loadFailureTypesRepository();
	}

	private Repository loadPrimitiveTypesRepository() {
		RepositoryPackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}

	private ResourceRepository loadResourceTypeRepository() {
		ResourcetypePackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/Palladio.resourcetype"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		ResourceRepository repository = (ResourceRepository) resource.getContents().get(0);
		return repository;
	}

	private Repository loadFailureTypesRepository() {
		RepositoryPackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/FailureTypes.repository"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}

	// ---------------------- Repository ----------------------
	/**
	 * Creates a representation of the model object '<em><b>Repository</b></em>'.
	 * 
	 * <p>
	 * The repository entity allows storing components, data types, and interfaces
	 * to be fetched and reused for construction of component instances as well as
	 * new component types.
	 * </p>
	 * 
	 * @return the repository in the making
	 */
	public Repo newRepository() {
		this.repo = new RepositoryCreator();
		return this.repo;
	}

	// ---------------------- Components ----------------------
	/**
	 * Creates a new basic component.
	 * <p>
	 * Basic components are atomic building blocks of a software architecture.
	 * Component developers specify basic components by associating interfaces to
	 * them in a providing or requiring role.
	 * </p>
	 * <p>
	 * Basic components offer the characteristics
	 * {@link repositoryStructure.components.BasicComponentCreator#withName(String)
	 * name},
	 * {@link repositoryStructure.components.BasicComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
	 * type},
	 * {@link repositoryStructure.components.BasicComponentCreator#withServiceEffectSpecification(Seff)
	 * SEFF},
	 * {@link repositoryStructure.components.BasicComponentCreator#withPassiveResource(String, ResourceTimeoutFailureType)
	 * passive resource} and
	 * {@link repositoryStructure.components.BasicComponentCreator#withVariableUsage(VariableUsageCreator)
	 * variable usage}.<br>
	 * The possible connections/roles to other components/interfaces are
	 * {@link repositoryStructure.components.BasicComponentCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.BasicComponentCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.BasicComponentCreator#conforms(CompleteComponentTypeCreator)
	 * conformity},
	 * {@link repositoryStructure.components.BasicComponentCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.BasicComponentCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces},
	 * </p>
	 * 
	 * @return the basic component in the making
	 * @see org.palladiosimulator.pcm.repository.BasicComponent
	 */
	public BasicComponentCreator newBasicComponent() {
		BasicComponentCreator basicComponent = new BasicComponentCreator(this.repo);
		return basicComponent;
	}

	public CompositeComponentCreator newCompositeComponent() {
		CompositeComponentCreator compositeComponent = new CompositeComponentCreator(this.repo);
		return compositeComponent;
	}

	public SubSystemCreator newSubSystem() {
		SubSystemCreator subSystem = new SubSystemCreator(this.repo);
		return subSystem;
	}

	public CompleteComponentTypeCreator newCompleteComponentType() {
		CompleteComponentTypeCreator cct = new CompleteComponentTypeCreator(this.repo);
		return cct;
	}

	public ProvidesComponentTypeCreator newProvidesComponentType() {
		ProvidesComponentTypeCreator pct = new ProvidesComponentTypeCreator(this.repo);
		return pct;
	}

	// ---------------------- Interfaces ----------------------
	public OperationInterfaceCreator newOperationInterface() {
		OperationInterfaceCreator operationInterface = new OperationInterfaceCreator(this.repo);
		return operationInterface;
	}

	public InfrastructureInterfaceCreator newInfrastructureInterface() {
		InfrastructureInterfaceCreator infrastructureInterface = new InfrastructureInterfaceCreator(this.repo);
		return infrastructureInterface;
	}

	public EventGroupCreator newEventGroup() {
		EventGroupCreator eventGroup = new EventGroupCreator(this.repo);
		return eventGroup;
	}

	// ---------------------- Failure Types ----------------------
	// access Failure Types using enums

	// ---------------------- DataTypes ----------------------
	// access Primitive Data Types using enums
	/**
	 * Creates a new collection data type with name <code>name</code> and of type
	 * <code>primitive</code>.
	 * 
	 * <p>
	 * A collection data type represents a list, array, set of items of the
	 * particular type. For example,
	 * <code>create.newCollectionDataType("StringList",
	 * Primitive.String)</code> realizes a data type conforming
	 * <code>List&lt;String&gt;</code> in Java.
	 * </p>
	 * 
	 * @param name      the <i>unique</i> name of the new collection data type
	 * @param primitive the primitive data type that the elements have
	 * @return the collection data type
	 * @see org.palladiosimulator.pcm.repository.CollectionDataType
	 * @see repositoryStructure.datatypes.Primitive
	 */
	public CollectionDataType newCollectionDataType(String name, Primitive primitive) {
		PrimitiveDataType p = PrimitiveType.getPrimitiveDataType(primitive);

		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(p);

		return coll;
	}

	/**
	 * Creates a new collection data type with name <code>name</code> and of type
	 * <code>dataType</code>.
	 * 
	 * <p>
	 * A collection data type represents a list, array, set of items of the
	 * particular type. All previously created data types and primitive data types
	 * can be referenced using fetching methods, e.g.
	 * {@link factory.MyRepositoryFactory#fetchOfDataType(String)
	 * fetchOfDataType(String)}. <br>
	 * For example, <code>create.newCollectionDataType("PersonList",
	 * create.fetchOfDataType("Person"))</code> realizes a data type conforming
	 * <code>List&lt;Person&gt;</code> in Java, assuming that a different data type
	 * called "Person" has been previously declared.
	 * </p>
	 * 
	 * @param name     the <i>unique</i> name of the new collection data type
	 * @param dataType the data type that the elements have
	 * @return the collection data type
	 * @see factory.MyRepositoryFactory#fetchOfDataType(String)
	 * @see factory.MyRepositoryFactory#fetchOfDataType(Primitive)
	 * @see org.palladiosimulator.pcm.repository.CollectionDataType
	 * @see org.palladiosimulator.pcm.repository.DataType
	 */
	public CollectionDataType newCollectionDataType(String name,
			org.palladiosimulator.pcm.repository.DataType dataType) {
		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(dataType);
		return coll;
	}

	/**
	 * Creates a new collection data type with name <code>name</code> and optionally
	 * the previously defined parent types <code>parents</code>.
	 * 
	 * <p>
	 * A composite data type represents a complex data type containing other data
	 * types. This construct is common in higher programming languages as record,
	 * struct, or class.<br>
	 * The contained data types can be added using method chaining with
	 * {@link repositoryStructure.datatypes.CompositeDataTypeCreator#withInnerDeclaration(String, Primitive)
	 * .withInnerDeclaration(String, Primitive)} and/or
	 * {@link repositoryStructure.datatypes.CompositeDataTypeCreator#withInnerDeclaration(String, DataType)
	 * .withInnerDeclaration(String, DataType)}.
	 * </p>
	 * 
	 * @param name    the <i>unique</i> name of the composite data type
	 * @param parents array of parent composite data types
	 * @return the composite data type in the making
	 * @see repositoryStructure.datatypes.CompositeDataTypeCreator#withInnerDeclaration(String,
	 *      Primitive)
	 * @see repositoryStructure.datatypes.CompositeDataTypeCreator#withInnerDeclaration(String,
	 *      DataType)
	 * @see org.palladiosimulator.pcm.repository.CompositeDataType
	 */
	public CompositeDataTypeCreator newCompositeDataType(String name, CompositeDataType... parents) {
		if (parents == null)
			parents = new CompositeDataType[0];
		CompositeDataTypeCreator c = new CompositeDataTypeCreator(name, parents);
		return c;
	}

	// ---------------------- Component Related Stuff ----------------------

	/**
	 * Creates a new {@link org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
	 * ResourceDemandingSEFF}. A ResourceDemandingSEFF is a
	 * '<em><b>ServiceEffectSpecification</b></em>' and a
	 * '<em><b>Resource-DemandingBehaviour</b></em>' at the same time inheriting
	 * from both classes.
	 * 
	 * <p>
	 * A resource demanding service effect specification (RDSEFF) is a special type
	 * of SEFF designed for performance and reliability predictions. Besides
	 * dependencies between provided and required services of a component, it
	 * additionally includes notions of resource usage, data flow, and parametric
	 * dependencies for more accurate predictions.
	 * </p>
	 * 
	 * <p>
	 * Use the methods
	 * {@link apiControlFlowInterfaces.seff.Seff#onSignature(Signature)
	 * onSignature(Signature)},
	 * {@link apiControlFlowInterfaces.seff.Seff#withSeffTypeID(String)
	 * withSeffTypeID(String)} and
	 * {@link apiControlFlowInterfaces.seff.Seff#withInternalBehaviour(InternalSeff)
	 * withInternalBehaviour(InternalSeff)} to define the seff.<br>
	 * In the end use the method
	 * {@link apiControlFlowInterfaces.seff.Seff#withSeffBehaviour()
	 * withSeffBehaviour()} to specify its step-wise behaviour.
	 * 
	 * @return the SEFF in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
	 */
	public Seff newSeff() {
		return new SeffCreator();
	}

	public InternalSeff newInternalBehaviour() {
		return new SeffCreator();
	}

	public RecoverySeff newRecoveryBehaviour() {
		return new SeffCreator();
	}

	/**
	 * Creates a new {@link org.palladiosimulator.pcm.parameter.VariableUsage
	 * VariableUsage}.
	 * 
	 * <p>
	 * Variable usages are used to characterize variables like input and output
	 * variables or component parameters. They contain the specification of the
	 * variable as VariableCharacterisation and also refer to the name of the
	 * characterized variable in its namedReference association.
	 * </p>
	 * <p>
	 * Use the methods
	 * {@link repositoryStructure.components.VariableUsageCreator#withVariableCharacterisation(String, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * withVariableCharacterisation(String, VariableCharacterisationType)},
	 * {@link repositoryStructure.components.VariableUsageCreator#withVariableReference(String)
	 * withVariableReference(String)} and
	 * {@link repositoryStructure.components.VariableUsageCreator#withNamespaceReference(String, String...)
	 * withNamespaceReference(String, String...)} to define the variable usage.
	 * </p>
	 * 
	 * @return the variable usage in the making
	 * @see repositoryStructure.components.VariableUsageCreator#withVariableCharacterisation(String,
	 *      org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * @see repositoryStructure.components.VariableUsageCreator#withVariableReference(String)
	 * @see repositoryStructure.components.VariableUsageCreator#withNamespaceReference(String,
	 *      String...)
	 * @see org.palladiosimulator.pcm.parameter.VariableUsage
	 */
	public VariableUsageCreator newVariableUsage() {
		return new VariableUsageCreator(this.repo);
	}

	// ---------------------- Fetching methods ----------------------

	// TODO: exceptionTypes, resourcetypes, resource interfaces etc
	/**
	 * Extracts the by <code>name</code> referenced data type from the repository.
	 * <p>
	 * This method throws a RuntimeException if no data type is present under the
	 * given <code>name</code>. If more than one data type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first data type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the data type
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(CollectionDataType)
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(CompositeDataTypeCreator)
	 * @see org.palladiosimulator.pcm.repository.DataType
	 */
	public DataType fetchOfDataType(String name) {

		DataType dataType = repo.getDataType(name);
		if (dataType == null)
			dataType = PrimitiveType.getPrimitiveDataType(name);
		if (dataType == null)
			throw new RuntimeException("Datatype '" + name + "' could not be found");

		return dataType;
	}

	/**
	 * Extracts the primitive data type corresponding to the enum
	 * <code>primitive</code> from the repository.
	 * 
	 * @param primitive
	 * @return the data type
	 * @see org.palladiosimulator.pcm.repository.PrimitiveDataType
	 */
	public DataType fetchOfDataType(Primitive primitive) {
		// TODO:
		EList<DataType> dataTypes = this.primitives.getDataTypes__Repository();
		for (DataType d : dataTypes) {
			System.out.println(d);
		}
		return PrimitiveType.getPrimitiveDataType(primitive);
	}

	public FailureType fetchOfFailureType(Failure failure) {
		// TODO:
		EList<FailureType> failureTypes = this.failures.getFailureTypes__Repository();
		for (FailureType f : failureTypes) {
			System.out.println(f);
		}

		return repositoryStructure.datatypes.FailureType.getFailureType(failure);
	}

	public ResourceType fetchOfResourcetype(String name) {
		// TODO:
		this.resourceTypes.getAvailableResourceTypes_ResourceRepository();
		this.resourceTypes.getResourceInterfaces__ResourceRepository();
		this.resourceTypes.getSchedulingPolicies__ResourceRepository();
		return null;
	}

	public ResourceTimeoutFailureType fetchOfResourceTimeoutFailureType(Failure failure) {
		FailureType failureType = repositoryStructure.datatypes.FailureType.getFailureType(failure);
		if (failureType instanceof ResourceTimeoutFailureType)
			return (ResourceTimeoutFailureType) failureType;
		// TODO: einkommentieren
//		else
//			throw new RuntimeException("ResourceTimeoutFailureType could not be found; must be of type SoftwareInducedFailure");
		return null;
	}

	public RepositoryComponent fetchOfComponent(String name) {
		RepositoryComponent component = repo.getComponent(name);
		if (component == null)
			throw new RuntimeException("Component '" + name + "' could not be found");
		return component;
	}

	public BasicComponent fetchOfBasicComponent(String name) {
		BasicComponent component = repo.getBasicComponent(name);
		if (component == null)
			throw new RuntimeException("BasicComponent '" + name + "' could not be found");
		return component;
	}

	public CompositeComponent fetchOfCompositeComponent(String name) {
		CompositeComponent component = repo.getCompositeComponent(name);
		if (component == null)
			throw new RuntimeException("CompositeComponent '" + name + "' could not be found");
		return component;
	}

	public SubSystem fetchOfSubSystem(String name) {
		SubSystem component = repo.getSubsystem(name);
		if (component == null)
			throw new RuntimeException("Subsystem '" + name + "' could not be found");
		return component;
	}

	public CompleteComponentType fetchOfCompleteComponentType(String name) {
		CompleteComponentType component = repo.getCompleteComponentType(name);
		if (component == null)
			throw new RuntimeException("CompleteComponentType '" + name + "' could not be found");
		return component;
	}

	public ProvidesComponentType fetchOfProvidesComponentType(String name) {
		ProvidesComponentType component = repo.getProvidesComponentType(name);
		if (component == null)
			throw new RuntimeException("ProvidesComponentType '" + name + "' could not be found");
		return component;
	}

	public Interface fetchOfInterface(String name) {
		Interface interfce = repo.getInterface(name);
		if (interfce == null)
			throw new RuntimeException("Interface '" + name + "' could not be found");
		return interfce;
	}

	public OperationInterface fetchOfOperationInterface(String name) {
		OperationInterface interfce = repo.getOperationInterface(name);
		if (interfce == null)
			throw new RuntimeException("OperationInterface '" + name + "' could not be found");
		return interfce;
	}

	public InfrastructureInterface fetchOfInfrastructureInterface(String name) {
		InfrastructureInterface interfce = repo.getInfrastructureInterface(name);
		if (interfce == null)
			throw new RuntimeException("InfrastructureInterface '" + name + "' could not be found");
		return interfce;
	}

	public EventGroup fetchOfEventGroup(String name) {
		EventGroup interfce = repo.getEventGroup(name);
		if (interfce == null)
			throw new RuntimeException("EventGroup '" + name + "' could not be found");
		return interfce;
	}

	public ProvidedRole fetchOfProvidedRole(String name) {
		ProvidedRole provRole = repo.getProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	public RequiredRole fetchOfRequiredRole(String name) {
		RequiredRole reqRole = repo.getRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	public OperationProvidedRole fetchOfOperationProvidedRole(String name) {
		OperationProvidedRole provRole = repo.getOperationProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	public OperationRequiredRole fetchOfOperationRequiredRole(String name) {
		OperationRequiredRole reqRole = repo.getOperationRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	public InfrastructureProvidedRole fetchOfInfrastructureProvidedRole(String name) {
		InfrastructureProvidedRole provRole = repo.getInfrastructureProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	public InfrastructureRequiredRole fetchOfInfrastructureRequiredRole(String name) {
		InfrastructureRequiredRole reqRole = repo.getInfrastructureRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	public SinkRole fetchOfSinkRole(String name) {
		SinkRole provRole = repo.getSinkRole(name);
		if (provRole == null)
			throw new RuntimeException("SinkRole '" + name + "' could not be found");
		return provRole;
	}

	public SourceRole fetchOfSourceRole(String name) {
		SourceRole reqRole = repo.getSourceRole(name);
		if (reqRole == null)
			throw new RuntimeException("SourceRole '" + name + "' could not be found");
		return reqRole;
	}

	public ResourceRequiredRole fetchOfResourceRequiredRole(String name) {
		// TODO: resource stuff
		return null;
	}

	public ResourceInterface fetchOfResourceInterface(String name) {
		// TODO: resource stuff
		return null;
	}

	public AssemblyContext fetchOfAssemblyContext(String name) {
		AssemblyContext assContext = repo.getAssemblyContext(name);
		if (assContext == null)
			throw new RuntimeException("Assembly context '" + name + "' could not be found");
		return assContext;
	}

	public Parameter fetchOfParameter(String name) {
		Parameter p = repo.getParameter(name);
		if (p == null)
			throw new RuntimeException("Parameter '" + name + "' could not be found");
		return p;
	}

	public Parameter fetchOfParameter(String name, Signature context) {
		Parameter p = repo.getParameter(name, context);
		if (p == null)
			throw new RuntimeException("Parameter '" + name + "' could not be found");
		return p;
	}

}
