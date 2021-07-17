package org.palladiosimulator.generator.fluent.component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;

/**
 * This class provides the implementation of the methods that add resource
 * demands, resource calls and infrastructure calls to a SEFF action. Most of
 * the actions in a SEFF offer these characteristics.
 *
 * @author Louisa Lambrecht
 */
public abstract class GeneralAction extends SeffAction {

    protected List<ParametricResourceDemand> demands = new ArrayList<>();
    protected List<InfrastructureCall> infrastructureCalls = new ArrayList<>();
    protected List<ResourceCall> resourceCalls = new ArrayList<>();

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand
     * ParametricResourceDemand} to this action.
     * <p>
     * Parametric Resource Demand specifies the amount of processing requested from
     * a certain type of resource in a parameterized way. It assigns the demand
     * specified as a Random-Variable
     * (<code>specification_stochasticExpression</code>) to an abstract
     * ProcessingResourceType <code>processingResource</code>(e.g., CPU, hard disk)
     * instead of a concrete ProcessingResourceSpecification (e.g., 5 GHz CPU, 20
     * MByte/s hard disk).
     * </p>
     *
     * @param specificationStochasticExpression
     * @param processingResource
     * @return this action in the making
     */
    public GeneralAction withResourceDemand(final String specificationStochasticExpression,
            final ProcessingResource processingResource) {
        IllegalArgumentException.throwIfNull(specificationStochasticExpression,
                "specification_stochasticExpression must not be null");
        IllegalArgumentException.throwIfNull(processingResource, "processingResource must not be null");
        final ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();

        final ProcessingResourceType processingResourceType = repository.getProcessingResourceType(processingResource);
        demand.setRequiredResource_ParametricResourceDemand(processingResourceType);

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(specificationStochasticExpression);
        demand.setSpecification_ParametericResourceDemand(rand);
        demands.add(demand);
        return this;
    }

    /**
     * Adds an
     * {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall
     * InfrastructureCall} to this action.
     *
     * @param numberOfCallsStochasticExpression
     * @param signature
     * @param requiredRole
     * @param variableUsages
     * @return this action in the making
     */
    public GeneralAction withInfrastructureCall(final String numberOfCallsStochasticExpression,
            final InfrastructureSignature signature, final InfrastructureRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {
        IllegalArgumentException.throwIfNull(numberOfCallsStochasticExpression,
                "numberOfCalls_stochasticExpression must not be null");
        IllegalArgumentException.throwIfNull(signature, "signature must not be null");
        IllegalArgumentException.throwIfNull(requiredRole, "requiredRole must not be null");
        IllegalArgumentException.throwIfNull(variableUsages, "variable usages must not be null");
        if (variableUsages.length > 0) {
            for (final VariableUsageCreator variableUsage : variableUsages) {
                IllegalArgumentException.throwIfNull(variableUsage, "variable usages must not be null");
            }
        }

        final InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(numberOfCallsStochasticExpression);
        call.setNumberOfCalls__InfrastructureCall(rand);
        call.setRequiredRole__InfrastructureCall(requiredRole);
        call.setSignature__InfrastructureCall(signature);
        if (variableUsages.length > 0) {
            Arrays.asList(variableUsages).stream().map(VariableUsageCreator::build)
                    .forEach(v -> call.getInputVariableUsages__CallAction().add(v));
        }

        infrastructureCalls.add(call);
        return this;
    }

    /**
     * Adds a {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall
     * ResourceCall} to this action.
     *
     * @param numberOfCallsStochasticExpression
     * @param signature
     * @param requiredRole
     * @param variableUsages
     * @return this action in the making
     */
    public GeneralAction withResourceCall(final String numberOfCallsStochasticExpression,
            final ResourceSignature signature, final ResourceRequiredRole requiredRole,
            final VariableUsageCreator... variableUsages) {

        IllegalArgumentException.throwIfNull(numberOfCallsStochasticExpression,
                "numberOfCalls_stochasticExpression must not be null");
        IllegalArgumentException.throwIfNull(signature, "signature must not be null");
        IllegalArgumentException.throwIfNull(requiredRole, "requiredRole must not be null");
        if ((variableUsages != null) && (variableUsages.length > 0)) {
            for (final VariableUsageCreator variableUsage : variableUsages) {
                IllegalArgumentException.throwIfNull(variableUsage, "variable usages must not be null");
            }
        }

        final ResourceCall call = SeffPerformanceFactory.eINSTANCE.createResourceCall();

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(numberOfCallsStochasticExpression);
        call.setNumberOfCalls__ResourceCall(rand);

        final org.palladiosimulator.pcm.resourcetype.ResourceSignature resourceSignature = repository
                .getResourceSignature(signature);
        call.setSignature__ResourceCall(resourceSignature);
        call.setResourceRequiredRole__ResourceCall(requiredRole);

        if ((variableUsages != null) && (variableUsages.length != 0)) {
            Arrays.asList(variableUsages).stream().map(VariableUsageCreator::build)
                    .forEach(v -> call.getInputVariableUsages__CallAction().add(v));
        }

        resourceCalls.add(call);
        return this;
    }

}
