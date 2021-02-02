package componentModel.factory;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
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
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.repository.util.RepositoryValidator;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.subsystem.SubSystem;

import componentModel.apiControlFlowInterfaces.Repo;
import componentModel.apiControlFlowInterfaces.seff.InternalSeff;
import componentModel.apiControlFlowInterfaces.seff.RecoverySeff;
import componentModel.apiControlFlowInterfaces.seff.Seff;
import componentModel.repositoryStructure.RepositoryCreator;
import componentModel.repositoryStructure.components.BasicComponentCreator;
import componentModel.repositoryStructure.components.CompleteComponentTypeCreator;
import componentModel.repositoryStructure.components.CompositeComponentCreator;
import componentModel.repositoryStructure.components.ProvidesComponentTypeCreator;
import componentModel.repositoryStructure.components.SubSystemCreator;
import componentModel.repositoryStructure.components.VariableUsageCreator;
import componentModel.repositoryStructure.components.seff.SeffCreator;
import componentModel.repositoryStructure.interfaces.EventGroupCreator;
import componentModel.repositoryStructure.interfaces.EventTypeCreator;
import componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator;
import componentModel.repositoryStructure.interfaces.OperationInterfaceCreator;
import componentModel.repositoryStructure.interfaces.OperationSignatureCreator;
import componentModel.repositoryStructure.internals.CommunicationLinkResource;
import componentModel.repositoryStructure.internals.Failure;
import componentModel.repositoryStructure.internals.Primitive;
import componentModel.repositoryStructure.types.CompositeDataTypeCreator;
import componentModel.repositoryStructure.types.ExceptionTypeCreator;
import componentModel.repositoryStructure.types.ResourceTimeoutFailureTypeCreator;
import shared.structure.ProcessingResource;
import shared.util.RepositoryLoader;
import shared.validate.IModelValidator;
import shared.validate.ModelValidator;

/**
 * This class provides all the methods to create a Repository and create
 * Entities that are added to this Repository. Characteristics of the entities
 * are specified by method chaining.<br>
 * Existing entities that have to be referenced later can be retrieved by using
 * this componentModel.factory's fetching methods.
 * <p>
 * Start creating a repository like this:
 * <code>FluentRepositoryFactory create = new FluentRepositoryFactory();</code><br>
 * <code>Repository repo = create.newRepository()<br> 
 * <p style=
"margin-left: 130px">//create datatypes, components, interfaces etc. here</p>
 * <p style="margin-left: 130px">.createRepositoryNow();</p>
 *  </code>
 * 
 * Refer to the project's Readme for an introduction and detailed examples.
 * 
 * @author Louisa Lambrecht
 *
 */
public class FluentRepositoryFactory {

	private RepositoryCreator repo;
	private Repository primitives;
	private ResourceRepository resourceTypes;
	private Repository failures;

