package org.palladiosimulator.generator.fluent.resourceenvironment.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.resourceenvironment.api.IResourceEnvironment;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.HddProcessingResourceSpecificationCreator;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.LinkingResourceCreator;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.ProcessingResourceSpecificationCreator;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.ResourceContainerCreator;
import org.palladiosimulator.generator.fluent.resourceenvironment.structure.ResourceEnvironmentCreator;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

/**
 * This class provides all the methods to create a
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * ResourceEnvironment} and create entities that are added to this
 * ResourceEnvironment. Characteristics of the entities are specified by method
 * chaining.<br>
 * <p>
 * Start creating a ResourceEnvironment like this:<br>
 * <code>
 * FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();<br>
 * ResourceEnvironment environment = create.newResourceEnvironment()<br>
 *      <p style=
"margin-left:40px">//add Resources</p>
 *      <p style="margin-left:40px">.createResourceEnvironmentNow();</p>
 * </code>
 * </p>
 *
 * @author Florian Krone
 */
public class FluentResourceEnvironmentFactory {
    private ResourceEnvironmentCreator resourceEnvironmentCreator;

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     * ResourceEnvironment}.
     *
     * @return the <code>ResourceEnvironemt</code> in the making
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    public IResourceEnvironment newResourceEnvironment() {
        EcorePlugin.ExtensionProcessor.process(null);
        final ResourceRepository resources = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        resourceEnvironmentCreator = new ResourceEnvironmentCreator(resources, validator);
        return resourceEnvironmentCreator;
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @return the <code>ResourceContainer</code> in the making
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public ResourceContainerCreator newResourceContainer() {
        return new ResourceContainerCreator(resourceEnvironmentCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @return the <code>ProcessingResourceSpecification</code> in the making
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator newProcessingResourceSpecification() {
        return new ProcessingResourceSpecificationCreator(resourceEnvironmentCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @return the <code>HDDProcessingResourceSpecification</code> in the making
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator newHddProcessingResourceSpecification() {
        return new HddProcessingResourceSpecificationCreator(resourceEnvironmentCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     * LinkingResource}.
     *
     * @return the <code>LinkingResource</code> in the making
     * @see org.palladiosimulator.pcm.resourceenvironment.LinkingResource
     */
    public LinkingResourceCreator newLinkingResource() {
        return new LinkingResourceCreator(resourceEnvironmentCreator);
    }
}
