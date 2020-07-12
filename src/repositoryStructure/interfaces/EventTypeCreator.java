package repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.repository.EventType
 * EventType}. It is used to create the '<em><b>EventType</b></em>' object
 * step-by-step, i.e. '<em><b>EventTypeCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.EventType
 */
public class EventTypeCreator extends Entity {

	private Parameter parameter;
	private List<FailureType> failureTypes = new ArrayList<>();
	private List<ExceptionType> exceptionTypes = new ArrayList<>();
	private EventGroupCreator correspondingEventGroup;

	public EventTypeCreator(EventGroupCreator eventGroup, RepositoryCreator repo) {
		this.correspondingEventGroup = eventGroup;
		this.repository = repo;
	}

	@Override
	public EventTypeCreator withName(String name) {
		return (EventTypeCreator) super.withName(name);
	}

	public EventTypeCreator withParameter(String name, Primitive dataType, ParameterModifier modifier) {
		PrimitiveDataType dt = PrimitiveType.getPrimitiveDataType(dataType);

		return withParameter(name, dt, modifier);
	}

	public EventTypeCreator withParameter(String name, DataType dataType, ParameterModifier modifier) {
		Parameter param = RepositoryFactory.eINSTANCE.createParameter();
		if (name != null)
			param.setParameterName(name);
		if (dataType != null)
			param.setDataType__Parameter(dataType);
		if (modifier != null)
			param.setModifier__Parameter(modifier);

		this.parameter = param;
		this.repository.addParameter(param);
		return this;

	}

//	public EventTypeCreator withParameter(Parameter parameter) {
//		this.parameter = parameter;
//		return this;
//	}

	public EventTypeCreator withFailureType(FailureType failureType) {
		this.failureTypes.add(failureType);
		return this;
	}

	public EventTypeCreator withExceptionType(ExceptionType exceptionType) {
		this.exceptionTypes.add(exceptionType);
		return this;
	}

	public EventGroupCreator now() {
		EventType eventType = this.build();
		correspondingEventGroup.addEventType(eventType);
		return correspondingEventGroup;
	}

	@Override
	protected EventType build() {
		EventType et = RepositoryFactory.eINSTANCE.createEventType();
		if (name != null)
			et.setEntityName(name);
		if (parameter != null)
			et.setParameter__EventType(parameter);
		et.getFailureType().addAll(failureTypes);
		et.getExceptions__Signature().addAll(exceptionTypes);

		return et;
	}
}
