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

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.UserData
 * User Data}. It is used to create the '<em><b>User Data</b></em>' object
 * step-by-step, i.e. '<em><b>UserDataCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.UserData
 */
public class UserDataCreator extends UsageModelEntity {

    private AssemblyContext assemblyContext;
    private final List<VariableUsage> variableUsage;

    public UserDataCreator(UsageModelCreator usgModelCreator, AssemblyContext context) {
        this.usageModelCreator = usgModelCreator;
        variableUsage = new ArrayList<>();
        withAssemblyContext(context);
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.parameter.VariableUsage Variable
     * Usage} to the user data.
     * <p>
     * Variable usages are used to characterise variables like input and output
     * variables or component parameters. They contain the specification of the
     * variable as VariableCharacterisation and also refer to the name of the
     * characterised variable in its namedReference association. Note that it was an
     * explicit design decision to refer to variable names instead of the actual
     * variables (i.e., by refering to Parameter class). It eased the writing of
     * transformations (DSolver as well as SimuCom) but put some complexity in the
     * frontend for entering the variable usages.
     * </p>
     * <p>
     * Create a new variable usage by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newVariableUsage(String variableReference)</code> or
     * <code>create.newVariableUsage(String namespaceReference, String... innerReferences)</code>
     * </p>
     *
     * @param variable usage in the making
     * @return the user data in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public UserDataCreator addToUserData(VariableUsageCreator var) {
        IllegalArgumentException.throwIfNull(var, "The given Variable must not be null");
        variableUsage.add(var.build());
        return this;
    }

    private UserDataCreator withAssemblyContext(AssemblyContext context) {
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
