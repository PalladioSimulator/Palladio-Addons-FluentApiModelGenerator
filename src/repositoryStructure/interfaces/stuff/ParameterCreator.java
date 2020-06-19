package repositoryStructure.interfaces.stuff;

import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;

public class ParameterCreator extends Entity {

	private DataType type;
	private EventType eventType;
	private ParameterModifier modifier;

	@Override
	public ParameterCreator withName(String name) {
		return (ParameterCreator) super.withName(name);
	}

	public ParameterCreator ofDataType(DataType type) {
		this.type = type;
		return this;
	}

	public ParameterCreator ofEventType(EventType eventType) {
		this.eventType = eventType;
		return this;
	}

	public ParameterCreator havingModifier(ParameterModifier modifier) {
		this.modifier = modifier;
		return this;
	}
	
	@Override
	public Parameter build() {
		Parameter param = RepositoryFactory.eINSTANCE.createParameter();
		if (name != null)
			param.setParameterName(name);
		if (type != null)
			param.setDataType__Parameter(type);
		if (eventType != null)
			param.setEventType__Parameter(eventType);
		if (modifier != null)
			param.setModifier__Parameter(modifier);

		return param;
	}
}
