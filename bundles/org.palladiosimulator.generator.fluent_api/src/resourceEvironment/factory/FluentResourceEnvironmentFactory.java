package resourceEvironment.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

import resourceEvironment.apiControlFlowInterfaces.IResourceEnvironment;
import resourceEvironment.structure.ProcessingResourceSpecificationCreator;
import resourceEvironment.structure.ResourceContainerCreator;
import resourceEvironment.structure.ResourceEnvironmentCreator;
import shared.util.RepositoryLoader;

public class FluentResourceEnvironmentFactory {
	private ResourceEnvironmentCreator resourceEnvironmentCreator;
	
	public IResourceEnvironment newResourceEnvironment() {
		EcorePlugin.ExtensionProcessor.process(null);
		ResourceRepository resources = RepositoryLoader.loadResourceTypeRepository(RepositoryLoader.RESOURCE_TYPE_PATH);
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		//IModelValidator validator = new ModelValidator(SystemValidator.INSTANCE, logger);
		resourceEnvironmentCreator = new ResourceEnvironmentCreator(resources);
		return resourceEnvironmentCreator;
	}
	
	public ResourceContainerCreator newResourceContainer() {
		return new ResourceContainerCreator(resourceEnvironmentCreator);
	}
	
	public ProcessingResourceSpecificationCreator newProcessingResourceSpecification() {
		return new ProcessingResourceSpecificationCreator(resourceEnvironmentCreator);
	}
}
