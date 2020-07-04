package repositoryStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.FailureType;
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
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.subsystem.SubSystem;

import apiControlFlowInterfaces.Repo;
import apiControlFlowInterfaces.RepoAddition;
import factory.MyRepositoryFactory;
import repositoryStructure.components.Component;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.datatypes.Failure;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;

public class RepositoryCreator extends Entity implements Repo, RepoAddition {

	private String description;

	private MyRepositoryFactory factory;

	private List<DataType> dataTypes;
	private List<FailureType> failureTypes;
	private List<Interface> interfaces;
	private List<RepositoryComponent> components;
	private List<ProvidedRole> providedRoles;
	private List<RequiredRole> requiredRoles;
	private List<ResourceRequiredRole> resourceRequiredRoles;
	private List<Parameter> parameters;
	private List<AssemblyContext> assemblyContexts;

	public RepositoryCreator(MyRepositoryFactory factory) {
		this.factory = factory;
		this.dataTypes = new ArrayList<>();
		this.failureTypes = new ArrayList<>();
		this.interfaces = new ArrayList<>();
		this.components = new ArrayList<>();
		this.providedRoles = new ArrayList<>();
		this.requiredRoles = new ArrayList<>();
		this.resourceRequiredRoles = new ArrayList<>();
		this.parameters = new ArrayList<>();
		this.assemblyContexts = new ArrayList<>();

		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.BOOLEAN));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.BYTE));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.CHAR));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.DOUBLE));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.INTEGER));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.LONG));
		this.dataTypes.add(PrimitiveType.getPrimitiveDataType(Primitive.STRING));

		this.failureTypes.add(repositoryStructure.datatypes.FailureType.getFailureType(Failure.HARDWARE_CPU));
		this.failureTypes.add(repositoryStructure.datatypes.FailureType.getFailureType(Failure.HARDWARE_HDD));
		this.failureTypes.add(repositoryStructure.datatypes.FailureType.getFailureType(Failure.HARDWARE_DELAY));
		this.failureTypes.add(repositoryStructure.datatypes.FailureType.getFailureType(Failure.NETWORK_LAN));
		this.failureTypes.add(repositoryStructure.datatypes.FailureType.getFailureType(Failure.SOFTWARE));
	}

	@Override
	public RepositoryCreator withName(String name) {
		return (RepositoryCreator) super.withName(name);
	}

	@Override
	public RepositoryCreator withId(String id) {
		return (RepositoryCreator) super.withId(id);
	}

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
	public Repository build() {
		Repository repo = RepositoryFactory.eINSTANCE.createRepository();
		if (name != null)
			repo.setEntityName(name);
		if (id != null)
			repo.setId(id);
		if (description != null)
			repo.setRepositoryDescription(description);

		repo.getDataTypes__Repository().addAll(dataTypes);
		repo.getInterfaces__Repository().addAll(interfaces);
		repo.getComponents__Repository().addAll(components);
		repo.getFailureTypes__Repository().addAll(failureTypes);

		return repo;
	}

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
}
