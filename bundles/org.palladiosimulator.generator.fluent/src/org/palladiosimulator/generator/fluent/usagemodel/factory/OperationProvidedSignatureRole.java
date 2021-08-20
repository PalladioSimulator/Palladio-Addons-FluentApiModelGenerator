package org.palladiosimulator.generator.fluent.usagemodel.factory;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;

/**
 * TODO
 * The Class OperationProvidedSignatureRole.
 */
public class OperationProvidedSignatureRole {

    private OperationProvidedRole provRole;
    private OperationSignature opSig;

    /**
     * TODO
     * Instantiates a new operation provided signature role.
     */
    public OperationProvidedSignatureRole() {
    }

    /**
     * TODO
     * Instantiates a new operation provided signature role.
     *
     * @param role the role
     * @param signature the signature
     */
    public OperationProvidedSignatureRole(final OperationProvidedRole role, final OperationSignature signature) {
        setRole(role);
        setSignature(signature);
    }

    /**
     * TODO
     * Filled.
     *
     * @return true, if successful
     */
    public boolean filled() {
        return ((this.provRole != null) && (this.opSig != null));
    }

    /**
     * TODO
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(final OperationProvidedRole role) {
        this.provRole = role;
    }

    /**
     * TODO
     * Sets the signature.
     *
     * @param signature the new signature
     */
    public void setSignature(final OperationSignature signature) {
        this.opSig = signature;
    }

    /**
     * TODO
     * Gets the role.
     *
     * @return the role
     */
    public OperationProvidedRole getRole() {
        return this.provRole;
    }

    /**
     * TODO
     * Gets the signature.
     *
     * @return the signature
     */
    public OperationSignature getSignature() {
        return this.opSig;
    }

}
