package org.palladiosimulator.generator.fluent.component.repositoryStructure.types;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryEntity;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;

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

    public ResourceTimeoutFailureTypeCreator(final String name, final RepositoryCreator repo) {
        this.name = name;
        repository = repo;
    }

    @Override
    public FailureType build() {
        final ResourceTimeoutFailureType timeout = ReliabilityFactory.eINSTANCE.createResourceTimeoutFailureType();
        timeout.setEntityName(name);
        return timeout;
    }

}
