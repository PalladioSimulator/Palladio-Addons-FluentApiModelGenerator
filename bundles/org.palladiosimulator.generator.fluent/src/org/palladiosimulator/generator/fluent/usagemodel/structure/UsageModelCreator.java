package org.palladiosimulator.generator.fluent.usagemodel.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModelAddition;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.WorkloadCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UserData;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.usagemodel.UsageModel Usage Model}. It is
 * used to create the '<em><b>Usage Model</b></em>' object step-by-step, i.e.
 * '<em><b>UsageModelCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.UsageModel
 */
public class UsageModelCreator extends UsageModelEntity implements IUsageModel, IUsageModelAddition {
    private final IModelValidator validator;
    private final System system;

    private final List<UsageScenario> usageScenarios;
    private final List<UserData> userDatas;

    public UsageModelCreator(System system, IModelValidator validator) {
        this.validator = validator;
        this.system = system;

        this.usageScenarios = new ArrayList<>();
        this.userDatas = new ArrayList<>();
    }

    @Override
    public UsageModel createUsageModelNow() {
        UsageModel usgModel = build();
        validator.validate(usgModel, "UsageModel");
        return usgModel;
    }

    @Override
    protected UsageModel build() {
        UsageModel usgModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        usgModel.getUserData_UsageModel().addAll(userDatas);
        usgModel.getUsageScenario_UsageModel().addAll(usageScenarios);

        return usgModel;
    }

    /**
     * Adds a {@link org.palladiosimulator.pcm.usagemodel.UserData UserData} to the
     * usage model.
     * <p>
     * UserData characterises data used in specific assembly contexts in the system.
     * This data is the same for all UsageScenarios, i.e.,multiple users accessing
     * the same components access the same data. This UserData refers to component
     * parameters of the system publicized by the software architect (see
     * pcm::parameters package). The domain expert characterises the values of
     * component parameters related to business concepts (e.g., user specific
     * data,data specific for a business domain), whereas the software architect
     * characterises the values of component parameters related to technical
     * concepts (e.g., size of caches, size of a thread pool, configuration
     * data,etc.). One UserData instance includes all parameter characterisation for
     * the annotated entity.
     * </p>
     * <p>
     * Create a new user data by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newUserData(AssemblyContext context)</code>.
     * </p>
     *
     * @param variableData in the making
     * @return the usage model in the making
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newUserData(AssemblyContext)
     * @see org.palladiosimulator.pcm.usagemodel.UserData
     */
    public IUsageModelAddition addToUsageModel(UserDataCreator userData) {
        IllegalArgumentException.throwIfNull(userData, "The given UserData must not be null");
        userDatas.add(userData.build());
        return this;
    }

    /**
     * Adds a {@link org.palladiosimulator.pcm.usagemodel.UsageScenario Usage
     * Scenario} to the usage model.
     * <p>
     * UsageScenarios are concurrently executed behaviours of users within one
     * UsageModel. It describes which services are directly invoked by users in one
     * specific use case and models the possible sequences of calling them. Each
     * UsageScenario includes a workload and a scenario behaviour.
     * </p>
     * <p>
     * Create a new usage scenario by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newUsageScenario(ScenarioBehaviourCreator scenarioBehavior, WorkloadCreator workload)</code>.
     * </p>
     *
     * @param usage scenario in the making
     * @return the usage model in the making
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newUsageScenario(ScenarioBehaviourCreator,
     *      WorkloadCreator)
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
     */
    public IUsageModelAddition addToUsageModel(UsageScenarioCreator usageScenario) {
        IllegalArgumentException.throwIfNull(usageScenario, "The given UsageScenario must not be null");
        usageScenarios.add(usageScenario.build());
        return this;
    }

    @Deprecated // TODO: move to Factory
    public OperationProvidedRole getOperationProvidedRoleByName(String name) {
        IllegalArgumentException.throwIfNull(system,
                "The referred System was not set correctly in FluentUsageModelFactory");

        OperationProvidedRole role = null;

        ProvidedRole r = system.getProvidedRoles_InterfaceProvidingEntity().stream()
                .filter(x -> x.getEntityName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ProvidedRole with name " + name + " found."));

        if (r instanceof OperationProvidedRole) {
            role = (OperationProvidedRole) r;
        }

        IllegalArgumentException.throwIfNull(role, "No OperationProvidedRole with name " + name + " found.");
        return role;
    }

    @Deprecated // TODO: move to Factory
    public AssemblyContext getAssemblyContextByName(String name) throws NoSuchElementException {
        IllegalArgumentException.throwIfNull(system,
                "The referred System was not set correctly in FluentUsageModelFactory");
        return system.getAssemblyContexts__ComposedStructure().stream().filter(x -> x.getEntityName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No AssemblyContext with name " + name + " found."));
    }

}
