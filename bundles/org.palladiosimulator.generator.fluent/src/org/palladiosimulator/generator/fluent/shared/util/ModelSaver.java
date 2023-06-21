package org.palladiosimulator.generator.fluent.shared.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.generator.fluent.exceptions.FluentApiException;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

/**
 * This utility class contains functions to save Repositories,
 * ResourceTypeReopsitories, Systems, ResourceEnvironemnts and Allocations.
 *
 * @author Florian Krone
 * @author Yves Kirschner
 * @see org.palladiosimulator.pcm.repository.Repository
 * @see org.palladiosimulator.pcm.system.System
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * @see org.palladiosimulator.pcm.allocation.Allocation
 */
public final class ModelSaver {

	private static void save(final EObject model, final String path, final String name, final String extension) {
		if (model == null) {
			throw new FluentApiException("The EObject that is the root of the saved model cannot be null.");
		}
		if ((path == null) || path.isBlank() || (name == null) || name.isBlank() || (extension == null)
				|| extension.isBlank()) {
			throw new FluentApiException(
					"The elements of the path where the model will be saved with the specified file name and extension must not be blank.");
		}

		// Register the resource
		final ResourceSet rs = new ResourceSetImpl();
		for (final String fe : new String[] { extension, "xmi" }) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fe, new XMIResourceFactoryImpl());
		}

		// Create the file
		final String fileName = name.strip() + "." + extension.strip();
		final File file = Paths.get(path.strip(), fileName).toAbsolutePath().normalize().toFile();

		final URI uri = URI.createFileURI(fileName);
		final Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

		// Put content to file resource
		resource.getContents().add(model);

		// Save options for the resource
		((XMIResource) resource).setEncoding("UTF-8");
		final Map<Object, Object> saveOptions = ((XMIResource) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());

		// Creates the directory and save the file
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			try (final FileOutputStream out = new FileOutputStream(file)) {
				resource.save(out, saveOptions);
			}
		} catch (final IOException e) {
			throw new FluentApiException(e);
		}

	}

	/**
	 * Saves an Allocation to the specified location.
	 *
	 * @param org.palladiosimulator.generator.fluent.allocation
	 * @param path                                              the path to the
	 *                                                          target file
	 *                                                          including the name
	 *                                                          without the file
	 *                                                          extension
	 * @see org.palladiosimulator.pcm.allocation.Allocation
	 */
	public static void saveAllocation(final Allocation allocation, final String path, final String name) {
		ModelSaver.save(allocation, path, name, "allocation");
	}

	/**
	 * Saves a Repository to the specified location.
	 *
	 * @param repository
	 * @param path       the path to the target file including the name without the
	 *                   file extension
	 * @see org.palladiosimulator.pcm.repository.Repository
	 */
	public static void saveRepository(final Repository repository, final String path, final String name) {
		ModelSaver.save(repository, path, name, "repository");
	}

	/**
	 * Saves a ResourceEnvironment to the specified location.
	 *
	 * @param resourceEnvironment
	 * @param path                the path to the target file including the name
	 *                            without the file extension
	 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
	 */
	public static void saveResourceEnvironment(final ResourceEnvironment resourceEnvironment, final String path,
			final String name) {
		ModelSaver.save(resourceEnvironment, path, name, "resourceenvironment");
	}

	/**
	 * Saves a System to the specified location.
	 *
	 * @param org.palladiosimulator.generator.fluent.system
	 * @param path                                          the path to the target
	 *                                                      file including the name
	 *                                                      without the file
	 *                                                      extension
	 * @see org.palladiosimulator.pcm.system.System
	 */
	public static void saveSystem(final System system, final String path, final String name) {
		ModelSaver.save(system, path, name, "system");
	}

	/**
	 * Saves a UsageModel to the specified location.
	 *
	 * @param usageModel
	 * @param path       the path to the target file including the name without the
	 *                   file extension
	 * @see org.palladiosimulator.pcm.usagemodel.UsageModel
	 */
	public static void saveUsageModel(final UsageModel usgModel, final String path, final String name) {
		ModelSaver.save(usgModel, path, name, "usagemodel");
	}

	/**
	 * Private utility class constructor
	 *
	 * @throws IllegalStateException on call
	 */
	private ModelSaver() throws IllegalStateException {
		throw new IllegalStateException("Utility classes cannot be instantiated.");
	}
}
