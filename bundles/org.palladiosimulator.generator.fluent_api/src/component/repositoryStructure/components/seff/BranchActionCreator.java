package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.apiControlFlowInterfaces.seff.Seff;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.BranchAction
 * BranchAction}. It is used to create the '<em><b>BranchAction</b></em>' object
 * step-by-step, i.e. '<em><b>BranchActionCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.BranchAction
 */
public class BranchActionCreator extends GeneralAction {

    private final List<AbstractBranchTransition> branches;

    protected BranchActionCreator(final SeffCreator seff) {
        this.seff = seff;
        branches = new ArrayList<>();
    }

    @Override
    public BranchActionCreator withName(final String name) {
        return (BranchActionCreator) super.withName(name);
    }

    /**
     * Adds a guarded branch transition to this action's list of branches.
     * <p>
     * A {@link org.palladiosimulator.pcm.seff.GuardedBranchTransition Guarded
     * Branch Transition} provides a link between a BranchAction and a nested
     * ResourceDemandingBehaviour, which includes the actions executed inside the
     * branch. It uses a guard, i.e. a boolean expression specified by a
     * RandomVariable, to determine whether the transition is chosen. If the guard
     * evaluates to true, the branch is chosen, otherwise if the guard evaluates to
     * false another branch transition must be chosen.
     * </p>
     *
     * @param branchConditionStochasticExpression boolean expression, condition of
     *                                            the branch
     * @param branchActions                       nested resource demanding
     *                                            behaviour
     * @param name                                of the branch
     * @return this branch action in the making
     */
    public BranchActionCreator withGuardedBranchTransition(final String branchConditionStochasticExpression,
            final Seff branchActions, final String name) {
        Objects.requireNonNull(branchConditionStochasticExpression,
                "branchCondition_stochasticExpression must not be null");
        Objects.requireNonNull(branchActions, "branchActions must not be null");
        final GuardedBranchTransition branch = SeffFactory.eINSTANCE.createGuardedBranchTransition();

        if (name != null) {
            branch.setEntityName(name);
        }

        final ResourceDemandingSEFF build = branchActions.buildRDSeff();
        if ((build.getDescribedService__SEFF() == null) && (build.getSeffTypeID() == null)
                && build.getResourceDemandingInternalBehaviours().isEmpty()) {
            final ResourceDemandingBehaviour branchBody = branchActions.buildBehaviour();
            branch.setBranchBehaviour_BranchTransition(branchBody);
        } else {
            final ResourceDemandingSEFF branchBody = branchActions.buildRDSeff();
            branch.setBranchBehaviour_BranchTransition(branchBody);
        }

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(branchConditionStochasticExpression);
        branch.setBranchCondition_GuardedBranchTransition(rand);

        branches.add(branch);
        return this;
    }

    /**
     * Adds a guarded branch transition to this action's list of branches.
     * <p>
     * A {@link org.palladiosimulator.pcm.seff.GuardedBranchTransition Guarded
     * Branch Transition} provides a link between a BranchAction and a nested
     * ResourceDemandingBehaviour, which includes the actions executed inside the
     * branch. It uses a guard, i.e. a boolean expression specified by a
     * RandomVariable, to determine whether the transition is chosen. If the guard
     * evaluates to true, the branch is chosen, otherwise if the guard evaluates to
     * false another branch transition must be chosen.
     * </p>
     *
     * @param branchConditionStochasticExpression boolean expression, condition of
     *                                            the branch
     * @param branchActions                       nested resource demanding
     *                                            behaviour
     * @return this branch action in the making
     */
    public BranchActionCreator withGuardedBranchTransition(final String branchConditionStochasticExpression,
            final SeffCreator branchActions) {
        return this.withGuardedBranchTransition(branchConditionStochasticExpression, branchActions, null);
    }

    /**
     * Adds a probabilistic branch transition to this action's list of branches.
     * <p>
     * A {@link org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition
     * Probabilistic Branch Transition} provides a link between a BranchAction and a
     * nested ResourceDemandingBehaviour, which includes the actions executed inside
     * the branch. But instead of using a guard, it specifies a branching
     * probability without parameter dependencies.
     * </p>
     *
     * @param branchProbability
     * @param branchActions     nested resource demanding behaviour
     * @param name
     * @return this branch action in the making
     */
    public BranchActionCreator withProbabilisticBranchTransition(final double branchProbability,
            final SeffCreator branchActions, final String name) {
        Objects.requireNonNull(branchActions, "branchActions must not be null");

        final ProbabilisticBranchTransition branch = SeffFactory.eINSTANCE.createProbabilisticBranchTransition();

        if (name != null) {
            branch.setEntityName(name);
        }

        final ResourceDemandingSEFF build = branchActions.buildRDSeff();
        if ((build.getDescribedService__SEFF() == null) && (build.getSeffTypeID() == null)
                && build.getResourceDemandingInternalBehaviours().isEmpty()) {
            branch.setBranchBehaviour_BranchTransition(branchActions.buildBehaviour());
        } else {
            branch.setBranchBehaviour_BranchTransition(build);
        }

        branch.setBranchProbability(branchProbability);

        branches.add(branch);
        return this;
    }

    /**
     * Adds a probabilistic branch transition to this action's list of branches.
     * <p>
     * A {@link org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition
     * Probabilistic Branch Transition} provides a link between a BranchAction and a
     * nested ResourceDemandingBehaviour, which includes the actions executed inside
     * the branch. But instead of using a guard, it specifies a branching
     * probability without parameter dependencies.
     * </p>
     *
     * @param branchProbability
     * @param branchActions     nested resource demanding behaviour
     * @return this branch action in the making
     */
    public BranchActionCreator withProbabilisticBranchTransition(final Double branchProbability,
            final SeffCreator branchActions) {
        return this.withProbabilisticBranchTransition(branchProbability, branchActions, null);
    }

    @Override
    public BranchActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (BranchActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public BranchActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (BranchActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public BranchActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (BranchActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected BranchAction build() {
        final BranchAction action = SeffFactory.eINSTANCE.createBranchAction();
        action.getBranches_Branch().addAll(branches);

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);

        return action;
    }
}
