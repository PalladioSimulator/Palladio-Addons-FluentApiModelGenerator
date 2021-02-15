package component.repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.apiControlFlowInterfaces.seff.Seff;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.LoopAction LoopAction}. It is used
 * to create the '<em><b>LoopAction</b></em>' object step-by-step, i.e.
 * '<em><b>LoopActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.LoopAction
 */
public class LoopActionCreator extends GeneralAction {

    private PCMRandomVariable iterationCount;
    private Seff loopBody;

    protected LoopActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public LoopActionCreator withName(final String name) {
        return (LoopActionCreator) super.withName(name);
    }

    /**
     * Specifies the number of repetitions the inner ResourceDemandingBehaviour is executed.
     * <p>
     * The number of repetitions is specified by a random variable evaluating to integer or an
     * IntPMF. The number of iterations specified by the random variable always needs to be bounded,
     * i.e., the probabilities in an IntPMF for iteration numbers above a certain threshold must be
     * zero. Otherwise, it would be possible that certain requests do not terminate, which would
     * complicate performance analyses. The stochastic expression defining the iteration random
     * variable may include references to input or component parameters to model dependencies
     * between the usage profile and the number of loop iterations.
     * </p>
     *
     * @param iterationCountStochasticExpression
     * @return this loop action in the making
     * @see org.palladiosimulator.pcm.seff.LoopAction
     */
    public LoopActionCreator withIterationCount(final String iterationCountStochasticExpression) {
        Objects.requireNonNull(iterationCountStochasticExpression,
                "iterationCount_stochasticExpression must not be null");
        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(iterationCountStochasticExpression);
        this.iterationCount = rand;
        return this;
    }

    /**
     * Specifies the inner ResourceDemandingBehaviour representing the loop body.
     * <p>
     * Notice, that loop actions should only be modeled if the loop body contains either external
     * service calls or resource demands directed at special resources. Otherwise, control flow
     * loops in component behaviour should be abstracted by subsuming them in InternalAction, which
     * combine a number of instructions.
     * </p>
     *
     * @param loopBody
     * @return this loop action in the making
     */
    public LoopActionCreator withLoopBody(final Seff loopBody) {
        Objects.requireNonNull(loopBody, "loopBody must not be null");
        this.loopBody = loopBody;
        return this;
    }

    @Override
    public LoopActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (LoopActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public LoopActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (LoopActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public LoopActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (LoopActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected LoopAction build() {
        final LoopAction action = SeffFactory.eINSTANCE.createLoopAction();
        action.setIterationCount_LoopAction(this.iterationCount);

        if (this.loopBody != null) {
            final ResourceDemandingSEFF build = this.loopBody.buildRDSeff();
            if (build.getDescribedService__SEFF() == null && build.getSeffTypeID() == null
                    && build.getResourceDemandingInternalBehaviours()
                        .isEmpty()) {
                action.setBodyBehaviour_Loop(this.loopBody.buildBehaviour());
            } else {
                action.setBodyBehaviour_Loop(build);
            }
        }

        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);

        return action;
    }
}
