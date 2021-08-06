package org.palladiosimulator.generator.fluent.usagemodel.structure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.ActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StartActionCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions.StopActionCreator;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class ScenarioBehaviourCreator extends UsageModelEntity {

    private List<AbstractUserAction> actions;

    public ScenarioBehaviourCreator(UsageModelCreator usgModelCreator) {
        this.actions = new ArrayList<>();
        usageModelCreator = usgModelCreator;
    }

    public ScenarioBehaviourCreator addActions(ActionCreator action) {
        IllegalArgumentException.throwIfNull(action, "The given Action must not be null");        
        AbstractUserAction usrAction = action.build();

        createActionFlow(usrAction);
        return this;
    }

    private void addStart(AbstractUserAction first) {
        //test if current is already Start
        if(first instanceof Start) {
            return;
        }        
        
        ActionCreator startCreator  = new StartActionCreator();
        AbstractUserAction start = startCreator.build();        
        start.setSuccessor(first);
        actions.add(start);
        
    }
    private void addStop(AbstractUserAction last) {
        //test if last is already Stop        
        if(last instanceof Stop) {
            return;
        }
        
        ActionCreator stopCreator  = new StopActionCreator();
        AbstractUserAction stop = stopCreator.build();
        stop.setPredecessor(last);
        actions.add(stop);
    }
    
    private void createActionFlow(AbstractUserAction start) {
        //use only successor, predecessor then happens from itself
        List<AbstractUserAction> flow = new ArrayList<>();
        AbstractUserAction before = start;
        
        AbstractUserAction current = start;
        
        addStart(current);
        
        while(current != null) {
            flow.add(current); 
            before = current;
            current = current.getSuccessor();
        }
        addStop(before);
        actions.addAll(flow);
    } 
    
    private void addStartStop() {
        ActionCreator startCreator  = new StartActionCreator();
        AbstractUserAction start = startCreator.build();
        ActionCreator stopCreator  = new StopActionCreator();
        AbstractUserAction stop = stopCreator.build();
        start.setSuccessor(stop);
        actions.add(start);
        actions.add(stop);
    }
    
    @Override
    public ScenarioBehaviour build() {
        ScenarioBehaviour scenBeh = UsagemodelFactory.eINSTANCE.createScenarioBehaviour();
        if (name != null) {
            scenBeh.setEntityName(name);
        }
        if(actions.isEmpty()) {
            //add Start and Stop Actions as this is required
            addStartStop();
        }
        scenBeh.getActions_ScenarioBehaviour().addAll(actions); 
        return scenBeh;
    }

    @Override
    public ScenarioBehaviourCreator withName(final String name) {
        return (ScenarioBehaviourCreator) super.withName(name);
    }

}
