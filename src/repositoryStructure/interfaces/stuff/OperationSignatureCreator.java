package repositoryStructure.interfaces.stuff;

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
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;
import repositoryStructure.interfaces.OperationInterfaceCreator;


public class OperationSignatureCreator extends Entity{
	
	private DataType returnType;
	private List<Parameter> ownedParameters = new ArrayList<>();
	private List<FailureType> failureTypes = new ArrayList<>();
	private List<ExceptionType> exceptionTypes = new ArrayList<>();
	private OperationInterfaceCreator correspondingInterface;

	public OperationSignatureCreator(OperationInterfaceCreator interfce) {
		this.correspondingInterface = interfce;
	}

	public OperationSignatureCreator withName(String name) {
		return (OperationSignatureCreator) super.withName(name);
	}
	
	public OperationInterfaceCreator withReturnType(DataType returnType) {
		this.returnType = returnType;
		OperationSignature signature = this.build();
		correspondingInterface.addOperationSignatures(signature);
		return correspondingInterface;
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
