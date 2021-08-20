package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
 * EntryLevelSystemCall}. It is used to create the '<em><b>EntryLevelSystemCall</b></em>' object
 * step-by-step, i.e. '<em><b>EntryLevelSystemCallCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class EntryLevelSystemCallCreator extends ActionCreator {

    private List<VariableUsage> outputParameterUsage;
    private List<VariableUsage> inputParameterUsage;
    private int priority;

    private OperationSignature opSignature;
    private OperationProvidedRole opRole;

    public EntryLevelSystemCallCreator(OperationSignature operationSignature,
            OperationProvidedRole operationProvidedRole) {
        this.outputParameterUsage = new ArrayList<VariableUsage>();
        this.inputParameterUsage = new ArrayList<VariableUsage>();
        this.priority = 0;
        withOperationSignatureEntryLevelSystemCall(operationSignature);
        withProvidedRoleEntryLevelSystemCall(operationProvidedRole);
    }

    private EntryLevelSystemCallCreator withOperationSignatureEntryLevelSystemCall(
            OperationSignature operationSignature) {
        IllegalArgumentException.throwIfNull(operationSignature, "The given Operation Signature must not be null");
        this.opSignature = operationSignature;
        return this;
    }

    private EntryLevelSystemCallCreator withProvidedRoleEntryLevelSystemCall(
            OperationProvidedRole operationProvidedRole) {
        IllegalArgumentException.throwIfNull(operationProvidedRole, "The given Provided Role must not be null");
        this.opRole = operationProvidedRole;
        return this;
    }

    /**
     * Adds an priority to the Entry Level System Call.
     *
     * @param priority
     * @return the current entry level system call in the making
     */
    public EntryLevelSystemCallCreator withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.parameter.VariableUsage Output Parameter} to the
     * entry level system call.
     * <p>
     * Create a new variable usage by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newVariableUsage(String variableReference)</code>
     * or<code>create.newVariableUsage(String namespaceReference, String... innerReferences)</code>.
     * </p>
     *
     * @param variable
     *            usage in the making
     * @return the entry level system call in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallOutput(VariableUsageCreator outputParameterUsage) {
        IllegalArgumentException.throwIfNull(outputParameterUsage, "The given Output Variable Usage must not be null");
        this.outputParameterUsage.add(outputParameterUsage.build());
        return this;
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.parameter.VariableUsage Input Parameter} to the
     * entry level system call.
     * <p>
     * Create a new variable usage by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newVariableUsage(String variableReference)</code>
     * or<code>create.newVariableUsage(String namespaceReference, String... innerReferences)</code>.
     * </p>
     *
     * @param variable
     *            usage in the making
     * @return the entry level system call in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallInput(VariableUsageCreator inputParameterUsage) {
        IllegalArgumentException.throwIfNull(inputParameterUsage, "The given Input Variable Usage must not be null");
        this.inputParameterUsage.add(inputParameterUsage.build());
        return this;
    }

    @Override
    public EntryLevelSystemCall build() {
        EntryLevelSystemCall call = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();

        call.getOutputParameterUsages_EntryLevelSystemCall()
            .addAll(this.outputParameterUsage);
        call.getInputParameterUsages_EntryLevelSystemCall()
            .addAll(this.inputParameterUsage);

        if (this.opSignature != null) {
            call.setOperationSignature__EntryLevelSystemCall(this.opSignature);
        }

        if (this.opRole != null) {
            call.setProvidedRole_EntryLevelSystemCall(this.opRole);
        }

        call.setPriority(this.priority);

        if (this.name != null) {
            call.setEntityName(this.name);
        }
        if (this.successor != null) {
            call.setSuccessor(this.successor);
        }

        return call;
    }

    @Override
    public EntryLevelSystemCallCreator withSuccessor(ActionCreator action) {
        return (EntryLevelSystemCallCreator) super.withSuccessor(action);
    }

    @Override
    public EntryLevelSystemCallCreator withName(final String name) {
        return (EntryLevelSystemCallCreator) super.withName(name);
    }

}
