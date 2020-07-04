package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

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

	// parent Interfaces
	@Override
	public OperationInterfaceCreator conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		return (OperationInterfaceCreator) super.conforms(interfce);
	}

	@Override
	public OperationInterfaceCreator withRequiredCharacterisation(Parameter parameter,
			VariableCharacterisationType type) {
		return (OperationInterfaceCreator) super.withRequiredCharacterisation(parameter, type);
	}

	@Override
	public OperationInterfaceCreator withProtocol() {
		return (OperationInterfaceCreator) super.withProtocol();
	}

	/**
	 * requires the specification of a return type
	 * 
	 * @param name
	 * @return the possibility to specify the signature
	 */
	public OperationSignatureCreator withOperationSignature() {
		OperationSignatureCreator operationSignature = new OperationSignatureCreator(this);
		return operationSignature;
	}

	@Override
	public OperationInterface build() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);

		interfce.getParentInterfaces__Interface().addAll(parentInterfaces);
		interfce.getProtocols__Interface().addAll(protocols);
		interfce.getRequiredCharacterisations().addAll(requiredCharacterisations);

		interfce.getSignatures__OperationInterface().addAll(operationSignatures);

		return interfce;
	}

	public void addOperationSignatures(OperationSignature signature) {
		this.operationSignatures.add(signature);
	}
}
