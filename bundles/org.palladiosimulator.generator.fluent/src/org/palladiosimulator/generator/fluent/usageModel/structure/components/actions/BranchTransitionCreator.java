package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class BranchTransitionCreator extends UsageModelEntity {

    private double probability;
    private ScenarioBehaviour branchedBeh;

    public BranchTransitionCreator() {
        this.probability = 0.0; // default value
    }

    @Override
    protected BranchTransition build() {
        BranchTransition branchT = UsagemodelFactory.eINSTANCE.createBranchTransition();

        branchT.setBranchProbability(probability);

        if (branchedBeh != null) {
            branchT.setBranchedBehaviour_BranchTransition(branchedBeh);
        }
        return branchT;
    }

    public BranchTransitionCreator addToBranchTransition(ScenarioBehaviourCreator branchedBehaviour) {
        IllegalArgumentException.throwIfNull(branchedBehaviour, "The branched Behavoiur must not be null");
        branchedBeh = branchedBehaviour.build();
        return this;
    }

    public BranchTransitionCreator withProbability(double probability) {
        this.probability = probability;
        return this;
    }

}
