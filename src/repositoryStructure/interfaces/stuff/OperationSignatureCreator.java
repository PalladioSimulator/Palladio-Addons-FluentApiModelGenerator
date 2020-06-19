package repositoryStructure.interfaces.stuff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;


public class OperationSignatureCreator extends Entity{
	
	private DataType returnType;
	private List<Parameter> ownedParameters = new ArrayList<>();
	private List<FailureType> failureTypes = new ArrayList<>();
	private List<ExceptionType> exceptionTypes = new ArrayList<>();

	public OperationSignatureCreator withName(String name) {
		return (OperationSignatureCreator) super.withName(name);
	}
	
	public OperationSignatureCreator withReturnType(DataType returnType) {
		this.returnType = returnType;
		return this;
	}
	
	public OperationSignatureCreator withParameter(Parameter parameter) {
		this.ownedParameters.add(parameter);
		return this;
	}
	
	public OperationSignatureCreator withParameter(ParameterCreator parameter) {
		Parameter p = parameter.build();
		return this.withParameter(p);
	}
	
	public OperationSignatureCreator withFailureType(FailureType failureType) {
		this.failureTypes.add(failureType);
		return this;
	}
	
	public OperationSignatureCreator withExceptionType(ExceptionType exceptionType) {
		this.exceptionTypes.add(exceptionType);
		return this;
	}

	@Override
	public OperationSignature build() {
		OperationSignature ops = RepositoryFactory.eINSTANCE.createOperationSignature();
		ops.setReturnType__OperationSignature(returnType);
		ops.getParameters__OperationSignature().addAll(ownedParameters);
		ops.getFailureType().addAll(failureTypes);
		ops.getExceptions__Signature().addAll(exceptionTypes);

		return ops;
	}
}
