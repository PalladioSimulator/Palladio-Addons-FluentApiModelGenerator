package factory;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;
import repositoryStructure.interfaces.stuff.OperationSignatureCreator;
import repositoryStructure.interfaces.stuff.ParameterCreator;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

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
	
	// ---------------------- Component Related Stuff ----------------------

	// ---------------------- Interface Related Stuff ----------------------
	
	public RequiredCharacterisationCreator newRequiredCharacterisation() {
		RequiredCharacterisationCreator rcc = new RequiredCharacterisationCreator();
		return rcc;
	}
	
	public OperationSignatureCreator newOperationSignature() {
		OperationSignatureCreator ops = new OperationSignatureCreator();
		return ops;
	}

	// ---------------------- Fetching methods ----------------------
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

	public ParameterCreator newParameter() {
		ParameterCreator pc = new ParameterCreator();
		return pc;
	}

	
}
