package org.palladiosimulator.generator.fluent.usagemodel.structure;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModel;
import org.palladiosimulator.generator.fluent.usagemodel.api.IUsageModelAddition;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UsageScenarioCreator;
import org.palladiosimulator.generator.fluent.usagemodel.structure.components.UserDataCreator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.UserData;

public class UsageModelCreator extends UsageModelEntity implements IUsageModel, IUsageModelAddition {
    private final IModelValidator validator;
    private final System system;
   // private final ResourceEnvironment resourceEnv;
   //private final Repository repository;
   // private final ResourceRepository resources ;
    
    private final List<UsageScenario> usageScenarios;
    private final List<UserData> userDatas;

    
    public UsageModelCreator(System system,/* Repository repository, ResourceRepository resources,*/IModelValidator validator) {
        this.validator = validator;
        this.system = system;
        // this.resourceEnv = resourceEnv;
        // this.repository  = repository;
        // this.resources = resources;
        
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
    
    public AssemblyContext getAssemblyContextByName(String name) throws NoSuchElementException {
        IllegalArgumentException.throwIfNull(system, "The referred System was not set correctly in FluentUsageModelFactory");
        return  system.getAssemblyContexts__ComposedStructure().stream().filter(x -> x.getEntityName().equals(name))
       .findFirst()
       .orElseThrow(() -> new IllegalArgumentException("No AssemblyContext with name " + name + " found."));        
    }

}
