package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;
import repositoryStructure.interfaces.stuff.OperationSignatureCreator;

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
