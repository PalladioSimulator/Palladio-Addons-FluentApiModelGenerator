package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.Loop Loop}. It is used to
 * create the '<em><b>Loop</b></em>' object step-by-step, i.e. '<em><b>LoopActionCreator</b></em>'
 * objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.Loop
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class LoopActionCreator extends ActionCreator {

    private PCMRandomVariable iteration;
    private ScenarioBehaviour bodyBehav;

    public LoopActionCreator(final String iteration, final ScenarioBehaviourCreator bodyBehaviour) {
        addToLoopAction(iteration);
        addToLoopAction(bodyBehaviour);
    }

    private LoopActionCreator addToLoopAction(final ScenarioBehaviourCreator bodyBehaviour) {
        IllegalArgumentException.throwIfNull(bodyBehaviour, "The given body Behavoiur must not be null");
        this.bodyBehav = bodyBehaviour.build();
        return this;
    }

    private LoopActionCreator addToLoopAction(final String iteration) {
        IllegalArgumentException.throwIfNull(iteration, "The given Iteration must not be null");
        this.iteration = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.iteration.setSpecification(iteration);
        return this;
    }

    @Override
    public Loop build() {
        final Loop loop = UsagemodelFactory.eINSTANCE.createLoop();

        if (this.bodyBehav != null) {
            loop.setBodyBehaviour_Loop(this.bodyBehav);
        }
        if (this.iteration != null) {
            loop.setLoopIteration_Loop(this.iteration);
        }

        if (this.name != null) {
            loop.setEntityName(this.name);
        }
        if (this.successor != null) {
            loop.setSuccessor(this.successor);
        }
        return loop;
    }

    @Override
    public LoopActionCreator withSuccessor(final ActionCreator action) {
        return (LoopActionCreator) super.withSuccessor(action);
    }

    @Override
    public LoopActionCreator withName(final String name) {
        return (LoopActionCreator) super.withName(name);
    }

}
