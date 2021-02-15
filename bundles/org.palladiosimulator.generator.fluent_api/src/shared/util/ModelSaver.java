package shared.util;

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
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.system.System;

public class ModelSaver {

    public static void saveRepository(final Repository repository, final String path, final String name,
            final boolean printToConsole) {
        save(repository, path, name, "repository", printToConsole);
    }

    public static void saveSystem(final System system, final String path, final String name,
            final boolean printToConsole) {
        save(system, path, name, "system", printToConsole);
    }

    public static void saveResourceEnvironment(final ResourceEnvironment resourceEnvironment, final String path,
            final String name, final boolean printToConsole) {
        save(resourceEnvironment, path, name, "resourceenvironment", printToConsole);
    }

    public static void saveAllocation(final Allocation allocation, final String path, final String name,
            final boolean printToConsole) {
        save(allocation, path, name, "allocation", printToConsole);
    }

    private static void save(final EObject model, final String path, final String name, final String extension,
            final boolean printToConsole) {
        final String outputFile = path + name + "." + extension;
        final String[] fileExtensions = new String[] { extension, "xml" };

        // Create File
        final ResourceSet rs = new ResourceSetImpl();
        for (final String fileext : fileExtensions) {
            rs.getResourceFactoryRegistry()
                .getExtensionToFactoryMap()
                .put(fileext, new XMLResourceFactoryImpl());
        }

        final URI uri = URI.createFileURI(outputFile);
        final Resource resource = rs.createResource(uri);
        ((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());

        // Put content to file resource
        resource.getContents()
            .add(model);

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
            throw new RuntimeException(e);
        }
    }
}
