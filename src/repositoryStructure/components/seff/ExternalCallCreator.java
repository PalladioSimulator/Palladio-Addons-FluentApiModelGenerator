package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Follow;

public class ExternalCallCreator implements Follow {

	private SeffCreator seff;

	private Integer retryCount;
	private OperationSignature signature;
	private OperationRequiredRole requiredRole;
	private List<VariableUsage> inputVariableUsages;
	private List<VariableUsage> returnVariableUsages;
	private List<FailureType> failures;

	public ExternalCallCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
		this.returnVariableUsages = new ArrayList<>();
		this.failures = new ArrayList<>();
	}

	public SeffCreator followedBy() {
		ExternalCallAction action = SeffFactory.eINSTANCE.createExternalCallAction();
		if (retryCount != null)
			action.setRetryCount(retryCount);
		if (signature != null)
			action.setCalledService_ExternalService(signature);
		if (requiredRole != null)
			action.setRole_ExternalService(requiredRole);
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		action.getReturnVariableUsage__CallReturnAction().addAll(returnVariableUsages);
		action.getFailureTypes_FailureHandlingEntity().addAll(failures);
		seff.setNext(action);
		return seff;
	}

	public ExternalCallCreator withRetryCount(Integer retryCount) {
		if (retryCount != null)
			this.retryCount = retryCount;
		return this;
	}

	public ExternalCallCreator withCalledService(OperationSignature signature) {
		this.signature = signature;
		return this;
	}

	public ExternalCallCreator withRequiredRole(OperationRequiredRole requiredRole) {
		this.requiredRole = requiredRole;
		return this;
	}

	public ExternalCallCreator withInputVariableUsage() {
		// TODO:
//		if ( != null)
		return this;
	}

	public ExternalCallCreator withReturnVariableUsage() {
		// TODO:
//		if ( != null)
			return this;
	}

	public ExternalCallCreator withFailureType(FailureType failure) {
		if (failure != null)
			this.failures.add(failure);
		return this;
	}
}
