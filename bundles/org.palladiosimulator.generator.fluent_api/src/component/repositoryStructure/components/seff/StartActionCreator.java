package component.repositoryStructure.components.seff;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;

import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.seff.StartAction
 * StartAction}. It is used to create the '<em><b>StartAction</b></em>' object
 * step-by-step, i.e. '<em><b>StartActionCreator</b></em>' objects are of
 * intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.StartAction
 */
public class StartActionCreator extends GeneralAction {

    protected StartActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public StartActionCreator withName(final String name) {
        return (StartActionCreator) super.withName(name);
    }

    @Override
    public StartActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (StartActionCreator) super.withResourceDemand(specificationStochasticExpression, processingResource);
    }

    @Override
    public StartActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StartActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    public StartActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (StartActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature, requiredRole,
                variableUsages);
    }

    @Override
    protected StartAction build() {
        final StartAction action = SeffFactory.eINSTANCE.createStartAction();
        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);
        return action;
    }

}
