package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;

public interface ExternalCallSeff {
	public ExternalCallSeff withName(String name);

	public ExternalCallSeff withRetryCount(Integer retryCount);

	public ExternalCallSeff withCalledService(OperationSignature signature);

	public ExternalCallSeff withRequiredRole(OperationRequiredRole requiredRole);

	public ExternalCallSeff withInputVariableUsage();

	public ExternalCallSeff withReturnVariableUsage();

	public ExternalCallSeff withFailureType(FailureType failure);

	public ActionSeff followedBy();

}