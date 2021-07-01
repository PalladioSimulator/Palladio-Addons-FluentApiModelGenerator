package org.palladiosimulator.generator.fluent.usageModel.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UserData;

public class UserDataCreator extends UsageModelEntity {

    private AssemblyContext assemblyContext;
    private final List<VariableUsage> variableUsage;

    public UserDataCreator(final UsageModelCreator usgModelCreator) {
        this.usageModelCreator = usgModelCreator;
        variableUsage = new ArrayList<>();
    }

    // TODO: evtl. VariableUsageCreator verschieben in shared aus
    // repositoryStructure.components
    public UserDataCreator addToUserData(VariableUsageCreator var) {
        IllegalArgumentException.throwIfNull(var, "The given Variable must not be null");
        variableUsage.add(var.build());
        return this;
    }

    public UserDataCreator withAssemblyContext(AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        assemblyContext = context;
        return this;
    }

    @Override
    public UserData build() {
        final UserData usrData = UsagemodelFactory.eINSTANCE.createUserData();
        if (assemblyContext != null) {
            usrData.setAssemblyContext_userData(assemblyContext);
        }
        usrData.getUserDataParameterUsages_UserData().addAll(variableUsage);
        return usrData;
    }

}
