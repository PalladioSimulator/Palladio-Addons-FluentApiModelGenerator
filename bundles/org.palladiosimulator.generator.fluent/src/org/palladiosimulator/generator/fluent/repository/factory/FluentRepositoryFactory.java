package org.palladiosimulator.generator.fluent.repository.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.exceptions.FluentApiException;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.repository.api.Repo;
import org.palladiosimulator.generator.fluent.repository.api.seff.InternalSeff;
import org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff;
import org.palladiosimulator.generator.fluent.repository.api.seff.Seff;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.SeffCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator;
import org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Failure;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.types.ExceptionTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.types.ResourceTimeoutFailureTypeCreator;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.CommunicationLinkResource;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
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
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.subsystem.SubSystem;

/**
 * This class provides all the methods to create a Repository and create Entities that are added to
 * this Repository. Characteristics of the entities are specified by method chaining.<br>
 * Existing entities that have to be referenced later can be retrieved by using this
 * org.palladiosimulator.generator.fluent.component.factory's fetching methods.
 * <p>
 * Start creating a repository like this:
 * <code>FluentRepositoryFactory create = new FluentRepositoryFactory();</code><br>
 * <code>Repository repo = create.newRepository()<br>
 * <p style=
"margin-left: 130px">//create datatypes, components, interfaces etc. here</p>
 * <p style="margin-left: 130px">.createRepositoryNow();</p>
 *  </code> Refer to the project's Readme for an introduction and detailed examples.
 *
 * TODO: all public methods can be called before newRepository, leading to a NullPointerException.
 * This can be resolved by checking it at the beginning of every method and throwing an
 * IllegalStateException with something helpful like "newRepository() must be called first!".
 *
 * @author Louisa Lambrecht
 */
public class FluentRepositoryFactory {

    private RepositoryCreator repo;
    private final Repository primitives;
    private final ResourceRepository resourceTypes;
    private final Repository failures;

    /**
     * Creates an instance of the FluentRepositoryFactory.
     */
    public FluentRepositoryFactory() {
        EcorePlugin.ExtensionProcessor.process(null);
        this.primitives = ModelLoader.loadRepository(ModelLoader.PRIMITIVE_TYPES_PATH);
        this.resourceTypes = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
        this.failures = ModelLoader.loadRepository(ModelLoader.FAILURE_TYPES_PATH);
    }

    // ---------------------- Repository ----------------------
    /**
     * Creates a representation of the model object '<em><b>Repository</b></em>'.
     * <p>
     * The repository entity allows storing components, data types, and interfaces to be fetched and
     * reused for construction of component instances as well as new component types.
     * </p>
     *
     * @return the repository in the making
     */
    public Repo newRepository() {
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);

        final IModelValidator validator = new ModelValidator(logger);

