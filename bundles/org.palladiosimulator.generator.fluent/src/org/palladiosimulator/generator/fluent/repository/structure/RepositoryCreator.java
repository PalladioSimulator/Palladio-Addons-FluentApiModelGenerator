package org.palladiosimulator.generator.fluent.repository.structure;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.palladiosimulator.generator.fluent.exceptions.FluentApiException;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
import org.palladiosimulator.generator.fluent.exceptions.NoSuchElementException;
import org.palladiosimulator.generator.fluent.repository.api.Repo;
import org.palladiosimulator.generator.fluent.repository.api.RepoAddition;
import org.palladiosimulator.generator.fluent.repository.structure.components.Component;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Failure;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.generator.fluent.repository.structure.types.CompositeDataTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.types.ExceptionTypeCreator;
import org.palladiosimulator.generator.fluent.repository.structure.types.ResourceTimeoutFailureTypeCreator;
import org.palladiosimulator.generator.fluent.shared.structure.CommunicationLinkResource;
import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.ResourceRequiredRole;
import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompleteComponentType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.EventGroup;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.ExceptionType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureProvidedRole;
import org.palladiosimulator.pcm.repository.InfrastructureRequiredRole;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidesComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.repository.SinkRole;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceInterface;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceSignature;
import org.palladiosimulator.pcm.resourcetype.ResourceType;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.subsystem.SubSystem;

/**
 * This class constructs a {@link org.palladiosimulator.pcm.repository.Repository Repository}. It is
 * used to create the '<em><b>Repository</b></em>' object step-by-step, i.e.
 * '<em><b>RepositoryCreator</b></em>' objects are of intermediate state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.Repository
 */
public class RepositoryCreator extends RepositoryEntity implements Repo, RepoAddition {

    private final Logger logger;
    private String description;
    private final IModelValidator validator;

    private final List<Repository> imports;
    private final List<DataType> importedDataTypes;
    private final List<FailureType> importedFailureTypes;
    private final List<RepositoryComponent> importedComponents;
    private final List<Interface> importedInterfaces;
    private final List<DataType> dataTypes;
    private final Map<Primitive, PrimitiveDataType> internalPrimitives;
    private final Map<ProcessingResource, ProcessingResourceType> internalProcessingResources;
    private final Map<org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature, ResourceSignature> internalResourceSignatures;
    private final Map<CommunicationLinkResource, CommunicationLinkResourceType> internalCommunicationLinkResources;
    private final Map<org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface, ResourceInterface> internalResourceInterfaces;
    private final Map<Failure, FailureType> internalFailureTypes;
    private final List<FailureType> failureTypes;
    private final List<ExceptionType> exceptionTypes;
    private final List<Interface> interfaces;
    private final List<RepositoryComponent> components;
    private final List<ProvidedRole> providedRoles;
    private final List<RequiredRole> requiredRoles;
    private final List<ResourceRequiredRole> resourceRequiredRoles;
    private final List<Parameter> parameters;
    private final List<AssemblyContext> assemblyContexts;
    private final List<EventChannel> eventChannels;
    private final List<Connector> connectors;
    private final List<RecoveryActionBehaviour> behaviours;
    private final List<PassiveResource> passiveResources;
    private final List<Signature> signatures;

    public RepositoryCreator(final Repository primitiveDataTypes, final ResourceRepository resourceTypes,
            final Repository failureTypes, final Logger logger, final IModelValidator validator) {
        this.imports = new ArrayList<>();
        this.importedDataTypes = new ArrayList<>();
        this.importedFailureTypes = new ArrayList<>();
        this.importedComponents = new ArrayList<>();
        this.importedInterfaces = new ArrayList<>();
        this.dataTypes = new ArrayList<>();
        this.internalPrimitives = new HashMap<>();
        this.internalProcessingResources = new HashMap<>();
        this.internalResourceSignatures = new HashMap<>();
        this.internalCommunicationLinkResources = new HashMap<>();
        this.internalResourceInterfaces = new HashMap<>();
        this.internalFailureTypes = new HashMap<>();
        this.failureTypes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.components = new ArrayList<>();
        this.providedRoles = new ArrayList<>();
        this.requiredRoles = new ArrayList<>();
        this.resourceRequiredRoles = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.assemblyContexts = new ArrayList<>();
        this.eventChannels = new ArrayList<>();
        this.connectors = new ArrayList<>();
        this.behaviours = new ArrayList<>();
        this.passiveResources = new ArrayList<>();
        this.exceptionTypes = new ArrayList<>();
        this.signatures = new ArrayList<>();

        initPredefinedDataTypesAndResources(primitiveDataTypes, resourceTypes, failureTypes);

        this.logger = logger;
        this.validator = validator;
    }

    private static Repository loadRepository(final String uri) {
        return RepositoryCreator.loadRepository(URI.createURI(uri));
    }

    private static Repository loadRepository(final URI uri) {
        RepositoryPackage.eINSTANCE.eClass();
        // Register the XMI resource factory for the .repository extension
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("repository", new XMIResourceFactoryImpl());
        // Get the resource
        final ResourceSet resSet = new ResourceSetImpl();
        final Resource resource = resSet.getResource(uri, true);
        // Get the first model element and cast it to the right type
        return (Repository) resource.getContents().get(0);
    }

