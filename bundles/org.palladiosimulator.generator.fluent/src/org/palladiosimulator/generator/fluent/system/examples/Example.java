package org.palladiosimulator.generator.fluent.system.examples;

import org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.generator.fluent.system.factory.FluentSystemFactory;
import org.palladiosimulator.pcm.system.System;

/**
 * TODO
 */
public class Example {
    public static void main(final String[] args) {
        basicExample();
        invalidSystem();
    }

    private static void invalidSystem() {
        final FluentSystemFactory create = new FluentSystemFactory();
        final System system = create.newSystem()
            .withName("invalid org.palladiosimulator.generator.fluent.system")
            .addRepository(ModelLoader.loadRepository("./miniExample.repository"))
            .addToSystem(create.newAssemblyContext())
            .addToSystem(create.newAssemblyContext()
                .withName("basic component context 1")
                .withEncapsulatedComponent("basic component"))
            .addToSystem(create.newOperationProvidedRole()
                .withName("role")
                .withProvidedInterface("interface"))
            .addToSystem(create.newProvidedDelegationConnectorCreator()
                .withOuterProvidedRole("role")
                .withProvidingContext("basic component context 1")
                .withOperationProvidedRole("basic component provides interface"))
            .createSystemNow();
        ModelSaver.saveSystem(system, "./invalid", true);
    }

    private static void basicExample() {
        final FluentSystemFactory create = new FluentSystemFactory();
        final System system = create.newSystem()
            .withName("basicSystem")
            .addRepository(ModelLoader.loadRepository("./miniExample.repository"))
            .addToSystem(create.newAssemblyContext()
                .withName("basic component context 1")
                .withEncapsulatedComponent("basic component"))
            .addToSystem(create.newAssemblyContext()
                .withName("basic component context 2")
                .withEncapsulatedComponent("basic component"))
            .addToSystem(create.newAssemblyConnector()
                .withName("connector")
                .withRequiringAssemblyContext("basic component context 1")
                .withOperationRequiredRole("basic component requires interface")
                .withProvidingAssemblyContext("basic component context 2")
                .withOperationProvidedRole("basic component provides interface"))
            .addToSystem(create.newOperationRequiredRole()
                .withName("org.palladiosimulator.generator.fluent.system required role")
                .withRequiredInterface("interface"))
            .addToSystem(create.newRequiredDelegationConnectorCreator()
                .withName("required delegation")
                .withOuterRequiredRole("org.palladiosimulator.generator.fluent.system required role")
                .withRequiringContext("basic component context 2")
                .withOperationRequiredRole("basic component requires interface"))
            .addToSystem(create.newOperationProvidedRole()
                .withName("org.palladiosimulator.generator.fluent.system provided role")
                .withProvidedInterface("interface"))
            .addToSystem(create.newProvidedDelegationConnectorCreator()
                .withName("provided delegation")
                .withOuterProvidedRole("org.palladiosimulator.generator.fluent.system provided role")
                .withProvidingContext("basic component context 1")
                .withOperationProvidedRole("basic component provides interface"))
            .addToSystem(create.newEventChannelCreator()
                .withName("event channel")
                .withEventGroup("event group"))
            .addToSystem(create.newEventChannelSinkConnector()
                .withName("sink connector")
                .withEventChannel("event channel")
                .withAssemblyContext("basic component context 1")
                .withSinkRole("handles event"))
            .addToSystem(create.newEventChannelSourceConnector()
                .withName("source connector")
                .withEventChannel("event channel")
                .withAssemblyContext("basic component context 2")
                .withSourceRole("emits event"))
            .addToSystem(create.newSinkRole()
                .withName("org.palladiosimulator.generator.fluent.system sink role")
                .withEventGroup("event group"))
            .addToSystem(create.newSinkDelegationConnector()
                .withName("sink delegation")
                .withOuterSinkRole("org.palladiosimulator.generator.fluent.system sink role")
                .withAssemblyContext("basic component context 2")
                .withSinkRole("handles event"))
            .addToSystem(create.newSourceRole()
                .withName("org.palladiosimulator.generator.fluent.system source role")
                .withEventGroup("event group"))
            .addToSystem(create.newSourceDelegationConnector()
                .withName("spurce delegation")
                .withOuterSourceRole("org.palladiosimulator.generator.fluent.system source role")
                .withAssemblyContext("basic component context 1")
                .withSourceRole("emits event"))
            .addToSystem(create.newAssemblyInfrastructureConnector()
                .withName("infrastructure connector")
                .withRequiringAssemblyContext("basic component context 1")
                .withInfrastructureRequiredRole("requres infrastructure")
                .withProvidingAssemblyContext("basic component context 2")
                .withInfrastructureProvidedRole("provides infrastructure"))
            .addToSystem(create.newInfrastructureProvidedRole()
                .withName("infrastructure provided role")
                .withProvidedInterface("infrastructure interface"))
            .addToSystem(create.newInfrastructureRequiredRole()
                .withName("infrastructure required role")
                .withRequiredInterface("infrastructure interface"))
            .addToSystem(create.newProvidedInfrastructureDelegationConnector()
                .withName("infrastructure provided delegation")
                .withOuterProvidedRole("infrastructure provided role")
                .withProvidingContext("basic component context 1")
                .withInfrastructureProvidedRole("provides infrastructure"))
            .addToSystem(create.newRequiredInfrastructureDelegationConnector()
                .withName("infrastructure required delegation")
                .withOuterRequiredRole("infrastructure required role")
                .withRequiringContext("basic component context 2")
                .withInfrastructureRequiredRole("requres infrastructure"))
            .addToSystem(create.newQoSAnnotations()
                .withName("annotations"))
            .addToSystem(create.newResourceRequiredRole()
                .withName("cpu role")
                .withRequiredInterface(ResourceInterface.CPU))
            .addToSystem(create.newResourceRequiredDelegationConnector()
                .withOuterRequiredRole("cpu role")
                .withInnerRequiredRole("cpu interface"))
            .addToSystem(create.newRequiredResourceDelegationConnector()
                .withName("resource delegation")
                .withOuterRequiredRole("cpu role")
                .withRequiringContext("basic component context 2")
                .withResourceRequiredRole("cpu interface"))
            .addToSystem(create.newAssemblyEventConnector()
                .withName("assembly event connector")
                .withSinkAssemblyContext("basic component context 1")
                .withSinkRole("handles event")
                .withSourceAssemblyContext("basic component context 2")
                .withSourceRole("emits event"))
            .createSystemNow();
        ModelSaver.saveSystem(system, "./basicExample", true);
    }
}
