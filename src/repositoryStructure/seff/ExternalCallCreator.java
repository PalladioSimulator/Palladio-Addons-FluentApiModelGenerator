package repositoryStructure.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class ExternalCallCreator {

	private SeffCreator seff;

	private int retryCount;
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

	public Action followedBy() {
		// TODO: resource demanding behaviour?
		ExternalCallAction action = SeffFactory.eINSTANCE.createExternalCallAction();
		action.setRetryCount(retryCount);
		action.setCalledService_ExternalService(signature);
		action.setRole_ExternalService(requiredRole);
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		action.getReturnVariableUsage__CallReturnAction().addAll(returnVariableUsages);
		action.getFailureTypes_FailureHandlingEntity().addAll(failures);
		seff.setNext(action);
		return seff;
	}

	public ExternalCallCreator withRetryCount(Integer retryCount) {
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
		return this;
	}

	public ExternalCallCreator withReturnVariableUsage() {
		// TODO:
		return this;
	}

	public ExternalCallCreator withFailureType(FailureType failure) {
		this.failures.add(failure);
		return this;
	}
}
