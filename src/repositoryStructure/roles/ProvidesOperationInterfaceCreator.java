package repositoryStructure.roles;

import org.palladiosimulator.pcm.repository.Interface;

public class ProvidesOperationInterfaceCreator {
	
	String name;
//	String id;
	Interface interfce;
	RoleType type;
	
	public ProvidesOperationInterfaceCreator(Interface interfce, RoleType type) {
		this(interfce, type, null);
	}
	
	public ProvidesOperationInterfaceCreator(Interface interfce, RoleType type, String name) {
		this.name = name;
		this.interfce = interfce;
		this.type = type;	
	}
	
	public enum RoleType{
		ProvidedRole, InfrastructureProvidedRole, SinkRole
	}

}
