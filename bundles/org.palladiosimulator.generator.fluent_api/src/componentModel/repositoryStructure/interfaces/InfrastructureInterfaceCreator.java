package componentModel.repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import componentModel.repositoryStructure.RepositoryCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
 * InfrastructureInterface}. It is used to create the
 * '<em><b>InfrastructureInterface</b></em>' object step-by-step, i.e.
 * '<em><b>InfrastructureInterfaceCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
 */
public class InfrastructureInterfaceCreator extends Interface {

	private List<InfrastructureSignature> signatures;

	public InfrastructureInterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.signatures = new ArrayList<>();
	}

	@Override
	public InfrastructureInterfaceCreator withName(String name) {
		return (InfrastructureInterfaceCreator) super.withName(name);
	}

//	@Override
//	public InfrastructureInterfaceCreator withId(String id) {
//		return (InfrastructureInterfaceCreator) super.withId(id);
//	}

	// parent Interfaces
	@Override
	public InfrastructureInterfaceCreator conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		return (InfrastructureInterfaceCreator) super.conforms(interfce);
	}

	@Override
	public InfrastructureInterfaceCreator withRequiredCharacterisation(Parameter parameter,
			VariableCharacterisationType type) {
		return (InfrastructureInterfaceCreator) super.withRequiredCharacterisation(parameter, type);
	}

	/**
	 * Adds the <code>signature</code> to this interface's list of signatures. The
	 * <code>signature</code> can be created using the componentModel.factory, i.e.
	 * <code>create.newInfrastructureSignature()</code>.
	 * 
	 * @param signature
	 * @return this infrastructure interface in the making
	 * @see componentModel.factory.FluentRepositoryFactory#newInfrastructureSignature()
	 */
	public InfrastructureInterfaceCreator withInfrastructureSignature(InfrastructureSignatureCreator signature) {
		InfrastructureSignature build = signature.build();
		this.repository.addSignature(build);
		this.signatures.add(build);
		return this;
	}

	@Override
	public InfrastructureInterface build() {
		InfrastructureInterface interfce = RepositoryFactory.eINSTANCE.createInfrastructureInterface();

		if (name != null)
			interfce.setEntityName(name);
//		if (id != null)
//			interfce.setId(id);

		interfce.getInfrastructureSignatures__InfrastructureInterface().addAll(signatures);

		interfce.getParentInterfaces__Interface().addAll(parentInterfaces);
		interfce.getRequiredCharacterisations().addAll(requiredCharacterisations);

		return interfce;
	}

	protected void addInfrastructureSignatures(InfrastructureSignature signature) {
		this.signatures.add(signature);
	}

}
