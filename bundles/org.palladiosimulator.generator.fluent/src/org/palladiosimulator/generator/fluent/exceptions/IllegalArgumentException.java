package org.palladiosimulator.generator.fluent.exceptions;

/**
 * Thrown by various accessor methods to indicate that the element being
 * requested does not exist.
 *
 * @author Yves Kirschner
 */
public class IllegalArgumentException extends FluentApiException {

    private static final long serialVersionUID = -6262647326073807402L;

    private static final String DEFAULT_MESSAGE = "";

    /**
     * Checks that the specified object reference is not {@code null}. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors.
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws FluentApiException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj) throws FluentApiException {
        return requireNonNull(obj, DEFAULT_MESSAGE);
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a
     * customized {@code FluentApiException} if it is. This method is designed
     * primarily for doing parameter validation in methods and constructors with
     * multiple parameters.
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                IllegalArgumentException} is thrown
     * @param <T>     the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws FluentApiException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) throws FluentApiException {
        return requireNonNull(obj, () -> new IllegalArgumentException(message));
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with {@code null} as its
     * detail message.
     */
    public IllegalArgumentException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public IllegalArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail
     * message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i>
     * automatically incorporated in this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method). (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for runtime
     * org.palladiosimulator.generator.fluent.exceptions that are little more than
     * wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method). (A {@code null} value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     */
    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail
     * message, cause, suppression enabled or disabled, and writable stack trace
     * enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause. (A {@code null} value is permitted, and
     *                           indicates that the cause is nonexistent or
     *                           unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected IllegalArgumentException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
