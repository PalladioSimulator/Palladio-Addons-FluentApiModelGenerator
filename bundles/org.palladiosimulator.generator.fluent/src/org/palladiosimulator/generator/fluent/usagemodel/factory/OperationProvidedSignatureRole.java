package org.palladiosimulator.generator.fluent.usagemodel.factory;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;

/**
 * This class is a helping class to encapsulate an OperationProvidedRole with its connected
 * operation Signature to make fetching and using this objects easier. So the only functionality is
 * to have a structure.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
 * @see org.palladiosimulator.pcm.repository.OperationSignature
 */
public class OperationProvidedSignatureRole {

    private OperationProvidedRole provRole;
    private OperationSignature opSig;

    /**
     * Instantiates a new operation provided signature role.
     */
    public OperationProvidedSignatureRole() {
    }

    /**
     * Instantiates a new operation provided signature role with parameters A check that both
     * objects are connected needs to be done beforehand.
     *
     * @param role
     *            the role
     * @param signature
     *            the signature
     *
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    public OperationProvidedSignatureRole(final OperationProvidedRole role, final OperationSignature signature) {
        this.setRole(role);
        this.setSignature(signature);
    }

    /**
     * Sets the operation provided role.
     *
     * @param role
     *            the new operation provided role
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public void setRole(final OperationProvidedRole role) {
        this.provRole = role;
    }

    /**
     * Sets the operation signature.
     *
     * @param signature
     *            the new operation signature
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    public void setSignature(final OperationSignature signature) {
        this.opSig = signature;
    }

    /**
     * Gets the operation provided role.
     *
     * @return the operation provided role
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRole getRole() {
        return this.provRole;
    }

    /**
     * Gets the operation signature
     *
     * @return the operation signature
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    public OperationSignature getSignature() {
        return this.opSig;
    }

}
