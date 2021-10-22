package org.palladiosimulator.generator.fluent.repository.examples;

import org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.pcm.repository.Repository;

/**
 * TODO
 */
public class SimplifiedMediaStore {

    public static void main(final String[] args) {
        simplifiedMediaStoreRepository();
    }

    public static void simplifiedMediaStoreRepository() {
        final FluentRepositoryFactory create = new FluentRepositoryFactory();
        final Repository repo = create.newRepository()
            .withName("SimplifiedMediaStore Repository")
            .addToRepository(create.newOperationInterface()
                .withName("IAudioDB"))
            .addToRepository(create.newOperationInterface()
                .withName("ISound"))
            .addToRepository(create.newOperationInterface()
                .withName("MediaStoreInterface"))
            .addToRepository(create.newOperationInterface()
                .withName("GUIInterface"))
            .addToRepository(create.newOperationInterface()
                .withName("AudioDBInterface"))
            .addToRepository(create.newBasicComponent()
                .withName("AudioDB")
                .provides(create.fetchOfOperationInterface("AudioDBInterface"), "ProvidesAudioDBInterface"))
            .addToRepository(create.newBasicComponent()
                .withName("DBAdapter")
                .requires(create.fetchOfOperationInterface("AudioDBInterface"), "RequiresAudioDBInterface")
                .provides(create.fetchOfOperationInterface("IAudioDB"), "ProvidesIAudioDB"))
            .addToRepository(create.newBasicComponent()
                .withName("DigitalWatermarking")
                .provides(create.fetchOfOperationInterface("ISound"), "ProvidesISound"))
            .addToRepository(create.newBasicComponent()
                .withName("MediaStore")
                .requires(create.fetchOfOperationInterface("ISound"), "RequiresISound")
                .requires(create.fetchOfOperationInterface("IAudioDB"), "RequiresIAudioDB")
                .provides(create.fetchOfOperationInterface("MediaStoreInterface"), "ProvidesMediaStoreInterface"))
            .addToRepository(create.newBasicComponent()
                .withName("WebGUI")
                .requires(create.fetchOfOperationInterface("MediaStoreInterface"), "RequiresMediaStoreInterface")
                .provides(create.fetchOfOperationInterface("GUIInterface"), "ProvidesGUIInterface"))
            .createRepositoryNow();

        ModelSaver.saveRepository(repo, "./simplifiedMediaStore", true);
    }

}
