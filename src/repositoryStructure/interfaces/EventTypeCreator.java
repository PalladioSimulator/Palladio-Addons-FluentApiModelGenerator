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
import repositoryStructure.datatypes.Primitive;
import repositoryStructure.datatypes.PrimitiveType;

public class EventTypeCreator extends Entity{

	private Parameter parameter;
	private List<FailureType> failureTypes = new ArrayList<>();
	private List<ExceptionType> exceptionTypes = new ArrayList<>();
	private EventGroupCreator correspondingEventGroup;
	
	public EventTypeCreator(EventGroupCreator eventGroup) {
		this.correspondingEventGroup = eventGroup;
	}
	
	@Override
	public EventTypeCreator withName(String name) {
		return (EventTypeCreator) super.withName(name);
	}
	
	public EventGroupCreator withParameter(String name, Primitive dataType, ParameterModifier modifier) {
		PrimitiveDataType dt = PrimitiveType.getPrimitiveDataType(dataType);
		
		return withParameter(name, dt, modifier);
	}
	
	public EventGroupCreator withParameter(String name, DataType dataType, ParameterModifier modifier) {
		Parameter param = RepositoryFactory.eINSTANCE.createParameter();
		if (name != null)
			param.setParameterName(name);
		if (dataType != null)
			param.setDataType__Parameter(dataType);
		if (modifier != null)
			param.setModifier__Parameter(modifier);
		
		return withParameter(param);
	}
	
	public EventGroupCreator withParameter(Parameter parameter) {
		this.parameter = parameter;
		EventType eventType = this.build();
		correspondingEventGroup.addEventType(eventType);
		return correspondingEventGroup;
	}
	
	public EventTypeCreator withFailureType(FailureType failureType) {
		this.failureTypes.add(failureType);
		return this;
	}
	
	public EventTypeCreator withExceptionType(ExceptionType exceptionType) {
		this.exceptionTypes.add(exceptionType);
		return this;
	}
	
	@Override
	public EventType build() {
		EventType et = RepositoryFactory.eINSTANCE.createEventType();
		et.setEntityName(this.name);
		et.setParameter__EventType(parameter);
		et.getFailureType().addAll(failureTypes);
		et.getExceptions__Signature().addAll(exceptionTypes);

		return et;
	}
}
