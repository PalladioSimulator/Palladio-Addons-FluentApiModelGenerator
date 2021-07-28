package org.palladiosimulator.generator.fluent.shared.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
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
 * @see org.palladiosimulator.pcm.repository.Repository
 * @see org.palladiosimulator.pcm.system.System
 * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
 * @see org.palladiosimulator.pcm.allocation.Allocation
 */
public class ModelSaver {

    /**
     * Saves a Repository to the specified location.
     *
     * @param repository
     * @param path           the path to the target file including the name without
     *                       the file extension
     * @param printToConsole prints the xml representation of the model to the
     *                       console if set to true
     * @see org.palladiosimulator.pcm.repository.Repository
     */
    public static void saveRepository(final Repository repository, final String path, final boolean printToConsole) {
        save(repository, path, "repository", printToConsole);
    }

    /**
     * Saves a System to the specified location.
     *
     * @param org.palladiosimulator.generator.fluent.system
     * @param path                                          the path to the target
     *                                                      file including the name
     *                                                      without the file
     *                                                      extension
     * @param printToConsole                                prints the xml
     *                                                      representation of the
     *                                                      model to the console if
     *                                                      set to true
     * @see org.palladiosimulator.pcm.system.System
     */
    public static void saveSystem(final System system, final String path, final boolean printToConsole) {
        save(system, path, "system", printToConsole);
    }

    /**
     * Saves a ResourceEnvironment to the specified location.
     *
     * @param resourceEnvironment
     * @param path                the path to the target file including the name
     *                            without the file extension
     * @param printToConsole      prints the xml representation of the model to the
     *                            console if set to true
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment
     */
    public static void saveResourceEnvironment(final ResourceEnvironment resourceEnvironment, final String path,
            final boolean printToConsole) {
        save(resourceEnvironment, path, "org.palladiosimulator.generator.fluent.resourceenvironment", printToConsole);
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
     * @param printToConsole                                    prints the xml
     *                                                          representation of
     *                                                          the model to the
     *                                                          console if set to
     *                                                          true
     * @see org.palladiosimulator.pcm.allocation.Allocation
     */
    public static void saveAllocation(final Allocation allocation, final String path, final boolean printToConsole) {
        save(allocation, path, "org.palladiosimulator.generator.fluent.allocation", printToConsole);
    }
    
    public static void saveUsageModel(final UsageModel usgModel, final String path, final boolean printToConsole) {
        save(usgModel, path, "usagemodel", printToConsole);
    }

    private static void save(final EObject model, final String path, final String extension,
            final boolean printToConsole) {
        final String outputFile = path + "." + extension;
        final String[] fileExtensions = new String[] { extension, "xml" };

        // Create File
        final ResourceSet rs = new ResourceSetImpl();
        for (final String fileext : fileExtensions) {
            rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
        }

        final URI uri = URI.createFileURI(outputFile);
        final Resource resource = rs.createResource(uri);
        ((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

        // Put content to file resource
        resource.getContents().add(model);

        // Save file
        ((XMLResource) resource).setEncoding("UTF-8");
        final Map<Object, Object> saveOptions = ((XMLResource) resource).getDefaultSaveOptions();
        saveOptions.put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
        saveOptions.put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());

        try {
            resource.save(saveOptions);
            if (printToConsole) {
                ((XMLResource) resource).save(java.lang.System.out, ((XMLResource) resource).getDefaultSaveOptions());
            }
        } catch (final IOException e) {
            throw new FluentApiException(e);
        } 
    }
}
