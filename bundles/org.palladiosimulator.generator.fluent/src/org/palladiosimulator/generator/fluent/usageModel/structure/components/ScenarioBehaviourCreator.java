package org.palladiosimulator.generator.fluent.usageModel.structure.components;

import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class ScenarioBehaviourCreator extends UsageModelEntity {

    @Override
    protected ScenarioBehaviour build() {
        final ScenarioBehaviour scenBeh = UsagemodelFactory.eINSTANCE.createUsageScenario().getScenarioBehaviour_UsageScenario();
       
       //scenBeh.getActions_ScenarioBehaviour().addAll(null); //da sind andere drinnen/eingeschlossen
       //TODO: wie geht das bei Seff?
      // scenBeh.setLoop_ScenarioBehaviour(null);//TODO
      // scenBeh.setBranchTransition_ScenarioBehaviour(null);//TODO
    
        return scenBeh;
    }
    
    
    @Override
    public ScenarioBehaviourCreator withName(final String name) {
        return (ScenarioBehaviourCreator) super.withName(name);        
    }

}
