package org.palladiosimulator.generator.fluent.usageModel.structure.components.actions;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.components.VariableUsageCreator;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class EntryLevelSystemCallCreator extends ActionCreator{

    List<VariableUsage> outputParameterUsage = new ArrayList<VariableUsage>();
    List<VariableUsage> inputParameterUsage = new ArrayList<VariableUsage>();
    int priority;
    
    //TODO Operation Siganture/Role ist aus Repository
    OperationSignature opSignature;    
    OperationProvidedRole opRole;
    
    @Override
    public EntryLevelSystemCall build() {
      EntryLevelSystemCall call = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();
      
      call.getOutputParameterUsages_EntryLevelSystemCall().addAll(outputParameterUsage);
      call.getInputParameterUsages_EntryLevelSystemCall().addAll(inputParameterUsage);
      
      if(opSignature != null) {
      call.setOperationSignature__EntryLevelSystemCall(opSignature);}
      
      if(opRole != null) {
          call.setProvidedRole_EntryLevelSystemCall(opRole);
      }
      
      //TODO check if set
      call.setPriority(priority);
        
            
      if (name != null) {
          call.setEntityName(name);
      }
      if (predecessor != null) {
          call.setPredecessor(predecessor);
      }
      if (successor != null) {
          call.setSuccessor(successor);
      }
      
    return call;
    }
    
    //TODO: sonst 2 gleiche Methoden
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallOutput(VariableUsageCreator outputParameterUsage) {
        IllegalArgumentException.throwIfNull(outputParameterUsage, "The given Output Variable Usage must not be null");
        this.outputParameterUsage.add(outputParameterUsage.build());
        return this;        
    }
    
    public EntryLevelSystemCallCreator addToEntryLevelSystemCallInput(VariableUsageCreator inputParameterUsage) {
        IllegalArgumentException.throwIfNull(inputParameterUsage, "The given Input Variable Usage must not be null");
        this.inputParameterUsage.add(inputParameterUsage.build());
        return this; 
    }

}
