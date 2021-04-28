package org.palladiosimulator.generator.fluent.component.repositoryStructure.types;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

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
public class ExceptionTypeCreator extends RepositoryEntity {

    private String exceptionMessage;

    public ExceptionTypeCreator(final RepositoryCreator repo) {
        repository = repo;
    }

    @Override
    public ExceptionTypeCreator withName(final String name) {
        return (ExceptionTypeCreator) super.withName(name);
    }

    /**
     * Specifies the <code>message</code> this exception provides.
     *
     * @param message
     * @return this exception in the making
     */
    public ExceptionTypeCreator withExceptionMessage(final String message) {
        IllegalArgumentException.throwIfNull(message, "message must not be null");
        exceptionMessage = message;
        return this;
    }

    @Override
    public ExceptionType build() {
        final ExceptionType exType = RepositoryFactory.eINSTANCE.createExceptionType();
        if (name != null) {
            exType.setExceptionName(name);
        }
        if (exceptionMessage != null) {
            exType.setExceptionMessage(exceptionMessage);
        }
        return exType;
    }

}
