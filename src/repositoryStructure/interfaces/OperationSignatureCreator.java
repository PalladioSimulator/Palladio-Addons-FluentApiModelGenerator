package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;

public class OperationSignatureCreator extends Entity {

	private DataType returnType;
	private List<Parameter> ownedParameters;
	private List<FailureType> failureTypes;
	private List<ExceptionType> exceptionTypes;
	private OperationInterfaceCreator correspondingInterface;

	public OperationSignatureCreator(OperationInterfaceCreator interfce, RepositoryCreator repo) {
		this.correspondingInterface = interfce;
		this.repository = repo;
		this.ownedParameters = new ArrayList<>();
		this.failureTypes = new ArrayList<>();
		this.exceptionTypes = new ArrayList<>();
	}

	public OperationSignatureCreator withName(String name) {
		return (OperationSignatureCreator) super.withName(name);
	}

	public OperationSignatureCreator withReturnType(DataType returnType) {
		this.returnType = returnType;
		return this;
	}

	public OperationSignatureCreator withParameter(String name, Primitive dataType, ParameterModifier modifier) {
		PrimitiveDataType dt = PrimitiveType.getPrimitiveDataType(dataType);

		return withParameter(name, dt, modifier);
	}

	public OperationSignatureCreator withParameter(String name, DataType dataType, ParameterModifier modifier) {
		Parameter param = RepositoryFactory.eINSTANCE.createParameter();
		if (name != null)
			param.setParameterName(name);
		if (dataType != null)
			param.setDataType__Parameter(dataType);
		if (modifier != null)
			param.setModifier__Parameter(modifier);

		ownedParameters.add(param);
		this.repository.addParameter(param);
		return this;
	}

	public OperationSignatureCreator withFailureType(FailureType failureType) {
		this.failureTypes.add(failureType);
		return this;
	}

	public OperationSignatureCreator withExceptionType(ExceptionType exceptionType) {
		this.exceptionTypes.add(exceptionType);
		return this;
	}

	public OperationInterfaceCreator now() {
		OperationSignature sign = this.build();
		correspondingInterface.addOperationSignatures(sign);
		return correspondingInterface;
	}

	@Override
	protected OperationSignature build() {
		OperationSignature ops = RepositoryFactory.eINSTANCE.createOperationSignature();
		if (name != null)
			ops.setEntityName(name);
		ops.setReturnType__OperationSignature(returnType);
		ops.getParameters__OperationSignature().addAll(ownedParameters);
		ops.getFailureType().addAll(failureTypes);
		ops.getExceptions__Signature().addAll(exceptionTypes);

		return ops;
	}

}
