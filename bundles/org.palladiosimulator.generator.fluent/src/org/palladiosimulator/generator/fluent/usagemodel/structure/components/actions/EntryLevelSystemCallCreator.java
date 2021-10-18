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

    private final List<VariableUsage> outputParameterUsage;
    private final List<VariableUsage> inputParameterUsage;
    private int priority;

    private OperationSignature opSignature;
    private OperationProvidedRole opRole;

    /**
     * Instantiates a new entry level system call creator.
     *
     * <p>
     * An EntryLevelSystemCall models the call to a service provided by a system. Therefore,
     * anEntryLevelSystemCall references a ProvidedRole of a PCM System, from which the called
     * interface and the providing component within the system can be derived, and a Signature
     * specifying the called service. Notice, that the usage model does not permit the domain expert
     * to model calls directly to components, but only to system roles. This decouples the System
     * structure(i.e., the component-based software architecture model and its allocation) from the
     * UsageModeland the software architect can change the System (e.g., include new components,
     * remove existing components, or change their wiring or allocation) independently from the
     * domain expert, if the system provided roles are not affected. EntryLevelSystemCalls may
     * include a set of input parameter characterisations and a set of output parameter
     * characterisations (as described in the pcm::parameters package). However, the random
     * variables characterising the input parameters like NUMBER_OF_ELEMENTS can not depend on other
     * variables in the usage model. They have to be composed from literals only including literals
     * describing random variables having a certain fixed distribution.
     * </p>
     *
     * @param operationSignature
     *            the operation signature
     * @param operationProvidedRole
     *            the operation provided role
     * 
     * @see org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     * @see org.palladiosimulator.pcm.repository.OperationSignature
     */
    public EntryLevelSystemCallCreator(final OperationSignature operationSignature,
            final OperationProvidedRole operationProvidedRole) {
        this.outputParameterUsage = new ArrayList<>();
        this.inputParameterUsage = new ArrayList<>();
        this.priority = 0;
        withOperationSignatureEntryLevelSystemCall(operationSignature);
        withProvidedRoleEntryLevelSystemCall(operationProvidedRole);
    }

    private EntryLevelSystemCallCreator withOperationSignatureEntryLevelSystemCall(
            final OperationSignature operationSignature) {
        IllegalArgumentException.throwIfNull(operationSignature, "The given Operation Signature must not be null");
        this.opSignature = operationSignature;
        return this;
    }

    private EntryLevelSystemCallCreator withProvidedRoleEntryLevelSystemCall(
            final OperationProvidedRole operationProvidedRole) {
        IllegalArgumentException.throwIfNull(operationProvidedRole, "The given Provided Role must not be null");
        this.opRole = operationProvidedRole;
        return this;
    }

    /**
     * Adds an priority to the Entry Level System Call.
     *
     * @param priority
     *            the priority
     * @return the current entry level system call in the making
     */
    public EntryLevelSystemCallCreator withPriority(final int priority) {
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
     * @param outputParameterUsage
     *            variable usage in the making
     * @return the entry level system call in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallOutput(
            final VariableUsageCreator outputParameterUsage) {
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
     * @param inputParameterUsage
     *            variable usage in the making
     * @return the entry level system call in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallInput(final VariableUsageCreator inputParameterUsage) {
        IllegalArgumentException.throwIfNull(inputParameterUsage, "The given Input Variable Usage must not be null");
        this.inputParameterUsage.add(inputParameterUsage.build());
        return this;
    }

    @Override
    public EntryLevelSystemCall build() {
        final EntryLevelSystemCall call = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();

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
    public EntryLevelSystemCallCreator withSuccessor(final ActionCreator action) {
        return (EntryLevelSystemCallCreator) super.withSuccessor(action);
    }

    @Override
    public EntryLevelSystemCallCreator withName(final String name) {
        return (EntryLevelSystemCallCreator) super.withName(name);
    }

}
