package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import apiControlFlowInterfaces.Follow;

public class EmitEventActionCreator implements Follow {

	private SeffCreator seff;

	private EventType eventType;
	private SourceRole requiredRole;
	private List<VariableUsage> inputVariableUsages;

	public EmitEventActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
	}

	public SeffCreator followedBy() {
		EmitEventAction action = SeffFactory.eINSTANCE.createEmitEventAction();
		if (eventType != null)
			action.setEventType__EmitEventAction(eventType);
		if (requiredRole != null)
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
//		if ( != null)
		// TODO: Vererbung?
		return this;
	}

}
