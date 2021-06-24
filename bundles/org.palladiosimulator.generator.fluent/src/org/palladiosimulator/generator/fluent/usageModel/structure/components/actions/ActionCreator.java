package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usageModel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;

public abstract class ActionCreator extends UsageModelEntity{
    AbstractUserAction predecessor;
    AbstractUserAction successor;
    
    public abstract AbstractUserAction build();
  
    public ActionCreator withPredecessor(ActionCreator act) {
       IllegalArgumentException.throwIfNull(act, "The given Predecessor Action must not be null");
       this.predecessor = act.build();
       return this;
    }
    
    public ActionCreator withSuccessor(ActionCreator act) {
        IllegalArgumentException.throwIfNull(act, "The given Successor Action must not be null");
        this.successor=act.build();
        return this;
    }
    
    @Override
    public ActionCreator withName(final String name) {
        return (ActionCreator) super.withName(name);
    }
    
}
