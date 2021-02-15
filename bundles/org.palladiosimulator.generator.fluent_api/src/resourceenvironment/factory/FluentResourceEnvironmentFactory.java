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

public class FluentResourceEnvironmentFactory {
    private ResourceEnvironmentCreator resourceEnvironmentCreator;

    public IResourceEnvironment newResourceEnvironment() {
        EcorePlugin.ExtensionProcessor.process(null);
        final ResourceRepository resources = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        this.resourceEnvironmentCreator = new ResourceEnvironmentCreator(resources, validator);
        return this.resourceEnvironmentCreator;
    }

    public ResourceContainerCreator newResourceContainer() {
        return new ResourceContainerCreator(this.resourceEnvironmentCreator);
    }

    public ProcessingResourceSpecificationCreator newProcessingResourceSpecification() {
        return new ProcessingResourceSpecificationCreator(this.resourceEnvironmentCreator);
    }

    public HddProcessingResourceSpecificationCreator newHddProcessingResourceSpecification() {
        return new HddProcessingResourceSpecificationCreator(this.resourceEnvironmentCreator);
    }

    public LinkingResourceCreator newLinkingResource() {
        return new LinkingResourceCreator(this.resourceEnvironmentCreator);
    }
}
