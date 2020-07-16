package repositoryStructure.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

import org.palladiosimulator.pcm.repository.DataType;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.repository.CompositeDataType
 * CompositeDataType}. It is used to create the
 * '<em><b>CompositeDataType</b></em>' object step-by-step, i.e.
 * '<em><b>CompositeDataTypeCreator</b></em>' objects are of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.CompositeDataType
 */
public class CompositeDataTypeCreator extends Entity{

	private List<CompositeDataType> parents;
	private List<InnerDeclaration> innerDeclarations;

	public CompositeDataTypeCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.parents = new ArrayList<>();
		this.innerDeclarations = new ArrayList<>();
	}

	@Override
	public CompositeDataTypeCreator withName(String name) {
		return (CompositeDataTypeCreator) super.withName(name);
	}
	
	/**
	 * Adds an inner data type to a composite data type with name <code>name</code>
	 * and of type <code>primitive</code>.
	 * <p>
	 * This corresponds to the declaration of fields in a Java class. For example
	 * <code>.withInnerDeclaration("age", Primitive.INTEGER)</code> conforms with
	 * <code>public int age;</code>.
	 * 
	 * @param name      for the inner data type
	 * @param primitive inner data type
	 * @return the composite data type in the making
	 * @see org.palladiosimulator.pcm.repository.CompositeDataType
	 */
	public CompositeDataTypeCreator withInnerDeclaration(String name, Primitive primitive) {
		InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		inner.setEntityName(name);
		PrimitiveDataType p = repository.getPrimitiveDataType(primitive);
		inner.setDatatype_InnerDeclaration(p);
		innerDeclarations.add(inner);
		return this;
	}

	/**
	 * Adds an inner data type to a composite data type with name <code>name</code>
	 * and of type <code>dataType</code>.
	 * <p>
	 * This corresponds to the declaration of fields in a Java class. For example
	 * <code>.withInnerDeclaration("age", Primitive.INTEGER)</code> conforms with
	 * <code>public int age;</code>.<br>
	 * Non primitive data types can be fetched from the repository using the method
	 * {@link factory.FluentRepositoryFactory#fetchOfDataType(String)
	 * fetchOfDataType(String)}.
	 * 
	 * @param name     for the inner data type
	 * @param dataType inner data type
	 * @return the composite data type in the making
	 * @see factory.FluentRepositoryFactory#fetchOfDataType(String)
	 * @see org.palladiosimulator.pcm.repository.CompositeDataType
	 */
	public CompositeDataTypeCreator withInnerDeclaration(String name, DataType dataType) {
		InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		inner.setEntityName(name);
		inner.setDatatype_InnerDeclaration(dataType);
		innerDeclarations.add(inner);
		return this;
	}
	
	public CompositeDataTypeCreator withParentCompositeDataType(CompositeDataType parent) {
		this.parents.add(parent);
		return this;
	}

	public CompositeDataType build() {
		CompositeDataType comp = RepositoryFactory.eINSTANCE.createCompositeDataType();
		comp.setEntityName(name);

		comp.getParentType_CompositeDataType().addAll(parents);
		comp.getInnerDeclaration_CompositeDataType().addAll(innerDeclarations);

		return comp;
	}
}
