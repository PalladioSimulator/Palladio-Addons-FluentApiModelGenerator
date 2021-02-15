package shared.util;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourcetypePackage;
import org.palladiosimulator.pcm.system.System;

public class ModelLoader {

    public static final String PRIMITIVE_TYPES_PATH = "pathmap://PCM_MODELS/PrimitiveTypes.repository";
    public static final String RESOURCE_TYPE_PATH = "pathmap://PCM_MODELS/Palladio.resourcetype";
    public static final String FAILURE_TYPES_PATH = "pathmap://PCM_MODELS/FailureTypes.repository";

    public static Repository loadRepository(final String uri) {
        return (Repository) load(uri, "repository");
    }

    public static ResourceRepository loadResourceTypeRepository(final String uri) {
        return (ResourceRepository) load(uri, "resourcetype");
    }

    public static System loadSystem(final String uri) {
        return (System) load(uri, "system");
    }

    public static ResourceEnvironment loadResourceEnvironment(final String uri) {
        return (ResourceEnvironment) load(uri, "resourceenvironment");
    }

    private static EObject load(final String uri, final String type) {
        ResourcetypePackage.eINSTANCE.eClass();
        // Register the XMI resource component.factory for the .resourcetype extension
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(type, new XMIResourceFactoryImpl());
        // Get the resource
        final ResourceSet resSet = new ResourceSetImpl();
        final Resource resource = resSet.getResource(URI.createURI(uri), true);
        // Get the first model element
        return resource.getContents()
            .get(0);
    }
}
