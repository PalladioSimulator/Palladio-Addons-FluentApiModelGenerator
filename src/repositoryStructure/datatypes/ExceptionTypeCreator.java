package repositoryStructure.datatypes;

import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class ExceptionTypeCreator extends DataType{

	
	private ExceptionTypeCreator() {}
	public ExceptionTypeCreator(RepositoryCreator repo) {
		this.repository = repo;
	}

	private String name;
	private String exceptionMessage;

	public ExceptionTypeCreator withName(String name) {
		this.name = name;
		return this;
	}
	
	public ExceptionTypeCreator withExceptionMessage(String message) {
		this.exceptionMessage = message;
		return this;
	}

	public ExceptionType build() {
		ExceptionType exType = RepositoryFactory.eINSTANCE.createExceptionType();
		if (name != null)
			exType.setExceptionName(name);
		if (exceptionMessage != null)
			exType.setExceptionMessage(exceptionMessage);
		return null;
	}

}
