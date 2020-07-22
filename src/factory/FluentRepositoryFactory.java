package factory;

import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PassiveResource;
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
import repositoryStructure.datatypes.CommunicationLinkResource;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.datatypes.ExceptionTypeCreator;
import repositoryStructure.datatypes.Failure;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.ProcessingResource;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

/**
 * TODO: javadoc
 * 
 * @author Louisa Lambrecht
 *
 */
public class FluentRepositoryFactory {

	private RepositoryCreator repo;

	/**
	 * TODO: javadoc
	 */
	public FluentRepositoryFactory() {
	}

	private Repository loadRepository(String uri) {
		RepositoryPackage.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());
		ResourceSet resSet = new ResourceSetImpl();
//		resSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(false));
//		Resource resource = resSet.getResource(URI.createPlatformResourceURI(uri, true), true);
		Resource resource = resSet.getResource(URI.createURI(uri), true);
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}

	private ResourceRepository loadResourceTypeRepository() {
		ResourcetypePackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .repository extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());
		m.put("resourcetype", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Get the resource
//		Resource resource = resSet.getResource(URI.createURI("pathmap://PCM_MODELS/Palladio.resourcetype"), true);
		Resource resource = resSet.getResource(URI.createURI("resources/Palladio.resourcetype"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		ResourceRepository repository = (ResourceRepository) resource.getContents().get(0);
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
		// TODO: pathmap://PCM_MODELS/PrimitiveTypes.repository
		Repository primitives = loadRepository("resources/PrimitiveTypes.repository");
		ResourceRepository resourceTypes = loadResourceTypeRepository();
		Repository failures = loadRepository("resources/FailureTypes.repository");
		this.repo = new RepositoryCreator(primitives, resourceTypes, failures);
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
	 * passive resource},
	 * {@link repositoryStructure.components.BasicComponentCreator#withVariableUsage(VariableUsageCreator)
	 * variable usage} and
	 * {@link repositoryStructure.components.BasicComponentCreator#conforms(CompleteComponentTypeCreator)
	 * conformity}.<br>
	 * The possible roles to other interfaces are
	 * {@link repositoryStructure.components.BasicComponentCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.BasicComponentCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.BasicComponentCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.BasicComponentCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.BasicComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.
	 * </p>
	 * 
	 * @return the basic component in the making
	 * @see org.palladiosimulator.pcm.repository.BasicComponent
	 */
	public BasicComponentCreator newBasicComponent() {
		BasicComponentCreator basicComponent = new BasicComponentCreator(this.repo);
		return basicComponent;
	}

	/**
	 * Creates a new composite component.
	 * 
	 * <p>
	 * Composite components are special implementation component types, which are
	 * composed from inner components. Component developers compose inner components
	 * within composite components with assembly connectors. A composite component
	 * may contain other composite components, which are also themselves composed
	 * out of inner components. This enables building arbitrary hierarchies of
	 * nested components.
	 * </p>
	 * <p>
	 * Composite components offer the characteristics
	 * {@link repositoryStructure.components.CompositeComponentCreator#withName(String)
	 * name},
	 * {@link repositoryStructure.components.CompositeComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
	 * type},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withVariableUsage(VariableUsageCreator)
	 * variable usage} and
	 * {@link repositoryStructure.components.CompositeComponentCreator#conforms(CompleteComponentTypeCreator)
	 * conformity}.<br>
	 * The possible roles to other interfaces are
	 * {@link repositoryStructure.components.CompositeComponentCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.CompositeComponentCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.CompositeComponentCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.CompositeComponentCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.CompositeComponentCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.CompositeComponentCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.CompositeComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.<br>
	 * Composite component/subsystem specific connections with other
	 * components/interfaces are
	 * {@link repositoryStructure.components.CompositeComponentCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
	 * assembly context},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withEventChannel()
	 * event channel},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
	 * assembly connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
	 * assembly event connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
	 * event channel sink connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
	 * event channel source connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
	 * assembly infrastructure connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
	 * provided delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
	 * required delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
	 * sink delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
	 * source delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
	 * provided infrastructure delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
	 * requires infrastructure delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
	 * required resource delegation connection},
	 * {@link repositoryStructure.components.CompositeComponentCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
	 * resource required delegation connection}.
	 * 
	 * @return the composite component in the making
	 * @see org.palladiosimulator.pcm.repository.CompositeComponent
	 */
	public CompositeComponentCreator newCompositeComponent() {
		CompositeComponentCreator compositeComponent = new CompositeComponentCreator(this.repo);
		return compositeComponent;
	}

	/**
	 * Creates a new subsystem.
	 * 
	 * <p>
	 * A SubSystem is structurally comparable to a CompositeComponent. The major
	 * difference is the white-box property it preserves for System Deployers,
	 * meaning that they can be allocated to different nodes of the resource
	 * environment.
	 * </p>
	 * <p>
	 * Subsystems offer the characteristics
	 * {@link repositoryStructure.components.SubSystemCreator#withName(String)
	 * name}.<br>
	 * The possible roles to other interfaces are
	 * {@link repositoryStructure.components.SubSystemCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.SubSystemCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.SubSystemCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.SubSystemCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.SubSystemCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.SubSystemCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.SubSystemCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.<br>
	 * Composite component/subsystem specific connections with other
	 * components/interfaces are
	 * {@link repositoryStructure.components.SubSystemCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
	 * assembly context},
	 * {@link repositoryStructure.components.SubSystemCreator#withEventChannel()
	 * event channel},
	 * {@link repositoryStructure.components.SubSystemCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
	 * assembly connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
	 * assembly event connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
	 * event channel sink connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
	 * event channel source connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
	 * assembly infrastructure connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
	 * provided delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
	 * required delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
	 * sink delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
	 * source delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
	 * provided infrastructure delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
	 * requires infrastructure delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
	 * required resource delegation connection},
	 * {@link repositoryStructure.components.SubSystemCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
	 * resource required delegation connection}.
	 * 
	 * @return the subsystem in the making
	 * @see org.palladiosimulator.pcm.subsystem.SubSystem
	 */
	public SubSystemCreator newSubSystem() {
		SubSystemCreator subSystem = new SubSystemCreator(this.repo);
		return subSystem;
	}

	/**
	 * Creates a new complete component type.
	 * <p>
	 * Complete (Component) types abstract from the realization of components. They
	 * only contain provided and required roles omitting the components’ internal
	 * structure, i.e., the service effect specifications or assemblies.
	 * </p>
	 * <p>
	 * Complete component types offer the characteristics
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#withName(String)
	 * name} and provide the roles
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#conforms(ProvidesComponentTypeCreator)
	 * conformity},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.CompleteComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.
	 * </p>
	 * 
	 * @return the complete component type in the making
	 * @see org.palladiosimulator.pcm.repository.CompleteComponentType
	 */
	public CompleteComponentTypeCreator newCompleteComponentType() {
		CompleteComponentTypeCreator cct = new CompleteComponentTypeCreator(this.repo);
		return cct;
	}

	/**
	 * Creates a new provided component type.
	 * <p>
	 * Provided (Component) Types abstract a component to its provided interfaces,
	 * leaving its requirements and implementation details open. So, provided types
	 * subsume components which offer the same functionality, but with different
	 * implementations.
	 * </p>
	 * <p>
	 * Provided component types offer the characteristics
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#withName(String)
	 * name} and provide the roles
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link repositoryStructure.components.ProvidesComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.
	 * </p>
	 * 
	 * @return the provides component type in the making
	 * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
	 */
	public ProvidesComponentTypeCreator newProvidesComponentType() {
		ProvidesComponentTypeCreator pct = new ProvidesComponentTypeCreator(this.repo);
		return pct;
	}

	// ---------------------- Interfaces ----------------------
	/**
	 * Creates a new operation interface.
	 * <p>
	 * The OperationInterface is a specific type of interface related to operation
	 * calls. For this, it also references a set of operation interfaces. Operations
	 * can represent methods, functions or any comparable concept.
	 * </p>
	 * <p>
	 * Operation interfaces are defined by their
	 * {@link repositoryStructure.interfaces.OperationInterfaceCreator#withName(String)
	 * name}, their
	 * {@link repositoryStructure.interfaces.OperationInterfaceCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link repositoryStructure.interfaces.OperationInterfaceCreator#withOperationSignature()
	 * operation signatures} and the corresponding
	 * {@link repositoryStructure.interfaces.OperationInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * required characterizations}.
	 * </p>
	 * 
	 * @return the operation interface in the making
	 * @see org.palladiosimulator.pcm.repository.OperationInterface
	 */
	public OperationInterfaceCreator newOperationInterface() {
		OperationInterfaceCreator operationInterface = new OperationInterfaceCreator(this.repo);
		return operationInterface;
	}

	/**
	 * Creates a new infrastructure interface.
	 * 
	 * <p>
	 * Infrastructure interfaces are defined by their
	 * {@link repositoryStructure.interfaces.InfrastructureInterfaceCreator#withName(String)
	 * name}, their
	 * {@link repositoryStructure.interfaces.InfrastructureInterfaceCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link repositoryStructure.interfaces.InfrastructureInterfaceCreator#withInfrastructureSignature()
	 * infrastructure signatures} and the corresponding
	 * {@link repositoryStructure.interfaces.InfrastructureInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * required characterizations}.
	 * </p>
	 * 
	 * @return the infrastructure interface in the making
	 * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
	 */
	public InfrastructureInterfaceCreator newInfrastructureInterface() {
		InfrastructureInterfaceCreator infrastructureInterface = new InfrastructureInterfaceCreator(this.repo);
		return infrastructureInterface;
	}

	/**
	 * Creates a new event group.
	 * <p>
	 * An EventGroup combines a set of EventTypes that are supported by a Sink
	 * and/or a Source. This is comparable to an operation interface combining a set
	 * of operation signatures.
	 * </p>
	 * <p>
	 * Event groups are defined by their
	 * {@link repositoryStructure.interfaces.EventGroupCreator#withName(String)
	 * name}, their
	 * {@link repositoryStructure.interfaces.EventGroupCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link repositoryStructure.interfaces.EventGroupCreator#withEventType() event
	 * types} and the corresponding
	 * {@link repositoryStructure.interfaces.EventGroupCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * required characterizations}.
	 * </p>
	 * 
	 * @return the event group in the making
	 * @see org.palladiosimulator.pcm.repository.EventGroup
	 */
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
		Objects.requireNonNull(name, "name must not be null");
		PrimitiveDataType p = repo.getPrimitiveDataType(primitive);

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
	 * {@link factory.FluentRepositoryFactory#fetchOfDataType(String)
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
	 * @see factory.FluentRepositoryFactory#fetchOfDataType(String)
	 * @see factory.FluentRepositoryFactory#fetchOfDataType(Primitive)
	 * @see org.palladiosimulator.pcm.repository.CollectionDataType
	 * @see org.palladiosimulator.pcm.repository.DataType
	 */
	public CollectionDataType newCollectionDataType(String name,
			org.palladiosimulator.pcm.repository.DataType dataType) {
		Objects.requireNonNull(name, "name must not be null");
		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(dataType);
		return coll;
	}

	/**
	 * Creates a new collection data type.
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
	public CompositeDataTypeCreator newCompositeDataType() {
		return new CompositeDataTypeCreator(this.repo);
	}

	/**
	 * Creates a new hardware induced failure type with name <code>name</code> and
	 * processing resource <code>processingResource</code>.
	 * 
	 * @param name
	 * @param processingResource
	 * @return the hardware induced failure type
	 * @see org.palladiosimulator.pcm.reliability.HardwareInducedFailureType
	 */
	public HardwareInducedFailureType newHardwareInducedFailureType(String name,
			ProcessingResource processingResource) {
		Objects.requireNonNull(name, "name must not be null");
		HardwareInducedFailureType h = ReliabilityFactory.eINSTANCE.createHardwareInducedFailureType();
		h.setEntityName(name);
		h.setProcessingResourceType__HardwareInducedFailureType(repo.getProcessingResource(processingResource));
		this.repo.addFailureType(h);
		return h;
	}

	/**
	 * Creates a new network induced failure type with name <code>name</code> and
	 * communication link resource <code>communicationLinkResource</code>.
	 * 
	 * @param name
	 * @param communicationLinkResource
	 * @return the network induced failure type
	 * @see org.palladiosimulator.pcm.reliability.NetworkInducedFailureType
	 */
	public NetworkInducedFailureType newNetworkInducedFailureType(String name,
			CommunicationLinkResource communicationLinkResource) {
		Objects.requireNonNull(name, "name must not be null");
		NetworkInducedFailureType n = ReliabilityFactory.eINSTANCE.createNetworkInducedFailureType();
		n.setEntityName(name);
		n.setCommunicationLinkResourceType__NetworkInducedFailureType(
				repo.getCommunicationLinkResource(communicationLinkResource));
		this.repo.addFailureType(n);
		return n;
	}

	/**
	 * Creates a new resource timeout failure type with name <code>name</code> and
	 * passive resource <code>passiveResource</code>.
	 * <p>
	 * An existing <code>passiveResource</code> can be fetched from the repository
	 * using the factory, i.e. <code>create.fetchOfPassiveResource(name)</code>.
	 * </p>
	 * 
	 * @param name
	 * @param passiveResource
	 * @return the resource timeout failure type
	 * @see factory.FluentRepositoryFactory#fetchOfPassiveResource(String)
	 * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
	 */
	public ResourceTimeoutFailureType newResourceTimeoutFailureType(String name) {
		Objects.requireNonNull(name, "name must not be null");
//		Objects.requireNonNull(passiveResource, "passiveResource must not be null");
		ResourceTimeoutFailureType timeout = ReliabilityFactory.eINSTANCE.createResourceTimeoutFailureType();
		timeout.setEntityName(name);
//		timeout.setPassiveResource__ResourceTimeoutFailureType(passiveResource);
		this.repo.addFailureType(timeout);
		return timeout;
	}

	/**
	 * Creates a new software induced failure type with name <code>name</code>.
	 * 
	 * @param name
	 * @return the software induced failure type
	 * @see org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType
	 */
	public SoftwareInducedFailureType newSoftwareInducedFailureType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		SoftwareInducedFailureType s = ReliabilityFactory.eINSTANCE.createSoftwareInducedFailureType();
		s.setEntityName(name);
		this.repo.addFailureType(s);
		return s;
	}
	
	public ExceptionTypeCreator newExceptionType() {
		return new ExceptionTypeCreator(this.repo);
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
	 * dependencies for more accurate predictions. Therefore, the class contains a
	 * chain of AbstractActions.
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
		return new SeffCreator(this.repo);
	}

	/**
	 * Creates a new
	 * ResourceDemandingInternalBehaviour/ResourceDemandingBehaviour/ForkedBehaviour
	 * (depending on the context). If the context does not distinctly favor any
	 * behaviour, ResourceDemandingBehaviour acts as default.
	 * 
	 * <p>
	 * It models the behaviour of a component service as a sequence of internal
	 * actions with resource demands, control flow constructs, and external calls.
	 * Therefore, the class contains a chain of AbstractActions.
	 * </p>
	 * 
	 * <p>
	 * Use the method
	 * {@link apiControlFlowInterfaces.seff.InternalSeff#withStartAction()
	 * withStartAction()} to specify its step-wise behaviour.
	 * 
	 * @return the internal behaviour in the making
	 * @see org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
	 * @see org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour
	 * @see org.palladiosimulator.pcm.seff.ForkedBehaviour
	 */
	public InternalSeff newInternalBehaviour() {
		return new SeffCreator(this.repo);
	}

	/**
	 * Creates a new
	 * {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
	 * RecoveryActionBehaviour}.
	 * <p>
	 * A recovery action behaviour provides a behaviour (a chain of AbstractActions)
	 * and alternatives of recovery blocks. They are resource demanding behaviours,
	 * thus any behaviour can be defined as an alternative. The alternatives of a
	 * recovery block form a chain. They are failure handling entities, i.e. they
	 * can handle failures that occur in previous alternatives. If one alternative
	 * fails, the next alternative is executed that can handle the failure type.
	 * </p>
	 * <p>
	 * Use the methods
	 * <ul>
	 * <li>{@link apiControlFlowInterfaces.seff.RecoverySeff#withFailureType(Failure)
	 * withFailureType(Failure)} to add possibly occurring failures to the
	 * behaviour,
	 * <li>{@link apiControlFlowInterfaces.seff.RecoverySeff#withAlternativeRecoveryBehaviour(org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour)
	 * withAlternativeRecoveryBehaviour(RecoveryActionBehaviour)} to add previously
	 * defined recovery behaviours as alternatives and
	 * <li>{@link apiControlFlowInterfaces.seff.RecoverySeff#withSeffBehaviour()
	 * withSeffBehaviour()} to specify this RecoveryActionBehaviour's step-wise
	 * behaviour.
	 * </ul>
	 * </p>
	 * <p>
	 * The alternatives of a recovery block form a chain and alternatives are
	 * referenced by name and have to be previously defined. Thus the chain of
	 * alternatives has to be created inversely. The last alternative that has no
	 * alternatives itself is created first, so the second last can reference it as
	 * its alternative.
	 * </p>
	 * 
	 * @return the recovery action behaviour in the making
	 * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
	 */
	public RecoverySeff newRecoveryBehaviour() {
		return new SeffCreator(this.repo);
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

	/**
	 * Extracts the by <code>name</code> referenced composite data type from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no composite data type is present
	 * under the given <code>name</code>. If more than one composite data type with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first composite data type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the composite data type
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(CompositeDataTypeCreator)
	 * @see org.palladiosimulator.pcm.repository.CompositeDataType
	 */
	public CompositeDataType fetchOfCompositeDataType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		CompositeDataType dataType = repo.getCompositeDataType(name);
		if (dataType == null)
			throw new RuntimeException("Composite data type '" + name + "' could not be found");

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
		PrimitiveDataType p = repo.getPrimitiveDataType(primitive);
		if (p == null)
			throw new RuntimeException("Primitive data Type '" + primitive + "' could not be found");
		return p;
	}

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
		Objects.requireNonNull(name, "name must not be null");
		DataType dataType = repo.getDataType(name);
		if (dataType == null)
			dataType = repo.getPrimitiveDataType(name);
		if (dataType == null)
			throw new RuntimeException("Datatype '" + name + "' could not be found");

		return dataType;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param name
	 * @return
	 */
	public ResourceTimeoutFailureType fetchOfResourceTimeoutFailureType(String name) {
		ResourceTimeoutFailureType failureType = repo.getResourceTimeoutFailureType(name);
		if (failureType == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return failureType;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param failure
	 * @return
	 */
	public FailureType fetchOfFailureType(Failure failure) {
		FailureType f = repo.getFailureType(failure);
		if (f == null)
			throw new RuntimeException("Failure Type '" + failure + "' could not be found");
		return f;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param name
	 * @return
	 */
	public FailureType fetchOfFailureType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		FailureType f = repo.getFailureType(name);
		if (f == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return f;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param name
	 * @return
	 */
	public ExceptionType fetchOfExceptionType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		ExceptionType e = repo.getExceptionType(name);
		if (e == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return e;
	}

	public ProcessingResource fetchOfProcessingResource() {
		// TODO: kann man die auch selbst erstellen oder nur über enums?
		return null;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param resourceInterface
	 * @return
	 */
	public ResourceInterface fetchOfResourceInterface(
			repositoryStructure.datatypes.ResourceInterface resourceInterface) {
		// TODO: kann man die auch selbst erstellen oder nur über enums?
		return null;
	}

	/**
	 * Extracts the <b>component</b> referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no <b>component</b> is present under
	 * the given <code>name</code>. If more than one <b>component</b> with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first <b>component</b> it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the <b>component</b>
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.repository.RepositoryComponent
	 */
	public RepositoryComponent fetchOfComponent(String name) {
		Objects.requireNonNull(name, "name must not be null");
		RepositoryComponent component = repo.getComponent(name);
		if (component == null)
			throw new RuntimeException("Component '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the basic component referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no basic component is present under
	 * the given <code>name</code>. If more than one basic component with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first basic component it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the basic component
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.repository.BasicComponent
	 */
	public BasicComponent fetchOfBasicComponent(String name) {
		Objects.requireNonNull(name, "name must not be null");
		BasicComponent component = repo.getBasicComponent(name);
		if (component == null)
			throw new RuntimeException("BasicComponent '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the composite component referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no composite component is present
	 * under the given <code>name</code>. If more than one composite component with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first composite component it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the composite component
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.repository.CompositeComponent
	 */
	public CompositeComponent fetchOfCompositeComponent(String name) {
		Objects.requireNonNull(name, "name must not be null");
		CompositeComponent component = repo.getCompositeComponent(name);
		if (component == null)
			throw new RuntimeException("CompositeComponent '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the subsystem referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no subsystem is present under the
	 * given <code>name</code>. If more than one subsystem with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first subsystem it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the subsystem
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.subsystem.SubSystem
	 */
	public SubSystem fetchOfSubSystem(String name) {
		Objects.requireNonNull(name, "name must not be null");
		SubSystem component = repo.getSubsystem(name);
		if (component == null)
			throw new RuntimeException("Subsystem '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the complete component type referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no complete component type is
	 * present under the given <code>name</code>. If more than one complete
	 * component type with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first complete component
	 * type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the complete component type
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.repository.CompleteComponentType
	 */
	public CompleteComponentType fetchOfCompleteComponentType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		CompleteComponentType component = repo.getCompleteComponentType(name);
		if (component == null)
			throw new RuntimeException("CompleteComponentType '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the provides component type referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no provides component type is
	 * present under the given <code>name</code>. If more than one provides
	 * component type with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first provides component
	 * type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the provides component type
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.components.Component)
	 * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
	 */
	public ProvidesComponentType fetchOfProvidesComponentType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		ProvidesComponentType component = repo.getProvidesComponentType(name);
		if (component == null)
			throw new RuntimeException("ProvidesComponentType '" + name + "' could not be found");
		return component;
	}

	/**
	 * Extracts the interface referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no interface is present under the
	 * given <code>name</code>. If more than one interface with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first interface it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the interface
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.interfaces.Interface)
	 * @see org.palladiosimulator.pcm.repository.Interface
	 */
	public Interface fetchOfInterface(String name) {
		Objects.requireNonNull(name, "name must not be null");
		Interface interfce = repo.getInterface(name);
		if (interfce == null)
			throw new RuntimeException("Interface '" + name + "' could not be found");
		return interfce;
	}

	/**
	 * Extracts the operation interface referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no operation interface is present
	 * under the given <code>name</code>. If more than one operation interface with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first operation interface it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the operation interface
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.interfaces.Interface)
	 * @see org.palladiosimulator.pcm.repository.OperationInterface
	 */
	public OperationInterface fetchOfOperationInterface(String name) {
		Objects.requireNonNull(name, "name must not be null");
		OperationInterface interfce = repo.getOperationInterface(name);
		if (interfce == null)
			throw new RuntimeException("OperationInterface '" + name + "' could not be found");
		return interfce;
	}

	/**
	 * Extracts the infrastructure interface referenced by <code>name</code> from
	 * the repository.
	 * <p>
	 * This method throws a RuntimeException if no infrastructure interface is
	 * present under the given <code>name</code>. If more than one infrastructure
	 * interface with this <code>name</code> is present, a warning will be printed
	 * during runtime and the system chooses the first infrastructure interface it
	 * finds.
	 * </p>
	 * 
	 * @param name
	 * @return the infrastructure interface
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.interfaces.Interface)
	 * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
	 */
	public InfrastructureInterface fetchOfInfrastructureInterface(String name) {
		Objects.requireNonNull(name, "name must not be null");
		InfrastructureInterface interfce = repo.getInfrastructureInterface(name);
		if (interfce == null)
			throw new RuntimeException("InfrastructureInterface '" + name + "' could not be found");
		return interfce;
	}

	/**
	 * Extracts the event group referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no event group is present under the
	 * given <code>name</code>. If more than one event group with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first event group it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event group
	 * @see apiControlFlowInterfaces.RepoAddition#addToRepository(repositoryStructure.interfaces.Interface)
	 * @see org.palladiosimulator.pcm.repository.EventGroup
	 */
	public EventGroup fetchOfEventGroup(String name) {
		Objects.requireNonNull(name, "name must not be null");
		EventGroup interfce = repo.getEventGroup(name);
		if (interfce == null)
			throw new RuntimeException("EventGroup '" + name + "' could not be found");
		return interfce;
	}

	/**
	 * Extracts the provided role referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no provided role is present under
	 * the given <code>name</code>. If more than one provided role with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first provided role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the provided role
	 * @see org.palladiosimulator.pcm.repository.ProvidedRole
	 */
	public ProvidedRole fetchOfProvidedRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		ProvidedRole provRole = repo.getProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	/**
	 * Extracts the operation provided role referenced by <codrequired roleom the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no operation provided role is
	 * present under the given <code>name</code>. If more than one operation
	 * provided role with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first operation provided
	 * role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the operation provided role
	 * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
	 */
	public OperationProvidedRole fetchOfOperationProvidedRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		OperationProvidedRole provRole = repo.getOperationProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	/**
	 * Extracts the infrastructure provided role referenced by <code>name</code>
	 * from the repository.
	 * <p>
	 * This method throws a RuntimeException if no infrastructure provided role is
	 * present under the given <code>name</code>. If more than one infrastructure
	 * provided role with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first infrastructure
	 * provided role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the infrastructure provided role
	 * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
	 */
	public InfrastructureProvidedRole fetchOfInfrastructureProvidedRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		InfrastructureProvidedRole provRole = repo.getInfrastructureProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		return provRole;
	}

	/**
	 * Extracts the sink role referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no sink role is present under the
	 * given <code>name</code>. If more than one sink role with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first sink role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the sink role
	 * @see org.palladiosimulator.pcm.repository.SinkRole
	 */
	public SinkRole fetchOfSinkRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		SinkRole provRole = repo.getSinkRole(name);
		if (provRole == null)
			throw new RuntimeException("SinkRole '" + name + "' could not be found");
		return provRole;
	}

	/**
	 * Extracts the required role referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no required role is present under
	 * the given <code>name</code>. If more than one required role with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first required role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the required role
	 * @see org.palladiosimulator.pcm.repository.RequiredRole
	 */
	public RequiredRole fetchOfRequiredRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		RequiredRole reqRole = repo.getRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	/**
	 * Extracts the operation required role referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no operation required role is
	 * present under the given <code>name</code>. If more than one operation
	 * required role with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first operation required
	 * role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the operation required role
	 * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
	 */
	public OperationRequiredRole fetchOfOperationRequiredRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		OperationRequiredRole reqRole = repo.getOperationRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	/**
	 * Extracts the infrastructure required role referenced by <code>name</code>
	 * from the repository.
	 * <p>
	 * This method throws a RuntimeException if no infrastructure required role is
	 * present under the given <code>name</code>. If more than one infrastructure
	 * required role with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first infrastructure
	 * required role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the infrastructure required role
	 * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
	 */
	public InfrastructureRequiredRole fetchOfInfrastructureRequiredRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		InfrastructureRequiredRole reqRole = repo.getInfrastructureRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	/**
	 * Extracts the source role referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no source role is present under the
	 * given <code>name</code>. If more than one source role with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first source role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the source role
	 * @see org.palladiosimulator.pcm.repository.SourceRole
	 */
	public SourceRole fetchOfSourceRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		SourceRole reqRole = repo.getSourceRole(name);
		if (reqRole == null)
			throw new RuntimeException("SourceRole '" + name + "' could not be found");
		return reqRole;
	}

	/**
	 * Extracts the resource required role referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no resource required role is present
	 * under the given <code>name</code>. If more than one resource required role
	 * with this <code>name</code> is present, a warning will be printed during
	 * runtime and the system chooses the first resource required role it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the resource required role
	 * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
	 */
	public ResourceRequiredRole fetchOfResourceRequiredRole(String name) {
		Objects.requireNonNull(name, "name must not be null");
		ResourceRequiredRole reqRole = repo.getResourceRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("ResourceRequiredRole '" + name + "' could not be found");
		return reqRole;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param name
	 * @return
	 */
	public Signature fetchOfSignature(String name) {
		Objects.requireNonNull(name, "name must not be null");
		Signature signature = repo.getSignature(name);
		if (signature == null)
			throw new RuntimeException("Signature '" + name + "' could not be found");
		return signature;
	}

	/**
	 * Extracts the operation signature referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no operation signature is present
	 * under the given <code>name</code>. If more than one operation signature with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first operation signature it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the operation signature
	 * @see org.palladiosimulator.pcm.repository.OperationSignature
	 */
	public OperationSignature fetchOfOperationSignature(String name) {
		Objects.requireNonNull(name, "name must not be null");
		OperationSignature signature = repo.getOperationSignature(name);
		if (signature == null)
			throw new RuntimeException("Operation signature '" + name + "' could not be found");
		return signature;
	}

	/**
	 * TODO: javadoc
	 * 
	 * @param name
	 * @return
	 */
	public InfrastructureSignature fetchOfInfrastructureSignature(String name) {
		Objects.requireNonNull(name, "name must not be null");
		InfrastructureSignature signature = repo.getInfrastructureSignature(name);
		if (signature == null)
			throw new RuntimeException("Operation signature '" + name + "' could not be found");
		return signature;
	}

	/**
	 * Extracts the event type referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no event type is present under the
	 * given <code>name</code>. If more than one event type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first event type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event type
	 * @see org.palladiosimulator.pcm.repository.EventType
	 */
	public EventType fetchOfEventType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		EventType eventType = repo.getEventType(name);
		if (eventType == null)
			throw new RuntimeException("EventType '" + name + "' could not be found");
		return eventType;
	}

	/**
	 * Extracts the assembly context referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no assembly context is present under
	 * the given <code>name</code>. If more than one assembly context with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first assembly context it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the assembly context
	 * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
	 */
	public AssemblyContext fetchOfAssemblyContext(String name) {
		Objects.requireNonNull(name, "name must not be null");
		AssemblyContext assContext = repo.getAssemblyContext(name);
		if (assContext == null)
			throw new RuntimeException("Assembly context '" + name + "' could not be found");
		return assContext;
	}

	/**
	 * Extracts the event channel referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no event channel is present under
	 * the given <code>name</code>. If more than one event channel with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first event channel it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event channel
	 * @see org.palladiosimulator.pcm.core.composition.EventChannel
	 */
	public EventChannel fetchOfEventChannel(String name) {
		Objects.requireNonNull(name, "name must not be null");
		EventChannel eventChannel = repo.getEventChannel(name);
		if (eventChannel == null)
			throw new RuntimeException("Event Channel '" + name + "' could not be found");
		return eventChannel;
	}

	/**
	 * Extracts the event channel sink connector referenced by <code>name</code>
	 * from the repository.
	 * <p>
	 * This method throws a RuntimeException if no event channel sink connector is
	 * present under the given <code>name</code>. If more than one event channel
	 * sink connector with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first event channel sink
	 * connector it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event channel sink connector
	 * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
	 */
	public EventChannelSinkConnector fetchOfEventChannelSinkConnector(String name) {
		Objects.requireNonNull(name, "name must not be null");
		EventChannelSinkConnector connector = repo.getEventChannelSinkConnector(name);
		if (connector == null)
			throw new RuntimeException("Event Channel Sink Connector '" + name + "' could not be found");
		return connector;
	}

	/**
	 * Extracts the event channel source connector referenced by <code>name</code>
	 * from the repository.
	 * <p>
	 * This method throws a RuntimeException if no event channel source connector is
	 * present under the given <code>name</code>. If more than one event channel
	 * source connector with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first event channel source
	 * connector it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event channel source connector
	 * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
	 */
	public EventChannelSourceConnector fetchOfEventChannelSourceConnector(String name) {
		Objects.requireNonNull(name, "name must not be null");
		EventChannelSourceConnector connector = repo.getEventChannelSourceConnector(name);
		if (connector == null)
			throw new RuntimeException("Event Channel Source Connector '" + name + "' could not be found");
		return connector;
	}

	/**
	 * Extracts the parameter referenced by <code>name</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no parameter is present under the
	 * given <code>name</code>. If more than one parameter with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first parameter it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the parameter
	 * @see org.palladiosimulator.pcm.repository.Parameter
	 */
	public Parameter fetchOfParameter(String name) {
		Objects.requireNonNull(name, "name must not be null");
		Parameter p = repo.getParameter(name);
		if (p == null)
			throw new RuntimeException("Parameter '" + name + "' could not be found");
		return p;
	}

	/**
	 * Extracts the parameter referenced by <code>name</code> occurring in the
	 * signature <code>context</code> from the repository.
	 * <p>
	 * This method throws a RuntimeException if no parameter is present under the
	 * given <code>name</code>. If more than one parameter with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first parameter it finds.
	 * </p>
	 * 
	 * @param name
	 * @param context
	 * @return the parameter
	 * @see org.palladiosimulator.pcm.repository.Parameter
	 */
	public Parameter fetchOfParameter(String name, Signature context) {
		Objects.requireNonNull(name, "name must not be null");
		Parameter p = repo.getParameter(name, context);
		if (p == null)
			throw new RuntimeException("Parameter '" + name + "' could not be found");
		return p;
	}

	/**
	 * Extracts the passive resource referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no passive resource is present under
	 * the given <code>name</code>. If more than one passive resource with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first passive resource it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the recovery action behaviour
	 * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
	 */
	public PassiveResource fetchOfPassiveResource(String name) {
		Objects.requireNonNull(name, "name must not be null");
		PassiveResource r = repo.getPassiveResource(name);
		if (r == null)
			throw new RuntimeException("Passive Resource '" + name + "' could not be found");
		return r;
	}

	/**
	 * Extracts the recovery action behaviour referenced by <code>name</code> from
	 * the repository.
	 * <p>
	 * This method throws a RuntimeException if no recovery action behaviour is
	 * present under the given <code>name</code>. If more than one recovery action
	 * behaviour with this <code>name</code> is present, a warning will be printed
	 * during runtime and the system chooses the first recovery action behaviour it
	 * finds.
	 * </p>
	 * 
	 * @param name
	 * @return the recovery action behaviour
	 * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
	 */
	public RecoveryActionBehaviour fetchOfRecoveryActionBehaviour(String name) {
		Objects.requireNonNull(name, "name must not be null");
		RecoveryActionBehaviour r = repo.getRecoveryActionBehaviour(name);
		if (r == null)
			throw new RuntimeException("Recovery action behaviour '" + name + "' could not be found");
		return r;
	}
}
