package repositoryStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
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
import org.palladiosimulator.pcm.repository.RepositoryPackage;
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
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
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
import repositoryStructure.datatypes.ResourceTimeoutFailureTypeCreator;

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

	private List<Repository> imports;
	private List<DataType> importedDataTypes;
	private List<FailureType> importedFailureTypes;
	private List<RepositoryComponent> importedComponents;
	private List<Interface> importedInterfaces;
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
		this.imports = new ArrayList<>();
		this.importedDataTypes = new ArrayList<>();
		this.importedFailureTypes = new ArrayList<>();
		this.importedComponents = new ArrayList<>();
		this.importedInterfaces = new ArrayList<>();
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

	private static Repository loadRepository(String uri) {
		RepositoryPackage.eINSTANCE.eClass();
		// Register the XMI resource factory for the .repository extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());
		// Get the resource
		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.getResource(URI.createURI(uri), true);
		// Get the first model element and cast it to the right type
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
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
					System.err.println("Unexpected failure type while reading internal failure types.");
			} else
				System.err.println("Unexpected failure type while reading internal failure types.");
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
	public Repo withImportedResource(String path) {
		Repository imported = loadRepository(path);
		this.imports.add(imported);
		this.importedDataTypes.addAll(imported.getDataTypes__Repository());
		this.importedFailureTypes.addAll(imported.getFailureTypes__Repository());
		this.importedComponents.addAll(imported.getComponents__Repository());
		this.importedInterfaces.addAll(imported.getInterfaces__Repository());
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
	public RepoAddition addToRepository(ResourceTimeoutFailureTypeCreator failureType) {
		Objects.requireNonNull(failureType, "failureType must not be null");
		failureTypes.add(failureType.build());
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
		DiagnosticChain dc = new BasicDiagnostic();

		boolean validate = v.validate(repo, dc, null);
		if (!validate)
			logger.severe("Repository is not valid.");

		// TODO: tut das validate und rufe add Methoden überall an den jeweiligen
		// stellen auf
		return repo;
	}

	private Repository getRepositoryByName(String name) {
		List<Repository> collect = imports.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than repository with name '" + name + "' found.");
		return collect.get(0);
	}

	// ------------- getter -------------
	// TODO: getter and add Methoden should not be visible for the user -> module
	
	// I didn't put much thought into where it actually makes sense to fetch
	// something from an imported resource. It probably doesn't make sense e.g. for
	// parameters. However, it is implemented. Maybe later this can be restricted if
	// it is confusing for the user,
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
		List<CompositeDataType> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getDataTypes__Repository().stream().filter(d -> d instanceof CompositeDataType)
					.map(d -> (CompositeDataType) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.dataTypes.stream().filter(d -> d instanceof CompositeDataType)
					.map(d -> (CompositeDataType) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getCompositeDataTypeFromList(name, collect);
	}

	private CompositeDataType getCompositeDataTypeFromList(String name, List<CompositeDataType> dataTypes) {
		List<CompositeDataType> collect = dataTypes.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one composite data type with name '" + name + "' found.");
		return collect.get(0);
	}

	public DataType getDataType(String name) {
		String[] split = name.split("\\.");
		if (split.length == 2) {
			String entityName = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			return getDataTypeFromList(entityName, r.getDataTypes__Repository());
		} else if (split.length == 1) {
			return getDataTypeFromList(name, this.dataTypes);
		} else {
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		}
	}

	private DataType getDataTypeFromList(String name, List<DataType> dataTypes) {
		List<DataType> collect = new ArrayList<>();
		List<CollectionDataType> collectColl = dataTypes.stream().filter(d -> d instanceof CollectionDataType)
				.map(d -> (CollectionDataType) d)
				.filter(d -> d.getEntityName() != null && d.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		List<CompositeDataType> collectComp = dataTypes.stream().filter(d -> d instanceof CompositeDataType)
				.map(d -> (CompositeDataType) d)
				.filter(d -> d.getEntityName() != null && d.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		collect.addAll(collectColl);
		collect.addAll(collectComp);

		if (collect.isEmpty())
			return getPrimitiveDataType(name);
		if (collect.size() > 1)
			logger.warning("More than one data type with name '" + name + "' found.");
		return collect.get(0);
	}

	public FailureType getFailureType(Failure failure) {
		return internalFailureTypes.get(failure);
	}

	public FailureType getFailureType(String name) {
		String[] split = name.split("\\.");
		if (split.length == 2) {
			String entityName = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			return getFailureTypeFromList(entityName, r.getFailureTypes__Repository());
		} else if (split.length == 1) {
			return getFailureTypeFromList(name, this.failureTypes);
		} else {
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		}
	}

	private FailureType getFailureTypeFromList(String name, List<FailureType> failureTypes) {
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
		List<ResourceTimeoutFailureType> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getFailureTypes__Repository().stream().filter(d -> d instanceof ResourceTimeoutFailureType)
					.map(d -> (ResourceTimeoutFailureType) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.failureTypes.stream().filter(d -> d instanceof ResourceTimeoutFailureType)
					.map(d -> (ResourceTimeoutFailureType) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getResourceTimeoutFailureTypeFromList(name, collect);
	}

	private ResourceTimeoutFailureType getResourceTimeoutFailureTypeFromList(String name,
			List<ResourceTimeoutFailureType> failureTypes) {
		List<ResourceTimeoutFailureType> collect = failureTypes.stream()
				.filter(b -> b.getEntityName() != null && b.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
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

	public ProcessingResourceType getProcessingResourceType(ProcessingResource processingResource) {
		// TODO Auto-generated method stub
		return null;
	}

	public CommunicationLinkResourceType getCommunicationLinkResource(
			CommunicationLinkResource communicationLinkResource) {
		// TODO Auto-generated method stub
		return null;
	}

	public RepositoryComponent getComponent(String name) {
		List<RepositoryComponent> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository();
		} else if (split.length == 1)
			collect = this.components;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getComponentFromList(name, collect);
	}

	private RepositoryComponent getComponentFromList(String name, List<RepositoryComponent> components) {
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
		List<BasicComponent> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository().stream().filter(d -> d instanceof BasicComponent)
					.map(d -> (BasicComponent) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.components.stream().filter(d -> d instanceof BasicComponent).map(d -> (BasicComponent) d)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getBasicComponentFromList(name, collect);
	}

	private BasicComponent getBasicComponentFromList(String name, List<BasicComponent> components) {
		List<BasicComponent> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one basic component with name '" + name + "' found.");
		return collect.get(0);
	}

	public CompositeComponent getCompositeComponent(String name) {
		List<CompositeComponent> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository().stream().filter(d -> d instanceof CompositeComponent)
					.map(d -> (CompositeComponent) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.components.stream().filter(d -> d instanceof CompositeComponent)
					.map(d -> (CompositeComponent) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getCompositeComponentFromList(name, collect);
	}

	private CompositeComponent getCompositeComponentFromList(String name, List<CompositeComponent> components) {
		List<CompositeComponent> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one composite component with name '" + name + "' found.");
		return collect.get(0);
	}

	public SubSystem getSubsystem(String name) {
		List<SubSystem> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository().stream().filter(d -> d instanceof SubSystem).map(d -> (SubSystem) d)
					.collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.components.stream().filter(d -> d instanceof SubSystem).map(d -> (SubSystem) d)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getSubsystemFromList(name, collect);
	}

	private SubSystem getSubsystemFromList(String name, List<SubSystem> components) {
		List<SubSystem> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one subsystem with name '" + name + "' found.");
		return collect.get(0);
	}

	public CompleteComponentType getCompleteComponentType(String name) {
		List<CompleteComponentType> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository().stream().filter(d -> d instanceof CompleteComponentType)
					.map(d -> (CompleteComponentType) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.components.stream().filter(d -> d instanceof CompleteComponentType)
					.map(d -> (CompleteComponentType) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getCompleteComponentTypeFromList(name, collect);
	}

	private CompleteComponentType getCompleteComponentTypeFromList(String name,
			List<CompleteComponentType> components) {
		List<CompleteComponentType> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one complete component type with name '" + name + "' found.");
		return collect.get(0);
	}

	public ProvidesComponentType getProvidesComponentType(String name) {
		List<ProvidesComponentType> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getComponents__Repository().stream().filter(d -> d instanceof ProvidesComponentType)
					.map(d -> (ProvidesComponentType) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.components.stream().filter(d -> d instanceof ProvidesComponentType)
					.map(d -> (ProvidesComponentType) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getProvidesComponentTypeFromList(name, collect);
	}

	private ProvidesComponentType getProvidesComponentTypeFromList(String name,
			List<ProvidesComponentType> components) {
		List<ProvidesComponentType> collect = components.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one provides component type with name '" + name + "' found.");
		return collect.get(0);
	}

	public Interface getInterface(String name) {
		List<Interface> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getInterfaces__Repository();
		} else if (split.length == 1)
			collect = this.interfaces;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getInterfaceFromList(name, collect);
	}

	private Interface getInterfaceFromList(String name, List<Interface> interfaces) {
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
		List<OperationInterface> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getInterfaces__Repository().stream().filter(d -> d instanceof OperationInterface)
					.map(d -> (OperationInterface) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.interfaces.stream().filter(d -> d instanceof OperationInterface)
					.map(d -> (OperationInterface) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getOperationInterfaceFromList(name, collect);
	}

	private OperationInterface getOperationInterfaceFromList(String name, List<OperationInterface> interfaces) {
		List<OperationInterface> collect = interfaces.stream()
				.filter(i -> i.getEntityName() != null && i.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation interface with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureInterface getInfrastructureInterface(String name) {
		List<InfrastructureInterface> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getInterfaces__Repository().stream().filter(d -> d instanceof InfrastructureInterface)
					.map(d -> (InfrastructureInterface) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.interfaces.stream().filter(d -> d instanceof InfrastructureInterface)
					.map(d -> (InfrastructureInterface) d).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getInfrastructureInterfaceFromList(name, collect);
	}

	private InfrastructureInterface getInfrastructureInterfaceFromList(String name,
			List<InfrastructureInterface> interfaces) {
		List<InfrastructureInterface> collect = interfaces.stream()
				.filter(i -> i.getEntityName() != null && i.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure interface with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventGroup getEventGroup(String name) {
		List<EventGroup> collect;
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			collect = r.getInterfaces__Repository().stream().filter(d -> d instanceof EventGroup)
					.map(d -> (EventGroup) d).collect(Collectors.toList());
		} else if (split.length == 1)
			collect = this.interfaces.stream().filter(d -> d instanceof EventGroup).map(d -> (EventGroup) d)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getEventGroupFromList(name, collect);
	}

	private EventGroup getEventGroupFromList(String name, List<EventGroup> interfaces) {
		List<EventGroup> collect = interfaces.stream()
				.filter(i -> i.getEntityName() != null && i.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event group with name '" + name + "' found.");
		return collect.get(0);
	}

	public ProvidedRole getProvidedRole(String name) {
		List<ProvidedRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity());
		} else if (split.length == 1)
			collect = this.providedRoles;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getProvidedRoleFromList(name, collect);
	}

	private ProvidedRole getProvidedRoleFromList(String name, List<ProvidedRole> providedRoles) {
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
		List<OperationProvidedRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity().stream()
						.filter(p -> p instanceof OperationProvidedRole).map(p -> (OperationProvidedRole) p)
						.collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.providedRoles.stream().filter(r -> r instanceof OperationProvidedRole)
					.map(r -> (OperationProvidedRole) r).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getOperationProvidedRoleFromList(name, collect);
	}

	private OperationProvidedRole getOperationProvidedRoleFromList(String name,
			List<OperationProvidedRole> providedRoles) {
		List<OperationProvidedRole> collect = providedRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation provided role with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureProvidedRole getInfrastructureProvidedRole(String name) {
		List<InfrastructureProvidedRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity().stream()
						.filter(p -> p instanceof InfrastructureProvidedRole).map(p -> (InfrastructureProvidedRole) p)
						.collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.providedRoles.stream().filter(r -> r instanceof InfrastructureProvidedRole)
					.map(r -> (InfrastructureProvidedRole) r).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getInfrastructureProvidedRoleFromList(name, collect);
	}

	private InfrastructureProvidedRole getInfrastructureProvidedRoleFromList(String name,
			List<InfrastructureProvidedRole> providedRoles) {
		List<InfrastructureProvidedRole> collect = providedRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure provided role with name '" + name + "' found.");
		return collect.get(0);
	}

	public SinkRole getSinkRole(String name) {
		List<SinkRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity().stream().filter(p -> p instanceof SinkRole)
						.map(p -> (SinkRole) p).collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.providedRoles.stream().filter(r -> r instanceof SinkRole).map(r -> (SinkRole) r)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getSinkRoleFromList(name, collect);
	}

	private SinkRole getSinkRoleFromList(String name, List<SinkRole> providedRoles) {
		List<SinkRole> collect = providedRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one sink role with name '" + name + "' found.");
		return collect.get(0);
	}

	public RequiredRole getRequiredRole(String name) {
		List<RequiredRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity());
		} else if (split.length == 1)
			collect = this.requiredRoles;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getRequiredRoleFromList(name, collect);
	}

	private RequiredRole getRequiredRoleFromList(String name, List<RequiredRole> requiredRoles) {
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
		List<OperationRequiredRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity().stream()
						.filter(p -> p instanceof OperationRequiredRole).map(p -> (OperationRequiredRole) p)
						.collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.requiredRoles.stream().filter(r -> r instanceof OperationRequiredRole)
					.map(r -> (OperationRequiredRole) r).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getOperationRequiredRoleFromList(name, collect);
	}

	private OperationRequiredRole getOperationRequiredRoleFromList(String name,
			List<OperationRequiredRole> requiredRoles) {
		List<OperationRequiredRole> collect = requiredRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureRequiredRole getInfrastructureRequiredRole(String name) {
		List<InfrastructureRequiredRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity().stream()
						.filter(p -> p instanceof InfrastructureRequiredRole).map(p -> (InfrastructureRequiredRole) p)
						.collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.requiredRoles.stream().filter(r -> r instanceof InfrastructureRequiredRole)
					.map(r -> (InfrastructureRequiredRole) r).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getInfrastructureRequiredRoleFromList(name, collect);
	}

	private InfrastructureRequiredRole getInfrastructureRequiredRoleFromList(String name,
			List<InfrastructureRequiredRole> requiredRoles) {
		List<InfrastructureRequiredRole> collect = requiredRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure required role with name '" + name + "' found.");
		return collect.get(0);
	}

	public SourceRole getSourceRole(String name) {
		List<SourceRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity().stream()
						.filter(p -> p instanceof SourceRole).map(p -> (SourceRole) p).collect(Collectors.toList()));
		} else if (split.length == 1)
			collect = this.requiredRoles.stream().filter(r -> r instanceof SourceRole).map(r -> (SourceRole) r)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getSourceRoleFromList(name, collect);
	}

	private SourceRole getSourceRoleFromList(String name, List<SourceRole> requiredRoles) {
		List<SourceRole> collect = requiredRoles.stream()
				.filter(r -> r.getEntityName() != null && r.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one source role with name '" + name + "' found.");
		return collect.get(0);
	}

	public ResourceRequiredRole getResourceRequiredRole(String name) {
		List<ResourceRequiredRole> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (RepositoryComponent c : r.getComponents__Repository())
				collect.addAll(c.getResourceRequiredRoles__ResourceInterfaceRequiringEntity());
		} else if (split.length == 1)
			collect = this.resourceRequiredRoles;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getResourceRequiredRoleFromList(name, collect);
	}

	private ResourceRequiredRole getResourceRequiredRoleFromList(String name,
			List<ResourceRequiredRole> resourceRequiredRoles) {
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
		List<Signature> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (Interface i : r.getInterfaces__Repository()) {
				if (i instanceof OperationInterface) {
					OperationInterface e = (OperationInterface) i;
					collect.addAll(e.getSignatures__OperationInterface());
				} else if (i instanceof InfrastructureInterface) {
					InfrastructureInterface e = (InfrastructureInterface) i;
					collect.addAll(e.getInfrastructureSignatures__InfrastructureInterface());
				} else if (i instanceof EventGroup) {
					EventGroup e = (EventGroup) i;
					collect.addAll(e.getEventTypes__EventGroup());
				}
			}
		} else if (split.length == 1)
			collect = this.signatures;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getSignatureFromList(name, collect);
	}

	private Signature getSignatureFromList(String name, List<Signature> signatures) {
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
		List<OperationSignature> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (Interface i : r.getInterfaces__Repository()) {
				if (i instanceof OperationInterface) {
					OperationInterface e = (OperationInterface) i;
					collect.addAll(e.getSignatures__OperationInterface());
				}
			}
		} else if (split.length == 1)
			collect = this.signatures.stream().filter(i -> i instanceof OperationSignature)
					.map(i -> (OperationSignature) i).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getOperationSignatureFromList(name, collect);
	}

	private OperationSignature getOperationSignatureFromList(String name, List<OperationSignature> signatures) {
		List<OperationSignature> collect = signatures.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one operation signature with name '" + name + "' found.");
		return collect.get(0);
	}

	public InfrastructureSignature getInfrastructureSignature(String name) {
		List<InfrastructureSignature> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (Interface i : r.getInterfaces__Repository()) {
				if (i instanceof InfrastructureInterface) {
					InfrastructureInterface e = (InfrastructureInterface) i;
					collect.addAll(e.getInfrastructureSignatures__InfrastructureInterface());
				}
			}
		} else if (split.length == 1)
			collect = this.signatures.stream().filter(i -> i instanceof InfrastructureSignature)
					.map(i -> (InfrastructureSignature) i).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getInfrastructureSignatureFromList(name, collect);
	}

	private InfrastructureSignature getInfrastructureSignatureFromList(String name,
			List<InfrastructureSignature> signatures) {
		List<InfrastructureSignature> collect = signatures.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one infrastructure signature with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventType getEventType(String name) {
		List<EventType> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (Interface i : r.getInterfaces__Repository()) {
				if (i instanceof EventGroup) {
					EventGroup e = (EventGroup) i;
					collect.addAll(e.getEventTypes__EventGroup());
				}
			}
		} else if (split.length == 1)
			collect = this.signatures.stream().filter(i -> i instanceof EventType).map(i -> (EventType) i)
					.collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getEventTypeFromList(name, collect);
	}

	private EventType getEventTypeFromList(String name, List<EventType> signatures) {
		List<EventType> collect = signatures.stream()
				.filter(c -> c.getEntityName() != null && c.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event type with name '" + name + "' found.");
		return collect.get(0);
	}

	public AssemblyContext getAssemblyContext(String name) {
		List<AssemblyContext> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof ComposedProvidingRequiringEntity) {
					ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
					collect.addAll(cc.getAssemblyContexts__ComposedStructure());
				}
			}
		} else if (split.length == 1)
			collect = this.assemblyContexts;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getAssemblyContextFromList(name, collect);
	}

	private AssemblyContext getAssemblyContextFromList(String name, List<AssemblyContext> assemblyContexts) {
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
		List<EventChannel> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof ComposedProvidingRequiringEntity) {
					ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
					collect.addAll(cc.getEventChannel__ComposedStructure());
				}
			}
		} else if (split.length == 1)
			collect = this.eventChannels;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getEventChannelFromList(name, collect);
	}

	private EventChannel getEventChannelFromList(String name, List<EventChannel> eventChannels) {
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
		List<EventChannelSinkConnector> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof ComposedProvidingRequiringEntity) {
					ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
					collect.addAll(cc.getConnectors__ComposedStructure().stream()
							.filter(f -> f instanceof EventChannelSinkConnector).map(f -> (EventChannelSinkConnector) f)
							.collect(Collectors.toList()));
				}
			}
		} else if (split.length == 1)
			collect = this.connectors.stream().filter(f -> f instanceof EventChannelSinkConnector)
					.map(f -> (EventChannelSinkConnector) f).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getEventChannelSinkConnectorFromList(name, collect);
	}

	private EventChannelSinkConnector getEventChannelSinkConnectorFromList(String name,
			List<EventChannelSinkConnector> connectors) {
		List<EventChannelSinkConnector> collect = connectors.stream()
				.filter(a -> a.getEntityName() != null && a.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event channel sink connector with name '" + name + "' found.");
		return collect.get(0);
	}

	public EventChannelSourceConnector getEventChannelSourceConnector(String name) {
		List<EventChannelSourceConnector> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof ComposedProvidingRequiringEntity) {
					ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
					collect.addAll(cc.getConnectors__ComposedStructure().stream()
							.filter(f -> f instanceof EventChannelSourceConnector)
							.map(f -> (EventChannelSourceConnector) f).collect(Collectors.toList()));
				}
			}
		} else if (split.length == 1)
			collect = this.connectors.stream().filter(f -> f instanceof EventChannelSourceConnector)
					.map(f -> (EventChannelSourceConnector) f).collect(Collectors.toList());
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getEventChannelSourceConnectorFromList(name, collect);
	}

	private EventChannelSourceConnector getEventChannelSourceConnectorFromList(String name,
			List<EventChannelSourceConnector> connectors) {
		List<EventChannelSourceConnector> collect = connectors.stream()
				.filter(a -> a.getEntityName() != null && a.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			logger.warning("More than one event channel source connector with name '" + name + "' found.");
		return collect.get(0);
	}

	public Parameter getParameter(String name) {
		List<Parameter> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			for (Interface i : r.getInterfaces__Repository()) {
				if (i instanceof OperationInterface) {
					OperationInterface e = (OperationInterface) i;
					for (OperationSignature s : e.getSignatures__OperationInterface())
						collect.addAll(s.getParameters__OperationSignature());
				} else if (i instanceof InfrastructureInterface) {
					InfrastructureInterface e = (InfrastructureInterface) i;
					for (InfrastructureSignature s : e.getInfrastructureSignatures__InfrastructureInterface())
						collect.addAll(s.getParameters__InfrastructureSignature());
				} else if (i instanceof EventGroup) {
					EventGroup e = (EventGroup) i;
					for (EventType s : e.getEventTypes__EventGroup())
						collect.add(s.getParameter__EventType());
				}
			}
		} else if (split.length == 1)
			collect = this.parameters;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getParameterFromList(name, collect);
	}

	private Parameter getParameterFromList(String name, List<Parameter> parameters) {
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
		List<Parameter> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");
			// it is assumed that split[0] = name of the repository refers to the same
			// repository that the signature <context> comes from
		} else if (split.length == 1)
			;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");

		if (context instanceof OperationSignature)
			collect.addAll(((OperationSignature) context).getParameters__OperationSignature());
		else if (context instanceof InfrastructureSignature)
			collect.addAll(((InfrastructureSignature) context).getParameters__InfrastructureSignature());
		else if (context instanceof EventType)
			collect.add(((EventType) context).getParameter__EventType());

		return getParameterFromList(name, collect);
	}

	public PassiveResource getPassiveResource(String name) {
		List<PassiveResource> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof BasicComponent) {
					BasicComponent cc = (BasicComponent) c;
					collect.addAll(cc.getPassiveResource_BasicComponent());
				}
			}
		} else if (split.length == 1)
			collect = this.passiveResources;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getPassiveResourceFromList(name, collect);
	}

	private PassiveResource getPassiveResourceFromList(String name, List<PassiveResource> passiveResources) {
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
		List<RecoveryActionBehaviour> collect = new ArrayList<>();
		String[] split = name.split("\\.");
		if (split.length == 2) {
			name = split[1];
			Repository r = getRepositoryByName(split[0]);
			if (r == null)
				throw new RuntimeException("Repository '" + split[0] + "' could not be found");

			Set<RecoveryActionBehaviour> set = new HashSet<>();
			for (RepositoryComponent c : r.getComponents__Repository()) {
				if (c instanceof BasicComponent) {
					BasicComponent cc = (BasicComponent) c;
					EList<ServiceEffectSpecification> seffs = cc.getServiceEffectSpecifications__BasicComponent();
					for (ServiceEffectSpecification s : seffs) {
						if (s instanceof ResourceDemandingSEFF) {
							ResourceDemandingSEFF rseff = (ResourceDemandingSEFF) s;
							List<RecoveryAction> recoveryActions = rseff.getSteps_Behaviour().stream()
									.filter(step -> step instanceof RecoveryAction).map(step -> (RecoveryAction) step)
									.collect(Collectors.toList());
							for (RecoveryAction a : recoveryActions)
								set.addAll(a.getRecoveryActionBehaviours__RecoveryAction());
						}
					}
				}
			}
			collect.addAll(set);
		} else if (split.length == 1)
			collect = this.behaviours;
		else
			throw new IllegalArgumentException(
					"To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
		return getRecoveryActionBehaviourFromList(name, collect);
	}

	private RecoveryActionBehaviour getRecoveryActionBehaviourFromList(String name,
			List<RecoveryActionBehaviour> behaviours) {
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
