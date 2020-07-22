package repositoryStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
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
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
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
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.subsystem.SubSystem;

import apiControlFlowInterfaces.Repo;
import apiControlFlowInterfaces.RepoAddition;
import repositoryStructure.components.Component;
import repositoryStructure.datatypes.CommunicationLinkResource;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.datatypes.ExceptionTypeCreator;
import repositoryStructure.datatypes.Failure;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.ProcessingResource;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.Repository Repository}. It is
 * used to create the '<em><b>Repository</b></em>' object step-by-step, i.e.
 * '<em><b>RepositoryCreator</b></em>' objects are of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.Repository
 */
public class RepositoryCreator extends Entity implements Repo, RepoAddition {

	private Logger logger;
	private String description;

	private List<DataType> dataTypes;
	private Map<Primitive, PrimitiveDataType> internalPrimitives;
	private List<ProcessingResourceType> internalProcessingResources;
	private List<CommunicationLinkResourceType> internalCommunicationLinkResources;
	private List<ResourceInterface> internalResourceInterfaces;
	private List<SchedulingPolicy> internalSchedulingPolicies;
	private Map<Failure, FailureType> internalFailureTypes;
	private List<FailureType> failureTypes;
	private List<ExceptionType> exceptionTypes;
	private List<Interface> interfaces;
	private List<RepositoryComponent> components;
	private List<ProvidedRole> providedRoles;
	private List<RequiredRole> requiredRoles;
	private List<ResourceRequiredRole> resourceRequiredRoles;
	private List<Parameter> parameters;
	private List<AssemblyContext> assemblyContexts;
	private List<EventChannel> eventChannels;
	private List<Connector> connectors;
	private List<RecoveryActionBehaviour> behaviours;
	private List<PassiveResource> passiveResources;
	private List<Signature> signatures;

	public RepositoryCreator(Repository primitiveDataTypes, ResourceRepository resourceTypes, Repository failureTypes) {
		this.dataTypes = new ArrayList<>();
		this.internalPrimitives = new HashMap<>();
		this.internalProcessingResources = new ArrayList<>();
		this.internalCommunicationLinkResources = new ArrayList<>();
		this.internalResourceInterfaces = new ArrayList<>();
		this.internalSchedulingPolicies = new ArrayList<>();
		this.internalFailureTypes = new HashMap<>();
		this.failureTypes = new ArrayList<>();
		this.interfaces = new ArrayList<>();
		this.components = new ArrayList<>();
		this.providedRoles = new ArrayList<>();
		this.requiredRoles = new ArrayList<>();
		this.resourceRequiredRoles = new ArrayList<>();
		this.parameters = new ArrayList<>();
		this.assemblyContexts = new ArrayList<>();
		this.eventChannels = new ArrayList<>();
		this.connectors = new ArrayList<>();
		this.behaviours = new ArrayList<>();
		this.passiveResources = new ArrayList<>();
		this.exceptionTypes = new ArrayList<>();
		this.signatures = new ArrayList<>();

		initPredefinedDataTypesAndResources(primitiveDataTypes, resourceTypes, failureTypes);

		this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
	}

