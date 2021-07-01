package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class BranchActionCreator extends ActionCreator {

    private List<BranchTransition> transitions = new ArrayList<BranchTransition>();

    public BranchActionCreator addToBranchAction(BranchTransitionCreator branchTransition) {
        IllegalArgumentException.throwIfNull(branchTransition, "The given Branch Transition must not be null");
        this.transitions.add(branchTransition.build());
        return this;
    }

    @Override
    public Branch build() {
        Branch b = UsagemodelFactory.eINSTANCE.createBranch();

        b.getBranchTransitions_Branch().addAll(transitions);

        if (name != null) {
            b.setEntityName(name);
        }
        if (predecessor != null) {
            b.setPredecessor(predecessor);
        }
        if (successor != null) {
            b.setSuccessor(successor);
        }
        return b;
    }

    @Override
    public BranchActionCreator withPredecessor(ActionCreator action) {
        return (BranchActionCreator) super.withPredecessor(action);
    }

    @Override
    public BranchActionCreator withSuccessor(ActionCreator action) {
        return (BranchActionCreator) super.withSuccessor(action);
    }

    @Override
    public BranchActionCreator withName(final String name) {
        return (BranchActionCreator) super.withName(name);
    }
}
