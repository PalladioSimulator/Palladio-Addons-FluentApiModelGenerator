package component.repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import component.repositoryStructure.RepositoryCreator;
import component.repositoryStructure.RepositoryEntity;
import component.repositoryStructure.internals.Failure;
import component.repositoryStructure.internals.Primitive;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.OperationSignature
 * OperationSignature}. It is used to create the
 * '<em><b>OperationSignature</b></em>' object step-by-step, i.e.
 * '<em><b>OperationSignatureCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.OperationSignature
 */
public class OperationSignatureCreator extends RepositoryEntity {

    private DataType returnType;
    private final List<Parameter> ownedParameters;
    private final List<FailureType> failureTypes;
    private final List<ExceptionType> exceptionTypes;

    public OperationSignatureCreator(final RepositoryCreator repo) {
        repository = repo;
        ownedParameters = new ArrayList<>();
        failureTypes = new ArrayList<>();
        exceptionTypes = new ArrayList<>();
    }

    @Override
    public OperationSignatureCreator withName(final String name) {
        return (OperationSignatureCreator) super.withName(name);
    }

    /**
     * Defines the <code>returnType</code> of the operation signature.
     * <p>
     * An existing data type can be fetched from the repository using the
     * component.factory, i.e. <code>create.fetchOfDataType(name)</code>.
     * </p>
     *
     * @param returnType
     * @return this operation signature in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfDataType(String)
     */
    public OperationSignatureCreator withReturnType(final DataType returnType) {
        Objects.requireNonNull(returnType, "returnType must not be null");
        this.returnType = returnType;
        return this;
    }

    /**
     * Defines the <code>returnType</code> of the operation signature.
     * <p>
     * A {@link component.repositoryStructure.internals.Primitive Primitive} data
     * type can have the values '<em><b>boolean</b></em>',
     * '<em><b>integer</b></em>', '<em><b>string</b></em>',
     * '<em><b>double</b></em>', '<em><b>long</b></em>', '<em><b>char</b></em>',
     * '<em><b>byte</b></em>'.
     * </p>
     *
     * @param returnType
     * @return this operation signature in the making
     */
    public OperationSignatureCreator withReturnType(final Primitive returnType) {
        Objects.requireNonNull(returnType, "returnType must not be null");
        final PrimitiveDataType primitiveDataType = repository.getPrimitiveDataType(returnType);
        return this.withReturnType(primitiveDataType);
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
     * A {@link component.repositoryStructure.internals.Primitive Primitive} data
     * type can have the values '<em><b>boolean</b></em>',
     * '<em><b>integer</b></em>', '<em><b>string</b></em>',
     * '<em><b>double</b></em>', '<em><b>long</b></em>', '<em><b>char</b></em>',
     * '<em><b>byte</b></em>'.
     * </p>
     *
     * @param name
     * @param dataType
     * @param modifier may be null
     * @return this operation signature in the making
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public OperationSignatureCreator withParameter(final String name, final Primitive dataType,
            final ParameterModifier modifier) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(dataType, "dataType must not be null");
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
     * component.factory, i.e. <code>create.fetchOfDataType(name)</code>.
     * </p>
     *
     * @param name
     * @param dataType
     * @param modifier may be null
     * @return this operation signature in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * @see org.palladiosimulator.pcm.repository.ParameterModifier
     */
    public OperationSignatureCreator withParameter(final String name, final DataType dataType,
            final ParameterModifier modifier) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(dataType, "dataType must not be null");
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();

        param.setParameterName(name);
        param.setDataType__Parameter(dataType);

        if (modifier != null) {
            param.setModifier__Parameter(modifier);
        }

        ownedParameters.add(param);
        repository.addParameter(param);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the operation signature's list of
     * possible failures.
     * <p>
     * Failure types can be fetched from the repository using the component.factory,
     * i.e. <code>create.fetchOfFailureType(name)</code>.
     * </p>
     *
     * @param failureType
     * @return this operation signature in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfFailureType(String)
     */
    public OperationSignatureCreator withFailureType(final FailureType failureType) {
        Objects.requireNonNull(failureType, "failureType must not be null");
        failureTypes.add(failureType);
        return this;
    }

    /**
     * Adds the <code>failureType</code> to the operation signature's list of
     * possible failures.
     * <p>
     * A {@link component.repositoryStructure.internals.Failure Failure} type can
     * have the values '<em><b>HARDWARE_CPU</b></em>',
     * '<em><b>HARDWARE_HDD</b></em>', '<em><b>HARDWARE_DELAY</b></em>',
     * '<em><b>NETWORK_LAN</b></em>', '<em><b>SOFTWARE</b></em>'.
     * </p>
     *
     * @param failureType
     * @return this operation signature in the making
     */
    public OperationSignatureCreator withFailureType(final Failure failureType) {
        Objects.requireNonNull(failureType, "failureType must not be null");
        final FailureType failure = repository.getFailureType(failureType);
        return this.withFailureType(failure);
    }

    /**
     * Adds the <code>exceptionType</code> to the operation signature's list of
     * possible exceptions.
     * <p>
     * An existing exception type can be fetched from the repository using the
     * component.factory, i.e. <code>create.fetchOfExceptionType(name)</code>.
     * </p>
     *
     * @param exceptionType
     * @return this operation signature in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfExceptionType(String)
     */
    public OperationSignatureCreator withExceptionType(final ExceptionType exceptionType) {
        Objects.requireNonNull(exceptionType, "exceptionType must not be null");
        exceptionTypes.add(exceptionType);
        return this;
    }

    @Override
    protected OperationSignature build() {
        final OperationSignature ops = RepositoryFactory.eINSTANCE.createOperationSignature();
        if (name != null) {
            ops.setEntityName(name);
        }
        ops.setReturnType__OperationSignature(returnType);
        ops.getParameters__OperationSignature().addAll(ownedParameters);
        ops.getFailureType().addAll(failureTypes);
        ops.getExceptions__Signature().addAll(exceptionTypes);

        return ops;
    }

}
