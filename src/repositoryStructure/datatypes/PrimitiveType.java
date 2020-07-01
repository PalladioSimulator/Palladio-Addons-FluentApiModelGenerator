package repositoryStructure.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypeFactory;

public class PrimitiveType {

	private static Map<Primitive, PrimitiveDataType> primitives = new HashMap<>();

	private static void init() {
		RepositoryFactory fact = RepositoryFactory.eINSTANCE;

		PrimitiveDataType p1 = fact.createPrimitiveDataType();
		p1.setType(PrimitiveTypeEnum.BOOL);
		primitives.put(Primitive.BOOLEAN, p1);

		PrimitiveDataType p2 = fact.createPrimitiveDataType();
		p2.setType(PrimitiveTypeEnum.INT);
		primitives.put(Primitive.INTEGER, p2);

		PrimitiveDataType p3 = fact.createPrimitiveDataType();
		p3.setType(PrimitiveTypeEnum.STRING);
		primitives.put(Primitive.STRING, p3);

		PrimitiveDataType p4 = fact.createPrimitiveDataType();
		p4.setType(PrimitiveTypeEnum.DOUBLE);
		primitives.put(Primitive.DOUBLE, p4);

		PrimitiveDataType p5 = fact.createPrimitiveDataType();
		p5.setType(PrimitiveTypeEnum.LONG);
		primitives.put(Primitive.LONG, p5);

		PrimitiveDataType p6 = fact.createPrimitiveDataType();
		p6.setType(PrimitiveTypeEnum.CHAR);
		primitives.put(Primitive.CHAR, p6);

		PrimitiveDataType p7 = fact.createPrimitiveDataType();
		p7.setType(PrimitiveTypeEnum.BYTE);
		primitives.put(Primitive.BYTE, p7);

		PrimitiveDataType p8 = fact.createPrimitiveDataType();
		p8.setType(PrimitiveTypeEnum.BYTE);
		primitives.put(Primitive.BYTE, p8);
		
		//TODO: resource sachen
		ResourcetypeFactory resFact = ResourcetypeFactory.eINSTANCE;
		CommunicationLinkResourceType clr = resFact.createCommunicationLinkResourceType();
		resFact.createProcessingResourceType();
		resFact.createResourceInterface();
		resFact.createResourceRepository();
		resFact.createResourceSignature();
		resFact.createSchedulingPolicy();
		
		
		
	}

	public static PrimitiveDataType getPrimitiveDataType(Primitive primitive) {
		if(primitives.isEmpty())
			init();
		return primitives.get(primitive);
	}
}
