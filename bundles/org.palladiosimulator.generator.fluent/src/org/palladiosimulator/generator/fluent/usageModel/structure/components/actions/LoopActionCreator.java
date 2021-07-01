package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class LoopActionCreator extends ActionCreator {

    private PCMRandomVariable iteration;
    private ScenarioBehaviour bodyBehav;

    public LoopActionCreator addToLoopAction(ScenarioBehaviourCreator bodyBehaviour) {
        IllegalArgumentException.throwIfNull(bodyBehaviour, "The given body Behavoiur must not be null");
        this.bodyBehav = bodyBehaviour.build();
        return this;
    }

    public LoopActionCreator addToLoopAction(String iteration) {
        IllegalArgumentException.throwIfNull(iteration, "The given Iteration must not be null");
        this.iteration = CoreFactory.eINSTANCE.createPCMRandomVariable();
        this.iteration.setSpecification(iteration);
        return this;
    }

    @Override
    public Loop build() {
        Loop loop = UsagemodelFactory.eINSTANCE.createLoop();

        if (bodyBehav != null) {
            loop.setBodyBehaviour_Loop(bodyBehav);
        }
        if (iteration != null) {
            loop.setLoopIteration_Loop(iteration);
        }

        if (name != null) {
            loop.setEntityName(name);
        }
        if (predecessor != null) {
            loop.setPredecessor(predecessor);
        }
        if (successor != null) {
            loop.setSuccessor(successor);
        }
        return loop;
    }

    @Override
    public LoopActionCreator withPredecessor(ActionCreator action) {
        return (LoopActionCreator) super.withPredecessor(action);
    }

    @Override
    public LoopActionCreator withSuccessor(ActionCreator action) {
        return (LoopActionCreator) super.withSuccessor(action);
    }

    @Override
    public LoopActionCreator withName(final String name) {
        return (LoopActionCreator) super.withName(name);
    }

}