	private void initPredefinedDataTypesAndResources(Repository primitiveDataTypes, ResourceRepository resourceTypes,
			Repository failureTypes) {
		// Primitive Data Types
		EList<DataType> dts = primitiveDataTypes.getDataTypes__Repository();
		for (DataType d : dts) {
			PrimitiveDataType p = (PrimitiveDataType) d;
			PrimitiveTypeEnum type = p.getType();
			switch (type) {
			case BOOL:
				this.internalPrimitives.put(Primitive.BOOLEAN, p);
				break;
			case BYTE:
				this.internalPrimitives.put(Primitive.BYTE, p);
				break;
			case CHAR:
				this.internalPrimitives.put(Primitive.CHAR, p);
				break;
			case DOUBLE:
				this.internalPrimitives.put(Primitive.DOUBLE, p);
				break;
			case INT:
				this.internalPrimitives.put(Primitive.INTEGER, p);
				break;
			case LONG:
				this.internalPrimitives.put(Primitive.LONG, p);
				break;
			case STRING:
				this.internalPrimitives.put(Primitive.STRING, p);
				break;
			default:
				System.err.println("TODO:primitives");
				break;
			}
		}

		// ResourceTypes
		for (ResourceType resourceType : resourceTypes.getAvailableResourceTypes_ResourceRepository()) {
			if (resourceType instanceof ProcessingResourceType) {
				this.internalProcessingResources.add((ProcessingResourceType) resourceType);
			} else if (resourceType instanceof CommunicationLinkResourceType) {
				this.internalCommunicationLinkResources.add((CommunicationLinkResourceType) resourceType);
			}
		}

		this.internalResourceInterfaces.addAll(resourceTypes.getResourceInterfaces__ResourceRepository());
		this.internalSchedulingPolicies.addAll(resourceTypes.getSchedulingPolicies__ResourceRepository());

		// FailureTypes
		EList<FailureType> failures = failureTypes.getFailureTypes__Repository();
		for (FailureType f : failures) {
			if (f instanceof SoftwareInducedFailureType && !this.internalFailureTypes.containsKey(Failure.SOFTWARE))
				this.internalFailureTypes.put(Failure.SOFTWARE, f);
			else if (f instanceof NetworkInducedFailureType
					&& !this.internalFailureTypes.containsKey(Failure.NETWORK_LAN))
				this.internalFailureTypes.put(Failure.NETWORK_LAN, f);
			else if (f instanceof HardwareInducedFailureType) {
				if (f.getEntityName().toLowerCase().contentEquals("hardwareinducedfailure (cpu)")
						&& !this.internalFailureTypes.containsKey(Failure.HARDWARE_CPU))
					this.internalFailureTypes.put(Failure.HARDWARE_CPU, f);
				else if (f.getEntityName().toLowerCase().contentEquals("hardwareinducedfailure (hdd)")
						&& !this.internalFailureTypes.containsKey(Failure.HARDWARE_HDD))
					this.internalFailureTypes.put(Failure.HARDWARE_HDD, f);
				else if (f.getEntityName().toLowerCase().contentEquals("hardwareinducedfailure (delay)")
						&& !this.internalFailureTypes.containsKey(Failure.HARDWARE_DELAY))
					this.internalFailureTypes.put(Failure.HARDWARE_DELAY, f);
				else
					System.err.println("TODO:hardwareFailure");
			} else
				System.err.println("TODO:failure");
		}
	}

	@Override
	public RepositoryCreator withName(String name) {
		return (RepositoryCreator) super.withName(name);
	}

//	@Override
//	public RepositoryCreator withId(String id) {
//		return (RepositoryCreator) super.withId(id);
//	}

	@Override
	public RepositoryCreator withDescription(String description) {
		Objects.requireNonNull(description, "description must not be null");
		this.description = description;
		return this;
	}

	@Override
	public RepoAddition addToRepository(CollectionDataType collectionDataType) {
		Objects.requireNonNull(collectionDataType, "collectionDataType must not be null");
		dataTypes.add(collectionDataType);
		return this;
	}

	@Override
	public RepoAddition addToRepository(CompositeDataTypeCreator compositeDataType) {
		Objects.requireNonNull(compositeDataType, "compositeDataType must not be null");
		CompositeDataType dataType = compositeDataType.build();
		dataTypes.add(dataType);
		return this;
	}

	@Override
	public RepoAddition addToRepository(FailureType failureType) {
		Objects.requireNonNull(failureType, "failureType must not be null");
		failureTypes.add(failureType);
		return this;
	}

	@Override
	public RepoAddition addToRepository(ExceptionTypeCreator exceptionType) {
		Objects.requireNonNull(exceptionType, "exceptionType must not be null");
		ExceptionType build = exceptionType.build();
		exceptionTypes.add(build);
		return this;
	}

	@Override
	public RepoAddition addToRepository(repositoryStructure.interfaces.Interface interfce) {
		Objects.requireNonNull(interfce, "interfce must not be null");
		Interface i = interfce.build();
		interfaces.add(i);
		return this;
	}

	@Override
	public RepoAddition addToRepository(Component component) {
		Objects.requireNonNull(component, "component must not be null");
		RepositoryComponent c = component.build();
		components.add(c);
		return this;
	}

	@Override
	protected Repository build() {
		Repository repo = RepositoryFactory.eINSTANCE.createRepository();
		if (name != null)
			repo.setEntityName(name);
//		if (id != null)
//			repo.setId(id);
		if (description != null)
			repo.setRepositoryDescription(description);

		repo.getDataTypes__Repository().addAll(dataTypes);
		repo.getInterfaces__Repository().addAll(interfaces);
		repo.getComponents__Repository().addAll(components);

		return repo;
	}

