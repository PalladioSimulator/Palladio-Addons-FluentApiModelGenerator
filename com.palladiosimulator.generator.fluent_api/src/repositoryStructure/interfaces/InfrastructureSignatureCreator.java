package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.internals.Failure;
import repositoryStructure.internals.Primitive;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
 * InfrastructureSignature}. It is used to create the
 * '<em><b>InfrastructureSignature</b></em>' object step-by-step, i.e.
 * '<em><b>InfrastructureSignatureCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
 */
public class InfrastructureSignatureCreator extends Entity {

	private InfrastructureInterfaceCreator correspondingInterface;

	private List<Parameter> parameters;
	private List<ExceptionType> exceptions;
	private List<FailureType> failures;

	protected InfrastructureSignatureCreator(InfrastructureInterfaceCreator infrastructureInterfaceCreator,
			RepositoryCreator repository) {
		this.repository = repository;
		this.correspondingInterface = infrastructureInterfaceCreator;
		this.parameters = new ArrayList<>();
		this.exceptions = new ArrayList<>();
		this.failures = new ArrayList<>();
	}

	public InfrastructureSignatureCreator withName(String name) {
		return (InfrastructureSignatureCreator) super.withName(name);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
	 * and adds it to the signature's ordered list of parameters.
	 * <p>
	 * Each parameter is a tuple of a <code>name</code> and a <code>dataType</code>
	 * (which is unique across the parameters). Optionally, the
	 * <code>modifier</code>s '<em><b>in</b></em>', '<em><b>out</b></em>', and
	 * '<em><b>inout</b></em>' (with its OMG IDL semantics) can be used for
	 * parameters, e.g. <code>ParameterModifier.IN</code>.
	 * </p>
	 * <p>
	 * A {@link repositoryStructure.internals.Primitive Primitive} data type can
	 * have the values '<em><b>boolean</b></em>', '<em><b>integer</b></em>',
	 * '<em><b>string</b></em>', '<em><b>double</b></em>', '<em><b>long</b></em>',
	 * '<em><b>char</b></em>', '<em><b>byte</b></em>'.
	 * </p>
	 * 
	 * @param name
	 * @param dataType
	 * @param modifier may be null
	 * @return this infrastructure signature in the making
	 * @see org.palladiosimulator.pcm.repository.ParameterModifier
	 */
	public InfrastructureSignatureCreator withParameter(String name, Primitive dataType, ParameterModifier modifier) {
		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(dataType, "dataType must not be null");
		PrimitiveDataType dt = repository.getPrimitiveDataType(dataType);
		return withParameter(name, dt, modifier);
	}

	/**
	 * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
	 * and adds it to the signature's ordered list of parameters.
	 * <p>
	 * Each parameter is a tuple of a <code>name</code> and a <code>dataType</code>
	 * (which is unique across the parameters). Optionally, the
	 * <code>modifier</code>s '<em><b>in</b></em>', '<em><b>out</b></em>', and
	 * '<em><b>inout</b></em>' (with its OMG IDL semantics) can be used for
	 * parameters, e.g. <code>ParameterModifier.IN</code>.
	 * </p>
	 * <p>
	 * An existing data type can be fetched from the repository using the factory,
	 * i.e. <code>create.fetchOfDataType(name)</code>.
	 * </p>
	 * 
	 * @param name
	 * @param dataType
	 * @param modifier may be null
	 * @return this infrastructure signature in the making
	 * @see factory.FluentRepositoryFactory#fetchOfDataType(String)
	 * @see org.palladiosimulator.pcm.repository.ParameterModifier
	 */
	public InfrastructureSignatureCreator withParameter(String name, DataType dataType, ParameterModifier modifier) {
		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(dataType, "dataType must not be null");
		Parameter param = RepositoryFactory.eINSTANCE.createParameter();
		if (name != null)
			param.setParameterName(name);
		if (dataType != null)
			param.setDataType__Parameter(dataType);
		if (modifier != null)
			param.setModifier__Parameter(modifier);

		parameters.add(param);
		this.repository.addParameter(param);
		return this;
	}

	/**
	 * Adds the <code>failureType</code> to the signature's list of possible
	 * failures.
	 * <p>
	 * Failure types can be fetched from the repository using the factory, i.e.
	 * <code>create.fetchOfFailureType(name)</code>.
	 * </p>
	 * 
	 * @param failureType
	 * @return this infrastructure signature in the making
	 * @see factory.FluentRepositoryFactory#fetchOfFailureType(String)
	 */
	public InfrastructureSignatureCreator withFailureType(FailureType failureType) {
		Objects.requireNonNull(failureType, "failureType must not be null");
		this.failures.add(failureType);
		return this;
	}

	/**
	 * Adds the <code>failureType</code> to the signature's list of possible
	 * failures.
	 * <p>
	 * A {@link repositoryStructure.internals.Failure Failure} type can have the
	 * values '<em><b>HARDWARE_CPU</b></em>', '<em><b>HARDWARE_HDD</b></em>',
	 * '<em><b>HARDWARE_DELAY</b></em>', '<em><b>NETWORK_LAN</b></em>',
	 * '<em><b>SOFTWARE</b></em>'.
	 * </p>
	 * 
	 * @param failureType
	 * @return this infrastructure signature in the making
	 */
	public InfrastructureSignatureCreator withFailureType(Failure failureType) {
		Objects.requireNonNull(failureType, "failureType must not be null");
		FailureType failure = this.repository.getFailureType(failureType);
		return withFailureType(failure);
	}

	/**
	 * Adds the <code>exceptionType</code> to the signature's list of possible
	 * exceptions.
	 * <p>
	 * An existing exception type can be fetched from the repository using the
	 * factory, i.e. <code>create.fetchOfExceptionType(name)</code>.
	 * </p>
	 * 
	 * @param exceptionType
	 * @return this infrastructure signature in the making
	 * @see factory.FluentRepositoryFactory#fetchOfExceptionType(String)
	 */
	public InfrastructureSignatureCreator withExceptionType(ExceptionType exceptionType) {
		Objects.requireNonNull(exceptionType, "exceptionType must not be null");
		this.exceptions.add(exceptionType);
		return this;
	}

	/**
	 * Turns the infrastructure-signature-in-the-making into an
	 * '<em><b>Infrastructure Signature</b></em>' object and adds it to the
	 * corresponding interface.
	 * 
	 * @return the corresponding infrastructure interface
	 * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
	 */
	public InfrastructureInterfaceCreator createSignature() {
		InfrastructureSignature sign = this.build();
		correspondingInterface.addInfrastructureSignatures(sign);
		this.repository.addSignature(sign);
		return correspondingInterface;
	}

	@Override
	protected InfrastructureSignature build() {
		InfrastructureSignature sig = RepositoryFactory.eINSTANCE.createInfrastructureSignature();
		if (name != null)
			sig.setEntityName(name);
		sig.getParameters__InfrastructureSignature().addAll(parameters);
		sig.getExceptions__Signature().addAll(exceptions);
		sig.getFailureType().addAll(failures);

		return sig;
	}

}
