package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

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
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;

public class InfrastructureSignatureCreator extends Entity {

	private InfrastructureInterfaceCreator correspondingInterface;

	private List<Parameter> parameters;
	private List<ExceptionType> exceptions;
	private List<FailureType> failures;

	public InfrastructureSignatureCreator(InfrastructureInterfaceCreator infrastructureInterfaceCreator,
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

	public InfrastructureSignatureCreator withParameter(String name, Primitive dataType, ParameterModifier modifier) {
		PrimitiveDataType dt = PrimitiveType.getPrimitiveDataType(dataType);

		return withParameter(name, dt, modifier);
	}

	public InfrastructureSignatureCreator withParameter(String name, DataType dataType, ParameterModifier modifier) {
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

	public InfrastructureSignatureCreator withFailureType(FailureType failureType) {
		this.failures.add(failureType);
		return this;
	}

	public InfrastructureSignatureCreator withExceptionType(ExceptionType exceptionType) {
		this.exceptions.add(exceptionType);
		return this;
	}

	public InfrastructureInterfaceCreator now() {
		InfrastructureSignature sign = this.build();
		correspondingInterface.addInfrastructureSignatures(sign);
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
