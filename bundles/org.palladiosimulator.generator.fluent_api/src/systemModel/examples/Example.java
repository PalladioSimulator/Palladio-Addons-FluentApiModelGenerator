package systemModel.examples;

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
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.system.System;

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
				.addToSystem(create.newAssemblyContext()
						.withName("basic component context 1")
						.withEncapsulatedComponent("basic component"))
				.addToSystem(create.newAssemblyContext()
						.withName("basic component context 2")
						.withEncapsulatedComponent("basic component"))
				.addToSystem(create.newAssemblyConnector()
						.withName("connector")
						.withRequiringAssemblyContext("basic component context 1")
						.withOperationRequiredRole("basic component requires interface")
						.withProvidingAssemblyContext("basic component context 2")
						.withOperationProvidedRole("basic component provides interface"))
				.addToSystem(create.newOperationRequiredRole()
						.withName("system required role")
						.withRequiredInterface("interface"))
				.addToSystem(create.newRequiredDelegationConnectorCreator()
						.withName("required delegation")
						.withOuterRequiredRole("system required role")
						.withRequiringContext("basic component context 2")
						.withOperationRequiredRole("basic component requires interface"))
				.addToSystem(create.newOperationProvidedRole()
						.withName("system provided role")
						.withProvidedInterface("interface"))
				.addToSystem(create.newProvidedDelegationConnectorCreator()
						.withName("provided delegation")
						.withOuterProvidedRole("system provided role")
						.withProvidingContext("basic component context 1")
						.withOperationProvidedRole("basic component provides interface"))
				.addToSystem(create.newEventChannelCreator()
						.withName("event channel")
						.withEventGroup("event group"))
				.addToSystem(create.newEventChannelSinkConnector()
						.withName("sink connector")
						.withEventChannel("event channel")
						.withAssemblyContext("basic component context 1")
						.withSinkRole("handles event"))
				.addToSystem(create.newEventChannelSourceConnector()
						.withName("source connector")
						.withEventChannel("event channel")
						.withAssemblyContext("basic component context 2")
						.withSourceRole("emits event"))
				.addToSystem(create.newSinkRole()
						.withName("system sink role")
						.withEventGroup("event group"))
				.addToSystem(create.newSinkDelegationConnector()
						.withName("sink delegation")
						.withOuterSinkRole("system sink role")
						.withAssemblyContext("basic component context 2")
						.withSinkRole("handles event"))
				.addToSystem(create.newSourceRole()
						.withName("system source role")
						.withEventGroup("event group"))
				.addToSystem(create.newSourceDelegationConnector()
						.withName("spurce delegation")
						.withOuterSourceRole("system source role")
						.withAssemblyContext("basic component context 1")
						.withSourceRole("emits event"))
				.addToSystem(create.newAssemblyInfrastructureConnector()
						.withName("infrastructure connector")
						.withRequiringAssemblyContext("basic component context 1")
						.withInfrastructureRequiredRole("requres infrastructure")
						.withProvidingAssemblyContext("basic component context 2")
						.withInfrastructureProvidedRole("provides infrastructure"))
				.addToSystem(create.newInfrastructurenProvidedRole()
						.withName("infrastructure provided role")
						.withProvidedInterface("infrastructure interface"))
				.addToSystem(create.newInfrastructureRequiredRole()
						.withName("infrastructure required role")
						.withRequiredInterface("infrastructure interface"))
				.addToSystem(create.newProvidedInfrastructureDelegationConnectorCreator()
						.withName("infrastructure provided delegation")
						.withOuterProvidedRole("infrastructure provided role")
						.withProvidingContext("basic component context 1")
						.withInfrastructureProvidedRole("provides infrastructure"))
				.addToSystem(create.newRequiredInfrastructureDelegationConnectorCreator()
						.withName("infrastructure required delegation")
						.withOuterRequiredRole("infrastructure required role")
						.withRequiringContext("basic component context 2")
						.withInfrastructuRequiredRole("requres infrastructure"))
				.addToSystem(create.newQoSAnnotations().withName("annotations"))
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
