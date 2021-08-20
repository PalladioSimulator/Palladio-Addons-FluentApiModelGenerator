package org.palladiosimulator.generator.fluent.usagemodel.structure.components;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.ClosedWorkloadCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.OpenWorkloadCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.workload.WorkloadCreator;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.usagemodel.UsageScenario Usage
 * Scenario}. It is used to create the '<em><b>Usage Scenario</b></em>' object step-by-step, i.e.
 * '<em><b>UsageScenarioCreator</b></em>' objects are of intermediate state.
 *
 * @author Eva-Maria Neumann
 * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
 */
public class UsageScenarioCreator extends UsageModelEntity {

    private ScenarioBehaviour scenarioBehaviour;
    private Workload workload; // can be Open OR Closed

    /**
     * TODO
     * Instantiates a new usage scenario creator.
     *
     * @param usgModelCreator the usg model creator
     * @param scenBehave the scen behave
     * @param work the work
     */
    public UsageScenarioCreator(final UsageModelCreator usgModelCreator, final ScenarioBehaviourCreator scenBehave,
            final WorkloadCreator work) {
        this.usageModelCreator = usgModelCreator;
        addToUsageScenario(scenBehave);
        addToUsageScenario(work);
    }

    private void addToUsageScenario(final WorkloadCreator workload) {
        if (workload instanceof OpenWorkloadCreator) {
            final OpenWorkloadCreator o = (OpenWorkloadCreator) workload;
            this.workload = o.build();
        } else {
            // then ClosedWorkloadCrator
            final ClosedWorkloadCreator c = (ClosedWorkloadCreator) workload;
            this.workload = c.build();
        }
    }

    private UsageScenarioCreator addToUsageScenario(final ScenarioBehaviourCreator scenBehave) {
        IllegalArgumentException.throwIfNull(scenBehave, "The given ScenarioBehaviour must not be null");
        this.scenarioBehaviour = scenBehave.build();
        return this;
    }

    @Override
    public UsageScenario build() {
        final UsageScenario usgScenario = UsagemodelFactory.eINSTANCE.createUsageScenario();
        if (this.name != null) {
            usgScenario.setEntityName(this.name);
        }
        if (this.scenarioBehaviour != null) {
            usgScenario.setScenarioBehaviour_UsageScenario(this.scenarioBehaviour);
        }
        if (this.workload != null) {
            usgScenario.setWorkload_UsageScenario(this.workload);
        }
        return usgScenario;
    }

    @Override
    public UsageScenarioCreator withName(final String name) {
        return (UsageScenarioCreator) super.withName(name);
    }

}
