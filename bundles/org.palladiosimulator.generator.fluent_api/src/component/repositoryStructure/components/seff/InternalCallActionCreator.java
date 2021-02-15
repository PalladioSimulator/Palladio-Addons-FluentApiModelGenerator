package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.apiControlFlowInterfaces.seff.InternalSeff;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.InternalCallAction
 * InternalCallAction}. It is used to create the '<em><b>InternalCallAction</b></em>' object
 * step-by-step, i.e. '<em><b>InternalCallActionCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.InternalCallAction
 */
public class InternalCallActionCreator extends GeneralAction {

    private final List<VariableUsage> inputVariableUsages;
    private InternalSeff internalSeff;

    protected InternalCallActionCreator(final SeffCreator seff) {
        this.seff = seff;
        this.demands = new ArrayList<>();
        this.infrastructureCalls = new ArrayList<>();
        this.resourceCalls = new ArrayList<>();
        this.inputVariableUsages = new ArrayList<>();
    }

    @Override
    public InternalCallActionCreator withName(final String name) {
        return (InternalCallActionCreator) super.withName(name);
    }

    /**
     * Adds the <code>variableUsage</code> to this action's list of input variable usages.
     *
     * @param variableUsage
     * @return this internal call action in the making
     * @see component.factory.FluentRepositoryFactory#newVariableUsage()
     */
    public InternalCallActionCreator withInputVaribleUsage(final VariableUsageCreator variableUsage) {
        Objects.requireNonNull(variableUsage, "variableUsage must not be null");
        this.inputVariableUsages.add(variableUsage.build());
        return this;
    }

    /**
     * Specifies the internal behaviour that is called on by this action.
     *
     * @param internalBehaviour
     * @return this internal call action in the making
     * @see component.factory.FluentRepositoryFactory#newInternalBehaviour()
     */
    public InternalCallActionCreator withInternalBehaviour(final InternalSeff internalBehaviour) {
        Objects.requireNonNull(internalBehaviour, "internalBehaviour must not be null");
        this.internalSeff = internalBehaviour;
        return this;
    }

    @Override
    public InternalCallActionCreator withResourceDemand(final String specification_stochasticExpression,
            final ProcessingResource processingResource) {
        return (InternalCallActionCreator) super.withResourceDemand(specification_stochasticExpression,
                processingResource);
    }

    @Override
    public InternalCallActionCreator withInfrastructureCall(final String numberOfCalls_stochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalCallActionCreator) super.withInfrastructureCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public InternalCallActionCreator withResourceCall(final String numberOfCalls_stochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (InternalCallActionCreator) super.withResourceCall(numberOfCalls_stochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected InternalCallAction build() {
        final InternalCallAction action = SeffFactory.eINSTANCE.createInternalCallAction();

        if (this.internalSeff != null) {
            final ResourceDemandingInternalBehaviour internal = this.internalSeff.buildInternalBehaviour();
            action.setCalledResourceDemandingInternalBehaviour(internal);
        }

        action.getInputVariableUsages__CallAction()
            .addAll(this.inputVariableUsages);

        action.getResourceDemand_Action()
            .addAll(this.demands);
        action.getInfrastructureCall__Action()
            .addAll(this.infrastructureCalls);
        action.getResourceCall__Action()
            .addAll(this.resourceCalls);

        return action;
    }

}
