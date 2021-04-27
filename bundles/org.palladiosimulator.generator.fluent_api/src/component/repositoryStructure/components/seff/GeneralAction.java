package component.repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

import component.repositoryStructure.components.VariableUsageCreator;
import component.repositoryStructure.internals.ResourceSignature;
import shared.structure.ProcessingResource;

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
        Objects.requireNonNull(specificationStochasticExpression,
                "specification_stochasticExpression must not be null");
        Objects.requireNonNull(processingResource, "processingResource must not be null");
        final ParametricResourceDemand demand = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();

        final ProcessingResourceType processingResourceType = this.repository
                .getProcessingResourceType(processingResource);
        demand.setRequiredResource_ParametricResourceDemand(processingResourceType);

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(specificationStochasticExpression);
        demand.setSpecification_ParametericResourceDemand(rand);
        this.demands.add(demand);
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
        Objects.requireNonNull(numberOfCallsStochasticExpression,
                "numberOfCalls_stochasticExpression must not be null");
        Objects.requireNonNull(signature, "signature must not be null");
        Objects.requireNonNull(requiredRole, "requiredRole must not be null");
        if ((variableUsages != null) && (variableUsages.length > 0)) {
            for (final VariableUsageCreator variableUsage : variableUsages) {
                Objects.requireNonNull(variableUsage, "variable usages must not be null");
            }
        }

        final InfrastructureCall call = SeffPerformanceFactory.eINSTANCE.createInfrastructureCall();

        if (numberOfCallsStochasticExpression != null) {
            final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
            rand.setSpecification(numberOfCallsStochasticExpression);
            call.setNumberOfCalls__InfrastructureCall(rand);
        }
        if (requiredRole != null) {
            call.setRequiredRole__InfrastructureCall(requiredRole);
        }
        if (signature != null) {
            call.setSignature__InfrastructureCall(signature);
        }
        if ((variableUsages != null) && (variableUsages.length != 0)) {
            Arrays.asList(variableUsages).stream().map(VariableUsageCreator::build)
                    .forEach(v -> call.getInputVariableUsages__CallAction().add(v));
        }

        this.infrastructureCalls.add(call);
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

        Objects.requireNonNull(numberOfCallsStochasticExpression,
                "numberOfCalls_stochasticExpression must not be null");
        Objects.requireNonNull(signature, "signature must not be null");
        Objects.requireNonNull(requiredRole, "requiredRole must not be null");
        if ((variableUsages != null) && (variableUsages.length > 0)) {
            for (final VariableUsageCreator variableUsage : variableUsages) {
                Objects.requireNonNull(variableUsage, "variable usages must not be null");
            }
        }

        final ResourceCall call = SeffPerformanceFactory.eINSTANCE.createResourceCall();

        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(numberOfCallsStochasticExpression);
        call.setNumberOfCalls__ResourceCall(rand);

        final org.palladiosimulator.pcm.resourcetype.ResourceSignature resourceSignature = this.repository
                .getResourceSignature(signature);
        call.setSignature__ResourceCall(resourceSignature);
        call.setResourceRequiredRole__ResourceCall(requiredRole);

        if ((variableUsages != null) && (variableUsages.length != 0)) {
            Arrays.asList(variableUsages).stream().map(VariableUsageCreator::build)
                    .forEach(v -> call.getInputVariableUsages__CallAction().add(v));
        }

        this.resourceCalls.add(call);
        return this;
    }

}
