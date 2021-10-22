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
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.UserData User Data}. It is
 * used to create the '<em><b>User Data</b></em>' object step-by-step, i.e.
 * '<em><b>UserDataCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.UserData
 */
public class UserDataCreator extends UsageModelEntity {

    private AssemblyContext assemblyContext;
    private final List<VariableUsage> variableUsage;

    /**
     * Instantiates a new user data creator.
     *
     * <p>
     * UserData characterises data used in specific assembly contexts in the system. This data is
     * the same for all UsageScenarios, i.e.,multiple users accessing the same components access the
     * same data. This UserData refers to component parameters of the system publicized by the
     * software architect (see pcm::parameters package). The domain expert characterises the values
     * of component parameters related to business concepts (e.g., user specific data,data specific
     * for a business domain), whereas the software architect characterises the values of component
     * parameters related to technical concepts (e.g., size of caches, size of a thread pool,
     * configuration data,etc.). One UserData instance includes all parameter characterisation for
     * the annotated entity.
     * </p>
     *
     * @param usgModelCreator
     *            the usage model creator
     * @param context
     *            the assembly context
     *
     * @see org.palladiosimulator.pcm.usagemodel.UserData
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     *
     */
    public UserDataCreator(final UsageModelCreator usgModelCreator, final AssemblyContext context) {
        this.usageModelCreator = usgModelCreator;
        this.variableUsage = new ArrayList<>();
        this.withAssemblyContext(context);
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.parameter.VariableUsage Variable Usage} to the user
     * data.
     * <p>
     * Variable usages are used to characterise variables like input and output variables or
     * component parameters. They contain the specification of the variable as
     * VariableCharacterisation and also refer to the name of the characterised variable in its
     * namedReference association. Note that it was an explicit design decision to refer to variable
     * names instead of the actual variables (i.e., by refering to Parameter class). It eased the
     * writing of transformations (DSolver as well as SimuCom) but put some complexity in the
     * frontend for entering the variable usages.
     * </p>
     * <p>
     * Create a new variable usage by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newVariableUsage(String variableReference)</code> or
     * <code>create.newVariableUsage(String namespaceReference, String... innerReferences)</code>
     * </p>
     *
     * @param variable
     *            usage in the making
     * @return the user data in the making
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public UserDataCreator addToUserData(final VariableUsageCreator variable) {
        IllegalArgumentException.throwIfNull(variable, "The given Variable must not be null");
        this.variableUsage.add(variable.build());
        return this;
    }

    private UserDataCreator withAssemblyContext(final AssemblyContext context) {
        IllegalArgumentException.throwIfNull(context, "The given AssemblyContext must not be null.");
        this.assemblyContext = context;
        return this;
    }

    @Override
    public UserData build() {
        final UserData usrData = UsagemodelFactory.eINSTANCE.createUserData();
        if (this.assemblyContext != null) {
            usrData.setAssemblyContext_userData(this.assemblyContext);
        }
        usrData.getUserDataParameterUsages_UserData()
            .addAll(this.variableUsage);
        return usrData;
    }

}
