package org.palladiosimulator.generator.fluent.exceptions;

import java.util.function.Supplier;

/**
 * Thrown by various accessor methods to indicate that the element being requested does not exist.
 *
 * @author Yves Kirschner
 */
public class FluentApiException extends RuntimeException {

    private static final long serialVersionUID = 4990563621773025662L;

    /**
     * Checks that the specified object reference is not {@code null} and throws a customized
     * {@code FluentApiException} if it is. This method is designed primarily for doing parameter
     * validation in methods and constructors with multiple parameters.
     *
     * @param <T>
     *            the type of the reference
     * @param obj
     *            the object reference to check for nullity
     * @param constructor
     *            the supplier of the exception constructor
     * @return {@code obj} if not {@code null}
     * @throws FluentApiException
     *             if {@code obj} is {@code null}
     */
    protected static <T> T requireNonNull(final T obj, final Supplier<FluentApiException> constructor)
            throws FluentApiException {
        if (obj == null) {
            throw constructor.get();
        }

        if (obj instanceof Iterable<?>) {
            for (final Object element : (Iterable<?>) obj) {
                if (element == null) {
                    throw constructor.get();
                }
            }
        }

        return obj;
    }

    /**
     * Constructs a new {@code FluentApiException} with the default message.
     */
    public FluentApiException() {
    }

    /**
     * Constructs a new {@code FluentApiException} with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the
     *            {@link #getMessage()} method.
     */
    public FluentApiException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FluentApiException} with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically
     * incorporated in this runtime exception's detail message.
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the
     *            {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *            (A {@code null} value is permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public FluentApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code FluentApiException} with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and
     * detail message of {@code cause}). This constructor is useful for runtime
     * org.palladiosimulator.generator.fluent.exceptions that are little more than wrappers for
     * other throwables.
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *            (A {@code null} value is permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public FluentApiException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code FluentApiException} with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message
     *            the detail message.
     * @param cause
     *            the cause. (A {@code null} value is permitted, and indicates that the cause is
     *            nonexistent or unknown.)
     * @param enableSuppression
     *            whether or not suppression is enabled or disabled
     * @param writableStackTrace
     *            whether or not the stack trace should be writable
     */
    protected FluentApiException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
