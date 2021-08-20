package org.palladiosimulator.generator.fluent.usagemodel.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.ActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StartActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StopActionCreator;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour Scenario
 * Behaviour}. It is used to create the '<em><b>Scenario Behaviour</b></em>' object step-by-step,
 * i.e. '<em><b>ScenarioBehaviourCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
 */
public class ScenarioBehaviourCreator extends UsageModelEntity {

    private List<AbstractUserAction> actions;

    public ScenarioBehaviourCreator(UsageModelCreator usgModelCreator) {
        this.actions = new ArrayList<>();
        this.usageModelCreator = usgModelCreator;
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.usagemodel.AbstractUserAction Abstract User Action}
     * to the scenario behaviour.
     * <p>
     * Concrete User Actions of the scenario behaviour are Branch, Delay, EntryLevelSystemCall,
     * Loop, Start and Stop. Set connected user actions by using
     * {@link org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.ActionCreator#withSuccessor(ActionCreator)}.
     * If a concrete start or end action is not added manually, it will be added as it's mandatory
     * to the action chain.
     * </p>
     * <p>
     * Create a new action by using the org.palladiosimulator.generator.fluent.usagemodel.factory,
     * i.e. <code>create.newBranchAction()</code>.
     * </p>
     *
     * @param action
     *            (or action chain) in the making
     * @return the scenario behaviour in the making
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newBranchAction()
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newDelayAction(String)
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newEntryLevelSystemCall(org.palladiosimulator.pcm.repository.OperationProvidedRole,
     *      org.palladiosimulator.pcm.repository.OperationSignature)
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newLoopAction(String,
     *      ScenarioBehaviourCreator)
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newStartAction()
     * @see org.palladiosimulator.generator.fluent.usagemodel.factory.FluentUsageModelFactory#newStopAction()
     * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
     */
    public ScenarioBehaviourCreator addToScenarioBehaviour(ActionCreator action) {
        IllegalArgumentException.throwIfNull(action, "The given Action must not be null");
        AbstractUserAction usrAction = action.build();

        createActionFlow(usrAction);
        return this;
    }

    private void addStart(AbstractUserAction first) {
        // test if current is already Start
        if (first instanceof Start) {
            return;
        }

        ActionCreator startCreator = new StartActionCreator();
        AbstractUserAction start = startCreator.build();
        start.setSuccessor(first);
        this.actions.add(start);

    }

    private void addStop(AbstractUserAction last) {
        // test if last is already Stop
        if (last instanceof Stop) {
            return;
        }

        ActionCreator stopCreator = new StopActionCreator();
        AbstractUserAction stop = stopCreator.build();
        stop.setPredecessor(last);
        this.actions.add(stop);
    }

    private void createActionFlow(AbstractUserAction start) {
        // use only successor, predecessor then happens from itself
        List<AbstractUserAction> flow = new ArrayList<>();
        AbstractUserAction before = start;

        AbstractUserAction current = start;

        addStart(current);

        while (current != null) {
            flow.add(current);
            before = current;
            current = current.getSuccessor();
        }
        addStop(before);
        this.actions.addAll(flow);
    }

    private void addStartStop() {
        ActionCreator startCreator = new StartActionCreator();
        AbstractUserAction start = startCreator.build();
        ActionCreator stopCreator = new StopActionCreator();
        AbstractUserAction stop = stopCreator.build();
        start.setSuccessor(stop);
        this.actions.add(start);
        this.actions.add(stop);
    }

    @Override
    public ScenarioBehaviour build() {
        ScenarioBehaviour scenBeh = UsagemodelFactory.eINSTANCE.createScenarioBehaviour();
        if (this.name != null) {
            scenBeh.setEntityName(this.name);
        }
        if (this.actions.isEmpty()) {
            // add Start and Stop Actions as this is required
            addStartStop();
        }
        scenBeh.getActions_ScenarioBehaviour()
            .addAll(this.actions);
        return scenBeh;
    }

    @Override
    public ScenarioBehaviourCreator withName(final String name) {
        return (ScenarioBehaviourCreator) super.withName(name);
    }

}
