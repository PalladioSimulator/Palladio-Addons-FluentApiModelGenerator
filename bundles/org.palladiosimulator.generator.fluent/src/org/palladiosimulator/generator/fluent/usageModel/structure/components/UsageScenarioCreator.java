package org.palladiosimulator.generator.fluent.usageModel.structure.components;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.workload.ClosedWorkloadCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.workload.OpenWorkloadCreator;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;

public class UsageScenarioCreator extends UsageModelEntity {
    //TODO eventuell alles unter Workload hier rein, da 1er Beziehungen??
    
    private ScenarioBehaviour scenarioBehaviour;
    private Workload workload; //can be Open OR Closed
    
    
    public UsageScenarioCreator addToUsageScenario(final OpenWorkloadCreator workload) {
        if (workload != null) {
            //TODO: Fehlermeldung, da schon existiert
        }
        this.workload = workload.build();
        return this;
    }
    
    public UsageScenarioCreator addToUsageScenario(final ClosedWorkloadCreator workload) {
        if (workload != null) {
            //TODO: Fehlermeldung, da schon existiert
        }
        this.workload = workload.build();
        return this;
    }
    
    public UsageScenarioCreator addToUsageScenario(final ScenarioBehaviourCreator scenBehave) {
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
        if(scenarioBehaviour != null) {
            usgScenario.setScenarioBehaviour_UsageScenario(scenarioBehaviour);
        }
        if(workload != null) {
            usgScenario.setWorkload_UsageScenario(workload);
        }
        return usgScenario;
    }

    @Override
    public UsageScenarioCreator withName(final String name) {
        return (UsageScenarioCreator) super.withName(name);        
    }


}
