package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

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
	 * Creates a new
	 * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
	 * InfrastructureSignature}.
	 * <p>
	 * Every service of an interface has a unique signature, like <code>void
	 * doSomething(int a)</code>. A PCM signature is comparable to a method
	 * signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An infrastructure signature contains
	 * <ul>
	 * <!--
	 * <li>a
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value), -->
	 * <li>an
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures.
	 * </ul>
	 * A signature has to be unique for an interface through the tuple (identifier,
	 * order of parameters). Different interfaces can define equally named
	 * signatures, however, they are not identical.
	 * </p>
	 * <p>
	 * To return to editing the infrastructure interface this signature belongs to,
	 * the modification of the signature has to be completed with calling a
	 * {@link repositoryStructure.interfaces.InfrastructureSignatureCreator#createSignature()
	 * final method}.
	 * </p>
	 * 
	 * @return the infrastructure signature in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withName(String)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withParameter(String,
	 *      repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see repositoryStructure.interfaces.InfrastructureSignatureCreator#createSignature()
	 */
	public InfrastructureSignatureCreator withInfrastructureSignature() {
		InfrastructureSignatureCreator signature = new InfrastructureSignatureCreator(this, this.repository);
		return signature;
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
