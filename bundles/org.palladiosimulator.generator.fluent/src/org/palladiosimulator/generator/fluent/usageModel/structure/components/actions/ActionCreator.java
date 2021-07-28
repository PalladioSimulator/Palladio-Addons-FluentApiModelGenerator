package org.palladiosimulator.generator.fluent.usagemodel.structure.components.actions;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.usagemodel.structure.UsageModelEntity;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;

public abstract class ActionCreator extends UsageModelEntity {
    protected AbstractUserAction predecessor;
    protected AbstractUserAction successor;
    
    public abstract AbstractUserAction build();

    @Deprecated
    //TODO:brauchen wir die?
    public ActionCreator withPredecessor(ActionCreator act) {
        IllegalArgumentException.throwIfNull(act, "The given Predecessor Action must not be null");
        this.predecessor = act.build();
        return this;
    }

    public ActionCreator withSuccessor(ActionCreator act) {
        IllegalArgumentException.throwIfNull(act, "The given Successor Action must not be null");        
        this.successor = act.build();
        return this;
    }

    public AbstractUserAction getSuccessor() {
        return this.successor;
    }
    
    @Override
    public ActionCreator withName(final String name) {
        return (ActionCreator) super.withName(name);
    }

}
