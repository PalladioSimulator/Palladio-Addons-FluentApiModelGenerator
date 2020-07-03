package repositoryStructure.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class SetVariableActionCreator {

	private SeffCreator seff;
	
	private List<InfrastructureCall> infrastructureCalls;
	private List<ResourceCall> resourceCalls;
	private List<ParametricResourceDemand> parametricResourceDemands;
	private List<VariableUsage> localVariableUsages;
	

	public SetVariableActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.infrastructureCalls = new ArrayList<>();
		this.resourceCalls = new ArrayList<>();
		this.parametricResourceDemands = new ArrayList<>();
		this.localVariableUsages = new ArrayList<>();
	}
	
	public Action followedBy() {
		// TODO: resource demanding behaviour?
		// TODO: iwelche Voraussetzungen + alles iwie über Vererbung, schon mal gemacht.
		SetVariableAction action = SeffFactory.eINSTANCE.createSetVariableAction();
		action.getInfrastructureCall__Action().addAll(infrastructureCalls);
		action.getResourceCall__Action().addAll(resourceCalls);
		action.getResourceDemand_Action().addAll(parametricResourceDemands);
		action.getLocalVariableUsages_SetVariableAction().addAll(localVariableUsages);
		
		seff.setNext(action);
		return seff;
	}
}

