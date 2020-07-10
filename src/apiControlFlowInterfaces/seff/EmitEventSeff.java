package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.SourceRole;

public interface EmitEventSeff {
	public EmitEventSeff withName(String name);

	public EmitEventSeff withEventType(EventType eventType);

	public EmitEventSeff withSourceRole(SourceRole sourceRole);

	public EmitEventSeff withInputVariableUsage();

	public ActionSeff followedBy();

}