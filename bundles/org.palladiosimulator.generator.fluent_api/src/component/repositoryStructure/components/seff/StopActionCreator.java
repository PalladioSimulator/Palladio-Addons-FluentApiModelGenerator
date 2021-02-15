package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;

import component.apiControlFlowInterfaces.seff.StopSeff;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StopAction StopAction}. It is used
 * to create the '<em><b>StopAction</b></em>' object step-by-step, i.e.
 * '<em><b>StopActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StopAction
 */
public class StopActionCreator extends GeneralAction implements StopSeff {

    private final List<ParametricResourceDemand> demands = new ArrayList<>();
    private final List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
    private final List<ResourceCall> resourceCalls = new ArrayList<>();

    protected StopActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public StopActionCreator withName(final String name) {
        return (StopActionCreator) super.withName(name);
    }

    @Override
    public SeffCreator createBehaviourNow() {
        final AbstractAction action = this.build();
        this.seff.setNext(action);
        return this.seff;
    }

    @Override
    public StopActionCreator withResourceDemand(final String specification_stochasticExpression,
            final ProcessingResource processingResource) {
        return (StopActionCreator) super.withResourceDemand(specification_stochasticExpression, processingResource);
    }

    @Override
    public StopActionCreator withInfrastructureCall(final String numberOfCalls_stochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StopActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public StopActionCreator withResourceCall(final String numberOfCalls_stochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StopActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected StopAction build() {
        final StopAction action = SeffFactory.eINSTANCE.createStopAction();
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);
        action.getResourceDemand_Action()
            .addAll(this.demands);
        return action;
    }
}
