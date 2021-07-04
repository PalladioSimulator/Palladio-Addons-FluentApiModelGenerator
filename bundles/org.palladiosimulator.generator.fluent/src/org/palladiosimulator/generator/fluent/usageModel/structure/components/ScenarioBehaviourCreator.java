package org.palladiosimulator.generator.fluent.usageModel.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.actions.ActionCreator;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class ScenarioBehaviourCreator extends UsageModelEntity {

    private List<AbstractUserAction> actions;

    public ScenarioBehaviourCreator() {
        this.actions = new ArrayList<>();
    }

    public ScenarioBehaviourCreator addAction(ActionCreator action) {
        IllegalArgumentException.throwIfNull(action, "The given Action must not be null");
        AbstractUserAction usrAction = action.build();
        actions.add(usrAction);
        return this;
    }

    @Override
    public ScenarioBehaviour build() {
        final ScenarioBehaviour scenBeh = UsagemodelFactory.eINSTANCE.createUsageScenario()
                .getScenarioBehaviour_UsageScenario();

        scenBeh.getActions_ScenarioBehaviour().addAll(actions); // da sind andere drinnen/eingeschlossen
        return scenBeh;
    }

    @Override
    public ScenarioBehaviourCreator withName(final String name) {
        return (ScenarioBehaviourCreator) super.withName(name);
    }

}