    private void initPredefinedDataTypesAndResources(final Repository primitiveDataTypes,
            final ResourceRepository resourceTypes, final Repository failureTypes) {
        // Primitive Data Types
        final EList<DataType> dts = primitiveDataTypes.getDataTypes__Repository();
        for (final DataType d : dts) {
            final PrimitiveDataType p = (PrimitiveDataType) d;
            final PrimitiveTypeEnum type = p.getType();
            switch (type) {
            case BOOL:
                this.internalPrimitives.put(Primitive.BOOLEAN, p);
                break;
            case BYTE:
                this.internalPrimitives.put(Primitive.BYTE, p);
                break;
            case CHAR:
                this.internalPrimitives.put(Primitive.CHAR, p);
                break;
            case DOUBLE:
                this.internalPrimitives.put(Primitive.DOUBLE, p);
                break;
            case INT:
                this.internalPrimitives.put(Primitive.INTEGER, p);
                break;
            case LONG:
                this.internalPrimitives.put(Primitive.LONG, p);
                break;
            case STRING:
                this.internalPrimitives.put(Primitive.STRING, p);
                break;
            default: // just ignore everything not matching any type
            }
        }

        // ResourceTypes
        for (final ResourceType resourceType : resourceTypes.getAvailableResourceTypes_ResourceRepository()) {
            if (resourceType instanceof ProcessingResourceType) {
                final ProcessingResourceType p = (ProcessingResourceType) resourceType;
                switch (p.getEntityName()) {
                    case "CPU":
                        this.internalProcessingResources.put(ProcessingResource.CPU, p);
                        break;
                    case "HDD":
                        this.internalProcessingResources.put(ProcessingResource.HDD, p);
                        break;
                    case "DELAY":
                        this.internalProcessingResources.put(ProcessingResource.DELAY, p);
                        break;
                    default:
                        logger.warning("Unexpected Processing Resource Type.");
                }

            } else if (resourceType instanceof CommunicationLinkResourceType) {
                this.internalCommunicationLinkResources.put(CommunicationLinkResource.LAN,
                        (CommunicationLinkResourceType) resourceType);
            }
        }

        // Resource interfaces and signatures
        for (final ResourceInterface resourceInterface : resourceTypes.getResourceInterfaces__ResourceRepository()) {
            switch (resourceInterface.getEntityName()) {
                case "CpuInterface":
                    this.internalResourceInterfaces.put(
                        org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface.CPU,
                        resourceInterface);
                    break;
                case "HddInterface":
                    this.internalResourceInterfaces.put(
                        org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface.HDD,
                        resourceInterface);
                    break;
                default:
                    logger.warning("Unexpected Resource Interface.");
            }

            for (final ResourceSignature s : resourceInterface.getResourceSignatures__ResourceInterface()) {
                switch (s.getEntityName()) {
                    case "process":
                        this.internalResourceSignatures.put(
                            org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature.PROCESS,
                            s);
                        break;
                    case "read":
                        this.internalResourceSignatures.put(
                            org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature.READ,
                            s);
                        break;
                    case "write":
                        this.internalResourceSignatures.put(
                            org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature.WRITE,
                            s);
                        break;
                    default:
                        logger.warning("Unexpected Resource Signature.");
                }
            }
        }

        // FailureTypes
        final EList<FailureType> failures = failureTypes.getFailureTypes__Repository();
        for (final FailureType f : failures) {
            if (f instanceof SoftwareInducedFailureType) {
                this.internalFailureTypes.putIfAbsent(Failure.SOFTWARE, f);
            } else if (f instanceof NetworkInducedFailureType) {
                this.internalFailureTypes.putIfAbsent(Failure.NETWORK_LAN, f);
            } else if (f instanceof HardwareInducedFailureType) {
                switch (f.getEntityName().toLowerCase(Locale.US)) {
                    case "hardwareinducedfailure (cpu)":
                        this.internalFailureTypes.putIfAbsent(Failure.HARDWARE_CPU, f);
                        break;
                    case "hardwareinducedfailure (hdd)":
                        this.internalFailureTypes.putIfAbsent(Failure.HARDWARE_HDD, f);
                        break;
                    case "hardwareinducedfailure (delay)":
                        this.internalFailureTypes.putIfAbsent(Failure.HARDWARE_DELAY, f);
                        break;
                    default:
                        logger.warning("Unexpected failure type while reading internal failure types.");
                }
            } else {
                logger.warning("Unexpected failure type while reading internal failure types.");
            }
        }
    }

    @Override
    public RepositoryCreator withName(final String name) {
        return (RepositoryCreator) super.withName(name);
    }

    @Override
    public RepositoryCreator withDescription(final String description) {
        IllegalArgumentException.throwIfNull(description, "description must not be null");
        this.description = description;
        return this;
    }

