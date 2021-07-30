package org.palladiosimulator.generator.fluent.usagemodel.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModelAddition;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UserData;

public class UsageModelCreator extends UsageModelEntity implements IUsageModel, IUsageModelAddition {
    private final IModelValidator validator;
    private final System system;
    private final Repository repository;
    
    private final List<UsageScenario> usageScenarios;
    private final List<UserData> userDatas;

    
    public UsageModelCreator(System system, Repository repository, IModelValidator validator) {
        this.validator = validator;
        this.system = system;
        this.repository  = repository;
        
        this.usageScenarios = new ArrayList<>();
        this.userDatas = new ArrayList<>();
    }

    public UsageModel createUsageModelNow() {
        final UsageModel usgModel = build();
        validator.validate(usgModel, "UsageModel");
        return usgModel;
    }

    @Override
    protected UsageModel build() {
        final UsageModel usgModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        usgModel.getUserData_UsageModel().addAll(userDatas);
        usgModel.getUsageScenario_UsageModel().addAll(usageScenarios);

        return usgModel;
    }

    public IUsageModelAddition addToUsageModel(final UserDataCreator userData) {
        IllegalArgumentException.throwIfNull(userData, "The given UserData must not be null");
        userDatas.add(userData.build());
        return this;
    }

    public IUsageModelAddition addToUsageModel(final UsageScenarioCreator usageScenario) {
        IllegalArgumentException.throwIfNull(usageScenario, "The given UsageScenario must not be null");
        usageScenarios.add(usageScenario.build());
        return this;
    }
    
    public OperationSignature getOperationSignatureByName(String name) {
        IllegalArgumentException.throwIfNull(repository, "The referred Repository was not set correctly in FluentUsageModelFactory");
        OperationSignature sig = null;
        List<Interface> list = repository.getInterfaces__Repository();
        
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) instanceof OperationInterface) {
                OperationInterface opInt = (OperationInterface) list.get(i);
                sig = opInt.getSignatures__OperationInterface().stream().filter(x -> x.getEntityName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No AssemblyContext with name " + name + " found.")); 
            }
        }
      /*  OperationSignature l = repository.getInterfaces__Repository().stream().
                filter(c -> c instanceof OperationInterface).map(c -> (OperationInterface) c)
                .map(x -> x.getSignatures__OperationInterface()
                */
        IllegalArgumentException.throwIfNull(sig,"No OperationSignature with name " + name + " found.");
                
        return sig;
    }
    
   //OperationProvidedRole
   public OperationProvidedRole getOperationProvidedRoleByName(String name) {
        IllegalArgumentException.throwIfNull(repository, "The referred Repository was not set correctly in FluentUsageModelFactory");
        
        OperationProvidedRole role = null;
        List<RepositoryComponent> list = repository.getComponents__Repository();
        
        for(int i = 0; i < list.size(); i++) {
            ProvidedRole r = list.get(i).getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No ProvidedRole with name " + name + " found."));
            
            if(r instanceof OperationProvidedRole) {
                role = (OperationProvidedRole) r;
            }        
        }     
        
       IllegalArgumentException.throwIfNull(role,"No OperationSignature with name " + name + " found.");
        
        return role;
    }
    
    public OperationProvidedRole getOperationProvidedRoleByNameSystem(String name) {
        IllegalArgumentException.throwIfNull(system, "The referred System was not set correctly in FluentUsageModelFactory");
        
        OperationProvidedRole role = null;
        ProvidedRole r = system.getProvidedRoles_InterfaceProvidingEntity().stream().filter(x -> x.getEntityName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No ProvidedRole with name " + name + " found."));
            
        if(r instanceof OperationProvidedRole) {
            role = (OperationProvidedRole) r;
        }        
        
        return role;
    }
    
    public AssemblyContext getAssemblyContextByName(String name) throws NoSuchElementException {
        IllegalArgumentException.throwIfNull(system, "The referred System was not set correctly in FluentUsageModelFactory");
        return  system.getAssemblyContexts__ComposedStructure().stream().filter(x -> x.getEntityName().equals(name))
       .findFirst()
       .orElseThrow(() -> new IllegalArgumentException("No AssemblyContext with name " + name + " found."));        
    }

}
