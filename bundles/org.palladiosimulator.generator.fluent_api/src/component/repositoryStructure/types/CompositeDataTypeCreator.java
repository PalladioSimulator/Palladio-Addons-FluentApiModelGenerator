package component.repositoryStructure.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import component.repositoryStructure.RepositoryCreator;
import component.repositoryStructure.RepositoryEntity;
import component.repositoryStructure.internals.Primitive;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.repository.CompositeDataType
 * CompositeDataType}. It is used to create the '<em><b>CompositeDataType</b></em>' object
 * step-by-step, i.e. '<em><b>CompositeDataTypeCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.CompositeDataType
 */
public class CompositeDataTypeCreator extends RepositoryEntity {

    private final List<CompositeDataType> parents;
    private final List<InnerDeclaration> innerDeclarations;

    public CompositeDataTypeCreator(final RepositoryCreator repo) {
        this.repository = repo;
        this.parents = new ArrayList<>();
        this.innerDeclarations = new ArrayList<>();
    }

    @Override
    public CompositeDataTypeCreator withName(final String name) {
        return (CompositeDataTypeCreator) super.withName(name);
    }

    /**
     * Adds an inner data type to a composite data type with name <code>name</code> and of type
     * <code>primitive</code>.
     * <p>
     * This corresponds to the declaration of fields in a Java class. For example
     * <code>.withInnerDeclaration("age", Primitive.INTEGER)</code> conforms with
     * <code>public int age;</code>.
     *
     * @param name
     *            for the inner data type
     * @param primitive
     *            inner data type
     * @return this composite data type in the making
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    public CompositeDataTypeCreator withInnerDeclaration(final String name, final Primitive primitive) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(primitive, "primitive must not be null");
        final InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
        inner.setEntityName(name);
        final PrimitiveDataType p = this.repository.getPrimitiveDataType(primitive);
        inner.setDatatype_InnerDeclaration(p);
        this.innerDeclarations.add(inner);
        return this;
    }

    /**
     * Adds an inner data type to a composite data type with name <code>name</code> and of type
     * <code>dataType</code>.
     * <p>
     * This corresponds to the declaration of fields in a Java class. For example
     * <code>.withInnerDeclaration("age", Primitive.INTEGER)</code> conforms with
     * <code>public int age;</code>.<br>
     * Non primitive data types can be fetched from the repository using the method
     * {@link component.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * fetchOfDataType(String)}.
     *
     * @param name
     *            for the inner data type
     * @param dataType
     *            inner data type
     * @return this composite data type in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfDataType(String)
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    public CompositeDataTypeCreator withInnerDeclaration(final String name, final DataType dataType) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(dataType, "dataType must not be null");
        final InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
        inner.setEntityName(name);
        inner.setDatatype_InnerDeclaration(dataType);
        this.innerDeclarations.add(inner);
        return this;
    }

    /**
     * Adds <code>parent</code> to the list of parents this composite data type inherits from.
     * <p>
     * An existing composite data type can be fetched from the repository using the
     * component.factory, i.e. <code>create.fetchOfCompleteComponentType(name)</code>.
     * </p>
     *
     * @param parent
     * @return this composite data type in the making
     * @see component.factory.FluentRepositoryFactory#fetchOfComposite
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    public CompositeDataTypeCreator withParentCompositeDataType(final CompositeDataType parent) {
        Objects.requireNonNull(parent, "parent must not be null");
        this.parents.add(parent);
        return this;
    }

    @Override
    public CompositeDataType build() {
        final CompositeDataType comp = RepositoryFactory.eINSTANCE.createCompositeDataType();
        comp.setEntityName(this.name);

        comp.getParentType_CompositeDataType()
            .addAll(this.parents);
        comp.getInnerDeclaration_CompositeDataType()
            .addAll(this.innerDeclarations);

        return comp;
    }
}
