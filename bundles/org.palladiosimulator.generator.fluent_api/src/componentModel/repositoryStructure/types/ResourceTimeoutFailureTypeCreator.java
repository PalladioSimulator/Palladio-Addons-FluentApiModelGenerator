package componentModel.repositoryStructure.types;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;

import componentModel.repositoryStructure.RepositoryEntity;
import componentModel.repositoryStructure.RepositoryCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
 * ResourceTimeoutFailureType}. It is used to create the
 * '<em><b>ResourceTimeoutFailureType</b></em>' object and prevent the usage of
 * the creating method in the factory at a later point.
 * '<em><b>ResourceTimeoutFailureTypeCreator</b></em>' objects are of
 * intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType
 */
public class ResourceTimeoutFailureTypeCreator extends RepositoryEntity {

	public ResourceTimeoutFailureTypeCreator(String name, RepositoryCreator repo) {
		this.name = name;
		this.repository = repo;
	}

	@Override
	public FailureType build() {
		ResourceTimeoutFailureType timeout = ReliabilityFactory.eINSTANCE.createResourceTimeoutFailureType();
		timeout.setEntityName(name);
		return timeout;
	}

}
