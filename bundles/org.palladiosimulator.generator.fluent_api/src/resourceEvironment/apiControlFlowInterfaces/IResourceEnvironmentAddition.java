package resourceEvironment.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

import resourceEvironment.structure.ResourceContainerCreator;

public interface IResourceEnvironmentAddition {

	/**
	 * Turns this resource-environment-in-the-making into a
	 * Palladio-'<em><b>ResourceEnvironment</b></em>' object.
	 * 
	 * @return the final ResourceEnvironment object
	 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
	 */
	ResourceEnvironment createResourceEnvironmentNow();
	
	IResourceEnvironmentAddition addToResourceEnvironment(ResourceContainerCreator resourceContainer);
}
