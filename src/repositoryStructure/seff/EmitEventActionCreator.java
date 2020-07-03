package repositoryStructure.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Action;
import repositoryStructure.SeffCreator;

public class EmitEventActionCreator {
	
	private SeffCreator seff;

	private EventType eventType;
	private SourceRole requiredRole;
	private List<VariableUsage> inputVariableUsages;
	
	public EmitEventActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
	}

	public Action followedBy() {
		// TODO: resource demanding behaviour? -> Vererbung?
		EmitEventAction action = SeffFactory.eINSTANCE.createEmitEventAction();
		action.setEventType__EmitEventAction(eventType);
		action.setSourceRole__EmitEventAction(requiredRole);
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		seff.setNext(action);
		return seff;
	}
	
	public EmitEventActionCreator withEventType(EventType eventType) {
		this.eventType = eventType;
		return this;
	}
	
	public EmitEventActionCreator withSourceRole(SourceRole sourceRole) {
		this.requiredRole = sourceRole;
		return this;
	}
	
	public EmitEventActionCreator withInputVariableUsage() {
		// TODO: Vererbung?
		return this;
	}

}
