package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Seff.ExternalCallSeff;
import apiControlFlowInterfaces.Seff.FollowSeff;

public class ExternalCallActionCreator extends SeffAction implements ExternalCallSeff, FollowSeff {

	private Integer retryCount;
	private OperationSignature signature;
	private OperationRequiredRole requiredRole;
	private List<VariableUsage> inputVariableUsages;
	private List<VariableUsage> returnVariableUsages;
	private List<FailureType> failures;

	public ExternalCallActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
		this.returnVariableUsages = new ArrayList<>();
		this.failures = new ArrayList<>();
	}

	@Override
	public ExternalCallActionCreator withName(String name) {
		return (ExternalCallActionCreator) super.withName(name);
	}
	
	public ExternalCallActionCreator withRetryCount(Integer retryCount) {
		if (retryCount != null)
			this.retryCount = retryCount;
		return this;
	}

	public ExternalCallActionCreator withCalledService(OperationSignature signature) {
		this.signature = signature;
		return this;
	}

	public ExternalCallActionCreator withRequiredRole(OperationRequiredRole requiredRole) {
		this.requiredRole = requiredRole;
		return this;
	}

	public ExternalCallActionCreator withInputVariableUsage() {
		// TODO:
//		if ( != null)
		return this;
	}

	public ExternalCallActionCreator withReturnVariableUsage() {
		// TODO:
//		if ( != null)
		return this;
	}

	public ExternalCallActionCreator withFailureType(FailureType failure) {
		if (failure != null)
			this.failures.add(failure);
		return this;
	}

	@Override
	public ExternalCallAction build() {
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

		return action;
	}
}
