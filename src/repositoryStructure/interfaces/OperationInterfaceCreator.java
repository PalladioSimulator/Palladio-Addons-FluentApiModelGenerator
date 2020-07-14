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

	private List<OperationSignature> operationSignatures;

	public OperationInterfaceCreator(RepositoryCreator repo) {
		this.repository = repo;
		operationSignatures = new ArrayList<>();
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
	 * Creates a new {@link org.palladiosimulator.pcm.repository.OperationSignature
	 * OperationSignature}.
	 * <p>
	 * Every service of an interface has a unique signature, like <code>void
	 * doSomething(int a)</code>. A PCM signature is comparable to a method
	 * signature in programming languages like C#, Java or the OMG IDL.
	 * </p>
	 * <p>
	 * An operation signature contains
	 * <ul>
	 * <li>a
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * type of the return value} or void (no return value),
	 * <li>an
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#withName(String)
	 * identifier} naming the service,
	 * <li>an ordered set of
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String, org.palladiosimulator.pcm.repository.DataType, org.palladiosimulator.pcm.repository.ParameterModifier)
	 * parameters} (0..*). Each parameter is a tuple of a <code>dataType</code> and
	 * an <code>identifier</code> (which is unique across the parameters).
	 * Optionally, the <code>modifiers</code> in, out, and inout (with its OMG IDL
	 * semantics) can be used for parameters.
	 * <li>and an unordered set of
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * exceptions}.
	 * <li>Furthermore
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * failures} that may occur inside external services must be specified at the
	 * service signatures.
	 * </ul>
	 * A signature has to be unique for an interface through the tuple (identifier,
	 * order of parameters). Different interfaces can define equally named
	 * signatures, however, they are not identical.
	 * </p>
	 * <p>
	 * To return to editing the operation interface this signature belongs to, the
	 * modification of the signature has to be completed with calling a
	 * {@link repositoryStructure.interfaces.OperationSignatureCreator#now() final
	 * method}.
	 * </p>
	 * 
	 * @return the operation signature in the making
	 * @see org.palladiosimulator.pcm.repository.Signature
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withName(String)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withReturnType(org.palladiosimulator.pcm.repository.DataType)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String,
	 *      org.palladiosimulator.pcm.repository.DataType,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withParameter(String,
	 *      repositoryStructure.datatypes.Primitive,
	 *      org.palladiosimulator.pcm.repository.ParameterModifier)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withExceptionType(org.palladiosimulator.pcm.repository.ExceptionType)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#withFailureType(org.palladiosimulator.pcm.reliability.FailureType)
	 * @see repositoryStructure.interfaces.OperationSignatureCreator#now()
	 */
	public OperationSignatureCreator withOperationSignature() {
		OperationSignatureCreator operationSignature = new OperationSignatureCreator(this, this.repository);
		return operationSignature;
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

		interfce.getSignatures__OperationInterface().addAll(operationSignatures);

		return interfce;
	}

	protected void addOperationSignatures(OperationSignature signature) {
		this.operationSignatures.add(signature);
	}
}
