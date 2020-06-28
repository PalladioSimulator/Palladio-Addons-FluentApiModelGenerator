package repositoryStructure.interfaces;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import repositoryStructure.RepositoryCreator;

public class InfrastructureInterfaceCreator extends Interface {

	public InfrastructureInterfaceCreator(RepositoryCreator repo) {

	}

	@Override
	public InfrastructureInterfaceCreator withName(String name) {
		return (InfrastructureInterfaceCreator) super.withName(name);
	}

	@Override
	public InfrastructureInterfaceCreator withId(String id) {
		return (InfrastructureInterfaceCreator) super.withId(id);
	}

	// parent Interfaces
	@Override
	public InfrastructureInterfaceCreator conforms(org.palladiosimulator.pcm.repository.Interface interfce) {
		return (InfrastructureInterfaceCreator) super.conforms(interfce);
	}

	@Override
	public InfrastructureInterfaceCreator withRequiredCharacterisation(Parameter parameter,
			VariableCharacterisationType type) {
		return (InfrastructureInterfaceCreator) super.withRequiredCharacterisation(parameter, type);
	}

	@Override
	public InfrastructureInterfaceCreator withProtocol() {
		return (InfrastructureInterfaceCreator) super.withProtocol();
	}

	@Override
	public InfrastructureInterface build() {
		InfrastructureInterface interfce = RepositoryFactory.eINSTANCE.createInfrastructureInterface();

		if (name != null)
			interfce.setEntityName(name);
		if (id != null)
			interfce.setId(id);

		// TODO: add to Lists
		interfce.getParentInterfaces__Interface();
		interfce.getProtocols__Interface();
		interfce.getRequiredCharacterisations();

		interfce.getInfrastructureSignatures__InfrastructureInterface();

		return interfce;
	}

}