    @Override
    public Repo withImportedResource(final URI uri) {
        IllegalArgumentException.throwIfNull(uri, "URI must not be null.");

        if (!"repository".equalsIgnoreCase(uri.fileExtension(), Locale.US)) {
            throw new IllegalArgumentException("The specified URI must lead to a .repository file");
        }

        final Repository imported = RepositoryCreator.loadRepository(uri);

        return withImportedResource(imported);
    }

    @Override
    public Repo withImportedResource(final String path) {
        IllegalArgumentException.throwIfNull(path, "path must not be null.");

        if (!path.toLowerCase(Locale.US)
            .endsWith("." + "repository")) {
            throw new IllegalArgumentException("The specified path must lead to a .repository file");
        }

        final Repository imported = RepositoryCreator.loadRepository(path);

        return withImportedResource(imported);
    }

    @Override
    public Repo withImportedResource(final Repository repository) {
        this.imports.add(IllegalArgumentException.throwIfNull(repository, "repository must not be null"));
        this.importedDataTypes.addAll(repository.getDataTypes__Repository());
        this.importedFailureTypes.addAll(repository.getFailureTypes__Repository());
        this.importedComponents.addAll(repository.getComponents__Repository());
        this.importedInterfaces.addAll(repository.getInterfaces__Repository());

        return this;
    }

    @Override
    public RepoAddition addToRepository(final CollectionDataType collectionDataType) {
        this.dataTypes
            .add(IllegalArgumentException.throwIfNull(collectionDataType, "collectionDataType must not be null"));
        return this;
    }

    @Override
    public RepoAddition addToRepository(final CompositeDataTypeCreator compositeDataType) {
        IllegalArgumentException.throwIfNull(compositeDataType, "compositeDataType must not be null");
        final CompositeDataType dataType = compositeDataType.build();
        this.dataTypes.add(dataType);
        return this;
    }

    @Override
    public RepoAddition addToRepository(final FailureType failureType) {
        this.failureTypes.add(IllegalArgumentException.throwIfNull(failureType, "failureType must not be null"));
        return this;
    }

    @Override
    public RepoAddition addToRepository(final ResourceTimeoutFailureTypeCreator failureType) {
        this.failureTypes.add(IllegalArgumentException.throwIfNull(failureType, "failureType must not be null")
            .build());
        return this;
    }

    @Override
    public RepoAddition addToRepository(final ExceptionTypeCreator exceptionType) {
        this.exceptionTypes.add(IllegalArgumentException.throwIfNull(exceptionType, "exceptionType must not be null")
            .build());
        return this;
    }

    @Override
    public RepoAddition addToRepository(
            final org.palladiosimulator.generator.fluent.repository.structure.interfaces.Interface interfce) {
        this.interfaces.add(IllegalArgumentException.throwIfNull(interfce, "interfce must not be null")
            .build());
        return this;
    }

    @Override
    public RepoAddition addToRepository(final Component component) {
        this.components.add(IllegalArgumentException.throwIfNull(component, "component must not be null")
            .build());
        return this;
    }

    @Override
    protected Repository build() {
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        if (this.name != null) {
            repo.setEntityName(this.name);
        }
        // if (id != null)
        // repo.setId(id);
        if (this.description != null) {
            repo.setRepositoryDescription(this.description);
        }

        repo.getDataTypes__Repository()
            .addAll(this.dataTypes);
        repo.getInterfaces__Repository()
            .addAll(this.interfaces);
        repo.getComponents__Repository()
            .addAll(this.components);

        return repo;
    }

    @Override
    public Repository createRepositoryNow() {
        final Repository repo = build();
        this.validator.validate(repo, this.name);

        return repo;
    }

