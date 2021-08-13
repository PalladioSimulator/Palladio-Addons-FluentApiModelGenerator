package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.ScenarioBehaviourCreator;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.usagemodel.BranchTransition
 * BranchTransition}. It is used to create the
 * '<em><b>BranchTransition</b></em>' object step-by-step, i.e.
 * '<em><b>BranchTransitionCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.BranchTransition
 */
public class BranchTransitionCreator extends UsageModelEntity {

    private double probability;
    private ScenarioBehaviour branchedBeh;

    public BranchTransitionCreator(ScenarioBehaviourCreator branchedBehaviour) {
        this.probability = 0.0; // default value
        addToBranchTransition(branchedBehaviour);
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

    private BranchTransitionCreator addToBranchTransition(ScenarioBehaviourCreator branchedBehaviour) {
        IllegalArgumentException.throwIfNull(branchedBehaviour, "The branched Behavoiur must not be null");
        branchedBeh = branchedBehaviour.build();
        return this;
    }

    /**
     * Adds an probability to the branch transition
     *
     * @param probability of the branch
     * @return the current branch transition in the making
     */
    public BranchTransitionCreator withProbability(double probability) {
        this.probability = probability;
        return this;
    }

}
