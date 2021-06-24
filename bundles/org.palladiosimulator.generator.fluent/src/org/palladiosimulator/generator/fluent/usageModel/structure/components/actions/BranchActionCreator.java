package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class BranchActionCreator extends ActionCreator{
   
    //TODO: vllt Probabilistic BranchTransition
    List<BranchTransition> transitions = new ArrayList<BranchTransition>();

    @Override
    public Branch build() {
       Branch b = UsagemodelFactory.eINSTANCE.createBranch();
       
       b.getBranchTransitions_Branch().addAll(transitions);
       
       if (name != null) {
           b.setEntityName(name);
       }
       if (predecessor != null) {
           b.setPredecessor(predecessor);
       }
       if (successor != null) {
           b.setSuccessor(successor);
       }
       return b;
    }
    
    //TODO
    public BranchActionCreator addToBranchAction(){
        return this;        
    }
    
}
