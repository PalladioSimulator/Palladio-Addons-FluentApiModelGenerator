package org.palladiosimulator.generator.fluent.repository.structure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.repository.structure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Failure;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.InfrastructureSignature
 * InfrastructureSignature}. It is used to create the
 * '<em><b>InfrastructureSignature</b></em>' object step-by-step, i.e.
 * '<em><b>InfrastructureSignatureCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.InfrastructureSignature
 */
public class InfrastructureSignatureCreator extends RepositoryEntity {

    private final List<Parameter> parameters;
    private final List<ExceptionType> exceptions;
    private final List<FailureType> failures;

    public InfrastructureSignatureCreator(final RepositoryCreator repository) {
        this.repository = repository;
        parameters = new ArrayList<>();
        exceptions = new ArrayList<>();
        failures = new ArrayList<>();
    }

    @Override
    public InfrastructureSignatureCreator withName(final String name) {
        return (InfrastructureSignatureCreator) super.withName(name);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
     * and adds it to the signature's ordered list of parameters.
     * <p>
     * Each parameter is a tuple of a <code>name</code> and a <code>dataType</code>
     * (which is unique across the parameters). Optionally, the
     * <code>modifier</code>s '<em><b>in</b></em>', '<em><b>out</b></em>', and
     * '<em><b>inout</b></em>' (with its OMG IDL semantics) can be used for
     * parameters, e.g. <code>ParameterModifier.IN</code>.
     * </p>
     * <p>
     * A
     * {@link org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive
     * Primitive} data type can have the values '<em><b>boolean</b></em>',
     * '<em><b>integer</b></em>', '<em><b>string</b></em>',
     * '<em><b>double</b></em>', '<em><b>long</b></em>', '<em><b>char</b></em>',
     * '<em><b>byte</b></em>'.
     * </p>
     *
     * @param name
     * @param dataType
     * @param modifier may be null
     * @return this infrastructure signature in the making
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public InfrastructureSignatureCreator withParameter(final String name, final Primitive dataType,
            final ParameterModifier modifier) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        IllegalArgumentException.throwIfNull(dataType, "dataType must not be null");
        final PrimitiveDataType dt = repository.getPrimitiveDataType(dataType);
        return this.withParameter(name, dt, modifier);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.repository.Parameter Parameter}
     * and adds it to the signature's ordered list of parameters.
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
     * @return this infrastructure signature in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public InfrastructureSignatureCreator withParameter(final String name, final DataType dataType,
            final ParameterModifier modifier) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        IllegalArgumentException.throwIfNull(dataType, "dataType must not be null");
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();

        param.setParameterName(name);

        param.setDataType__Parameter(dataType);

        if (modifier != null) {
            param.setModifier__Parameter(modifier);
        }

        parameters.add(param);
        repository.addParameter(param);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the signature's list of possible
     * failures.
     * <p>
     * Failure types can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfFailureType(name)</code>.
     * </p>
     *
     * @param failureType
     * @return this infrastructure signature in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    public InfrastructureSignatureCreator withFailureType(final FailureType failureType) {
        IllegalArgumentException.throwIfNull(failureType, "failureType must not be null");
        failures.add(failureType);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the signature's list of possible
     * failures.
     * <p>
     * A
     * {@link org.palladiosimulator.generator.fluent.repository.structure.internals.Failure
     * Failure} type can have the values '<em><b>HARDWARE_CPU</b></em>',
     * '<em><b>HARDWARE_HDD</b></em>', '<em><b>HARDWARE_DELAY</b></em>',
     * '<em><b>NETWORK_LAN</b></em>', '<em><b>SOFTWARE</b></em>'.
     * </p>
     *
     * @param failureType
     * @return this infrastructure signature in the making
     */
    public InfrastructureSignatureCreator withFailureType(final Failure failureType) {
        IllegalArgumentException.throwIfNull(failureType, "failureType must not be null");
        final FailureType failure = repository.getFailureType(failureType);
        return this.withFailureType(failure);
    }

    /**
     * Adds the <code>exceptionType</code> to the signature's list of possible
     * org.palladiosimulator.generator.fluent.exceptions.
     * <p>
     * An existing exception type can be fetched from the repository using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.fetchOfExceptionType(name)</code>.
     * </p>
     *
     * @param exceptionType
     * @return this infrastructure signature in the making
     * @see org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory#fetchOfExceptionType(String)
     */
    public InfrastructureSignatureCreator withExceptionType(final ExceptionType exceptionType) {
        IllegalArgumentException.throwIfNull(exceptionType, "exceptionType must not be null");
        exceptions.add(exceptionType);
        return this;
    }

    @Override
    protected InfrastructureSignature build() {
        final InfrastructureSignature sig = RepositoryFactory.eINSTANCE.createInfrastructureSignature();
        if (name != null) {
            sig.setEntityName(name);
        }
        sig.getParameters__InfrastructureSignature().addAll(parameters);
        sig.getExceptions__Signature().addAll(exceptions);
        sig.getFailureType().addAll(failures);

        return sig;
    }

}
