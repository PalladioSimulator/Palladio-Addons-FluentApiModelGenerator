package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class EntryLevelSystemCallCreator extends ActionCreator {

    private List<VariableUsage> outputParameterUsage;
    private List<VariableUsage> inputParameterUsage;
    private int priority;

    private OperationSignature opSignature;
    private OperationProvidedRole opRole;

    public EntryLevelSystemCallCreator() {
        outputParameterUsage = new ArrayList<VariableUsage>();
        inputParameterUsage = new ArrayList<VariableUsage>();
        priority = 0;
    }

    public EntryLevelSystemCallCreator withOperationSignatureEntryLevelSystemCall(
            OperationSignature operationSignature) {
        IllegalArgumentException.throwIfNull(operationSignature, "The given Operation Signature must not be null");
        this.opSignature = operationSignature;
        return this;
    }

    public EntryLevelSystemCallCreator withProvidedRoleEntryLevelSystemCall(
            OperationProvidedRole operationProvidedRole) {
        IllegalArgumentException.throwIfNull(operationProvidedRole, "The given Provided Role must not be null");
        this.opRole = operationProvidedRole;
        return this;
    }

    public EntryLevelSystemCallCreator addToEntryLevelSystemCallOutput(VariableUsageCreator outputParameterUsage) {
        IllegalArgumentException.throwIfNull(outputParameterUsage, "The given Output Variable Usage must not be null");
        this.outputParameterUsage.add(outputParameterUsage.build());
        return this;
    }

    public EntryLevelSystemCallCreator addToEntryLevelSystemCallInput(VariableUsageCreator inputParameterUsage) {
        IllegalArgumentException.throwIfNull(inputParameterUsage, "The given Input Variable Usage must not be null");
        this.inputParameterUsage.add(inputParameterUsage.build());
        return this;
    }

    @Override
    public EntryLevelSystemCall build() {
        EntryLevelSystemCall call = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();

        call.getOutputParameterUsages_EntryLevelSystemCall().addAll(outputParameterUsage);
        call.getInputParameterUsages_EntryLevelSystemCall().addAll(inputParameterUsage);

        if (opSignature != null) {
            call.setOperationSignature__EntryLevelSystemCall(opSignature);
        }

        if (opRole != null) {
            call.setProvidedRole_EntryLevelSystemCall(opRole);
        }

        call.setPriority(priority);

        if (name != null) {
            call.setEntityName(name);
        }
        if (predecessor != null) {
            call.setPredecessor(predecessor);
        }
        if (successor != null) {
            call.setSuccessor(successor);
        }

        return call;
    }

    @Override
    public EntryLevelSystemCallCreator withPredecessor(ActionCreator action) {
        return (EntryLevelSystemCallCreator) super.withPredecessor(action);
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