    private Repository getRepositoryByName(final String name) {
        final List<Repository> collect = this.imports.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than repository with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    // ------------- getter -------------
    // TODO: getter and add Methoden should not be visible for the user ->
    // module-info.java

    // I didn't put much thought into where it actually makes sense to fetch
    // something from an imported resource. It probably doesn't make sense e.g. for
    // parameters. However, it is implemented. Maybe later this can be restricted if
    // it is confusing for the user,
    public PrimitiveDataType getPrimitiveDataType(final Primitive primitive) {
        IllegalArgumentException.throwIfNull(primitive, "primitive must not be null");
        return this.internalPrimitives.get(primitive);
    }

    /**
     * TODO
     *
     * @param name
     * @return
     * @throws NoSuchElementException
     *             Thrown if no role matches the given name.
     */
    public PrimitiveDataType getPrimitiveDataType(final String name) throws NoSuchElementException {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final String adjustedName = name.toUpperCase(Locale.US);
        switch (adjustedName) {
            case "INT":
                return this.internalPrimitives.get(Primitive.INTEGER);
            case "BOOL":
                return this.internalPrimitives.get(Primitive.BOOLEAN);
            default:
                try {
                    final Primitive valueOf = Primitive.valueOf(adjustedName);
                    return this.internalPrimitives.get(valueOf);
                } catch (final IllegalArgumentException e) {
                    throw new NoSuchElementException(String.format("A primitive data type name '%s' was nou found.", name), e);
                }
        }
    }

    public CompositeDataType getCompositeDataType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<CompositeDataType> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new NoSuchElementException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getDataTypes__Repository()
                .stream()
                .filter(d -> d instanceof CompositeDataType)
                .map(d -> (CompositeDataType) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.dataTypes.stream()
                .filter(d -> d instanceof CompositeDataType)
                .map(d -> (CompositeDataType) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getCompositeDataTypeFromList(actualName, collect);
    }

    private CompositeDataType getCompositeDataTypeFromList(final String name, final List<CompositeDataType> dataTypes) {
        final List<CompositeDataType> collect = dataTypes.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one composite data type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public DataType getDataType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            final String entityName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            return getDataTypeFromList(entityName, r.getDataTypes__Repository());
        }
        if (split.length == 1) {
            return getDataTypeFromList(name, this.dataTypes);
        }
        throw new IllegalArgumentException(
                "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
    }

    private DataType getDataTypeFromList(final String name, final List<DataType> dataTypes) {
        final List<DataType> collect = new ArrayList<>();
        final List<CollectionDataType> collectColl = dataTypes.stream()
            .filter(d -> d instanceof CollectionDataType)
            .map(d -> (CollectionDataType) d)
            .filter(d -> (d.getEntityName() != null) && d.getEntityName().equals(name))
            .collect(Collectors.toList());
        final List<CompositeDataType> collectComp = dataTypes.stream()
            .filter(d -> d instanceof CompositeDataType)
            .map(d -> (CompositeDataType) d)
            .filter(d -> (d.getEntityName() != null) && d.getEntityName().equals(name))
            .collect(Collectors.toList());
        collect.addAll(collectColl);
        collect.addAll(collectComp);

        if (collect.isEmpty()) {
            return this.getPrimitiveDataType(name);
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one data type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public FailureType getFailureType(final Failure failure) {
        IllegalArgumentException.throwIfNull(failure, "failure must not be null");
        return this.internalFailureTypes.get(failure);
    }

    public FailureType getFailureType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            final String entityName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            return getFailureTypeFromList(entityName, r.getFailureTypes__Repository());
        }
        if (split.length == 1) {
            return getFailureTypeFromList(name, this.failureTypes);
        }
        throw new IllegalArgumentException(
                "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
    }

    private FailureType getFailureTypeFromList(final String name, final List<FailureType> failureTypes) {
        final List<FailureType> collect = failureTypes.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        final List<FailureType> collect2 = this.internalFailureTypes.values()
            .stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        collect.addAll(collect2);
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one failure type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ResourceTimeoutFailureType getResourceTimeoutFailureType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<ResourceTimeoutFailureType> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getFailureTypes__Repository()
                .stream()
                .filter(d -> d instanceof ResourceTimeoutFailureType)
                .map(d -> (ResourceTimeoutFailureType) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.failureTypes.stream()
                .filter(d -> d instanceof ResourceTimeoutFailureType)
                .map(d -> (ResourceTimeoutFailureType) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getResourceTimeoutFailureTypeFromList(actualName, collect);
    }

    private ResourceTimeoutFailureType getResourceTimeoutFailureTypeFromList(final String name,
            final List<ResourceTimeoutFailureType> failureTypes) {
        final List<ResourceTimeoutFailureType> collect = failureTypes.stream()
            .filter(b -> (b.getEntityName() != null) && b.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one resource timeout failure type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ExceptionType getExceptionType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        final List<ExceptionType> collect = this.exceptionTypes.stream()
            .filter(c -> (c.getExceptionName() != null) && c.getExceptionName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one component with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ProcessingResourceType getProcessingResourceType(final ProcessingResource processingResource) {
        IllegalArgumentException.throwIfNull(processingResource, "processingResource must not be null");
        return this.internalProcessingResources.get(processingResource);
    }

    public CommunicationLinkResourceType getCommunicationLinkResource(
            final CommunicationLinkResource communicationLinkResource) {
        IllegalArgumentException.throwIfNull(communicationLinkResource, "communicationLinkResource must not be null");
        return this.internalCommunicationLinkResources.get(communicationLinkResource);
    }

    public ResourceInterface getResourceInterface(
            final org.palladiosimulator.generator.fluent.shared.structure.ResourceInterface resourceInterface) {
        IllegalArgumentException.throwIfNull(resourceInterface, "resourceInterface must not be null");
        return this.internalResourceInterfaces.get(resourceInterface);
    }

    public ResourceSignature getResourceSignature(
            final org.palladiosimulator.generator.fluent.repository.structure.internals.ResourceSignature resourceSignature) {
        IllegalArgumentException.throwIfNull(resourceSignature, "resourceSignature must not be null");
        return this.internalResourceSignatures.get(resourceSignature);
    }

    public RepositoryComponent getComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<RepositoryComponent> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository();
        } else if (split.length == 1) {
            collect = this.components;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getComponentFromList(actualName, collect);
    }

    private RepositoryComponent getComponentFromList(final String name, final List<RepositoryComponent> components) {
        final List<RepositoryComponent> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one component with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public BasicComponent getBasicComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<BasicComponent> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository()
                .stream()
                .filter(d -> d instanceof BasicComponent)
                .map(d -> (BasicComponent) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.components.stream()
                .filter(d -> d instanceof BasicComponent)
                .map(d -> (BasicComponent) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getBasicComponentFromList(actualName, collect);
    }

    private BasicComponent getBasicComponentFromList(final String name, final List<BasicComponent> components) {
        final List<BasicComponent> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one basic component with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public CompositeComponent getCompositeComponent(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<CompositeComponent> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository()
                .stream()
                .filter(d -> d instanceof CompositeComponent)
                .map(d -> (CompositeComponent) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.components.stream()
                .filter(d -> d instanceof CompositeComponent)
                .map(d -> (CompositeComponent) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getCompositeComponentFromList(actualName, collect);
    }

    private CompositeComponent getCompositeComponentFromList(final String name,
            final List<CompositeComponent> components) {
        final List<CompositeComponent> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one composite component with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public SubSystem getSubsystem(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<SubSystem> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository()
                .stream()
                .filter(d -> d instanceof SubSystem)
                .map(d -> (SubSystem) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.components.stream()
                .filter(d -> d instanceof SubSystem)
                .map(d -> (SubSystem) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getSubsystemFromList(actualName, collect);
    }

    private SubSystem getSubsystemFromList(final String name, final List<SubSystem> components) {
        final List<SubSystem> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one subsystem with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public CompleteComponentType getCompleteComponentType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<CompleteComponentType> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository()
                .stream()
                .filter(d -> d instanceof CompleteComponentType)
                .map(d -> (CompleteComponentType) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.components.stream()
                .filter(d -> d instanceof CompleteComponentType)
                .map(d -> (CompleteComponentType) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getCompleteComponentTypeFromList(actualName, collect);
    }

    private CompleteComponentType getCompleteComponentTypeFromList(final String name,
            final List<CompleteComponentType> components) {
        final List<CompleteComponentType> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one complete component type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ProvidesComponentType getProvidesComponentType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<ProvidesComponentType> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getComponents__Repository()
                .stream()
                .filter(d -> d instanceof ProvidesComponentType)
                .map(d -> (ProvidesComponentType) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.components.stream()
                .filter(d -> d instanceof ProvidesComponentType)
                .map(d -> (ProvidesComponentType) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getProvidesComponentTypeFromList(actualName, collect);
    }

    private ProvidesComponentType getProvidesComponentTypeFromList(final String name,
            final List<ProvidesComponentType> components) {
        final List<ProvidesComponentType> collect = components.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one provides component type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public Interface getInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<Interface> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getInterfaces__Repository();
        } else if (split.length == 1) {
            collect = this.interfaces;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getInterfaceFromList(actualName, collect);
    }

    private Interface getInterfaceFromList(final String name, final List<Interface> interfaces) {
        final List<Interface> collect = interfaces.stream()
            .filter(i -> (i.getEntityName() != null) && i.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one interface with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public OperationInterface getOperationInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<OperationInterface> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getInterfaces__Repository()
                .stream()
                .filter(d -> d instanceof OperationInterface)
                .map(d -> (OperationInterface) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.interfaces.stream()
                .filter(d -> d instanceof OperationInterface)
                .map(d -> (OperationInterface) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getOperationInterfaceFromList(actualName, collect);
    }

    private OperationInterface getOperationInterfaceFromList(final String name,
            final List<OperationInterface> interfaces) {
        final List<OperationInterface> collect = interfaces.stream()
            .filter(i -> (i.getEntityName() != null) && i.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one operation interface with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public InfrastructureInterface getInfrastructureInterface(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<InfrastructureInterface> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getInterfaces__Repository()
                .stream()
                .filter(d -> d instanceof InfrastructureInterface)
                .map(d -> (InfrastructureInterface) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.interfaces.stream()
                .filter(d -> d instanceof InfrastructureInterface)
                .map(d -> (InfrastructureInterface) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getInfrastructureInterfaceFromList(actualName, collect);
    }

    private InfrastructureInterface getInfrastructureInterfaceFromList(final String name,
            final List<InfrastructureInterface> interfaces) {
        final List<InfrastructureInterface> collect = interfaces.stream()
            .filter(i -> (i.getEntityName() != null) && i.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one infrastructure interface with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public EventGroup getEventGroup(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<EventGroup> collect;
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            collect = r.getInterfaces__Repository()
                .stream()
                .filter(d -> d instanceof EventGroup)
                .map(d -> (EventGroup) d)
                .collect(Collectors.toList());
        } else if (split.length == 1) {
            collect = this.interfaces.stream()
                .filter(d -> d instanceof EventGroup)
                .map(d -> (EventGroup) d)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getEventGroupFromList(actualName, collect);
    }

    private EventGroup getEventGroupFromList(final String name, final List<EventGroup> interfaces) {
        final List<EventGroup> collect = interfaces.stream()
            .filter(i -> (i.getEntityName() != null) && i.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one event group with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ProvidedRole getProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<ProvidedRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity());
            }
        } else if (split.length == 1) {
            collect = this.providedRoles;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getProvidedRoleFromList(actualName, collect);
    }

    private ProvidedRole getProvidedRoleFromList(final String name, final List<ProvidedRole> providedRoles) {
        final List<ProvidedRole> collect = providedRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one provided role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public OperationProvidedRole getOperationProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<OperationProvidedRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity()
                    .stream()
                    .filter(p -> p instanceof OperationProvidedRole)
                    .map(p -> (OperationProvidedRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.providedRoles.stream()
                .filter(r -> r instanceof OperationProvidedRole)
                .map(r -> (OperationProvidedRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getOperationProvidedRoleFromList(actualName, collect);
    }

    private OperationProvidedRole getOperationProvidedRoleFromList(final String name,
            final List<OperationProvidedRole> providedRoles) {
        final List<OperationProvidedRole> collect = providedRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one operation provided role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public InfrastructureProvidedRole getInfrastructureProvidedRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<InfrastructureProvidedRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity()
                    .stream()
                    .filter(p -> p instanceof InfrastructureProvidedRole)
                    .map(p -> (InfrastructureProvidedRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.providedRoles.stream()
                .filter(r -> r instanceof InfrastructureProvidedRole)
                .map(r -> (InfrastructureProvidedRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getInfrastructureProvidedRoleFromList(actualName, collect);
    }

    private InfrastructureProvidedRole getInfrastructureProvidedRoleFromList(final String name,
            final List<InfrastructureProvidedRole> providedRoles) {
        final List<InfrastructureProvidedRole> collect = providedRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one infrastructure provided role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public SinkRole getSinkRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<SinkRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getProvidedRoles_InterfaceProvidingEntity()
                    .stream()
                    .filter(p -> p instanceof SinkRole)
                    .map(p -> (SinkRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.providedRoles.stream()
                .filter(r -> r instanceof SinkRole)
                .map(r -> (SinkRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getSinkRoleFromList(actualName, collect);
    }

    private SinkRole getSinkRoleFromList(final String name, final List<SinkRole> providedRoles) {
        final List<SinkRole> collect = providedRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one sink role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public RequiredRole getRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<RequiredRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity());
            }
        } else if (split.length == 1) {
            collect = this.requiredRoles;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getRequiredRoleFromList(actualName, collect);
    }

    private RequiredRole getRequiredRoleFromList(final String name, final List<RequiredRole> requiredRoles) {
        final List<RequiredRole> collect = requiredRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one required role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public OperationRequiredRole getOperationRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<OperationRequiredRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity()
                    .stream()
                    .filter(p -> p instanceof OperationRequiredRole)
                    .map(p -> (OperationRequiredRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.requiredRoles.stream()
                .filter(r -> r instanceof OperationRequiredRole)
                .map(r -> (OperationRequiredRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getOperationRequiredRoleFromList(actualName, collect);
    }

    private OperationRequiredRole getOperationRequiredRoleFromList(final String name,
            final List<OperationRequiredRole> requiredRoles) {
        final List<OperationRequiredRole> collect = requiredRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one operation required role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public InfrastructureRequiredRole getInfrastructureRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<InfrastructureRequiredRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity()
                    .stream()
                    .filter(p -> p instanceof InfrastructureRequiredRole)
                    .map(p -> (InfrastructureRequiredRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.requiredRoles.stream()
                .filter(r -> r instanceof InfrastructureRequiredRole)
                .map(r -> (InfrastructureRequiredRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getInfrastructureRequiredRoleFromList(actualName, collect);
    }

    private InfrastructureRequiredRole getInfrastructureRequiredRoleFromList(final String name,
            final List<InfrastructureRequiredRole> requiredRoles) {
        final List<InfrastructureRequiredRole> collect = requiredRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one infrastructure required role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public SourceRole getSourceRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<SourceRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getRequiredRoles_InterfaceRequiringEntity()
                    .stream()
                    .filter(p -> p instanceof SourceRole)
                    .map(p -> (SourceRole) p)
                    .collect(Collectors.toList()));
            }
        } else if (split.length == 1) {
            collect = this.requiredRoles.stream()
                .filter(r -> r instanceof SourceRole)
                .map(r -> (SourceRole) r)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getSourceRoleFromList(actualName, collect);
    }

    private SourceRole getSourceRoleFromList(final String name, final List<SourceRole> requiredRoles) {
        final List<SourceRole> collect = requiredRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one source role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public ResourceRequiredRole getResourceRequiredRole(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<ResourceRequiredRole> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                collect.addAll(c.getResourceRequiredRoles__ResourceInterfaceRequiringEntity());
            }
        } else if (split.length == 1) {
            collect = this.resourceRequiredRoles;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getResourceRequiredRoleFromList(actualName, collect);
    }

    private ResourceRequiredRole getResourceRequiredRoleFromList(final String name,
            final List<ResourceRequiredRole> resourceRequiredRoles) {
        final List<ResourceRequiredRole> collect = resourceRequiredRoles.stream()
            .filter(r -> (r.getEntityName() != null) && r.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one resource required role with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public Signature getSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<Signature> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final Interface i : r.getInterfaces__Repository()) {
                if (i instanceof OperationInterface) {
                    final OperationInterface e = (OperationInterface) i;
                    collect.addAll(e.getSignatures__OperationInterface());
                } else if (i instanceof InfrastructureInterface) {
                    final InfrastructureInterface e = (InfrastructureInterface) i;
                    collect.addAll(e.getInfrastructureSignatures__InfrastructureInterface());
                } else if (i instanceof EventGroup) {
                    final EventGroup e = (EventGroup) i;
                    collect.addAll(e.getEventTypes__EventGroup());
                }
            }
        } else if (split.length == 1) {
            collect = this.signatures;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getSignatureFromList(actualName, collect);
    }

    private Signature getSignatureFromList(final String name, final List<Signature> signatures) {
        final List<Signature> collect = signatures.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one signature with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public OperationSignature getOperationSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<OperationSignature> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final Interface i : r.getInterfaces__Repository()) {
                if (i instanceof OperationInterface) {
                    final OperationInterface e = (OperationInterface) i;
                    collect.addAll(e.getSignatures__OperationInterface());
                }
            }
        } else if (split.length == 1) {
            collect = this.signatures.stream()
                .filter(i -> i instanceof OperationSignature)
                .map(i -> (OperationSignature) i)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getOperationSignatureFromList(actualName, collect);
    }

    private OperationSignature getOperationSignatureFromList(final String name,
            final List<OperationSignature> signatures) {
        final List<OperationSignature> collect = signatures.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one operation signature with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public InfrastructureSignature getInfrastructureSignature(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<InfrastructureSignature> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final Interface i : r.getInterfaces__Repository()) {
                if (i instanceof InfrastructureInterface) {
                    final InfrastructureInterface e = (InfrastructureInterface) i;
                    collect.addAll(e.getInfrastructureSignatures__InfrastructureInterface());
                }
            }
        } else if (split.length == 1) {
            collect = this.signatures.stream()
                .filter(i -> i instanceof InfrastructureSignature)
                .map(i -> (InfrastructureSignature) i)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getInfrastructureSignatureFromList(actualName, collect);
    }

    private InfrastructureSignature getInfrastructureSignatureFromList(final String name,
            final List<InfrastructureSignature> signatures) {
        final List<InfrastructureSignature> collect = signatures.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one infrastructure signature with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public EventType getEventType(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<EventType> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final Interface i : r.getInterfaces__Repository()) {
                if (i instanceof EventGroup) {
                    final EventGroup e = (EventGroup) i;
                    collect.addAll(e.getEventTypes__EventGroup());
                }
            }
        } else if (split.length == 1) {
            collect = this.signatures.stream()
                .filter(i -> i instanceof EventType)
                .map(i -> (EventType) i)
                .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getEventTypeFromList(actualName, collect);
    }

    private EventType getEventTypeFromList(final String name, final List<EventType> signatures) {
        final List<EventType> collect = signatures.stream()
            .filter(c -> (c.getEntityName() != null) && c.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one event type with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public AssemblyContext getAssemblyContext(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<AssemblyContext> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final RepositoryComponent c : r.getComponents__Repository()) {
                if (c instanceof ComposedProvidingRequiringEntity) {
                    final ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
                    collect.addAll(cc.getAssemblyContexts__ComposedStructure());
                }
            }
        } else if (split.length == 1) {
            collect = this.assemblyContexts;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getAssemblyContextFromList(actualName, collect);
    }

    private AssemblyContext getAssemblyContextFromList(final String name,
            final List<AssemblyContext> assemblyContexts) {
        final List<AssemblyContext> collect = assemblyContexts.stream()
            .filter(a -> (a.getEntityName() != null) && a.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one assembly context with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public EventChannel getEventChannel(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<EventChannel> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final RepositoryComponent c : r.getComponents__Repository()) {
                if (c instanceof ComposedProvidingRequiringEntity) {
                    final ComposedProvidingRequiringEntity cc = (ComposedProvidingRequiringEntity) c;
                    collect.addAll(cc.getEventChannel__ComposedStructure());
                }
            }
        } else if (split.length == 1) {
            collect = this.eventChannels;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getEventChannelFromList(actualName, collect);
    }

    private EventChannel getEventChannelFromList(final String name, final List<EventChannel> eventChannels) {
        final List<EventChannel> collect = eventChannels.stream()
            .filter(a -> (a.getEntityName() != null) && a.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one event channel with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public Parameter getParameter(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<Parameter> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
            for (final Interface i : r.getInterfaces__Repository()) {
                if (i instanceof OperationInterface) {
                    final OperationInterface e = (OperationInterface) i;
                    for (final OperationSignature s : e.getSignatures__OperationInterface()) {
                        collect.addAll(s.getParameters__OperationSignature());
                    }
                } else if (i instanceof InfrastructureInterface) {
                    final InfrastructureInterface e = (InfrastructureInterface) i;
                    for (final InfrastructureSignature s : e.getInfrastructureSignatures__InfrastructureInterface()) {
                        collect.addAll(s.getParameters__InfrastructureSignature());
                    }
                } else if (i instanceof EventGroup) {
                    final EventGroup e = (EventGroup) i;
                    for (final EventType s : e.getEventTypes__EventGroup()) {
                        collect.add(s.getParameter__EventType());
                    }
                }
            }
        } else if (split.length == 1) {
            collect = this.parameters;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getParameterFromList(actualName, collect);
    }

    private Parameter getParameterFromList(final String name, final List<Parameter> parameters) {
        final List<Parameter> collect = parameters.stream()
            .filter(p -> (p.getParameterName() != null) && p.getParameterName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one parameter with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public Parameter getParameter(final String name, final Signature context) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        IllegalArgumentException.throwIfNull(context, "context must not be null");
        String actualName = name;
        final List<Parameter> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            // it is assumed that split[0] = name of the repository refers to the same
            // repository that the signature <context> comes from
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }
        } else if (split.length != 1) {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }

        if (context instanceof OperationSignature) {
            collect.addAll(((OperationSignature) context).getParameters__OperationSignature());
        } else if (context instanceof InfrastructureSignature) {
            collect.addAll(((InfrastructureSignature) context).getParameters__InfrastructureSignature());
        } else if (context instanceof EventType) {
            collect.add(((EventType) context).getParameter__EventType());
        }

        return getParameterFromList(actualName, collect);
    }

    public PassiveResource getPassiveResource(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<PassiveResource> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            for (final RepositoryComponent c : r.getComponents__Repository()) {
                if (c instanceof BasicComponent) {
                    final BasicComponent cc = (BasicComponent) c;
                    collect.addAll(cc.getPassiveResource_BasicComponent());
                }
            }
        } else if (split.length == 1) {
            collect = this.passiveResources;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getPassiveResourceFromList(actualName, collect);
    }

    private PassiveResource getPassiveResourceFromList(final String name,
            final List<PassiveResource> passiveResources) {
        final List<PassiveResource> collect = passiveResources.stream()
            .filter(b -> (b.getEntityName() != null) && b.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one passive resource with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    public RecoveryActionBehaviour getRecoveryActionBehaviour(final String name) {
        IllegalArgumentException.throwIfNull(name, "name must not be null");
        String actualName = name;
        List<RecoveryActionBehaviour> collect = new ArrayList<>();
        final String[] split = name.split("\\.");
        if (split.length == 2) {
            actualName = split[1];
            final Repository r = getRepositoryByName(split[0]);
            if (r == null) {
                throw new FluentApiException("Repository '" + split[0] + "' could not be found");
            }

            final Set<RecoveryActionBehaviour> set = new HashSet<>();
            for (final RepositoryComponent c : r.getComponents__Repository()) {
                if (c instanceof BasicComponent) {
                    final BasicComponent cc = (BasicComponent) c;
                    final EList<ServiceEffectSpecification> seffs = cc.getServiceEffectSpecifications__BasicComponent();
                    for (final ServiceEffectSpecification s : seffs) {
                        if (s instanceof ResourceDemandingSEFF) {
                            final ResourceDemandingSEFF rseff = (ResourceDemandingSEFF) s;
                            final List<RecoveryAction> recoveryActions = rseff.getSteps_Behaviour()
                                .stream()
                                .filter(step -> step instanceof RecoveryAction)
                                .map(step -> (RecoveryAction) step)
                                .collect(Collectors.toList());
                            for (final RecoveryAction a : recoveryActions) {
                                set.addAll(a.getRecoveryActionBehaviours__RecoveryAction());
                            }
                        }
                    }
                }
            }
            collect.addAll(set);
        } else if (split.length == 1) {
            collect = this.behaviours;
        } else {
            throw new IllegalArgumentException(
                    "To access entities from imported repositories use the format <importedRepositoryName>.<entityName>");
        }
        return getRecoveryActionBehaviourFromList(actualName, collect);
    }

    private RecoveryActionBehaviour getRecoveryActionBehaviourFromList(final String name,
            final List<RecoveryActionBehaviour> behaviours) {
        final List<RecoveryActionBehaviour> collect = behaviours.stream()
            .filter(b -> (b.getEntityName() != null) && b.getEntityName().equals(name))
            .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        if (collect.size() > 1) {
            this.logger.warning("More than one recovery action behaviour with name '" + name + "' found.");
        }
        return collect.get(0);
    }

    // ------------- adding -------------

    public void addComponent(final RepositoryComponent c) {
        this.components.add(c);
    }

    public void addInterface(final Interface i) {
        this.interfaces.add(i);
    }

    public void addProvidedRole(final ProvidedRole pr) {
        this.providedRoles.add(pr);
    }

    public void addRequiredRole(final RequiredRole rr) {
        this.requiredRoles.add(rr);
    }

    public void addResourceRequiredRole(final ResourceRequiredRole rr) {
        this.resourceRequiredRoles.add(rr);
    }

    public void addSignature(final Signature sign) {
        this.signatures.add(sign);
    }

    public void addAssemblyContext(final AssemblyContext ac) {
        this.assemblyContexts.add(ac);
    }

    public void addConnector(final Connector r) {
        this.connectors.add(r);
    }

    public void addEventChannel(final EventChannel eg) {
        this.eventChannels.add(eg);
    }

    public void addParameter(final Parameter p) {
        this.parameters.add(p);
    }

    public void addPassiveResource(final PassiveResource pass) {
        this.passiveResources.add(pass);
    }

    public void addRecoveryActionBehaviour(final RecoveryActionBehaviour recovery) {
        this.behaviours.add(recovery);
    }

}
