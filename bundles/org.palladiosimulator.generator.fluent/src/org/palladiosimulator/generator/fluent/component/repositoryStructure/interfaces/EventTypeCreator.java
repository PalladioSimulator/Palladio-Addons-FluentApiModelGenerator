package org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.Failure;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.Primitive;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.repository.EventType
 * EventType}. It is used to create the '<em><b>EventType</b></em>' object
 * step-by-step, i.e. '<em><b>EventTypeCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.EventType
 */
public class EventTypeCreator extends RepositoryEntity {

    private Parameter parameter;
    private final List<FailureType> failureTypes = new ArrayList<>();
    private final List<ExceptionType> exceptionTypes = new ArrayList<>();

    public EventTypeCreator(final RepositoryCreator repo) {
        repository = repo;
    }

    @Override
    public EventTypeCreator withName(final String name) {
        return (EventTypeCreator) super.withName(name);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
     * and adds it to the event type's ordered list of parameters.
     * <p>
     * Each parameter is a tuple of a <code>name</code> and a <code>dataType</code>
     * (which is unique across the parameters). Optionally, the
     * <code>modifier</code>s '<em><b>in</b></em>', '<em><b>out</b></em>', and
     * '<em><b>inout</b></em>' (with its OMG IDL semantics) can be used for
     * parameters, e.g. <code>ParameterModifier.IN</code>.
     * </p>
     * <p>
     * A
     * {@link org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.Primitive
     * Primitive} data type can have the values '<em><b>boolean</b></em>',
     * '<em><b>integer</b></em>', '<em><b>string</b></em>',
     * '<em><b>double</b></em>', '<em><b>long</b></em>', '<em><b>char</b></em>',
     * '<em><b>byte</b></em>'.
     * </p>
     *
     * @param name
     * @param dataType
     * @param modifier may be null
     * @return this event type in the making
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public EventTypeCreator withParameter(final String name, final Primitive dataType,
            final ParameterModifier modifier) {
        IllegalArgumentException.requireNonNull(name, "name must not be null");
        IllegalArgumentException.requireNonNull(dataType, "dataType must not be null");
        final PrimitiveDataType dt = repository.getPrimitiveDataType(dataType);
        return this.withParameter(name, dt, modifier);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
     * and adds it to the event type's ordered list of parameters.
     * <p>
     * Each parameter is a tuple of a <code>name</code> and a <code>dataType</code>
     * (which is unique across the parameters). Optionally, the
     * <code>modifier</code>s '<em><b>in</b></em>', '<em><b>out</b></em>', and
     * '<em><b>inout</b></em>' (with its OMG IDL semantics) can be used for
     * parameters, e.g. <code>ParameterModifier.IN</code>.
     * </p>
     * <p>
     * An existing data type can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfDataType(name)</code>.
     * </p>
     *
     * @param name
     * @param dataType
     * @param modifier may be null
     * @return this event type in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public EventTypeCreator withParameter(final String name, final DataType dataType,
            final ParameterModifier modifier) {
        IllegalArgumentException.requireNonNull(name, "name must not be null");
        IllegalArgumentException.requireNonNull(dataType, "dataType must not be null");
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();

        param.setParameterName(name);
        param.setDataType__Parameter(dataType);

        if (modifier != null) {
            param.setModifier__Parameter(modifier);
        }

        parameter = param;
        repository.addParameter(param);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the event type's list of possible
     * failures.
     * <p>
     * Failure types can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfFailureType(name)</code>.
     * </p>
     *
     * @param failureType
     * @return this event type in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    public EventTypeCreator withFailureType(final FailureType failureType) {
        IllegalArgumentException.requireNonNull(failureType, "failureType must not be null");
        failureTypes.add(failureType);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the event type's list of possible
     * failures.
     * <p>
     * A
     * {@link org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.Failure
     * Failure} type can have the values '<em><b>HARDWARE_CPU</b></em>',
     * '<em><b>HARDWARE_HDD</b></em>', '<em><b>HARDWARE_DELAY</b></em>',
     * '<em><b>NETWORK_LAN</b></em>', '<em><b>SOFTWARE</b></em>'.
     * </p>
     *
     * @param failureType
     * @return this event type in the making
     */
    public EventTypeCreator withFailureType(final Failure failureType) {
        IllegalArgumentException.requireNonNull(failureType, "failureType must not be null");
        final FailureType failure = repository.getFailureType(failureType);
        return this.withFailureType(failure);
    }

    /**
     * Adds the <code>exceptionType</code> to the event type's list of possible
     * org.palladiosimulator.generator.fluent.exceptions.
     * <p>
     * An existing exception type can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfExceptionType(name)</code>.
     * </p>
     *
     * @param exceptionType
     * @return this event type in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#fetchOfExceptionType(String)
     */
    public EventTypeCreator withExceptionType(final ExceptionType exceptionType) {
        IllegalArgumentException.requireNonNull(exceptionType, "exceptionType must not be null");
        exceptionTypes.add(exceptionType);
        return this;
    }

    @Override
    protected EventType build() {
        final EventType et = RepositoryFactory.eINSTANCE.createEventType();
        if (name != null) {
            et.setEntityName(name);
        }
        if (parameter != null) {
            et.setParameter__EventType(parameter);
        }
        et.getFailureType().addAll(failureTypes);
        et.getExceptions__Signature().addAll(exceptionTypes);

        return et;
    }
}
