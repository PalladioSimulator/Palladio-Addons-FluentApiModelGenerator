
import apiControlFlowInterfaces.Repo;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompleteComponentTypeCreator;
import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.ProvidesComponentTypeCreator;
import repositoryStructure.components.SubSystemCreator;
import repositoryStructure.interfaces.EventGroupCreator;
import repositoryStructure.interfaces.InfrastructureInterfaceCreator;
import repositoryStructure.interfaces.OperationInterfaceCreator;

public class MyRepositoryFactory {

	RepositoryCreator repo;
	
	// Components
	BasicComponentCreator basicComponent;
	OperationInterfaceCreator operationInterface;
	EventGroupCreator eventGroup;

	// TODO: welchen passenderen Namen könnte diese Klasse bekommen?
	// ---------------------- Repository ----------------------
	public Repo newRepository() {
		this.repo = new RepositoryCreator();
		return this.repo;
	}

	// ---------------------- Components ----------------------
	public BasicComponentCreator newBasicComponent() {
		this.basicComponent = new BasicComponentCreator(this.repo);
		return this.basicComponent;
	}

	public CompositeComponentCreator newCompositeComponent() {
		return null;
	}
	
	public SubSystemCreator newSubSystem() {
		return null;
	}
	
	public CompleteComponentTypeCreator newCompleteComponentType() {
		return null;
	}
	
	public ProvidesComponentTypeCreator newProvidesComponentType() {
		return null;
	}
	
	// ---------------------- Interfaces ----------------------
	
	public OperationInterfaceCreator newOperationInterface() {
		this.operationInterface = new OperationInterfaceCreator(this.repo);
		return this.operationInterface;
	}
	
	public InfrastructureInterfaceCreator newInfrastructureInterface() {
		return null;
	}
	
	public EventGroupCreator newEventGroup() {
		//TODO: repo
		this.eventGroup = new EventGroupCreator();
		return this.eventGroup;
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
	
	
}
