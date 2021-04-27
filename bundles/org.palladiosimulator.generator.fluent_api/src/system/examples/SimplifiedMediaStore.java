package system.examples;

import org.palladiosimulator.pcm.system.System;

import shared.util.ModelLoader;
import shared.util.ModelSaver;
import system.factory.FluentSystemFactory;

public class SimplifiedMediaStore {

    public static void main(String[] args) {
        simplifiedMediaStoreSystem();
    }

    public static void simplifiedMediaStoreSystem() {
        FluentSystemFactory create = new FluentSystemFactory();
        System system = create.newSystem().withName("SimplifiedMediaStore System")
                .addRepository(ModelLoader.loadRepository("./simplifiedMediaStore.repository"))
                .addToSystem(
                        create.newAssemblyContext().withName("AudioDB Component").withEncapsulatedComponent("AudioDB"))
                .addToSystem(create.newAssemblyContext().withName("DBAdapter Component")
                        .withEncapsulatedComponent("DBAdapter"))
                .addToSystem(create.newAssemblyContext().withName("DigitalWatermarking Component")
                        .withEncapsulatedComponent("DigitalWatermarking"))
                .addToSystem(create.newAssemblyContext().withName("MediaStore Component")
                        .withEncapsulatedComponent("MediaStore"))
                .addToSystem(
                        create.newAssemblyContext().withName("WebGUI Component").withEncapsulatedComponent("WebGUI"))
                .addToSystem(create.newAssemblyConnector().withName("AudioDBInterfaceConnector")
                        .withProvidingAssemblyContext("AudioDB Component")
                        .withOperationProvidedRole("ProvidesAudioDBInterface")
                        .withRequiringAssemblyContext("DBAdapter Component")
                        .withOperationRequiredRole("RequiresAudioDBInterface"))
                .addToSystem(create.newAssemblyConnector().withName("IAudioDB Connector")
                        .withProvidingAssemblyContext("DBAdapter Component")
                        .withOperationProvidedRole("ProvidesIAudioDB")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresIAudioDB"))
                .addToSystem(create.newAssemblyConnector().withName("ISound Connector")
                        .withProvidingAssemblyContext("DigitalWatermarking Component")
                        .withOperationProvidedRole("ProvidesISound")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresISound"))
                .addToSystem(create.newAssemblyConnector().withName("MediaStoreInterface Connector")
                        .withProvidingAssemblyContext("MediaStore Component")
                        .withOperationProvidedRole("ProvidesMediaStoreInterface")
                        .withRequiringAssemblyContext("WebGUI Component")
                        .withOperationRequiredRole("RequiresMediaStoreInterface"))
                .addToSystem(create.newOperationProvidedRole().withName("SystemProvidesGUIInterface")
                        .withProvidedInterface("GUIInterface"))
                .addToSystem(create.newProvidedDelegationConnectorCreator().withName("GUIInterface Connector")
                        .withOuterProvidedRole("SystemProvidesGUIInterface").withProvidingContext("WebGUI Component")
                        .withOperationProvidedRole("ProvidesGUIInterface"))
                .createSystemNow();

        ModelSaver.saveSystem(system, "./simplifiedMediaStore", true);
    }
}
