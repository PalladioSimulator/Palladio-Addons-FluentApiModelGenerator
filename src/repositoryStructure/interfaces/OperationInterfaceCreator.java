package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.stuff.OperationSignatureCreator;
import repositoryStructure.interfaces.stuff.RequiredCharacterisationCreator;

public class OperationInterfaceCreator extends Interface {
	
	private List<OperationSignature> operationSignatures;

	public OperationInterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
		operationSignatures = new ArrayList<>();
	}

	@Override
	public OperationInterfaceCreator withName(String name) {
		return (OperationInterfaceCreator) super.withName(name);
	}

	@Override
	public OperationInterfaceCreator withId(String id) {
		return (OperationInterfaceCreator) super.withId(id);
	}
	
	@Override
	public OperationInterfaceCreator withRequiredCharacterisation(RequiredCharacterisationCreator requiredCharacterisation){
		return (OperationInterfaceCreator) super.withRequiredCharacterisation(requiredCharacterisation);
	}

	public OperationInterfaceCreator withOperationSignature(OperationSignatureCreator operationSignature) {
		OperationSignature ops = operationSignature.build();
		operationSignatures.add(ops);
		return this;
	}
	@Override
	public OperationInterface build() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);
		
		// TODO: add to Lists
		interfce.getParentInterfaces__Interface().addAll(parentInterfaces);
		interfce.getProtocols__Interface().addAll(protocols);
		interfce.getRequiredCharacterisations().addAll(requiredCharacterisations);
		
		interfce.getSignatures__OperationInterface().addAll(operationSignatures);
		
		return interfce;
	}
}
