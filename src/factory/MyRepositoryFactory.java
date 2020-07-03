package factory;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import apiControlFlowInterfaces.Follow.Start;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.SeffCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
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

	// ---------------------- Repository ----------------------
	public RepositoryCreator newRepository() {
		this.repo = new RepositoryCreator(this);
		return this.repo;
	}

	// ---------------------- Components ----------------------
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

	public CollectionDataType newCollectionDataType(String name, Primitive primitive) {
		PrimitiveDataType p = PrimitiveType.getPrimitiveDataType(primitive);

		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(p);

		return coll;
	}

	public CollectionDataType newCollectionDataType(String name,
			org.palladiosimulator.pcm.repository.DataType collection) {
		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(collection);
		return coll;
	}

	public CompositeDataTypeCreator newCompositeDataType(String name, CompositeDataType... parent) {
		if (parent == null)
			parent = new CompositeDataType[0];
		CompositeDataTypeCreator c = new CompositeDataTypeCreator(name, parent);
		return c;
	}

	// ---------------------- Component Related Stuff ----------------------

	public Start newSeff() {
		SeffCreator seff = new SeffCreator();
		return seff;
	}

	// ---------------------- Fetching methods ----------------------

	// TODO: access of dataTypes
	// TODO: different component Types
	// TODO: different interface types
	// TODO: parameters
	// über String oder die fetch Methoden?

	public RepositoryComponent fetchOfComponent(String name) {
		RepositoryComponent component = repo.getComponent(name);
		if (component == null)
			throw new RuntimeException("Component '" + name + "' could not be found");
		else
			return component;
	}

	public OperationInterface fetchOfOperationInterface(String name) {
		OperationInterface interfce = repo.getOperationInterface(name);
		if (interfce == null)
			throw new RuntimeException("Interface '" + name + "' could not be found");
		else
			return interfce;
	}

	public Parameter getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataType fetchOfDataType(String name) {
		DataType dataType = repo.getDataType(name);
//		if(dataType == null)
//			throw new RuntimeException("Datatype '"+name+"' could not be found");
//		else
		return dataType;
	}

	public OperationProvidedRole fetchOfProvidedRole(String name) {
		OperationProvidedRole provRole = repo.getOperationProvidedRole(name);
		if (provRole == null)
			throw new RuntimeException("ProvidedRole '" + name + "' could not be found");
		else
			return provRole;
	}

	public AssemblyContext fetchOfAssemblyContext(String name) {
		AssemblyContext assContext = repo.getAssemblyContext(name);
//		if(assContext == null)
//			throw new RuntimeException("Assembly context '"+name+"' could not be found");
//		else
		return assContext;
	}

	public OperationRequiredRole fetchOfRequiredRole(String name) {
		OperationRequiredRole reqRole = repo.getOperationRequiredRole(name);
		if (reqRole == null)
			throw new RuntimeException("RequiredRole '" + name + "' could not be found");
		else
			return reqRole;
	}

	public ResourceTimeoutFailureType fetchOfFailureType(Failure failure) {
		FailureType failureType = repositoryStructure.datatypes.FailureType.getFailureType(failure);
		if (failureType instanceof ResourceTimeoutFailureType)
			return (ResourceTimeoutFailureType) failureType;
		else
			return null;
	}

	public EventGroup fetchOfEventGroup(String name) {
		EventGroup eventGroup = repo.getEventGroup(name);
		if (eventGroup == null)
			throw new RuntimeException("EventGroup '" + name + "' could not be found");
		else
			return eventGroup;
	}

}
