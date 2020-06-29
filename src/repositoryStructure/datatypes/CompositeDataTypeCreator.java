package repositoryStructure.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.DataType;

public class CompositeDataTypeCreator {

	private String name;
	private List<CompositeDataType> parents;
	private List<InnerDeclaration> innerDeclarations;
	
	public CompositeDataTypeCreator(String name, CompositeDataType[] parents) {
		this.name = name;
		this.parents = new ArrayList<>();
		this.innerDeclarations = new ArrayList<>();
		this.parents.addAll(Arrays.asList(parents));
	}
	
	public CompositeDataTypeCreator withInnerDeclaration(String name, Primitive primitive) {
		InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		inner.setEntityName(name);
		PrimitiveDataType p = PrimitiveType.getPrimitiveDataType(primitive);
		inner.setDatatype_InnerDeclaration(p);
		innerDeclarations.add(inner);
		return this;
	}
	
	public CompositeDataTypeCreator withInnerDeclaration(String name, DataType type) {
		InnerDeclaration inner = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		inner.setEntityName(name);
		inner.setDatatype_InnerDeclaration(type);
		innerDeclarations.add(inner);
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
