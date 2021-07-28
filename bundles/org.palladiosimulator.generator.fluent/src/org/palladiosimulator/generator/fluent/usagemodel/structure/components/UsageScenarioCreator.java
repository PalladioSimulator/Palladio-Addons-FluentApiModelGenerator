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

public class UsageScenarioCreator extends UsageModelEntity {

    private ScenarioBehaviour scenarioBehaviour;
    private Workload workload; // can be Open OR Closed

    public UsageScenarioCreator(UsageModelCreator usgModelCreator, ScenarioBehaviourCreator scenBehave, WorkloadCreator work) {
        usageModelCreator = usgModelCreator;
        addToUsageScenario(scenBehave);
        addToUsageScenario(work);
    }
    
    @Deprecated
    public UsageScenarioCreator(UsageModelCreator usgModelCreator) {
        usageModelCreator = usgModelCreator;
    }

    private void addToUsageScenario (WorkloadCreator workload) {
         if(workload instanceof OpenWorkloadCreator) {
             OpenWorkloadCreator o = (OpenWorkloadCreator) workload;
             this.workload = o.build();
         } else {
             //then ClosedWorkloadCrator
             ClosedWorkloadCreator c = (ClosedWorkloadCreator) workload;
             this.workload = c.build();
         }
    }
    
    @Deprecated
    public UsageScenarioCreator addToUsageScenario(OpenWorkloadCreator workload) {
        this.workload = workload.build();
        return this;
    }
    
    @Deprecated
    public UsageScenarioCreator addToUsageScenario(ClosedWorkloadCreator workload) {
        this.workload = workload.build();
        return this;
    }

    @Deprecated
    public UsageScenarioCreator addToUsageScenario(ScenarioBehaviourCreator scenBehave) {
        IllegalArgumentException.throwIfNull(scenBehave, "The given ScenarioBehaviour must not be null");
        this.scenarioBehaviour = scenBehave.build();
        return this;
    }

    @Override
    public UsageScenario build() {
        final UsageScenario usgScenario = UsagemodelFactory.eINSTANCE.createUsageScenario();
        if (name != null) {
            usgScenario.setEntityName(name);
        }
        if (scenarioBehaviour != null) {
            usgScenario.setScenarioBehaviour_UsageScenario(scenarioBehaviour);
        }
        if (workload != null) {
            usgScenario.setWorkload_UsageScenario(workload);
        }
        return usgScenario;
    }

    @Override
    public UsageScenarioCreator withName(final String name) {
        return (UsageScenarioCreator) super.withName(name);
    }

}