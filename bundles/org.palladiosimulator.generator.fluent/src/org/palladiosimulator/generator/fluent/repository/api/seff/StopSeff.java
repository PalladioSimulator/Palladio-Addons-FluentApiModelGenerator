package org.palladiosimulator.generator.fluent.repository.api.seff;

import org.palladiosimulator.generator.fluent.repository.structure.components.seff.SeffCreator;
import org.palladiosimulator.generator.fluent.repository.structure.components.seff.StopActionCreator;
import org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature;
import org.palladiosimulator.generator.fluent.shared.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;

/**
 * TODO
 */
public interface StopSeff {

    /**
     * Defines the name of this stop action.
     *
     * @param name
     * @return this stop action
     */
    StopActionCreator withName(String name);

    /**
     * Adds a {@link org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand
     * ParametricResourceDemand} to this action.
     * <p>
     * Parametric Resource Demand specifies the amount of processing requested from a certain type
     * of resource in a parameterized way. It assigns the demand specified as a Random-Variable
     * (<code>specification_stochasticExpression</code>) to an abstract ProcessingResourceType
     * <code>processingResource</code>(e.g., CPU, hard disk) instead of a concrete
     * ProcessingResourceSpecification (e.g., 5 GHz CPU, 20 MByte/s hard disk).
     * </p>
     *
     * @param specificationStochasticExpression
     * @param processingResource
     * @return this action in the making
     */
    StopActionCreator withResourceDemand(String specificationStochasticExpression,
            ProcessingResource processingResource);

    /**
     * Adds an {@link org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall
     * InfrastructureCall} to this action.
     *
     * @param numberOfCallsStochasticExpression
     * @param signature
     * @param requiredRole
     * @param variableUsages
     * @return this stop action in the making
     */
    StopActionCreator withInfrastructureCall(String numberOfCallsStochasticExpression,
            InfrastructureSignature signature, InfrastructureRequiredRole requiredRole,
            VariableUsageCreator... variableUsages);

    /**
     * Adds a {@link org.palladiosimulator.pcm.seff.seff_performance.ResourceCall ResourceCall} to
     * this action.
     *
     * @param numberOfCallsStochasticExpression
     * @param signature
     * @param requiredRole
     * @param variableUsages
     * @return this stop action in the making
     */
    StopActionCreator withResourceCall(String numberOfCallsStochasticExpression, ResourceSignature signature,
            ResourceRequiredRole requiredRole, VariableUsageCreator... variableUsages);

    /**
     * The body behaviour always ends with a stop action and a finishing call on this method. It
     * turns the stop-action-in-the-making into a '<em><b>StopAction</b></em>' and adds it to the
     * SEFF'S/behaviour's stepwise body behaviour
     *
     * @return the SEFF/behaviour
     */
    SeffCreator createBehaviourNow();

}
