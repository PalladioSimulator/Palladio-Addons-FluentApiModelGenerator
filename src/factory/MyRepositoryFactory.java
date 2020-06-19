package factory;

import java.util.ArrayList;
import java.util.List;


import repositoryStructure.RepositoryCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.Component;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.Interface;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class MyRepositoryFactory {
	// TODO: welchen passenderen Namen könnte diese Klasse bekommen?

	private RepositoryCreator repo;
	
	private List<Component> components;
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
		components.add(basicComponent);
		return basicComponent;
	}

	public CompositeComponentCreator newCompositeComponent() {
		CompositeComponentCreator compositeComponent = new CompositeComponentCreator(this.repo);
		components.add(compositeComponent);
		return compositeComponent;
	}
	
	public SubSystemCreator newSubSystem() {
		SubSystemCreator subSystem = new SubSystemCreator(this.repo);
		components.add(subSystem);
		return subSystem;
	}
	
	public CompleteComponentTypeCreator newCompleteComponentType() {
		CompleteComponentTypeCreator cct = new CompleteComponentTypeCreator(this.repo);
		components.add(cct);
		return cct;
	}
	
	public ProvidesComponentTypeCreator newProvidesComponentType() {
		ProvidesComponentTypeCreator pct = new ProvidesComponentTypeCreator(this.repo);
		components.add(pct);
		return pct;
	}
	
	// ---------------------- Interfaces ----------------------
	public OperationInterfaceCreator newOperationInterface() {
		OperationInterfaceCreator operationInterface = new OperationInterfaceCreator(this.repo);
		interfaces.add(operationInterface);
		return operationInterface;
	}
	
	public InfrastructureInterfaceCreator newInfrastructureInterface() {
		InfrastructureInterfaceCreator infrastructureInterface = new InfrastructureInterfaceCreator(this.repo);
		interfaces.add(infrastructureInterface);
		return infrastructureInterface;
	}
	
	public EventGroupCreator newEventGroup() {
		EventGroupCreator eventGroup = new EventGroupCreator(this.repo);
		interfaces.add(eventGroup);
		return eventGroup;
	}
	
	// ---------------------- Failure Types ----------------------
	
//	public FailureTypeCreator newHardwareInducedFailureType() {
//		return null;
//	}
//	
//	public FailureTypeCreator newSoftwareInducedFailureType() {
//		return null;
//	}
//	
//	public FailureTypeCreator newNetworkInducedFailureType() {
//		return null;
//	}
//	
//	public FailureTypeCreator newResourceTimeoutFailureType() {
//		return null;
//	}
	
	// ---------------------- DataTypes ----------------------
	
//	public DataTypeCreator newPrimitiveDataType() {
//		return null;
//	}
//	
//	public DataTypeCreator newCompositeDataType() {
//		return null;
//	}
//	
//	public DataTypeCreator newCollectionDataType() {
//		return null;
//	}
	
	public Component getComponentByName(String name) {
		for(Component c: components) {
			if(c.getName().equals(name))
				return c;
		}
		throw new RuntimeException("Component '"+name+"' could not be found");
	}
	
	public OperationInterfaceCreator getOperationInterfaceByName(String name) {
		for(Interface i: interfaces) {
			if(i instanceof OperationInterfaceCreator && i.getName().equals(name))
				return (OperationInterfaceCreator) i;
		}
		throw new RuntimeException("Interface '"+name+"' could not be found");
	}
	
}
