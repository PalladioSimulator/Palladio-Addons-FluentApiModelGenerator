package org.palladiosimulator.generator.fluent.usagemodel.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UserData;

public class UserDataCreator extends UsageModelEntity {

    private AssemblyContext assemblyContext;
    private final List<VariableUsage> variableUsage;

    @Deprecated
    public UserDataCreator(UsageModelCreator usgModelCreator) {
        this.usageModelCreator = usgModelCreator;
        variableUsage = new ArrayList<>();
    }
    
    public UserDataCreator(UsageModelCreator usgModelCreator, AssemblyContext context) {
        this.usageModelCreator = usgModelCreator;
        variableUsage = new ArrayList<>();
        withAssemblyContext(context);
    }

    public UserDataCreator addToUserData(VariableUsageCreator var) {
        IllegalArgumentException.throwIfNull(var, "The given Variable must not be null");
        variableUsage.add(var.build());
        return this;
    }

    @Deprecated
    public UserDataCreator withAssemblyContext(AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        assemblyContext = context;
        return this;
    }
    
    @Deprecated
    public UserDataCreator withAssemblyContext(String name) {
        AssemblyContext context = usageModelCreator.getAssemblyContextByName(name);
        return withAssemblyContext(context);
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