	@Override
	public Repository createRepositoryNow() {
		Repository repo = build();
		RepositoryValidator v = new RepositoryValidator();

		boolean validate = v.validate(repo, null, null);
		if (!validate)
			logger.severe("Repository is not valid.");
		return repo;
	}

	// ------------- getter -------------
	// TODO: getter and add Methoden should be protected
	public PrimitiveDataType getPrimitiveDataType(Primitive primitive) {
		return internalPrimitives.get(primitive);
	}

	public PrimitiveDataType getPrimitiveDataType(String name) {
		try {
			if (name.equalsIgnoreCase("int"))
				name = "integer";
			if (name.equalsIgnoreCase("bool"))
				name = "boolean";
			Primitive valueOf = Primitive.valueOf(name.toUpperCase());
			return internalPrimitives.get(valueOf);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public CompositeDataType getCompositeDataType(String name) {
		List<CompositeDataType> collect = dataTypes.stream().filter(c -> c instanceof CompositeDataType)
				.map(b -> (CompositeDataType) b)
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one composite data type with name '" + name + "' found.");
		return collect.get(0);
	}

	public DataType getDataType(String name) {
		for (DataType d : this.dataTypes) {
			if (d instanceof CompositeDataType) {
				CompositeDataType comp = (CompositeDataType) d;
				if (comp.getEntityName() != null && comp.getEntityName().contentEquals(name))
					return comp;
			} else if (d instanceof CollectionDataType) {
				CollectionDataType coll = (CollectionDataType) d;
				if (coll.getEntityName() != null && coll.getEntityName().contentEquals(name))
					return coll;
			} else {
			}
		}
		return null;
	}

	public FailureType getFailureType(Failure failure) {
		return internalFailureTypes.get(failure);
	}

	public FailureType getFailureType(String name) {
		List<FailureType> collect = failureTypes.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		List<FailureType> collect2 = internalFailureTypes.values().stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		collect.addAll(collect2);
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one failure type with name '" + name + "' found.");
		return collect.get(0);
	}

	public ResourceTimeoutFailureType getResourceTimeoutFailureType(String name) {
		List<ResourceTimeoutFailureType> collect = failureTypes.stream()
				.filter(b -> b instanceof ResourceTimeoutFailureType && b.getEntityName() != null
						&& b.getEntityName().contentEquals(name))
				.map(b -> (ResourceTimeoutFailureType) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one resource timeout failure type with name '" + name + "' found.");
		return collect.get(0);
	}

	public ExceptionType getExceptionType(String name) {
		List<ExceptionType> collect = exceptionTypes.stream()
				.filter(c -> c.getExceptionName() != null && c.getExceptionName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one component with name '" + name + "' found.");
		return collect.get(0);
	}

	public ProcessingResourceType getProcessingResource(ProcessingResource processingResource) {
		// TODO Auto-generated method stub
		return null;
	}

	public CommunicationLinkResourceType getCommunicationLinkResource(
			CommunicationLinkResource communicationLinkResource) {
		// TODO Auto-generated method stub
		return null;
	}

	public RepositoryComponent getComponent(String name) {
		List<RepositoryComponent> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one component with name '" + name + "' found.");
		return collect.get(0);
	}

	public BasicComponent getBasicComponent(String name) {
		List<BasicComponent> collect = components.stream().filter(
				c -> c instanceof BasicComponent && c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.map(b -> (BasicComponent) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one basic component with name '" + name + "' found.");
		return collect.get(0);
	}

	public CompositeComponent getCompositeComponent(String name) {
		List<CompositeComponent> collect = components.stream()
				.filter(c -> c instanceof CompositeComponent && c.getEntityName() != null
						&& c.getEntityName().contentEquals(name))
				.map(b -> (CompositeComponent) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one composite component with name '" + name + "' found.");
		return collect.get(0);
	}

	public SubSystem getSubsystem(String name) {
		List<SubSystem> collect = components.stream().filter(
				c -> c instanceof SubSystem && c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.map(b -> (SubSystem) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one subsystem with name '" + name + "' found.");
		return collect.get(0);
	}

	public CompleteComponentType getCompleteComponentType(String name) {
		List<CompleteComponentType> collect = components.stream()
				.filter(c -> c instanceof CompleteComponentType && c.getEntityName() != null
						&& c.getEntityName().contentEquals(name))
				.map(b -> (CompleteComponentType) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one complete component type with name '" + name + "' found.");
		return collect.get(0);
	}

	public ProvidesComponentType getProvidesComponentType(String name) {
		List<ProvidesComponentType> collect = components.stream()
				.filter(c -> c instanceof ProvidesComponentType && c.getEntityName() != null
						&& c.getEntityName().contentEquals(name))
				.map(b -> (ProvidesComponentType) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one provides component type with name '" + name + "' found.");
		return collect.get(0);
	}

	public Interface getInterface(String name) {
		List<Interface> collect = interfaces.stream()
				.filter(i -> i.getEntityName() != null && i.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one interface with name '" + name + "' found.");
		return collect.get(0);
	}

	public OperationInterface getOperationInterface(String name) {
		List<OperationInterface> collect = interfaces.stream()
				.filter(i -> i instanceof OperationInterface && i.getEntityName() != null
						&& i.getEntityName().contentEquals(name))
				.map(b -> (OperationInterface) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation interface with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureInterface getInfrastructureInterface(String name) {
		List<InfrastructureInterface> collect = interfaces.stream()
				.filter(i -> i instanceof InfrastructureInterface && i.getEntityName() != null
						&& i.getEntityName().contentEquals(name))
				.map(b -> (InfrastructureInterface) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure interface with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventGroup getEventGroup(String name) {
		List<EventGroup> collect = interfaces.stream().filter(
				i -> i instanceof EventGroup && i.getEntityName() != null && i.getEntityName().contentEquals(name))
				.map(b -> (EventGroup) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event group with name '" + name + "' found.");
		return collect.get(0);
	}

	public ProvidedRole getProvidedRole(String name) {
		List<ProvidedRole> collect = providedRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one provided role with name '" + name + "' found.");
		return collect.get(0);
	}

	public OperationProvidedRole getOperationProvidedRole(String name) {
		List<OperationProvidedRole> collect = providedRoles.stream()
				.filter(r -> r instanceof OperationProvidedRole && r.getEntityName() != null
						&& r.getEntityName().contentEquals(name))
				.map(b -> (OperationProvidedRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation provided role with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureProvidedRole getInfrastructureProvidedRole(String name) {
		List<InfrastructureProvidedRole> collect = providedRoles.stream()
				.filter(r -> r instanceof InfrastructureProvidedRole && r.getEntityName() != null
						&& r.getEntityName().contentEquals(name))
				.map(b -> (InfrastructureProvidedRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure provided role with name '" + name + "' found.");
		return collect.get(0);
	}

	public SinkRole getSinkRole(String name) {
		List<SinkRole> collect = providedRoles.stream().filter(
				r -> r instanceof SinkRole && r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.map(b -> (SinkRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one sink role with name '" + name + "' found.");
		return collect.get(0);
	}

	public RequiredRole getRequiredRole(String name) {
		List<RequiredRole> collect = requiredRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public OperationRequiredRole getOperationRequiredRole(String name) {
		List<OperationRequiredRole> collect = requiredRoles.stream()
				.filter(r -> r instanceof OperationRequiredRole && r.getEntityName() != null
						&& r.getEntityName().contentEquals(name))
				.map(b -> (OperationRequiredRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureRequiredRole getInfrastructureRequiredRole(String name) {
		List<InfrastructureRequiredRole> collect = requiredRoles.stream()
				.filter(r -> r instanceof InfrastructureRequiredRole && r.getEntityName() != null
						&& r.getEntityName().contentEquals(name))
				.map(b -> (InfrastructureRequiredRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public SourceRole getSourceRole(String name) {
		List<SourceRole> collect = requiredRoles.stream().filter(
				r -> r instanceof SourceRole && r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.map(b -> (SourceRole) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one source role with name '" + name + "' found.");
		return collect.get(0);
	}

	public ResourceRequiredRole getResourceRequiredRole(String name) {
		List<ResourceRequiredRole> collect = resourceRequiredRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one resource required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public Signature getSignature(String name) {
		List<Signature> collect = signatures.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one signature with name '" + name + "' found.");
		return collect.get(0);
	}

	public OperationSignature getOperationSignature(String name) {
		List<OperationSignature> collect = signatures.stream()
				.filter(c -> c instanceof OperationSignature && c.getEntityName() != null
						&& c.getEntityName().contentEquals(name))
				.map(b -> (OperationSignature) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation signature with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureSignature getInfrastructureSignature(String name) {
		List<InfrastructureSignature> collect = signatures.stream()
				.filter(c -> c instanceof InfrastructureSignature && c.getEntityName() != null
						&& c.getEntityName().contentEquals(name))
				.map(b -> (InfrastructureSignature) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure signature with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventType getEventType(String name) {
		List<EventType> collect = signatures.stream().filter(
				c -> c instanceof EventType && c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.map(b -> (EventType) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event type with name '" + name + "' found.");
		return collect.get(0);
	}

	public AssemblyContext getAssemblyContext(String name) {
		List<AssemblyContext> collect = assemblyContexts.stream()
				.filter(a -> a.getEntityName() != null && a.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one assembly context with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventChannel getEventChannel(String name) {
		List<EventChannel> collect = eventChannels.stream()
				.filter(a -> a.getEntityName() != null && a.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event channel with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventChannelSinkConnector getEventChannelSinkConnector(String name) {
		List<EventChannelSinkConnector> collect = connectors.stream()
				.filter(a -> a instanceof EventChannelSinkConnector && a.getEntityName() != null
						&& a.getEntityName().contentEquals(name))
				.map(b -> (EventChannelSinkConnector) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event channel sink connector with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventChannelSourceConnector getEventChannelSourceConnector(String name) {
		List<EventChannelSourceConnector> collect = connectors.stream()
				.filter(a -> a instanceof EventChannelSourceConnector && a.getEntityName() != null
						&& a.getEntityName().contentEquals(name))
				.map(b -> (EventChannelSourceConnector) b).collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event channel source connector with name '" + name + "' found.");
		return collect.get(0);
	}

	public Parameter getParameter(String name) {
		List<Parameter> collect = parameters.stream()
				.filter(p -> p.getParameterName() != null && p.getParameterName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one parameter with name '" + name + "' found.");
		return collect.get(0);
	}

	public Parameter getParameter(String name, Signature context) {
		List<Parameter> collect = parameters.stream()
				.filter(p -> p.getParameterName() != null && p.getParameterName().contentEquals(name)
						&& (p.getOperationSignature__Parameter().equals(context)
								|| p.getInfrastructureSignature__Parameter().equals(context)
								|| p.getEventType__Parameter().equals(context)))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one parameter with name '" + name + "' found in context '"
					+ context.getEntityName() + "'.");
		return collect.get(0);
	}

	public PassiveResource getPassiveResource(String name) {
		List<PassiveResource> collect = passiveResources.stream()
				.filter(b -> b.getEntityName() != null && b.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one passive resource with name '" + name + "' found.");
		return collect.get(0);
	}

	public RecoveryActionBehaviour getRecoveryActionBehaviour(String name) {
		List<RecoveryActionBehaviour> collect = behaviours.stream()
				.filter(b -> b.getEntityName() != null && b.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one recovery action behaviour with name '" + name + "' found.");
		return collect.get(0);
	}

	// ------------- adding -------------

	public void addDataType(DataType dt) {
		dataTypes.add(dt);
	}

	public void addFailureType(FailureType f) {
		failureTypes.add(f);
	}

	public void addComponent(RepositoryComponent c) {
		components.add(c);
	}

	public void addInterface(Interface i) {
		interfaces.add(i);
	}

	public void addProvidedRole(ProvidedRole pr) {
		providedRoles.add(pr);
	}

	public void addRequiredRole(RequiredRole rr) {
		requiredRoles.add(rr);
	}

	public void addResourceRequiredRole(ResourceRequiredRole rr) {
		resourceRequiredRoles.add(rr);
	}

	public void addSignature(Signature sign) {
		this.signatures.add(sign);
	}

	public void addAssemblyContext(AssemblyContext ac) {
		assemblyContexts.add(ac);
	}

	public void addConnector(Connector r) {
		connectors.add(r);
	}

	public void addEventChannel(EventChannel eg) {
		eventChannels.add(eg);
	}

	public void addParameter(Parameter p) {
		parameters.add(p);
	}

	public void addPassiveResource(PassiveResource pass) {
		passiveResources.add(pass);
	}

	public void addRecoveryActionBehaviour(RecoveryActionBehaviour recovery) {
		behaviours.add(recovery);
	}
}
