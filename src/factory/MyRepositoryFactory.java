package factory;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.SeffCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
import repositoryStructure.datatypes.CompositeDataTypeCreator;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.interfaces.stuff.OperationSignatureCreator;
import repositoryStructure.interfaces.stuff.ParameterCreator;

public class MyRepositoryFactory {
	// TODO: welchen passenderen Namen könnte diese Klasse bekommen?

	private RepositoryCreator repo;
	
	private List<RepositoryComponent> components;
	private List<Interface> interfaces;
	//TODO:
	private List<Object> failureTypes;
	private List<Object> dataTypes;

	
	public MyRepositoryFactory() {
		components = new ArrayList<>();
		interfaces = new ArrayList<>();
		failureTypes = new ArrayList<>();
		dataTypes = new ArrayList<>();
	}
	
	// ---------------------- Repository ----------------------
	public RepositoryCreator newRepository() {
		this.repo = new RepositoryCreator(this);
		return this.repo;
	}

	// ---------------------- Components ----------------------
	public BasicComponentCreator newBasicComponent() {
		BasicComponentCreator basicComponent = new BasicComponentCreator(this.repo);
//		components.add(basicComponent);
		return basicComponent;
	}

	public CompositeComponentCreator newCompositeComponent() {
		CompositeComponentCreator compositeComponent = new CompositeComponentCreator(this.repo);
//		components.add(compositeComponent);
		return compositeComponent;
	}
	
	public SubSystemCreator newSubSystem() {
		SubSystemCreator subSystem = new SubSystemCreator(this.repo);
//		components.add(subSystem);
		return subSystem;
	}
	
	public CompleteComponentTypeCreator newCompleteComponentType() {
		CompleteComponentTypeCreator cct = new CompleteComponentTypeCreator(this.repo);
//		components.add(cct);
		return cct;
	}
	
	public ProvidesComponentTypeCreator newProvidesComponentType() {
		ProvidesComponentTypeCreator pct = new ProvidesComponentTypeCreator(this.repo);
//		components.add(pct);
		return pct;
	}
	
	// ---------------------- Interfaces ----------------------
	public OperationInterfaceCreator newOperationInterface() {
		OperationInterfaceCreator operationInterface = new OperationInterfaceCreator(this.repo);
//		interfaces.add(operationInterface);
		return operationInterface;
	}
	
	public InfrastructureInterfaceCreator newInfrastructureInterface() {
		InfrastructureInterfaceCreator infrastructureInterface = new InfrastructureInterfaceCreator(this.repo);
//		interfaces.add(infrastructureInterface);
		return infrastructureInterface;
	}
	
	public EventGroupCreator newEventGroup() {
		EventGroupCreator eventGroup = new EventGroupCreator(this.repo);
//		interfaces.add(eventGroup);
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
	
	public CollectionDataType newCollectionDataType(String name, org.palladiosimulator.pcm.repository.DataType collection) {
		CollectionDataType coll = RepositoryFactory.eINSTANCE.createCollectionDataType();
		coll.setEntityName(name);
		coll.setInnerType_CollectionDataType(collection);
		return coll;
	}

	public CompositeDataTypeCreator newCompositeDataType(String name, CompositeDataType ... parent) {
		if(parent == null)
			parent = new CompositeDataType[0];
		CompositeDataTypeCreator c = new CompositeDataTypeCreator(name, parent);
		return c;
	}

	
	// ---------------------- Component Related Stuff ----------------------

	public SeffCreator newSeff() {
		SeffCreator seff = new SeffCreator();
		return seff;
	}
	// ---------------------- Interface Related Stuff ----------------------
	
	public OperationSignatureCreator newOperationSignature() {
		OperationSignatureCreator ops = new OperationSignatureCreator();
		return ops;
	}

	public ParameterCreator newParameter() {
		ParameterCreator pc = new ParameterCreator();
		return pc;
	}
	
	// ---------------------- Fetching methods ----------------------
	
	// TODO: access of dataTypes
	// TODO: different component Types
	// TODO: different interface types
	// TODO: parameters
	
	public RepositoryComponent fetchOfComponent(String name) {
		for(RepositoryComponent c: components) {
			if(c.getEntityName().equals(name))
				return c;
		}
		throw new RuntimeException("Component '"+name+"' could not be found");
	}
	
	public Interface fetchOfOperationInterface(String name) {
		for(Interface i: interfaces) {
			if(i instanceof OperationInterface && i.getEntityName().equals(name))
				return (OperationInterface) i;
		}
		throw new RuntimeException("Interface '"+name+"' could not be found");
	}

	public Parameter getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
