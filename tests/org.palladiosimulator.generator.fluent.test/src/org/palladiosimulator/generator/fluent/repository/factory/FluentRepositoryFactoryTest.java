package org.palladiosimulator.generator.fluent.repository.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;

/**
 * TODO
 *
 * @author dr6817
 */
class FluentRepositoryFactoryTest {

    private FluentRepositoryFactory factory;

    @BeforeEach
    public void init() {
        this.factory = new FluentRepositoryFactory();
    }

    @Test
    public void testCreateRepositoryWithName() {
        final String name = "test";
        assertEquals(name, this.factory.newRepository().withName(name).createRepositoryNow().getEntityName());
    }

    @Test
    public void testFetchOfCompositeDataType() {
        final String repositoryName = "example";
        final String dataTypeName = "Person";

        // create
        final Repository repositoryUnderTest = this.factory.newRepository().withName(repositoryName)
                .addToRepository(this.factory.newCompositeDataType().withName(dataTypeName)
                        .withInnerDeclaration("name", Primitive.STRING).withInnerDeclaration("age", Primitive.INTEGER))
                .createRepositoryNow();

        // fetch
        final FluentRepositoryFactory factoryUnderTest = new FluentRepositoryFactory();
        factoryUnderTest.newRepository().withImportedResource(repositoryUnderTest);
        final CompositeDataType person = factoryUnderTest.fetchOfCompositeDataType(repositoryName + "." + dataTypeName);

        assertSame(repositoryUnderTest.getDataTypes__Repository().get(0), person);
    }

}
