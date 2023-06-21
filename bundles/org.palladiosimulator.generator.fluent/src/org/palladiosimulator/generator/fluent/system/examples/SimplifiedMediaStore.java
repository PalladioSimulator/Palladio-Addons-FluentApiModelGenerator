package org.palladiosimulator.generator.fluent.system.examples;

import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.generator.fluent.system.factory.FluentSystemFactory;
import org.palladiosimulator.pcm.system.System;

/**
 * TODO
 */
public class SimplifiedMediaStore {

    public static void main(final String[] args) {
        simplifiedMediaStoreSystem();
    }

    public static void simplifiedMediaStoreSystem() {
        final FluentSystemFactory create = new FluentSystemFactory();
        final System system = create.newSystem()
            .withName("SimplifiedMediaStore System")
            .addRepository(ModelLoader.loadRepository("./simplifiedMediaStore.repository"))
            .addToSystem(create.newAssemblyContext()
                .withName("AudioDB Component")
                .withEncapsulatedComponent("AudioDB"))
            .addToSystem(create.newAssemblyContext()
                .withName("DBAdapter Component")
                .withEncapsulatedComponent("DBAdapter"))
            .addToSystem(create.newAssemblyContext()
                .withName("DigitalWatermarking Component")
                .withEncapsulatedComponent("DigitalWatermarking"))
            .addToSystem(create.newAssemblyContext()
                .withName("MediaStore Component")
                .withEncapsulatedComponent("MediaStore"))
            .addToSystem(create.newAssemblyContext()
                .withName("WebGUI Component")
                .withEncapsulatedComponent("WebGUI"))
            .addToSystem(create.newAssemblyConnector()
                .withName("AudioDBInterfaceConnector")
                .withProvidingAssemblyContext("AudioDB Component")
                .withOperationProvidedRole("ProvidesAudioDBInterface")
                .withRequiringAssemblyContext("DBAdapter Component")
                .withOperationRequiredRole("RequiresAudioDBInterface"))
            .addToSystem(create.newAssemblyConnector()
                .withName("IAudioDB Connector")
                .withProvidingAssemblyContext("DBAdapter Component")
                .withOperationProvidedRole("ProvidesIAudioDB")
                .withRequiringAssemblyContext("MediaStore Component")
                .withOperationRequiredRole("RequiresIAudioDB"))
            .addToSystem(create.newAssemblyConnector()
                .withName("ISound Connector")
                .withProvidingAssemblyContext("DigitalWatermarking Component")
                .withOperationProvidedRole("ProvidesISound")
                .withRequiringAssemblyContext("MediaStore Component")
                .withOperationRequiredRole("RequiresISound"))
            .addToSystem(create.newAssemblyConnector()
                .withName("MediaStoreInterface Connector")
                .withProvidingAssemblyContext("MediaStore Component")
                .withOperationProvidedRole("ProvidesMediaStoreInterface")
                .withRequiringAssemblyContext("WebGUI Component")
                .withOperationRequiredRole("RequiresMediaStoreInterface"))
            .addToSystem(create.newOperationProvidedRole()
                .withName("SystemProvidesGUIInterface")
                .withProvidedInterface("GUIInterface"))
            .addToSystem(create.newProvidedDelegationConnectorCreator()
                .withName("GUIInterface Connector")
                .withOuterProvidedRole("SystemProvidesGUIInterface")
                .withProvidingContext("WebGUI Component")
                .withOperationProvidedRole("ProvidesGUIInterface"))
            .createSystemNow();

        ModelSaver.saveSystem(system, "./","simplifiedMediaStore");
    }
}
