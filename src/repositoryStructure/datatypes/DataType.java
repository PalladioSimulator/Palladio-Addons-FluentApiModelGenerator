package repositoryStructure.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;


public class DataType {
	
	
	protected RepositoryCreator repository;
	Map<Primitives, PrimitiveDataType> primitiveDataTypes;
	
	
	protected DataType() {
		
	}
	public DataType(RepositoryCreator repo) {
		primitiveDataTypes = new HashMap<>();
		
		PrimitiveDataType primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.BOOL);
		primitiveDataTypes.put(Primitives.BOOLEAN, primitiveDataType);
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.INT);
		primitiveDataTypes.put(Primitives.INTEGER, primitiveDataType);
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.STRING);
		primitiveDataTypes.put(Primitives.STRING, primitiveDataType);
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.DOUBLE);
		primitiveDataTypes.put(Primitives.DOUBLE, primitiveDataType);
		
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.LONG);
		primitiveDataTypes.put(Primitives.LONG, primitiveDataType);
		
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.CHAR);
		primitiveDataTypes.put(Primitives.CHAR, primitiveDataType);
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.BYTE);
		primitiveDataTypes.put(Primitives.BYTE, primitiveDataType);
		
		primitiveDataType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		primitiveDataType.setType(PrimitiveTypeEnum.BYTE);
		primitiveDataTypes.put(Primitives.BYTE, primitiveDataType);
		
		
		RepositoryFactory.eINSTANCE.createCollectionDataType();
		RepositoryFactory.eINSTANCE.createCompositeDataType();
		RepositoryFactory.eINSTANCE.eAllContents().forEachRemaining(a -> System.out.println(a));
		
	}
	
	public enum Primitives {
		BOOLEAN, INTEGER, STRING, DOUBLE, LONG, CHAR, BYTE
	}
	
	public enum Failures {
		HARDWARE_CPU, HARDWARE_HDD, HARDWARE_DELAY, NETWORK_LAN, SOFTWARE
	}

}
