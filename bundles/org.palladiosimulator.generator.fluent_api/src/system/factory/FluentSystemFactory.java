package system.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.system.System;

import shared.util.ModelLoader;
import shared.validate.IModelValidator;
import shared.validate.ModelValidator;
import system.apiControlFlowInterfaces.ISystem;
import system.structure.AssemblyContextCreator;
import system.structure.EventChannelCreator;
import system.structure.SystemCreator;
import system.structure.connector.assemblyConnector.AssemblyConnectorCreator;
import system.structure.connector.assemblyEventConnector.AssemblyEventConnectorCreator;
import system.structure.connector.assemblyInfrastructureConnector.AssemblyInfrastructureConnectorCreator;
import system.structure.connector.eventChannel.EventChannelSinkConnectorCreator;
import system.structure.connector.eventChannel.EventChannelSourceConnectorCreator;
import system.structure.connector.eventDelegationConnector.SinkDelegationConnectorCreator;
import system.structure.connector.eventDelegationConnector.SourceDelegationConnectorCreator;
import system.structure.connector.infrastructureDelegationConnector.ProvidedInfrastructureDelegationConnectorCreator;
import system.structure.connector.infrastructureDelegationConnector.RequiredInfrastructureDelegationConnectorCreator;
import system.structure.connector.operationDelegationConnector.ProvidedDelegationConnectorCreator;
import system.structure.connector.operationDelegationConnector.RequiredDelegationConnectorCreator;
import system.structure.connector.requiredResourceDelegationConnector.RequiredResourceDelegationConnectorCreator;
import system.structure.connector.resourceRequiredDelegationConnector.ResourceRequiredDelegationConnectorCreator;
import system.structure.qosAnnotations.QoSAnnotationsCreator;
import system.structure.systemRole.InfrastructureProvidedRoleCreator;
import system.structure.systemRole.InfrastructureRequiredRoleCreator;
import system.structure.systemRole.OperationProvidedRoleCreator;
import system.structure.systemRole.OperationRequiredRoleCreator;
import system.structure.systemRole.ResourceRequiredRoleCreator;
import system.structure.systemRole.SinkRoleCreator;
import system.structure.systemRole.SourceRoleCreator;

/**
 * This class provides all the methods to create a {@link org.palladiosimulator.pcm.system.System
 * System} and create entities that are added to this System. Characteristics of the entities are
 * specified by method chaining.<br>
 * <p>
 * Start creating a system like this:<br>
 * <code>
 * FluentSystemFactory create = new FluentSystemFactory();<br>
 * System system = create.newSystem()<br>
 *      <p style=
"margin-left:40px">//add assembly contexts, connectors and roles to the system</p>
 *      <p style="margin-left:40px">.createSystemNow();</p>
 * </code>
 * </p>
 *
 * @author Florian Krone
 *
 */
public class FluentSystemFactory {
    private SystemCreator systemCreator;

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.system.System System}.
     *
     * @return the <code>System</code> in the making
     *
     * @see org.palladiosimulator.pcm.system.System
     */
    public ISystem newSystem() {
        EcorePlugin.ExtensionProcessor.process(null);
        final ResourceRepository resources = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        this.systemCreator = new SystemCreator(resources, validator);
        return this.systemCreator;
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext}.
     *
     * @return the <code>AssemblyContext</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContextCreator newAssemblyContext() {
        return new AssemblyContextCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * AssemblyConnector}.
     *
     * @return the <code>AssemblyConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     */
    public AssemblyConnectorCreator newAssemblyConnector() {
        return new AssemblyConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole}.
     *
     * @return the <code>OperationRequiredRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRoleCreator newOperationRequiredRole() {
        return new OperationRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * RequiredDelegationConnector}.
     *
     * @return the <code>RequiredDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     */
    public RequiredDelegationConnectorCreator newRequiredDelegationConnectorCreator() {
        return new RequiredDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole}.
     *
     * @return the <code>OperationProvidedRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRoleCreator newOperationProvidedRole() {
        return new OperationProvidedRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * ProvidedDelegationConnector}.
     *
     * @return the <code>ProvidedDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     */
    public ProvidedDelegationConnectorCreator newProvidedDelegationConnectorCreator() {
        return new ProvidedDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel}.
     *
     * @return the <code>EventChannel</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    public EventChannelCreator newEventChannelCreator() {
        return new EventChannelCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}.
     *
     * @return the <code>EventChannelSinkConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     */
    public EventChannelSinkConnectorCreator newEventChannelSinkConnector() {
        return new EventChannelSinkConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * EventChannelSourceConnector}.
     *
     * @return the <code>EventChannelSourceConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     */
    public EventChannelSourceConnectorCreator newEventChannelSourceConnector() {
        return new EventChannelSourceConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}.
     *
     * @return the <code>SinkRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleCreator newSinkRole() {
        return new SinkRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * SinkDelegationConnector}.
     *
     * @return the <code>SinkDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     */
    public SinkDelegationConnectorCreator newSinkDelegationConnector() {
        return new SinkDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}.
     *
     * @return the <code>SourceRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRoleCreator newSourceRole() {
        return new SourceRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * SourceDelegationConnector}.
     *
     * @return the <code>SourceDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     */
    public SourceDelegationConnectorCreator newSourceDelegationConnector() {
        return new SourceDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * AssemblyEventConnector}.
     *
     * @return the <code>AssemblyEventConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     */
    public AssemblyEventConnectorCreator newAssemblyEventConnector() {
        return new AssemblyEventConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * AssemblyInfrastructureConnector}.
     *
     * @return the <code>AssemblyInfrastructureConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     */
    public AssemblyInfrastructureConnectorCreator newAssemblyInfrastructureConnector() {
        return new AssemblyInfrastructureConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole}.
     *
     * @return the <code>InfrastructureRequiredRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleCreator newInfrastructureRequiredRole() {
        return new InfrastructureRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * RequiredInfrastructureDelegationConnector}.
     *
     * @return the <code>RequiredInfrastructureDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     */
    public RequiredInfrastructureDelegationConnectorCreator newRequiredInfrastructureDelegationConnector() {
        return new RequiredInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole}.
     *
     * @return the <code>InfrastructureProvidedRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleCreator newInfrastructureProvidedRole() {
        return new InfrastructureProvidedRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * ProvidedInfrastructureDelegationConnector}.
     *
     * @return the <code>ProvidedInfrastructureDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     */
    public ProvidedInfrastructureDelegationConnectorCreator newProvidedInfrastructureDelegationConnector() {
        return new ProvidedInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     * QoSAnnotations}.
     *
     * @return the <code>QoSAnnotations</code> in the making
     *
     * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     */
    public QoSAnnotationsCreator newQoSAnnotations() {
        return new QoSAnnotationsCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}.
     *
     * @return the <code>ResourceRequiredRole</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRoleCreator newResourceRequiredRole() {
        return new ResourceRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * ResourceRequiredDelegationConnector}.
     *
     * @return the <code>ResourceRequiredDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     */
    public ResourceRequiredDelegationConnectorCreator newResourceRequiredDelegationConnector() {
        return new ResourceRequiredDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * RequiredResourceDelegationConnector}.
     *
     * @return the <code>RequiredResourceDelegationConnector</code> in the making
     *
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     */
    public RequiredResourceDelegationConnectorCreator newRequiredResourceDelegationConnector() {
        return new RequiredResourceDelegationConnectorCreator(this.systemCreator);
    }
}
