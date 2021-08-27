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

    private final List<AbstractUserAction> actions;

    /**
     * Instantiates a new scenario behaviour creator.
     *
     * <p>
     * A ScenarioBehaviour specifies possible sequences of executing services provided by the
     * system.It contains a set of AbstractUserActions, each referencing a predecessor and successor
     * (except the first and last action), thereby forming a sequence of actions.See the
     * AbstractAction documentation for why it is advantageous to model control flow in this way, as
     * the same principle is used in the RDSEFF language.Concrete user actions of the usage model
     * are:- Branch- Loop- EntryLevelSystemCall- Delay- Start- Stop So far, ScenarioBehaviours do not
     * include forks in the user flow (i.e., splitting the flow with an AND semantic), as it is
     * assumed that users always act sequentially. As there are no random variables depending on
     * other variables in the usage model, there are no equivalent actions to
     * GuardedBranchTransitions or CollectionIteratorActions
     * </p>
     * 
     * @param usgModelCreator
     *            the usage model creator
     * 
     * @see org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour
     */
    public ScenarioBehaviourCreator(final UsageModelCreator usgModelCreator) {
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
    public ScenarioBehaviourCreator addToScenarioBehaviour(final ActionCreator action) {
        IllegalArgumentException.throwIfNull(action, "The given Action must not be null");
        final AbstractUserAction usrAction = action.build();

        createActionFlow(usrAction);
        return this;
    }

    private void addStart(final AbstractUserAction first) {
        // test if current is already Start
        if (first instanceof Start) {
            return;
        }

        final ActionCreator startCreator = new StartActionCreator();
        final AbstractUserAction start = startCreator.build();
        start.setSuccessor(first);
        this.actions.add(start);

    }

    private void addStop(final AbstractUserAction last) {
        // test if last is already Stop
        if (last instanceof Stop) {
            return;
        }

        final ActionCreator stopCreator = new StopActionCreator();
        final AbstractUserAction stop = stopCreator.build();
        stop.setPredecessor(last);
        this.actions.add(stop);
    }

    private void createActionFlow(final AbstractUserAction start) {
        // use only successor, predecessor then happens from itself
        final List<AbstractUserAction> flow = new ArrayList<>();
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
        final ActionCreator startCreator = new StartActionCreator();
        final AbstractUserAction start = startCreator.build();
        final ActionCreator stopCreator = new StopActionCreator();
        final AbstractUserAction stop = stopCreator.build();
        start.setSuccessor(stop);
        this.actions.add(start);
        this.actions.add(stop);
    }

    @Override
    public ScenarioBehaviour build() {
        final ScenarioBehaviour scenBeh = UsagemodelFactory.eINSTANCE.createScenarioBehaviour();
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