        this.repo = new RepositoryCreator(this.primitives, this.resourceTypes, this.failures, logger, validator);
        return this.repo;
    }

    // ---------------------- Components ----------------------
    /**
     * Creates a new basic component.
     * <p>
     * Basic components are atomic building blocks of a software architecture. Component developers
     * specify basic components by associating interfaces to them in a providing or requiring role.
     * </p>
     * <p>
     * Basic components offer the characteristics
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
     * type},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#withServiceEffectSpecification(Seff)
     * SEFF},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#withPassiveResource(String, ResourceTimeoutFailureType)
     * passive resource},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#withVariableUsage(VariableUsageCreator)
     * variable usage} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#conforms(CompleteComponentTypeCreator)
     * conformity}.<br>
     * The possible roles to other interfaces are
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#provides(OperationInterface, String)
     * providing interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#requires(OperationInterface, String)
     * requiring interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#emits(EventGroup, String)
     * emitting event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#handles(EventGroup, String)
     * handling event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#requiresResource(ResourceInterface)
     * requiring resources},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#providesInfrastructure(InfrastructureInterface, String)
     * providing infrastructure interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.BasicComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
     * requiring infrastructure interfaces}.
     * </p>
     *
     * @return the basic component in the making
     * @see org.palladiosimulator.pcm.repository.BasicComponent
     */
    public BasicComponentCreator newBasicComponent() {
        return new BasicComponentCreator(this.repo);
    }

    /**
     * Creates a new composite component.
     * <p>
     * Composite components are special implementation component types, which are composed from
     * inner components. Component developers compose inner components within composite components
     * with assembly connectors. A composite component may contain other composite components, which
     * are also themselves composed out of inner components. This enables building arbitrary
     * hierarchies of nested components.
     * </p>
     * <p>
     * Composite components offer the characteristics
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withName(String)
     * name},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#ofType(org.palladiosimulator.pcm.repository.ComponentType)
     * type},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withVariableUsage(VariableUsageCreator)
     * variable usage} and
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#conforms(CompleteComponentTypeCreator)
     * conformity}.<br>
     * The possible roles to other interfaces are
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#provides(OperationInterface, String)
     * providing interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#requires(OperationInterface, String)
     * requiring interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#emits(EventGroup, String)
     * emitting event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#handles(EventGroup, String)
     * handling event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#requiresResource(ResourceInterface)
     * requiring resources},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#providesInfrastructure(InfrastructureInterface, String)
     * providing infrastructure interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#requiresInfrastructure(InfrastructureInterface, String)
     * requiring infrastructure interfaces}.<br>
     * Composite component/subsystem specific connections with other components/interfaces are
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
     * assembly context},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withEventChannel()
     * event channel},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
     * assembly connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
     * assembly event connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
     * event channel sink connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
     * event channel source connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
     * assembly infrastructure connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
     * provided delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
     * required delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
     * sink delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
     * source delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
     * provided infrastructure delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
     * requires infrastructure delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
     * required resource delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompositeComponentCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
     * resource required delegation connection}.
     *
     * @return the composite component in the making
     * @see org.palladiosimulator.pcm.repository.CompositeComponent
     */
    public CompositeComponentCreator newCompositeComponent() {
        return new CompositeComponentCreator(this.repo);
    }

    /**
     * Creates a new subsystem.
     * <p>
     * A SubSystem is structurally comparable to a CompositeComponent. The major difference is the
     * white-box property it preserves for System Deployers, meaning that they can be allocated to
     * different nodes of the resource environment.
     * </p>
     * <p>
     * Subsystems offer the characteristics
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withName(String)
     * name}.<br>
     * The possible roles to other interfaces are
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#provides(OperationInterface, String)
     * providing interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#requires(OperationInterface, String)
     * requiring interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#emits(EventGroup, String)
     * emitting event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#handles(EventGroup, String)
     * handling event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#requiresResource(ResourceInterface)
     * requiring resources},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#providesInfrastructure(InfrastructureInterface, String)
     * providing infrastructure interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#requiresInfrastructure(InfrastructureInterface, String)
     * requiring infrastructure interfaces}.<br>
     * Composite component/subsystem specific connections with other components/interfaces are
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withAssemblyContext(RepositoryComponent, String, org.palladiosimulator.pcm.parameter.VariableUsage...)
     * assembly context},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withEventChannel()
     * event channel},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withAssemblyConnection(OperationProvidedRole, AssemblyContext, OperationRequiredRole, AssemblyContext)
     * assembly connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withAssemblyEventConnection(SinkRole, AssemblyContext, SourceRole, AssemblyContext, String)
     * assembly event connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withEventChannelSinkConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SinkRole, String)
     * event channel sink connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withEventChannelSourceConnection(AssemblyContext, org.palladiosimulator.pcm.core.composition.EventChannel, SourceRole)
     * event channel source connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withAssemblyInfrastructureConnection(InfrastructureProvidedRole, AssemblyContext, InfrastructureRequiredRole, AssemblyContext)
     * assembly infrastructure connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withProvidedDelegationConnection(AssemblyContext, OperationProvidedRole, OperationProvidedRole)
     * provided delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withRequiredDelegationConnection(AssemblyContext, OperationRequiredRole, OperationRequiredRole)
     * required delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withSinkDelegationConnection(AssemblyContext, SinkRole, SinkRole)
     * sink delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withSourceDelegationConnection(AssemblyContext, SourceRole, SourceRole)
     * source delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withProvidedInfrastructureDelegationConnection(AssemblyContext, InfrastructureProvidedRole, InfrastructureProvidedRole)
     * provided infrastructure delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withRequiredInfrastructureDelegationConnection(AssemblyContext, InfrastructureRequiredRole, InfrastructureRequiredRole)
     * requires infrastructure delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#withRequiredResourceDelegationConnection(AssemblyContext, ResourceRequiredRole, ResourceRequiredRole)
     * required resource delegation connection},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.SubSystemCreator#resourceRequiredDegelationConnection(ResourceRequiredRole, ResourceRequiredRole)
     * resource required delegation connection}.
     *
     * @return the subsystem in the making
     * @see org.palladiosimulator.pcm.subsystem.SubSystem
     */
    public SubSystemCreator newSubSystem() {
        return new SubSystemCreator(this.repo);
    }

    /**
     * Creates a new complete component type.
     * <p>
     * Complete (Component) types abstract from the realization of components. They only contain
     * provided and required roles omitting the components’ internal structure, i.e., the service
     * effect specifications or assemblies.
     * </p>
     * <p>
     * Complete component types offer the characteristics
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#withName(String)
     * name} and provide the roles
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#provides(OperationInterface, String)
     * providing interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#requires(OperationInterface, String)
     * requiring interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#emits(EventGroup, String)
     * emitting event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#handles(EventGroup, String)
     * handling event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#conforms(ProvidesComponentTypeCreator)
     * conformity},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#requiresResource(ResourceInterface)
     * requiring resources},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
     * providing infrastructure interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.CompleteComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
     * requiring infrastructure interfaces}.
     * </p>
     *
     * @return the complete component type in the making
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType
     */
    public CompleteComponentTypeCreator newCompleteComponentType() {
        return new CompleteComponentTypeCreator(this.repo);
    }

    /**
     * Creates a new provided component type.
     * <p>
     * Provided (Component) Types abstract a component to its provided interfaces, leaving its
     * requirements and implementation details open. So, provided types subsume components which
     * offer the same functionality, but with different implementations.
     * </p>
     * <p>
     * Provided component types offer the characteristics
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#withName(String)
     * name} and provide the roles
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#provides(OperationInterface, String)
     * providing interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#requires(OperationInterface, String)
     * requiring interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#emits(EventGroup, String)
     * emitting event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#handles(EventGroup, String)
     * handling event groups},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#requiresResource(ResourceInterface)
     * requiring resources},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#providesInfrastructure(InfrastructureInterface, String)
     * providing infrastructure interfaces},
     * {@link org.palladiosimulator.generator.fluent.repository.structure.components.ProvidesComponentTypeCreator#requiresInfrastructure(InfrastructureInterface, String)
     * requiring infrastructure interfaces}.
     * </p>
     *
     * @return the provides component type in the making
     * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
     */
    public ProvidesComponentTypeCreator newProvidesComponentType() {
        return new ProvidesComponentTypeCreator(this.repo);
    }

    // ---------------------- Interfaces ----------------------
    /**
     * Creates a new operation interface.
     * <p>
     * The OperationInterface is a specific type of interface related to operation calls. For this,
     * it also references a set of operation interfaces. Operations can represent methods, functions
     * or any comparable concept.
     * </p>
     * <p>
     * Operation interfaces are defined by their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator#withName(String)
     * name}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator#conforms(Interface)
     * parental interfaces (conformity)}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator#withOperationSignature()
     * operation signatures} and the corresponding
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
     * required characterizations}.
     * </p>
     *
     * @return the operation interface in the making
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationInterfaceCreator newOperationInterface() {
        return new OperationInterfaceCreator(this.repo);
    }

    /**
     * Creates a new infrastructure interface.
     * <p>
     * Infrastructure interfaces are defined by their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator#withName(String)
     * name}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator#conforms(Interface)
     * parental interfaces (conformity)}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator#withInfrastructureSignature()
     * infrastructure signatures} and the corresponding
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureInterfaceCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
     * required characterizations}.
     * </p>
     *
     * @return the infrastructure interface in the making
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureInterfaceCreator newInfrastructureInterface() {
        return new InfrastructureInterfaceCreator(this.repo);
    }

    /**
     * Creates a new event group.
     * <p>
     * An EventGroup combines a set of EventTypes that are supported by a Sink and/or a Source. This
     * is comparable to an operation interface combining a set of operation signatures.
     * </p>
     * <p>
     * Event groups are defined by their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator#withName(String)
     * name}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator#conforms(Interface)
     * parental interfaces (conformity)}, their
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator#withEventType()
     * event types} and the corresponding
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventGroupCreator#withRequiredCharacterisation(Parameter, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
     * required characterizations}.
     * </p>
     *
     * @return the event group in the making
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public EventGroupCreator newEventGroup() {
        return new EventGroupCreator(this.repo);
    }

    // ---------------------- Failure Types ----------------------
    // access Failure Types using enums

    // ---------------------- DataTypes ----------------------
    // access Primitive Data Types using enums
    /**
     * Creates a new collection data type with name <code>name</code> and of type
     * <code>primitive</code>.
     * <p>
     * A collection data type represents a list, array, set of items of the particular type. For
     * example, <code>create.newCollectionDataType("StringList",
     * Primitive.String)</code> realizes a data type conforming <code>List&lt;String&gt;</code> in
     * Java.
     * </p>
     *
     * @param name
     *            the <i>unique</i> name of the new collection data type
     * @param primitive
     *            the primitive data type that the elements have
     * @return the collection data type
     * @see org.palladiosimulator.pcm.repository.CollectionDataType
     * @see org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive
     */
    public CollectionDataType newCollectionDataType(final String name, final Primitive primitive) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final PrimitiveDataType p = this.repo.getPrimitiveDataType(primitive);

        final CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
        coll.setEntityName(name);
        coll.setInnerType_CollectionDataType(p);

        return coll;
    }

    /**
     * Creates a new collection data type with name <code>name</code> and of type
     * <code>dataType</code>.
     * <p>
     * A collection data type represents a list, array, set of items of the particular type. All
     * previously created data types and primitive data types can be referenced using fetching
     * methods, e.g.
     * {@link org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * fetchOfDataType(String)}. <br>
     * For example, <code>create.newCollectionDataType("PersonList",
     * create.fetchOfDataType("Person"))</code> realizes a data type conforming
     * <code>List&lt;Person&gt;</code> in Java, assuming that a different data type called "Person"
     * has been previously declared.
     * </p>
     *
     * @param name
     *            the <i>unique</i> name of the new collection data type
     * @param dataType
     *            the data type that the elements have
     * @return the collection data type
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfDataType(Primitive)
     * @see org.palladiosimulator.pcm.repository.CollectionDataType
     * @see org.palladiosimulator.pcm.repository.DataType
     */
    public static CollectionDataType newCollectionDataType(final String name, final DataType dataType) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
        coll.setEntityName(name);
        coll.setInnerType_CollectionDataType(dataType);
        return coll;
    }

    /**
     * Creates a new collection data type.
     * <p>
     * A composite data type represents a complex data type containing other data types. This
     * construct is common in higher programming languages as record, struct, or class.<br>
     * The contained data types can be added using method chaining with
     * {@link org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator#withInnerDeclaration(String, Primitive)
     * .withInnerDeclaration(String, Primitive)} and/or
     * {@link org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator#withInnerDeclaration(String, DataType)
     * .withInnerDeclaration(String, DataType)}.
     * </p>
     *
     * @param name
     *            the <i>unique</i> name of the composite data type
     * @param parents
     *            array of parent composite data types
     * @return the composite data type in the making
     * @see org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator#withInnerDeclaration(String,
     *      Primitive)
     * @see org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator#withInnerDeclaration(String,
     *      DataType)
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    public CompositeDataTypeCreator newCompositeDataType() {
        return new CompositeDataTypeCreator(this.repo);
    }

    /**
     * Creates a new hardware induced failure type with name <code>name</code> and processing
     * resource <code>processingResource</code>.
     *
     * @param name
     * @param processingResource
     * @return the hardware induced failure type
     * @see org.palladiosimulator.pcm.reliability.HardwareInducedFailureType
     */
    public HardwareInducedFailureType newHardwareInducedFailureType(final String name,
            final ProcessingResource processingResource) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final HardwareInducedFailureType h = ReliabilityFactory.eINSTANCE.createHardwareInducedFailureType();
        h.setEntityName(name);
        h.setProcessingResourceType__HardwareInducedFailureType(
                this.repo.getProcessingResourceType(processingResource));
        return h;
    }

    /**
     * Creates a new network induced failure type with name <code>name</code> and communication link
     * resource <code>communicationLinkResource</code>.
     *
     * @param name
     * @param communicationLinkResource
     * @return the network induced failure type
     * @see org.palladiosimulator.pcm.reliability.NetworkInducedFailureType
     */
    public NetworkInducedFailureType newNetworkInducedFailureType(final String name,
            final CommunicationLinkResource communicationLinkResource) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final NetworkInducedFailureType n = ReliabilityFactory.eINSTANCE.createNetworkInducedFailureType();
        n.setEntityName(name);
        n.setCommunicationLinkResourceType__NetworkInducedFailureType(
                this.repo.getCommunicationLinkResource(communicationLinkResource));
        return n;
    }

    /**
     * Creates a new resource timeout failure type with name <code>name</code>.
     *
     * @param name
     * @return the resource timeout failure type
     * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
     */
    public ResourceTimeoutFailureTypeCreator newResourceTimeoutFailureType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        return new ResourceTimeoutFailureTypeCreator(name, this.repo);
    }

    /**
     * Creates a new software induced failure type with name <code>name</code>.
     *
     * @param name
     * @return the software induced failure type
     * @see org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType
     */
    public static SoftwareInducedFailureType newSoftwareInducedFailureType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final SoftwareInducedFailureType s = ReliabilityFactory.eINSTANCE.createSoftwareInducedFailureType();
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
     * '<em><b>Resource-DemandingBehaviour</b></em>' at the same time inheriting from both classes.
     * <p>
     * A resource demanding service effect specification (RDSEFF) is a special type of SEFF designed
     * for performance and reliability predictions. Besides dependencies between provided and
     * required services of a component, it additionally includes notions of resource usage, data
     * flow, and parametric dependencies for more accurate predictions. Therefore, the class
     * contains a chain of AbstractActions.
     * </p>
     * <p>
     * Use the methods
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.Seff#onSignature(Signature)
     * onSignature(Signature)},
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.Seff#withSeffTypeID(String)
     * withSeffTypeID(String)} and
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.Seff#withInternalBehaviour(InternalSeff)
     * withInternalBehaviour(InternalSeff)} to define the seff.<br>
     * In the end use the method
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.Seff#withSeffBehaviour()
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
     * Creates a new ResourceDemandingInternalBehaviour/ResourceDemandingBehaviour/ForkedBehaviour
     * (depending on the context). If the context does not distinctly favor any behaviour,
     * ResourceDemandingBehaviour acts as default.
     * <p>
     * It models the behaviour of a component service as a sequence of internal actions with
     * resource demands, control flow constructs, and external calls. Therefore, the class contains
     * a chain of AbstractActions.
     * </p>
     * <p>
     * Use the method
     * {@link org.palladiosimulator.generator.fluent.repository.api.seff.InternalSeff#withStartAction()
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
     * Creates a new {@link org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
     * RecoveryActionBehaviour}.
     * <p>
     * A recovery action behaviour provides a behaviour (a chain of AbstractActions) and
     * alternatives of recovery blocks. They are resource demanding behaviours, thus any behaviour
     * can be defined as an alternative. The alternatives of a recovery block form a chain. They are
     * failure handling entities, i.e. they can handle failures that occur in previous alternatives.
     * If one alternative fails, the next alternative is executed that can handle the failure type.
     * </p>
     * <p>
     * Use the methods
     * <ul>
     * <li>{@link org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff#withFailureType(Failure)
     * withFailureType(Failure)} to add possibly occurring failures to the behaviour,
     * <li>{@link org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff#withAlternativeRecoveryBehaviour(org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour)
     * withAlternativeRecoveryBehaviour(RecoveryActionBehaviour)} to add previously defined recovery
     * behaviours as alternatives and
     * <li>{@link org.palladiosimulator.generator.fluent.repository.api.seff.RecoverySeff#withSeffBehaviour()
     * withSeffBehaviour()} to specify this RecoveryActionBehaviour's step-wise behaviour.
     * </ul>
     * </p>
     * <p>
     * The alternatives of a recovery block form a chain and alternatives are referenced by name and
     * have to be previously defined. Thus the chain of alternatives has to be created inversely.
     * The last alternative that has no alternatives itself is created first, so the second last can
     * reference it as its alternative.
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
     * doSomething(int a)</code>. A PCM signature is comparable to a method signature in programming
     * languages like C#, Java or the OMG IDL.
     * </p>
     * <p>
     * An operation signature contains
     * <ul>
     * <li>a
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * type of the return value} or void (no return value),
     * <li>an
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withName(String)
     * identifier} naming the service,
     * <li>an ordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
     * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and an
     * <code>identifier</code> (which is unique across the parameters). Optionally, the
     * <code>modifiers</code> in, out, and inout (with its OMG IDL semantics) can be used for
     * parameters.
     * <li>and an unordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * org.palladiosimulator.generator.fluent.exceptions}.
     * <li>Furthermore
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * failures} that may occur inside external services must be specified at the service
     * signatures.
     * </ul>
     * A signature has to be unique for an interface through the tuple (identifier, order of
     * parameters). Different interfaces can define equally named signatures, however, they are not
     * identical.
     * </p>
     *
     * @return the operation signature in the making
     * @see org.palladiosimulator.pcm.repository.Signature
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withParameter(String,
     *      org.palladiosimulator.pcm.repository.DataType,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withParameter(String,
     *      org.palladiosimulator.generator.fluent.repository.structure.internals.datatypes.Primitive,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.OperationSignatureCreator#createSignature()
     */
    public OperationSignatureCreator newOperationSignature() {
        return new OperationSignatureCreator(this.repo);
    }

    /**
     * Creates a new {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
     * InfrastructureSignature}.
     * <p>
     * Every service of an interface has a unique signature, like <code>void
     * doSomething(int a)</code>. A PCM signature is comparable to a method signature in programming
     * languages like C#, Java or the OMG IDL.
     * </p>
     * <p>
     * An infrastructure signature contains
     * <ul>
     * <!--
     * <li>a
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * type of the return value} or void (no return value), -->
     * <li>an
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withName(String)
     * identifier} naming the service,
     * <li>an ordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
     * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and an
     * <code>identifier</code> (which is unique across the parameters). Optionally, the
     * <code>modifiers</code> in, out, and inout (with its OMG IDL semantics) can be used for
     * parameters.
     * <li>and an unordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * org.palladiosimulator.generator.fluent.exceptions}.
     * <li>Furthermore
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * failures} that may occur inside external services must be specified at the service
     * signatures.
     * </ul>
     * A signature has to be unique for an interface through the tuple (identifier, order of
     * parameters). Different interfaces can define equally named signatures, however, they are not
     * identical.
     * </p>
     *
     * @return the infrastructure signature in the making
     * @see org.palladiosimulator.pcm.repository.Signature
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withParameter(String,
     *      org.palladiosimulator.pcm.repository.DataType,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withParameter(String,
     *      org.palladiosimulator.generator.fluent.repository.structure.internals.datatypes.Primitive,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.InfrastructureSignatureCreator#createSignature()
     */
    public InfrastructureSignatureCreator newInfrastructureSignature() {
        return new InfrastructureSignatureCreator(this.repo);
    }

    /**
     * Creates a new {@link org.palladiosimulator.pcm.repository.EventType EventType}.
     * <p>
     * Every service of an interface/event group has a unique signature/event type, like <code>void
     * doSomething(int a)</code>. A PCM signature/event type is comparable to a method signature in
     * programming languages like C#, Java or the OMG IDL.
     * </p>
     * <p>
     * An event type contains
     * <ul>
     * <li>a
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * type of the return value} or void (no return value),
     * <li>an
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withName(String)
     * identifier} naming the service,
     * <li>an ordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
     * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and an
     * <code>identifier</code> (which is unique across the parameters). Optionally, the
     * <code>modifiers</code> in, out, and inout (with its OMG IDL semantics) can be used for
     * parameters.
     * <li>and an unordered set of
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * org.palladiosimulator.generator.fluent.exceptions}.
     * <li>Furthermore
     * {@link org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * failures} that may occur inside external services must be specified at the service
     * signatures/event types.
     * </ul>
     * A signature/event type has to be unique for an interface/event group through the tuple
     * (identifier, order of parameters). Different interfaces/event groups can define equally named
     * signatures/event types, however, they are not identical.
     * </p>
     *
     * @return the event type in the making
     * @see org.palladiosimulator.pcm.repository.Signature
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withName(String)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withParameter(String,
     *      org.palladiosimulator.pcm.repository.DataType,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withParameter(String,
     *      org.palladiosimulator.generator.fluent.repository.structure.internals.datatypes.Primitive,
     *      org.palladiosimulator.pcm.repository.ParameterModifier)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
     * @see org.palladiosimulator.generator.fluent.repository.structure.interfaces.EventTypeCreator#createEventType()
     */
    public EventTypeCreator newEventType() {
        return new EventTypeCreator(this.repo);
    }

    /**
     * Creates a new {@link org.palladiosimulator.pcm.parameter.VariableUsage VariableUsage}.
     * <p>
     * Variable usages are used to characterize variables like input and output variables or
     * component parameters. They contain the specification of the variable as
     * VariableCharacterisation and also refer to the name of the characterized variable in its
     * namedReference association.
     * </p>
     * <p>
     * Use the methods
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withVariableCharacterisation(String, org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
     * withVariableCharacterisation(String, VariableCharacterisationType)},
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withVariableReference(String)
     * withVariableReference(String)} and
     * {@link org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withNamespaceReference(String, String...)
     * withNamespaceReference(String, String...)} to define the variable usage.
     * </p>
     *
     * @return the variable usage in the making
     * @see org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withVariableCharacterisation(String,
     *      org.palladiosimulator.pcm.parameter.VariableCharacterisationType)
     * @see org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withVariableReference(String)
     * @see org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator#withNamespaceReference(String,
     *      String...)
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    // VariableUsageCreator moved to org.palladiosimulator.generator.fluent.shared.components
    public VariableUsageCreator newVariableUsage() {
        return new VariableUsageCreator();
    }

    // ---------------------- Fetching methods ----------------------

    /**
     * Extracts the by <code>name</code> referenced composite data type from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no composite data type is present under the given
     * <code>name</code>. If more than one composite data type with this <code>name</code> is
     * present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first composite data type it finds.
     * </p>
     *
     * @param name
     * @return the composite data type
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(CompositeDataTypeCreator)
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    public CompositeDataType fetchOfCompositeDataType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final CompositeDataType dataType = this.repo.getCompositeDataType(name);
        if (dataType == null) {
            throw new FluentApiException("Composite data type '" + name + "' could not be found");
        }

        return dataType;
    }

    /**
     * Extracts the primitive data type corresponding to the enum <code>primitive</code> from the
     * repository.
     *
     * @param primitive
     * @return the data type
     * @see org.palladiosimulator.pcm.repository.PrimitiveDataType
     */
    public DataType fetchOfDataType(final Primitive primitive) {
        final PrimitiveDataType p = this.repo.getPrimitiveDataType(primitive);
        if (p == null) {
            throw new FluentApiException("Primitive data Type '" + primitive + "' could not be found");
        }
        return p;
    }

    /**
     * Extracts the by <code>name</code> referenced data type from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no data type is present under the given
     * <code>name</code>. It is advised to check existence first by calling
     * {@link FluentRepositoryFactory#containsDataType(String)}. If more than one data type with
     * this <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first data type it finds.
     * </p>
     *
     * @param name
     * @param imported
     * @return the data type
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(CollectionDataType)
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(CompositeDataTypeCreator)
     * @see org.palladiosimulator.pcm.repository.DataType
     */
    public DataType fetchOfDataType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        DataType dataType = this.repo.getDataType(name);
        if (dataType == null) {
            dataType = this.repo.getPrimitiveDataType(name);
        }
        if (dataType == null) {
            throw new FluentApiException("Datatype '" + name + "' could not be found");
        }

        return dataType;
    }

    /**
     * Checks whether the by <code>name</code> referenced data type is in the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     *
     * @param name
     * @return <code>true</code> iff the data type is contained
     * @see apiControlFlowInterfaces.RepoAddition#addToRepository(CollectionDataType)
     * @see apiControlFlowInterfaces.RepoAddition#addToRepository(CompositeDataTypeCreator)
     * @see org.palladiosimulator.pcm.repository.DataType
     */
    public boolean containsDataType(final String name) {
        try {
            return this.repo.getDataType(name) != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Extracts the resource timeout failure type referenced by <code>name</code> from the
     * repository. If the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no resource timeout failure type is present under
     * the given <code>name</code>. If more than one resource timeout failure type with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first resource timeout failure type
     * it finds.
     * </p>
     *
     * @param name
     * @return the resource timeout failure type
     * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
     */
    public ResourceTimeoutFailureType fetchOfResourceTimeoutFailureType(final String name) {
        final ResourceTimeoutFailureType failureType = this.repo.getResourceTimeoutFailureType(name);
        if (failureType == null) {
            throw new FluentApiException("Failure Type '" + name + "' could not be found");
        }
        return failureType;
    }

    /**
     * Extracts the failure type referenced by <code>failure</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no failure type is present under the given
     * <code>name</code>. If more than one failure type with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first failure type it finds.
     * </p>
     *
     * @param failure
     * @return the failure type
     * @see org.palladiosimulator.pcm.reliability.FailureType
     */
    public FailureType fetchOfFailureType(final Failure failure) {
        final FailureType f = this.repo.getFailureType(failure);
        if (f == null) {
            throw new FluentApiException("Failure Type '" + failure + "' could not be found");
        }
        return f;
    }

    /**
     * Extracts the failure type referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no failure type is present under the given
     * <code>name</code>. If more than one failure type with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first failure type it finds.
     * </p>
     *
     * @param name
     * @return the failure type
     * @see org.palladiosimulator.pcm.reliability.FailureType
     */
    public FailureType fetchOfFailureType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final FailureType f = this.repo.getFailureType(name);
        if (f == null) {
            throw new FluentApiException("Failure Type '" + name + "' could not be found");
        }
        return f;
    }

    /**
     * Extracts the exception type referenced by <code>name</code> from the repository.
     * <p>
     * This method throws a FluentApiException if no exception type is present under the given
     * <code>name</code>. If more than one exception type with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first exception type it finds.
     * </p>
     *
     * @param name
     * @return the exception type
     * @see org.palladiosimulator.pcm.repository.ExceptionType
     */
    public ExceptionType fetchOfExceptionType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final ExceptionType e = this.repo.getExceptionType(name);
        if (e == null) {
            throw new FluentApiException("Failure Type '" + name + "' could not be found");
        }
        return e;
    }

    /**
     * Extracts the <b>component</b> referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no <b>component</b> is present under the given
     * <code>name</code>. If more than one <b>component</b> with this <code>name</code> is present,
     * a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first <b>component</b> it finds.
     * </p>
     *
     * @param name
     * @return the <b>component</b>
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    public RepositoryComponent fetchOfComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final RepositoryComponent component = this.repo.getComponent(name);
        if (component == null) {
            throw new FluentApiException("Component '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the basic component referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no basic component is present under the given
     * <code>name</code>. If more than one basic component with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first basic component it finds.
     * </p>
     *
     * @param name
     * @return the basic component
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.repository.BasicComponent
     */
    public BasicComponent fetchOfBasicComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final BasicComponent component = this.repo.getBasicComponent(name);
        if (component == null) {
            throw new FluentApiException("BasicComponent '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the composite component referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no composite component is present under the given
     * <code>name</code>. If more than one composite component with this <code>name</code> is
     * present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first composite component it finds.
     * </p>
     *
     * @param name
     * @return the composite component
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.repository.CompositeComponent
     */
    public CompositeComponent fetchOfCompositeComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final CompositeComponent component = this.repo.getCompositeComponent(name);
        if (component == null) {
            throw new FluentApiException("CompositeComponent '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the subsystem referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no subsystem is present under the given
     * <code>name</code>. If more than one subsystem with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first subsystem it finds.
     * </p>
     *
     * @param name
     * @return the subsystem
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.subsystem.SubSystem
     */
    public SubSystem fetchOfSubSystem(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final SubSystem component = this.repo.getSubsystem(name);
        if (component == null) {
            throw new FluentApiException("Subsystem '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the complete component type referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no complete component type is present under the
     * given <code>name</code>. If more than one complete component type with this <code>name</code>
     * is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first complete component type it
     * finds.
     * </p>
     *
     * @param name
     * @return the complete component type
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.repository.CompleteComponentType
     */
    public CompleteComponentType fetchOfCompleteComponentType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final CompleteComponentType component = this.repo.getCompleteComponentType(name);
        if (component == null) {
            throw new FluentApiException("CompleteComponentType '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the provides component type referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no provides component type is present under the
     * given <code>name</code>. If more than one provides component type with this <code>name</code>
     * is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first provides component type it
     * finds.
     * </p>
     *
     * @param name
     * @return the provides component type
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.component.repositoryStructure.components.Component)
     * @see org.palladiosimulator.pcm.repository.ProvidesComponentType
     */
    public ProvidesComponentType fetchOfProvidesComponentType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final ProvidesComponentType component = this.repo.getProvidesComponentType(name);
        if (component == null) {
            throw new FluentApiException("ProvidesComponentType '" + name + "' could not be found");
        }
        return component;
    }

    /**
     * Extracts the interface referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no interface is present under the given
     * <code>name</code>. If more than one interface with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first interface it finds.
     * </p>
     *
     * @param name
     * @return the interface
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.repository.structure.interfaces.Interface)
     * @see org.palladiosimulator.pcm.repository.Interface
     */
    public Interface fetchOfInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final Interface interfce = this.repo.getInterface(name);
        if (interfce == null) {
            throw new FluentApiException("Interface '" + name + "' could not be found");
        }
        return interfce;
    }

    /**
     * Extracts the operation interface referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no operation interface is present under the given
     * <code>name</code>. If more than one operation interface with this <code>name</code> is
     * present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first operation interface it finds.
     * </p>
     *
     * @param name
     * @return the operation interface
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.repository.structure.interfaces.Interface)
     * @see org.palladiosimulator.pcm.repository.OperationInterface
     */
    public OperationInterface fetchOfOperationInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final OperationInterface interfce = this.repo.getOperationInterface(name);
        if (interfce == null) {
            throw new FluentApiException("OperationInterface '" + name + "' could not be found");
        }
        return interfce;
    }

    /**
     * Extracts the infrastructure interface referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no infrastructure interface is present under the
     * given <code>name</code>. If more than one infrastructure interface with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first infrastructure interface it
     * finds.
     * </p>
     *
     * @param name
     * @return the infrastructure interface
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.repository.structure.interfaces.Interface)
     * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
     */
    public InfrastructureInterface fetchOfInfrastructureInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final InfrastructureInterface interfce = this.repo.getInfrastructureInterface(name);
        if (interfce == null) {
            throw new FluentApiException("InfrastructureInterface '" + name + "' could not be found");
        }
        return interfce;
    }

    /**
     * Extracts the event group referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no event group is present under the given
     * <code>name</code>. If more than one event group with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first event group it finds.
     * </p>
     *
     * @param name
     * @return the event group
     * @see org.palladiosimulator.generator.fluent.repository.api.RepoAddition#addToRepository(org.palladiosimulator.generator.fluent.repository.structure.interfaces.Interface)
     * @see org.palladiosimulator.pcm.repository.EventGroup
     */
    public EventGroup fetchOfEventGroup(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final EventGroup interfce = this.repo.getEventGroup(name);
        if (interfce == null) {
            throw new FluentApiException("EventGroup '" + name + "' could not be found");
        }
        return interfce;
    }

    /**
     * Extracts the provided role referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no provided role is present under the given
     * <code>name</code>. If more than one provided role with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first provided role it finds.
     * </p>
     *
     * @param name
     * @return the provided role
     * @see org.palladiosimulator.pcm.repository.ProvidedRole
     */
    public ProvidedRole fetchOfProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final ProvidedRole provRole = this.repo.getProvidedRole(name);
        if (provRole == null) {
            throw new FluentApiException("ProvidedRole '" + name + "' could not be found");
        }
        return provRole;
    }

    /**
     * Extracts the operation provided role referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no operation provided role is present under the
     * given <code>name</code>. If more than one operation provided role with this <code>name</code>
     * is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first operation provided role it
     * finds.
     * </p>
     *
     * @param name
     * @return the operation provided role
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRole fetchOfOperationProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final OperationProvidedRole provRole = this.repo.getOperationProvidedRole(name);
        if (provRole == null) {
            throw new FluentApiException("ProvidedRole '" + name + "' could not be found");
        }
        return provRole;
    }

    /**
     * Extracts the infrastructure provided role referenced by <code>name</code> from the
     * repository. If the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no infrastructure provided role is present under
     * the given <code>name</code>. If more than one infrastructure provided role with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first infrastructure provided role
     * it finds.
     * </p>
     *
     * @param name
     * @return the infrastructure provided role
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRole fetchOfInfrastructureProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final InfrastructureProvidedRole provRole = this.repo.getInfrastructureProvidedRole(name);
        if (provRole == null) {
            throw new FluentApiException("ProvidedRole '" + name + "' could not be found");
        }
        return provRole;
    }

    /**
     * Extracts the sink role referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no sink role is present under the given
     * <code>name</code>. If more than one sink role with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first sink role it finds.
     * </p>
     *
     * @param name
     * @return the sink role
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRole fetchOfSinkRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final SinkRole provRole = this.repo.getSinkRole(name);
        if (provRole == null) {
            throw new FluentApiException("SinkRole '" + name + "' could not be found");
        }
        return provRole;
    }

    /**
     * Extracts the required role referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no required role is present under the given
     * <code>name</code>. If more than one required role with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first required role it finds.
     * </p>
     *
     * @param name
     * @return the required role
     * @see org.palladiosimulator.pcm.repository.RequiredRole
     */
    public RequiredRole fetchOfRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final RequiredRole reqRole = this.repo.getRequiredRole(name);
        if (reqRole == null) {
            throw new FluentApiException("RequiredRole '" + name + "' could not be found");
        }
        return reqRole;
    }

    /**
     * Extracts the operation required role referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no operation required role is present under the
     * given <code>name</code>. If more than one operation required role with this <code>name</code>
     * is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first operation required role it
     * finds.
     * </p>
     *
     * @param name
     * @return the operation required role
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRole fetchOfOperationRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final OperationRequiredRole reqRole = this.repo.getOperationRequiredRole(name);
        if (reqRole == null) {
            throw new FluentApiException("RequiredRole '" + name + "' could not be found");
        }
        return reqRole;
    }

    /**
     * Extracts the infrastructure required role referenced by <code>name</code> from the
     * repository. If the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no infrastructure required role is present under
     * the given <code>name</code>. If more than one infrastructure required role with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first infrastructure required role
     * it finds.
     * </p>
     *
     * @param name
     * @return the infrastructure required role
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRole fetchOfInfrastructureRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final InfrastructureRequiredRole reqRole = this.repo.getInfrastructureRequiredRole(name);
        if (reqRole == null) {
            throw new FluentApiException("RequiredRole '" + name + "' could not be found");
        }
        return reqRole;
    }

    /**
     * Extracts the source role referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no source role is present under the given
     * <code>name</code>. If more than one source role with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first source role it finds.
     * </p>
     *
     * @param name
     * @return the source role
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRole fetchOfSourceRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final SourceRole reqRole = this.repo.getSourceRole(name);
        if (reqRole == null) {
            throw new FluentApiException("SourceRole '" + name + "' could not be found");
        }
        return reqRole;
    }

    /**
     * Extracts the resource required role referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no resource required role is present under the
     * given <code>name</code>. If more than one resource required role with this <code>name</code>
     * is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first resource required role it
     * finds.
     * </p>
     *
     * @param name
     * @return the resource required role
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRole fetchOfResourceRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final ResourceRequiredRole reqRole = this.repo.getResourceRequiredRole(name);
        if (reqRole == null) {
            throw new FluentApiException("ResourceRequiredRole '" + name + "' could not be found");
        }
        return reqRole;
    }

    /**
     * Extracts the signature referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no signature is present under the given
     * <code>name</code>. If more than one signature with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first signature it finds.
     * </p>
     *
     * @param name
     * @return the signature
     * @see org.palladiosimulator.pcm.repository.Signature
     */
    public Signature fetchOfSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final Signature signature = this.repo.getSignature(name);
        if (signature == null) {
            throw new FluentApiException("Signature '" + name + "' could not be found");
        }
        return signature;
    }

    /**
     * Extracts the operation signature referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no operation signature is present under the given
     * <code>name</code>. If more than one operation signature with this <code>name</code> is
     * present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first operation signature it finds.
     * </p>
     *
     * @param name
     * @return the operation signature
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    public OperationSignature fetchOfOperationSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final OperationSignature signature = this.repo.getOperationSignature(name);
        if (signature == null) {
            throw new FluentApiException("Operation signature '" + name + "' could not be found");
        }
        return signature;
    }

    /**
     * Extracts the infrastructure signature referenced by <code>name</code> from the repository. If
     * the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no infrastructure signature is present under the
     * given <code>name</code>. If more than one infrastructure signature with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first infrastructure signature it
     * finds.
     * </p>
     *
     * @param name
     * @return the infrastructure signature
     * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
     */
    public InfrastructureSignature fetchOfInfrastructureSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final InfrastructureSignature signature = this.repo.getInfrastructureSignature(name);
        if (signature == null) {
            throw new FluentApiException("Operation signature '" + name + "' could not be found");
        }
        return signature;
    }

    /**
     * Extracts the event type referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no event type is present under the given
     * <code>name</code>. If more than one event type with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first event type it finds.
     * </p>
     *
     * @param name
     * @return the event type
     * @see org.palladiosimulator.pcm.repository.EventType
     */
    public EventType fetchOfEventType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final EventType eventType = this.repo.getEventType(name);
        if (eventType == null) {
            throw new FluentApiException("EventType '" + name + "' could not be found");
        }
        return eventType;
    }

    /**
     * Extracts the assembly context referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no assembly context is present under the given
     * <code>name</code>. If more than one assembly context with this <code>name</code> is present,
     * a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first assembly context it finds.
     * </p>
     *
     * @param name
     * @return the assembly context
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContext fetchOfAssemblyContext(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final AssemblyContext assContext = this.repo.getAssemblyContext(name);
        if (assContext == null) {
            throw new FluentApiException("Assembly context '" + name + "' could not be found");
        }
        return assContext;
    }

    /**
     * Extracts the event channel referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no event channel is present under the given
     * <code>name</code>. If more than one event channel with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first event channel it finds.
     * </p>
     *
     * @param name
     * @return the event channel
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    public EventChannel fetchOfEventChannel(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final EventChannel eventChannel = this.repo.getEventChannel(name);
        if (eventChannel == null) {
            throw new FluentApiException("Event Channel '" + name + "' could not be found");
        }
        return eventChannel;
    }

    /**
     * Extracts the parameter referenced by <code>name</code> from the repository. If the entity
     * belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no parameter is present under the given
     * <code>name</code>. If more than one parameter with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first parameter it finds.
     * </p>
     *
     * @param name
     * @return the parameter
     * @see org.palladiosimulator.pcm.repository.Parameter
     */
    public Parameter fetchOfParameter(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final Parameter p = this.repo.getParameter(name);
        if (p == null) {
            throw new FluentApiException("Parameter '" + name + "' could not be found");
        }
        return p;
    }

    /**
     * Extracts the parameter referenced by <code>name</code> occurring in the signature
     * <code>context</code> from the repository. If the entity belongs to an imported repository,
     * refer to it as <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no parameter is present under the given
     * <code>name</code>. If more than one parameter with this <code>name</code> is present, a
     * warning will be printed during runtime and the org.palladiosimulator.generator.fluent.system
     * chooses the first parameter it finds.
     * </p>
     *
     * @param name
     * @param context
     * @return the parameter
     * @see org.palladiosimulator.pcm.repository.Parameter
     */
    public Parameter fetchOfParameter(final String name, final Signature context) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final Parameter p = this.repo.getParameter(name, context);
        if (p == null) {
            throw new FluentApiException("Parameter '" + name + "' could not be found");
        }
        return p;
    }

    /**
     * Extracts the passive resource referenced by <code>name</code> from the repository. If the
     * entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no passive resource is present under the given
     * <code>name</code>. If more than one passive resource with this <code>name</code> is present,
     * a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first passive resource it finds.
     * </p>
     *
     * @param name
     * @return the recovery action behaviour
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
     */
    public PassiveResource fetchOfPassiveResource(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final PassiveResource r = this.repo.getPassiveResource(name);
        if (r == null) {
            throw new FluentApiException("Passive Resource '" + name + "' could not be found");
        }
        return r;
    }

    /**
     * Extracts the recovery action behaviour referenced by <code>name</code> from the repository.
     * If the entity belongs to an imported repository, refer to it as
     * <code>&lt;repositoryName&gt;.&lt;name&gt;</code>.
     * <p>
     * This method throws a FluentApiException if no recovery action behaviour is present under the
     * given <code>name</code>. If more than one recovery action behaviour with this
     * <code>name</code> is present, a warning will be printed during runtime and the
     * org.palladiosimulator.generator.fluent.system chooses the first recovery action behaviour it
     * finds.
     * </p>
     *
     * @param name
     * @return the recovery action behaviour
     * @see org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour
     */
    public RecoveryActionBehaviour fetchOfRecoveryActionBehaviour(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final RecoveryActionBehaviour r = this.repo.getRecoveryActionBehaviour(name);
        if (r == null) {
            throw new FluentApiException("Recovery action behaviour '" + name + "' could not be found");
        }
        return r;
    }
}
