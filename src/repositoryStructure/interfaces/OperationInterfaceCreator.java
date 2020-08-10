package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.OperationInterface
 * OperationInterface}. It is used to create the
 * '<em><b>OperationInterface</b></em>' object step-by-step, i.e.
 * '<em><b>OperationInterfaceCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.OperationInterface
 */
public class OperationInterfaceCreator extends Interface {

	private List<OperationSignature> signatures;

	public OperationInterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
		signatures = new ArrayList<>();
	}

	@Override
	public OperationInterfaceCreator withName(String name) {
		return (OperationInterfaceCreator) super.withName(name);
	}

//	@Override
//	public OperationInterfaceCreator withId(String id) {
//		return (OperationInterfaceCreator) super.withId(id);
//	}

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

	/**
	 * Adds the <code>signature</code> to this interface's list of signatures. The
	 * <code>signature</code> can be created using the factory, i.e.
	 * <code>create.newOperationSignature()</code>.
	 * 
	 * @param signature
	 * @return this operation interface in the making
	 * @see factory.FluentRepositoryFactory#newOperationSignature()
	 */
	public OperationInterfaceCreator withOperationSignature(OperationSignatureCreator signature) {
		OperationSignature build = signature.build();
		this.repository.addSignature(build);
		this.signatures.add(build);
		return this;
	}

	@Override
	public OperationInterface build() {
		OperationInterface interfce = RepositoryFactory.eINSTANCE.createOperationInterface();
		if (name != null)
			interfce.setEntityName(name);
//		if (id != null)
//			interfce.setId(id);

		interfce.getParentInterfaces__Interface().addAll(parentInterfaces);
		interfce.getRequiredCharacterisations().addAll(requiredCharacterisations);

		interfce.getSignatures__OperationInterface().addAll(signatures);

		return interfce;
	}

	protected void addOperationSignatures(OperationSignature signature) {
		this.signatures.add(signature);
	}
}
