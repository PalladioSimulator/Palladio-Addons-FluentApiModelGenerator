package repositoryStructure.interfaces;

import apiControlFlowInterfaces.Inter;

public abstract class Interface implements Inter{

	protected String name;
	public abstract org.palladiosimulator.pcm.repository.Interface build();
}