	/**
	 * Creates an instance of the FluentRepositoryFactory.
	 */
	public FluentRepositoryFactory() {
		EcorePlugin.ExtensionProcessor.process(null);
		primitives = RepositoryLoader.loadRepository(RepositoryLoader.PRIMITIVE_TYPES_PATH);
		resourceTypes = RepositoryLoader.loadResourceTypeRepository(RepositoryLoader.RESOURCE_TYPE_PATH);
		failures = RepositoryLoader.loadRepository(RepositoryLoader.FAILURE_TYPES_PATH);
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
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		
		IModelValidator validator = new ModelValidator(RepositoryValidator.INSTANCE, logger);
		
		this.repo = new RepositoryCreator(primitives, resourceTypes, failures, logger, validator);
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
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#withName(String)
	 * name},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
	 * type},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#withServiceEffectSpecification(Seff)
	 * SEFF},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#withPassiveResource(String, ResourceTimeoutFailureType)
	 * passive resource},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#withVariableUsage(VariableUsageCreator)
	 * variable usage} and
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#conforms(CompleteComponentTypeCreator)
	 * conformity}.<br>
	 * The possible roles to other interfaces are
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link componentModel.repositoryStructure.components.BasicComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
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
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withName(String)
	 * name},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
	 * type},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withVariableUsage(VariableUsageCreator)
	 * variable usage} and
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#conforms(CompleteComponentTypeCreator)
	 * conformity}.<br>
	 * The possible roles to other interfaces are
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.<br>
	 * Composite component/subsystem specific connections with other
	 * components/interfaces are
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
	 * assembly context},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withEventChannel()
	 * event channel},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
	 * assembly connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
	 * assembly event connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
	 * event channel sink connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
	 * event channel source connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
	 * assembly infrastructure connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
	 * provided delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
	 * required delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
	 * sink delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
	 * source delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
	 * provided infrastructure delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
	 * requires infrastructure delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
	 * required resource delegation connection},
	 * {@link componentModel.repositoryStructure.components.CompositeComponentCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
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
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withName(String)
	 * name}.<br>
	 * The possible roles to other interfaces are
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#requiresInfrastructure(InfrastructureInterface, String)
	 * requiring infrastructure interfaces}.<br>
	 * Composite component/subsystem specific connections with other
	 * components/interfaces are
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
	 * assembly context},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withEventChannel()
	 * event channel},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
	 * assembly connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
	 * assembly event connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
	 * event channel sink connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
	 * event channel source connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
	 * assembly infrastructure connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
	 * provided delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
	 * required delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
	 * sink delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
	 * source delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
	 * provided infrastructure delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
	 * requires infrastructure delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
	 * required resource delegation connection},
	 * {@link componentModel.repositoryStructure.components.SubSystemCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
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
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#withName(String)
	 * name} and provide the roles
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#conforms(ProvidesComponentTypeCreator)
	 * conformity},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link componentModel.repositoryStructure.components.CompleteComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
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
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#withName(String)
	 * name} and provide the roles
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#provides(OperationInterface, String)
	 * providing interfaces},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#requires(OperationInterface, String)
	 * requiring interfaces},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#emits(EventGroup, String)
	 * emitting event groups},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#handles(EventGroup, String)
	 * handling event groups},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#requiresResource(ResourceInterface)
	 * requiring resources},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
	 * providing infrastructure interfaces},
	 * {@link componentModel.repositoryStructure.components.ProvidesComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
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
	 * {@link componentModel.repositoryStructure.interfaces.OperationInterfaceCreator#withName(String)
	 * name}, their
	 * {@link componentModel.repositoryStructure.interfaces.OperationInterfaceCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link componentModel.repositoryStructure.interfaces.OperationInterfaceCreator#withOperationSignature()
	 * operation signatures} and the corresponding
	 * {@link componentModel.repositoryStructure.interfaces.OperationInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
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
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator#withName(String)
	 * name}, their
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator#withInfrastructureSignature()
	 * infrastructure signatures} and the corresponding
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
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
	 * {@link componentModel.repositoryStructure.interfaces.EventGroupCreator#withName(String)
	 * name}, their
	 * {@link componentModel.repositoryStructure.interfaces.EventGroupCreator#conforms(Interface)
	 * parental interfaces (conformity)}, their
	 * {@link componentModel.repositoryStructure.interfaces.EventGroupCreator#withEventType() event
	 * types} and the corresponding
	 * {@link componentModel.repositoryStructure.interfaces.EventGroupCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
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
	 * @see componentModel.repositoryStructure.internals.Primitive
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
	 * {@link componentModel.factory.FluentRepositoryFactory#fetchOfDataType(String)
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
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfDataType(String)
	 * @see componentModel.factory.FluentRepositoryFactory#fetchOfDataType(Primitive)
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
	 * {@link componentModel.repositoryStructure.types.CompositeDataTypeCreator#withInnerDeclaration(String, Primitive)
	 * .withInnerDeclaration(String, Primitive)} and/or
	 * {@link componentModel.repositoryStructure.types.CompositeDataTypeCreator#withInnerDeclaration(String, DataType)
	 * .withInnerDeclaration(String, DataType)}.
	 * </p>
	 * 
	 * @param name    the <i>unique</i> name of the composite data type
	 * @param parents array of parent composite data types
	 * @return the composite data type in the making
	 * @see componentModel.repositoryStructure.types.CompositeDataTypeCreator#withInnerDeclaration(String,
	 *      Primitive)
	 * @see componentModel.repositoryStructure.types.CompositeDataTypeCreator#withInnerDeclaration(String,
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
		h.setProcessingResourceType__HardwareInducedFailureType(repo.getProcessingResourceType(processingResource));
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
		return n;
	}

	/**
	 * Creates a new resource timeout failure type with name <code>name</code>.
	 * 
	 * @param name
	 * @return the resource timeout failure type
	 * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
	 */
	public ResourceTimeoutFailureTypeCreator newResourceTimeoutFailureType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		return new ResourceTimeoutFailureTypeCreator(name, this.repo);
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
	 * {@link componentModel.apiControlFlowInterfaces.seff.Seff#onSignature(Signature)
	 * onSignature(Signature)},
	 * {@link componentModel.apiControlFlowInterfaces.seff.Seff#withSeffTypeID(String)
	 * withSeffTypeID(String)} and
	 * {@link componentModel.apiControlFlowInterfaces.seff.Seff#withInternalBehaviour(InternalSeff)
	 * withInternalBehaviour(InternalSeff)} to define the seff.<br>
	 * In the end use the method
	 * {@link componentModel.apiControlFlowInterfaces.seff.Seff#withSeffBehaviour()
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
	 * {@link componentModel.apiControlFlowInterfaces.seff.InternalSeff#withStartAction()
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
	 * <li>{@link componentModel.apiControlFlowInterfaces.seff.RecoverySeff#withFailureType(Failure)
	 * withFailureType(Failure)} to add possibly occurring failures to the
	 * behaviour,
	 * <li>{@link componentModel.apiControlFlowInterfaces.seff.RecoverySeff#withAlternativeRecoveryBehaviour(org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour)
	 * withAlternativeRecoveryBehaviour(RecoveryActionBehaviour)} to add previously
	 * defined recovery behaviours as alternatives and
	 * <li>{@link componentModel.apiControlFlowInterfaces.seff.RecoverySeff#withSeffBehaviour()
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
	 * Creates a new {@link org.palladiosimulator.pcm.repository.OperationSignature
	 * OperationSignature}.
	 * <p>
	 * Every service of an interface has a unique signature, like <code>void
	 * doSomething(int a)</code>. A PCM signature is comparable to a method
	 * signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An operation signature contains
	 * <ul>
	 * <li>a
	 * {@link componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value),
	 * <li>an
	 * {@link componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures.
	 * </ul>
	 * A signature has to be unique for an interface through the tuple (identifier,
	 * order of parameters). Different interfaces can define equally named
	 * signatures, however, they are not identical.
	 * </p>
	 * 
	 * @return the operation signature in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withName(String)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String,
	 *      componentModel.repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see componentModel.repositoryStructure.interfaces.OperationSignatureCreator#createSignature()
	 */
	public OperationSignatureCreator newOperationSignature() {
		return new OperationSignatureCreator(repo);
	}

	/**
	 * Creates a new
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
	 * InfrastructureSignature}.
	 * <p>
	 * Every service of an interface has a unique signature, like <code>void
	 * doSomething(int a)</code>. A PCM signature is comparable to a method
	 * signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An infrastructure signature contains
	 * <ul>
	 * <!--
	 * <li>a
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value), -->
	 * <li>an
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures.
	 * </ul>
	 * A signature has to be unique for an interface through the tuple (identifier,
	 * order of parameters). Different interfaces can define equally named
	 * signatures, however, they are not identical.
	 * </p>
	 * 
	 * @return the infrastructure signature in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withName(String)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String,
	 *      componentModel.repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see componentModel.repositoryStructure.interfaces.InfrastructureSignatureCreator#createSignature()
	 */
	public InfrastructureSignatureCreator newInfrastructureSignature() {
		return new InfrastructureSignatureCreator(repo);
	}

	/**
	 * Creates a new {@link org.palladiosimulator.pcm.repository.EventType
	 * EventType}.
	 * <p>
	 * Every service of an interface/event group has a unique signature/event type,
	 * like <code>void
	 * doSomething(int a)</code>. A PCM signature/event type is comparable to a
	 * method signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An event type contains
	 * <ul>
	 * <li>a
	 * {@link componentModel.repositoryStructure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value),
	 * <li>an
	 * {@link componentModel.repositoryStructure.interfaces.EventTypeCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link componentModel.repositoryStructure.interfaces.EventTypeCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link componentModel.repositoryStructure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link componentModel.repositoryStructure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures/event types.
	 * </ul>
	 * A signature/event type has to be unique for an interface/event group through
	 * the tuple (identifier, order of parameters). Different interfaces/event
	 * groups can define equally named signatures/event types, however, they are not
	 * identical.
	 * </p>
	 * 
	 * @return the event type in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withName(String)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withParameter(String,
	 *      componentModel.repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see componentModel.repositoryStructure.interfaces.EventTypeCreator#createEventType()
	 */
	public EventTypeCreator newEventType() {
		return new EventTypeCreator(repo);
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
	 * {@link componentModel.repositoryStructure.components.VariableUsageCreator#withVariableCharacterisation(String, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * withVariableCharacterisation(String, VariableCharacterisationType)},
	 * {@link componentModel.repositoryStructure.components.VariableUsageCreator#withVariableReference(String)
	 * withVariableReference(String)} and
	 * {@link componentModel.repositoryStructure.components.VariableUsageCreator#withNamespaceReference(String, String...)
	 * withNamespaceReference(String, String...)} to define the variable usage.
	 * </p>
	 * 
	 * @return the variable usage in the making
	 * @see componentModel.repositoryStructure.components.VariableUsageCreator#withVariableCharacterisation(String,
	 *      org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
	 * @see componentModel.repositoryStructure.components.VariableUsageCreator#withVariableReference(String)
	 * @see componentModel.repositoryStructure.components.VariableUsageCreator#withNamespaceReference(String,
	 *      String...)
	 * @see org.palladiosimulator.pcm.parameter.VariableUsage
	 */
	public VariableUsageCreator newVariableUsage() {
		return new VariableUsageCreator(this.repo);
	}

	// ---------------------- Fetching methods ----------------------

	/**
	 * Extracts the by <code>name</code> referenced composite data type from the
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no composite data type is present
	 * under the given <code>name</code>. If more than one composite data type with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first composite data type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the composite data type
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(CompositeDataTypeCreator)
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no data type is present under the
	 * given <code>name</code>. If more than one data type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first data type it finds.
	 * </p>
	 * 
	 * @param name
	 * @param imported
	 * @return the data type
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(CollectionDataType)
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(CompositeDataTypeCreator)
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
	 * Extracts the resource timeout failure type referenced by <code>name</code>
	 * from the repository. If the entity belongs to an imported repository, refer
	 * to it as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no resource timeout failure type is
	 * present under the given <code>name</code>. If more than one resource timeout
	 * failure type with this <code>name</code> is present, a warning will be
	 * printed during runtime and the system chooses the first resource timeout
	 * failure type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the resource timeout failure type
	 * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
	 */
	public ResourceTimeoutFailureType fetchOfResourceTimeoutFailureType(String name) {
		ResourceTimeoutFailureType failureType = repo.getResourceTimeoutFailureType(name);
		if (failureType == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return failureType;
	}

	/**
	 * Extracts the failure type referenced by <code>failure</code> from the
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no failure type is present under the
	 * given <code>name</code>. If more than one failure type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first failure type it finds.
	 * </p>
	 * 
	 * @param failure
	 * @return the failure type
	 * @see org.palladiosimulator.pcm.reliability.FailureType
	 */
	public FailureType fetchOfFailureType(Failure failure) {
		FailureType f = repo.getFailureType(failure);
		if (f == null)
			throw new RuntimeException("Failure Type '" + failure + "' could not be found");
		return f;
	}

	/**
	 * Extracts the failure type referenced by <code>name</code> from the
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no failure type is present under the
	 * given <code>name</code>. If more than one failure type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first failure type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the failure type
	 * @see org.palladiosimulator.pcm.reliability.FailureType
	 */
	public FailureType fetchOfFailureType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		FailureType f = repo.getFailureType(name);
		if (f == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return f;
	}

	/**
	 * Extracts the exception type referenced by <code>name</code> from the
	 * repository.
	 * <p>
	 * This method throws a RuntimeException if no exception type is present under
	 * the given <code>name</code>. If more than one exception type with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first exception type it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the exception type
	 * @see org.palladiosimulator.pcm.repository.ExceptionType
	 */
	public ExceptionType fetchOfExceptionType(String name) {
		Objects.requireNonNull(name, "name must not be null");
		ExceptionType e = repo.getExceptionType(name);
		if (e == null)
			throw new RuntimeException("Failure Type '" + name + "' could not be found");
		return e;
	}

	/**
	 * Extracts the <b>component</b> referenced by <code>name</code> from the
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no <b>component</b> is present under
	 * the given <code>name</code>. If more than one <b>component</b> with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first <b>component</b> it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the <b>component</b>
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no basic component is present under
	 * the given <code>name</code>. If more than one basic component with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first basic component it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the basic component
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no composite component is present
	 * under the given <code>name</code>. If more than one composite component with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first composite component it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the composite component
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no subsystem is present under the
	 * given <code>name</code>. If more than one subsystem with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first subsystem it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the subsystem
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.components.Component)
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no interface is present under the
	 * given <code>name</code>. If more than one interface with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first interface it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the interface
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.interfaces.Interface)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no operation interface is present
	 * under the given <code>name</code>. If more than one operation interface with
	 * this <code>name</code> is present, a warning will be printed during runtime
	 * and the system chooses the first operation interface it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the operation interface
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.interfaces.Interface)
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
	 * the repository. If the entity belongs to an imported repository, refer to it
	 * as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.interfaces.Interface)
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no event group is present under the
	 * given <code>name</code>. If more than one event group with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first event group it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the event group
	 * @see componentModel.apiControlFlowInterfaces.RepoAddition#addToRepository(componentModel.repositoryStructure.interfaces.Interface)
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * from the repository. If the entity belongs to an imported repository, refer
	 * to it as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * from the repository. If the entity belongs to an imported repository, refer
	 * to it as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * Extracts the signature referenced by <code>name</code> from the repository.
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no signature is present under the
	 * given <code>name</code>. If more than one signature with this
	 * <code>name</code> is present, a warning will be printed during runtime and
	 * the system chooses the first signature it finds.
	 * </p>
	 * 
	 * @param name
	 * @return the signature
	 * @see org.palladiosimulator.pcm.repository.Signature
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * Extracts the infrastructure signature referenced by <code>name</code> from
	 * the repository. If the entity belongs to an imported repository, refer to it
	 * as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
	 * <p>
	 * This method throws a RuntimeException if no infrastructure signature is
	 * present under the given <code>name</code>. If more than one infrastructure
	 * signature with this <code>name</code> is present, a warning will be printed
	 * during runtime and the system chooses the first infrastructure signature it
	 * finds.
	 * </p>
	 * 
	 * @param name
	 * @return the infrastructure signature
	 * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
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
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * Extracts the parameter referenced by <code>name</code> from the repository.
	 * If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * signature <code>context</code> from the repository. If the entity belongs to
	 * an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * repository. If the entity belongs to an imported repository, refer to it as
	 * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
	 * the repository. If the entity belongs to an imported repository, refer to it
	 * as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
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
