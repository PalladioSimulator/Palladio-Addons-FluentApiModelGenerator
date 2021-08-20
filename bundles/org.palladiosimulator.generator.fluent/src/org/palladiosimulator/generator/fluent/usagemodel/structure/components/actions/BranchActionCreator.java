package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.Branch Branch}. It is used to
 * create the '<em><b>Branch</b></em>' object step-by-step, i.e.
 * '<em><b>BranchActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.Branch
 * @see org.palladiosimulator.pcm.usagemodel.AbstractUserAction
 */
public class BranchActionCreator extends ActionCreator {

    private List<BranchTransition> transitions = new ArrayList<BranchTransition>();

    public BranchActionCreator() {
    }

    /**
     * Adds an {@link org.palladiosimulator.pcm.usagemodel.BranchTransition Branch Transition} to
     * the Branch.
     * <p>
     * The probability of all added BranchTransitions need to sum up to 1.
     * </p>
     * <p>
     * Create a new branch transition by using the
     * org.palladiosimulator.generator.fluent.usagemodel.factory, i.e.
     * <code>create.newBranchTransition(ScenarioBehaviourCreator branchedBehaviour)</code>.
     * </p>
     *
     * @param branch
     *            transition in the making
     * @return the current branch action in the making
     * @see org.palladiosimulator.pcm.usagemodel.BranchTransition
     */
    public BranchActionCreator addToBranchAction(BranchTransitionCreator branchTransition) {
        IllegalArgumentException.throwIfNull(branchTransition, "The given Branch Transition must not be null");
        this.transitions.add(branchTransition.build());
        return this;
    }

    @Override
    public Branch build() {
        Branch b = UsagemodelFactory.eINSTANCE.createBranch();

        b.getBranchTransitions_Branch()
            .addAll(this.transitions);

        if (this.name != null) {
            b.setEntityName(this.name);
        }
        if (this.successor != null) {
            b.setSuccessor(this.successor);
        }
        return b;
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
