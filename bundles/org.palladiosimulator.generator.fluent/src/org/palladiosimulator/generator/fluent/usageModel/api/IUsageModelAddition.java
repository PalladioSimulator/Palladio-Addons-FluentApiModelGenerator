package org.palladiosimulator.generator.fluent.usageModel.api;

import org.palladiosimulator.generator.fluent.usageModel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usageModel.structure.components.UserDataCreator;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

public interface IUsageModelAddition {

    UsageModel createUsageModelNow();
    
    IUsageModelAddition addToUsageModel(UserDataCreator userData);
    IUsageModelAddition addToUsageModel(UsageScenarioCreator usageScenario);
        
}
