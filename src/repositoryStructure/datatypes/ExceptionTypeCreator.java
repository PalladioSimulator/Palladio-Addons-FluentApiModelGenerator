package repositoryStructure.datatypes;

import java.util.Objects;

import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.ExceptionType ExceptionType}. It
 * is used to create the '<em><b>ExceptionType</b></em>' object step-by-step,
 * i.e. '<em><b>ExceptionTypeCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.ExceptionType
 */
public class ExceptionTypeCreator extends Entity {

	private String name;
	private String exceptionMessage;

	public ExceptionTypeCreator(RepositoryCreator repo) {
		// TODO: later
		this.repository = repo;
	}

	@Override
	public ExceptionTypeCreator withName(String name) {
		return (ExceptionTypeCreator) super.withName(name);
	}

	/**
	 * Specifies the <code>message</code> this exception provides.
	 * 
	 * @param message
	 * @return this exception in the making
	 */
	public ExceptionTypeCreator withExceptionMessage(String message) {
		Objects.requireNonNull(message, "message must not be null");
		this.exceptionMessage = message;
		return this;
	}

	@Override
	public ExceptionType build() {
		ExceptionType exType = RepositoryFactory.eINSTANCE.createExceptionType();
		if (name != null)
			exType.setExceptionName(name);
		if (exceptionMessage != null)
			exType.setExceptionMessage(exceptionMessage);
		return exType;
	}

}
