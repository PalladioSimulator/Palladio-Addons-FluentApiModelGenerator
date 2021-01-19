package systemModel.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.system.System;

import componentModel.repositoryStructure.components.seff.CollectionIteratorActionCreator;
import systemModel.factory.FluentSystemFactory;

public class Example {
	public static void main(String[] args) {
		basicExample();
	}
	
	private static void basicExample() {		
		FluentSystemFactory create = new FluentSystemFactory();
		System system = create.newSystem()
				.withRepository(loadRepository("./miniExample.repository"))
				.withName("basicSystem")
				.withAssembyContext(create.newAssemblyContext()
						.withName("basic component context 1")
						.withEncapsulatedComponentByName("basic component").build())
				.withAssembyContext(create.newAssemblyContext()
						.withName("basic component context 2")
						.withEncapsulatedComponentByName("basic component").build())
				.withAssemblyConnector(create.newAssemblyConnector()
						.withName("connector")
						.withRequiringAssemblyContext("basic component context 1")
						.withOperationRequiredRole("basic component requires interface")
						.withProvidingAssemblyContext("basic component context 2")
						.withOperationProvidedRole("basic component provides interface")
						.build())
				.withOperationRequiredRole(create.newOperationRequiredRole()
						.withName("system required role")
						.withRequiredInterfaceByName("interface")
						.build())
				.withRequiredDelegationConnector(create.newRequiredDelegationConnectorCreator()
						.withName("required delegation")
						.withOuterRequiredRoleByName("system required role")
						.withRequiringContextByName("basic component context 2")
						.withOperationRequiredRoleByName("basic component requires interface")
						.build())
				.withOperationProvidedRole(create.newOperationProvidedRole()
						.withName("system provided role")
						.withProvidedInterfaceByName("interface")
						.build())
				.withProvidedDelegationConnector(create.newProvidedDelegationConnectorCreator()
						.withName("provided delegation")
						.withOuterProvidedRoleByName("system provided role")
						.withProvidingContextByName("basic component context 1")
						.withOperationProvidedRoleByName("basic component provides interface")
						.build())
				.withEventChannel(create.newEventChannelCreator()
						.withName("event channel")
						.withEventGroupByName("event group")
						.build())
				.withEventChannelSinkRoleConnector(create.newEventChannelSinkConnector()
						.withName("sink connector")
						.withEventChannel("event channel")
						.withAssemblyContext("basic component context 1")
						.withSinkRole("handles event")
						.build())
				.withEventChannelSourceRoleConnector(create.newEventChannelSourceConnector()
						.withName("source connector")
						.withEventChannel("event channel")
						.withAssemblyContext("basic component context 2")
						.withSourceRole("emits event")
						.build())
				.createSystemNow();
		saveSystem(system, "./", "basicExample.system", true);
	}
	
	public static void saveSystem(System system, String path, String name, boolean printToConsole) {
		String outputFile = path + name;
		String[] fileExtensions = new String[] { "system", "xml" };

		// Create File
		ResourceSet rs = new ResourceSetImpl();
		for (String fileext : fileExtensions)
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());

		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

		// Put content to file resource
		resource.getContents().add(system);

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
	
	private static Repository loadRepository(String uri) {
		RepositoryPackage.eINSTANCE.eClass();
		// Register the XMI resource componentModel.factory for the .repository extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("repository", new XMIResourceFactoryImpl());
		// Get the resource
		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.getResource(URI.createURI(uri), true);
		// Get the first model element and cast it to the right type
		Repository repository = (Repository) resource.getContents().get(0);
		return repository;
	}
}
