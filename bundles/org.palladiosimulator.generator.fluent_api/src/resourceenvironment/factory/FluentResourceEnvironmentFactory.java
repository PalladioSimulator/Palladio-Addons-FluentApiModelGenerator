package resourceenvironment.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

import resourceenvironment.apiControlFlowInterfaces.IResourceEnvironment;
import resourceenvironment.structure.HddProcessingResourceSpecificationCreator;
import resourceenvironment.structure.LinkingResourceCreator;
import resourceenvironment.structure.ProcessingResourceSpecificationCreator;
import resourceenvironment.structure.ResourceContainerCreator;
import resourceenvironment.structure.ResourceEnvironmentCreator;
import shared.util.ModelLoader;
import shared.validate.IModelValidator;
import shared.validate.ModelValidator;

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
