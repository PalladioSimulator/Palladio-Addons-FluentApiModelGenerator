package component.repositoryStructure.components.seff;

import java.util.Objects;

import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

import component.apiControlFlowInterfaces.seff.Seff;
import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.seff.CollectionIteratorAction
 * CollectionIteratorAction}. It is used to create the
 * '<em><b>CollectionIteratorAction</b></em>' object step-by-step, i.e.
 * '<em><b>CollectionIteratorActionCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.CollectionIteratorAction
 */
public class CollectionIteratorActionCreator extends GeneralAction {

    private Parameter parameter;
    private Seff loopBody;

    protected CollectionIteratorActionCreator(final SeffCreator seff) {
        this.seff = seff;
    }

    @Override
    public CollectionIteratorActionCreator withName(final String name) {
        return (CollectionIteratorActionCreator) super.withName(name);
    }

    /**
     * Defines the parameter that holds the collection which is iterated over, i.e.
     * the parameter is of type CollectionDataType.
     *
     * @param parameter
     * @return this collection iterator action in the making
     */
    public CollectionIteratorActionCreator withParameter(final Parameter parameter) {
        Objects.requireNonNull(parameter, "parameter must not be null");
        this.parameter = parameter;
        return this;
    }

    /**
     * Defines the inner resource demanding behaviour that is executed for each
     * element of a collection.
     *
     * @param loopBody a nested resource demanding behaviour (seff)
     * @return this collection iterator action in the making
     */
    public CollectionIteratorActionCreator withLoopBody(final Seff loopBody) {
        Objects.requireNonNull(loopBody, "loopBody must not be null");
        this.loopBody = loopBody;
        return this;
    }

    @Override
    public CollectionIteratorActionCreator withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        return (CollectionIteratorActionCreator) super.withResourceDemand(specificationStochasticExpression,
                processingResource);
    }

    @Override
    public CollectionIteratorActionCreator withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (CollectionIteratorActionCreator) super.withInfrastructureCall(numberOfCallsStochasticExpression,
                signature, requiredRole, variableUsages);
    }

    @Override
    public CollectionIteratorActionCreator withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        return (CollectionIteratorActionCreator) super.withResourceCall(numberOfCallsStochasticExpression, signature,
                requiredRole, variableUsages);
    }

    @Override
    protected CollectionIteratorAction build() {
        final CollectionIteratorAction action = SeffFactory.eINSTANCE.createCollectionIteratorAction();

        if (parameter != null) {
            action.setParameter_CollectionIteratorAction(parameter);
        }

        if (loopBody != null) {
            final ResourceDemandingSEFF build = loopBody.buildRDSeff();
            if ((build.getDescribedService__SEFF() == null) && (build.getSeffTypeID() == null)
                    && build.getResourceDemandingInternalBehaviours().isEmpty()) {
                action.setBodyBehaviour_Loop(loopBody.buildBehaviour());
            } else {
                action.setBodyBehaviour_Loop(build);
            }
        }

        action.getInfrastructureCall__Action().addAll(infrastructureCalls);
        action.getResourceCall__Action().addAll(resourceCalls);
        action.getResourceDemand_Action().addAll(demands);

        return action;

    }

}
