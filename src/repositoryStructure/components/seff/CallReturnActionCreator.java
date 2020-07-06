package repositoryStructure.components.seff;

import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.Entity;

public class CallReturnActionCreator extends Entity {

	private SeffCreator seff;
	
	private List<VariableUsage> inputVariableUsages;
	private List<VariableUsage> returnVariableUsages;
	
	public CallReturnActionCreator(SeffCreator seff) {
		this.seff = seff;
	}

	protected CallReturnAction build() {
		CallReturnAction action = SeffFactory.eINSTANCE.createCallReturnAction();
		action.getEntityName();
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		action.getReturnVariableUsage__CallReturnAction().addAll(returnVariableUsages);
		return action;
	}
	
	//TODO: wird wahrscheinlich nicht gebraucht?
}
