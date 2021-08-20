package org.palladiosimulator.generator.fluent.usagemodel.factory;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;

//TODO: JavaDoc
public class OperationProvidedSignatureRole {

    private OperationProvidedRole provRole;
    private OperationSignature opSig;

    public OperationProvidedSignatureRole() {
    }

    public OperationProvidedSignatureRole(final OperationProvidedRole role, final OperationSignature signature) {
        setRole(role);
        setSignature(signature);
    }

    public boolean filled() {
        return ((this.provRole != null) && (this.opSig != null));
    }

    public void setRole(final OperationProvidedRole role) {
        this.provRole = role;
    }

    public void setSignature(final OperationSignature signature) {
        this.opSig = signature;
    }

    public OperationProvidedRole getRole() {
        return this.provRole;
    }

    public OperationSignature getSignature() {
        return this.opSig;
    }

}
