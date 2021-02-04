package resourceEvironment.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

import resourceEvironment.factory.FluentResourceEnvironmentFactory;
import shared.structure.CommunicationLinkResource;
import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;

public class Example {
	public static void main(String[] args) {
		basicResourceExample();
	}
	
	public static void basicResourceExample() {
		FluentResourceEnvironmentFactory create = new FluentResourceEnvironmentFactory();
		ResourceEnvironment environment = create.newResourceEnvironment()
				.withName("resource environment")
				.addToResourceEnvironment(create.newResourceContainer()
						.withName("container 1")
						.addProcessingResourceSpecification(create.newProcessingResourceSpecification()
								.withMttf(3)
								.withMttr(4)
								.isRequiredByContainer()
								.withNumberOfReplicas(2)
								.withSchedulingPolicy(SchedulingPolicies.FIRST_COME_FIRST_SERVE)
								.withProcessingResourceType(ProcessingResource.CPU)
								//.withProcessingRate("123")
								))
				.addToResourceEnvironment(create.newResourceContainer()
						.withName("container 2")
						.addHddProcessingResourceSpecification(create.newHddProcessingResourceSpecification()
								.withMttf(2)
								.withMttr(5.3)
								.withNumberOfReplicas(1)
								.withSchedulingPolicy(SchedulingPolicies.DELAY)
								.withProcessingResourceType(ProcessingResource.HDD)
								))
				.addToResourceEnvironment(create.newLinkingResource()
						.withName("linkin resource")
						.withCommunicationLinkResource(CommunicationLinkResource.LAN)
						.withFailureProbability(0.2)
						.addLinkedResourceContainer("container 1")
						.addLinkedResourceContainer("container 2"))
				.createResourceEnvironmentNow();
		saveEnvironment(environment, "./", "basicEnvironment.resourceenvironment", true);
	}
	
	public static void saveEnvironment(ResourceEnvironment environment, String path, String name, boolean printToConsole) {
		String outputFile = path + name;
		String[] fileExtensions = new String[] { "resourceenvironment", "xml" };

		// Create File
		ResourceSet rs = new ResourceSetImpl();
		for (String fileext : fileExtensions)
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());

		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

		// Put content to file resource
		resource.getContents().add(environment);

		// Save file
		((XMLResource) resource).setEncoding("UTF-8");
		Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());

		try {
			resource.save(saveOptions);
			if (printToConsole)
				((XMLResource) resource).save(java.lang.System.out, ((XMLResource) resource).getDefaultSaveOptions());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
