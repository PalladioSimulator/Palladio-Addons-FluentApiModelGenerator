package repositoryStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
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
import apiControlFlowInterfaces.SoftwareFailureType;
import repositoryStructure.components.Component;
import repositoryStructure.datatypes.CommunicationLinkResource;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
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

	private String description;

	private List<DataType> dataTypes;
	private Map<Primitive, PrimitiveDataType> internalPrimitives;
	private List<ProcessingResourceType> internalProcessingResources;
	private List<CommunicationLinkResourceType> internalCommunicationLinkResources;
	private List<ResourceInterface> internalResourceInterfaces;
	private List<SchedulingPolicy> internalSchedulingPolicies;
	private Map<Failure, FailureType> internalFailureTypes;
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

	public RepositoryCreator(Repository primitiveDataTypes, ResourceRepository resourceTypes, Repository failureTypes) {
		this.dataTypes = new ArrayList<>();
		this.internalPrimitives = new HashMap<>();
		this.internalProcessingResources = new ArrayList<>();
		this.internalCommunicationLinkResources = new ArrayList<>();
		this.internalResourceInterfaces = new ArrayList<>();
		this.internalSchedulingPolicies = new ArrayList<>();
		this.internalFailureTypes = new HashMap<>();
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

		initPredefinedDataTypesAndResources(primitiveDataTypes, resourceTypes, failureTypes);
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
		this.description = description;
		return this;
	}

	@Override
	public RepoAddition addToRepository(CollectionDataType collectionDataType) {
		dataTypes.add(collectionDataType);
		return this;
	}

	@Override
	public RepoAddition addToRepository(CompositeDataTypeCreator compositeDataType) {
		CompositeDataType dataType = compositeDataType.build();
		dataTypes.add(dataType);
		return this;
	}

	@Override
	public RepoAddition addToRepository(FailureType failureType) {
		// TODO:
		return this;
	}

	@Override
	public RepoAddition addToRepository(SoftwareFailureType failureType) {
		// TODO:
		return this;
	}

	@Override
	public RepoAddition addToRepository(repositoryStructure.interfaces.Interface interfce) {
		Interface i = interfce.build();
		interfaces.add(i);
		return this;
	}

	@Override
	public RepoAddition addToRepository(Component component) {
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
		return build();
	}

	// ------------- getter -------------
	// TODO: getter and add Methoden should be protected
	public DataType getDataType(String name) {
		for (DataType d : this.dataTypes) {
			if (d instanceof CompositeDataType) {
				CompositeDataType comp = (CompositeDataType) d;
				if (comp.getEntityName().contentEquals(name))
					return comp;
			} else if (d instanceof CollectionDataType) {
				CollectionDataType coll = (CollectionDataType) d;
				if (coll.getEntityName().contentEquals(name))
					return coll;
			} else {
			}
		}
		return null;
	}

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

	public FailureType getFailureType(Failure failure) {
		return internalFailureTypes.get(failure);
	}

	public Interface getInterface(String name) {
		return interfaces.stream().filter(i -> i.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public OperationInterface getOperationInterface(String name) {
		return (OperationInterface) interfaces.stream()
				.filter(i -> i instanceof OperationInterface && i.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public InfrastructureInterface getInfrastructureInterface(String name) {
		return (InfrastructureInterface) interfaces.stream()
				.filter(i -> i instanceof InfrastructureInterface && i.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public EventGroup getEventGroup(String name) {
		return (EventGroup) interfaces.stream()
				.filter(i -> i instanceof EventGroup && i.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public RepositoryComponent getComponent(String name) {
		return components.stream().filter(c -> c.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public BasicComponent getBasicComponent(String name) {
		return (BasicComponent) components.stream()
				.filter(c -> c instanceof BasicComponent && c.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public CompositeComponent getCompositeComponent(String name) {
		return (CompositeComponent) components.stream()
				.filter(c -> c instanceof CompositeComponent && c.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public SubSystem getSubsystem(String name) {
		return (SubSystem) components.stream()
				.filter(c -> c instanceof SubSystem && c.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public CompleteComponentType getCompleteComponentType(String name) {
		return (CompleteComponentType) components.stream()
				.filter(c -> c instanceof CompleteComponentType && c.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public ProvidesComponentType getProvidesComponentType(String name) {
		return (ProvidesComponentType) components.stream()
				.filter(c -> c instanceof ProvidesComponentType && c.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public ProvidedRole getProvidedRole(String name) {
		return providedRoles.stream().filter(r -> r.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public OperationProvidedRole getOperationProvidedRole(String name) {
		return (OperationProvidedRole) providedRoles.stream()
				.filter(r -> r instanceof OperationProvidedRole && r.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public InfrastructureProvidedRole getInfrastructureProvidedRole(String name) {
		return (InfrastructureProvidedRole) providedRoles.stream()
				.filter(r -> r instanceof InfrastructureProvidedRole && r.getEntityName().contentEquals(name))
				.findFirst().orElse(null);
	}

	public SinkRole getSinkRole(String name) {
		return (SinkRole) providedRoles.stream()
				.filter(r -> r instanceof SinkRole && r.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public RequiredRole getRequiredRole(String name) {
		return requiredRoles.stream().filter(r -> r.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public OperationRequiredRole getOperationRequiredRole(String name) {
		return (OperationRequiredRole) requiredRoles.stream()
				.filter(r -> r instanceof OperationRequiredRole && r.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public InfrastructureRequiredRole getInfrastructureRequiredRole(String name) {
		return (InfrastructureRequiredRole) requiredRoles.stream()
				.filter(r -> r instanceof InfrastructureRequiredRole && r.getEntityName().contentEquals(name))
				.findFirst().orElse(null);
	}

	public SourceRole getSourceRole(String name) {
		return (SourceRole) requiredRoles.stream()
				.filter(r -> r instanceof SourceRole && r.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public ResourceRequiredRole getResourceRequiredRole(String name) {
		return resourceRequiredRoles.stream().filter(r -> r.getEntityName().contentEquals(name)).findFirst()
				.orElse(null);
	}

	public Parameter getParameter(String name) {
		return parameters.stream().filter(p -> p.getParameterName().contentEquals(name)).findFirst().orElse(null);
	}

	public Parameter getParameter(String name, Signature context) {
		return parameters.stream().filter(
				p -> p.getParameterName().contentEquals(name) && (p.getOperationSignature__Parameter().equals(context)
						|| p.getInfrastructureSignature__Parameter().equals(context)
						|| p.getEventType__Parameter().equals(context)))
				.findFirst().orElse(null);
	}

	public AssemblyContext getAssemblyContext(String name) {
		return assemblyContexts.stream().filter(a -> a.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public EventChannel getEventChannel(String name) {
		return eventChannels.stream().filter(a -> a.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public EventChannelSinkConnector getEventChannelSinkConnector(String name) {
		return (EventChannelSinkConnector) connectors.stream()
				.filter(a -> a instanceof EventChannelSinkConnector && a.getEntityName().contentEquals(name))
				.findFirst().orElse(null);
	}

	public EventChannelSourceConnector getEventChannelSourceConnector(String name) {
		return (EventChannelSourceConnector) connectors.stream()
				.filter(a -> a instanceof EventChannelSourceConnector && a.getEntityName().contentEquals(name))
				.findFirst().orElse(null);
	}

	public RecoveryActionBehaviour getRecoveryActionBehaviour(String name) {
		return this.behaviours.stream().filter(b -> b.getEntityName().contentEquals(name)).findFirst().orElse(null);
	}

	public PassiveResource getPassiveResource(String name) {
		List<PassiveResource> collect = passiveResources.stream().filter(b -> b.getEntityName().contentEquals(name))
				.collect(Collectors.toList());
		if (collect.isEmpty())
			return null;
		if (collect.size() > 1)
			;// TODO: print log and change rest of methods
		return collect.get(0);
	}

	// ------------- adding -------------
	public void addDataType(DataType dt) {
		dataTypes.add(dt);
	}

	public void addInterface(Interface i) {
		interfaces.add(i);
	}

	public void addComponent(RepositoryComponent c) {
		components.add(c);
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

	public void addParameter(Parameter p) {
		parameters.add(p);
	}

	public void addAssemblyContext(AssemblyContext ac) {
		assemblyContexts.add(ac);
	}

	public void addEventChannel(EventChannel eg) {
		eventChannels.add(eg);
	}

	public void addConnector(Connector r) {
		connectors.add(r);
	}

	public void addRecoveryActionBehaviour(RecoveryActionBehaviour recovery) {
		behaviours.add(recovery);
	}

	public void addPassiveResource(PassiveResource pass) {
		passiveResources.add(pass);

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

	public EventType getEventType(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public OperationSignature getOperationSignature(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
