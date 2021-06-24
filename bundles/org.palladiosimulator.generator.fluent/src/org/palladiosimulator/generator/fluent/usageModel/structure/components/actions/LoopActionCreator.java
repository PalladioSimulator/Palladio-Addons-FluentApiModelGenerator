package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class LoopActionCreator extends ActionCreator {

    @Override
    public Loop build() {
        Loop loop = UsagemodelFactory.eINSTANCE.createLoop();

        //TODO
        loop.setBodyBehaviour_Loop(null);
        loop.setLoopIteration_Loop(null);
        
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


}
