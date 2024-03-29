package org.palladiosimulator.generator.fluent.resourceenvironment.api;

import org.palladiosimulator.generator.fluent.resourceenvironment.structure.LinkingResourceCreator;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.ResourceContainerCreator;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * TODO
 */
public interface IResourceEnvironmentAddition {

    /**
     * Completes the resource environment creation
     *
     * @return the created resource environment
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    ResourceEnvironment createResourceEnvironmentNow();

    /**
     * Adds a {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer} to the resource environment. The creator will be turned into the finished
     * container.
     *
     * @param resourceContainer
     * @return this resource environment
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * @see org.palladiosimulator.generator.fluent.resourceenvironment.factory.FluentResourceEnvironmentFactory#newResourceContainer()
     */
    IResourceEnvironmentAddition addToResourceEnvironment(ResourceContainerCreator resourceContainer);

    /**
     * Adds a {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource LinkingResource}
     * to the resource environment. The creator will be turned into the finished resource.
     *
     * @param linkingResource
     * @return this resource environment
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     * @see org.palladiosimulator.generator.fluent.resourceenvironment.factory.FluentResourceEnvironmentFactory#newLinkingResource()
     */
    IResourceEnvironmentAddition addToResourceEnvironment(LinkingResourceCreator linkingResource);
}
