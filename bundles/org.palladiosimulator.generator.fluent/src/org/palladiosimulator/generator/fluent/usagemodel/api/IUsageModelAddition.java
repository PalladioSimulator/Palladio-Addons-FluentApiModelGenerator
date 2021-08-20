package org.palladiosimulator.generator.fluent.usagemodel.api;

import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

public interface IUsageModelAddition {

    /**
     * Turns this usageModel-in-the-making into a Palladio-'<em><b>UsageModel</b></em>' object.
     *
     * @return the final UsageModel object
     * @see org.palladiosimulator.pcm.usagemodel.UsageModel
     */
    UsageModel createUsageModelNow();

    /**
     * Adds the <code>userData</code> to the list of User Datas provided by this usage Model.
     *
     * @param UserDataCreator
     *            userData
     * @return this UsageModel, now containing the <code>userData</code>
     * 
     * @see org.palladiosimulator.pcm.usagemodel.UserData
     */
    IUsageModelAddition addToUsageModel(UserDataCreator userData);

    /**
     * Adds the <code>usageScenario</code> to the list of UsageScenrios provided by this usage
     * Model.
     *
     * @param UserDataCreator
     *            userData
     * @return this UsageModel, now containing the <code>usageScenario</code>
     * 
     * @see org.palladiosimulator.pcm.usagemodel.UsageScenario
     */
    IUsageModelAddition addToUsageModel(UsageScenarioCreator usageScenario);

}
