package org.palladiosimulator.generator.fluent.system.factory;

import java.nio.channels.IllegalSelectorException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.shared.util.ModelLoader;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;
import org.palladiosimulator.generator.fluent.system.api.ISystem;
import org.palladiosimulator.generator.fluent.system.structure.AssemblyContextCreator;
import org.palladiosimulator.generator.fluent.system.structure.EventChannelCreator;
import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.event.AssemblyEventConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.event.EventChannelSinkConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.event.EventChannelSourceConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.event.SinkDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.event.SourceDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.infrastructure.AssemblyInfrastructureConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.infrastructure.ProvidedInfrastructureDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.infrastructure.RequiredInfrastructureDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.operation.AssemblyConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.operation.ProvidedDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.operation.RequiredDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.resource.RequiredResourceDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.connector.resource.ResourceRequiredDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent.system.structure.qos.QoSAnnotationsCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.InfrastructureProvidedRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.InfrastructureRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.OperationProvidedRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.OperationRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.ResourceRequiredRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.SinkRoleCreator;
import org.palladiosimulator.generator.fluent.system.structure.role.SourceRoleCreator;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

/**
 * This class provides all the methods to create a {@link org.palladiosimulator.pcm.system.System
 * System} and create entities that are added to this System. Characteristics of the entities are
 * specified by method chaining.<br>
 * <p>
 * Start creating a org.palladiosimulator.generator.fluent.system like this:<br>
 * <code>
 * FluentSystemFactory create = new FluentSystemFactory();<br>
 * System org.palladiosimulator.generator.fluent.system = create.newSystem()<br>
 *      <p style=
"margin-left:40px">//add assembly contexts, connectors and roles to the org.palladiosimulator.generator.fluent.system</p>
 *      <p style="margin-left:40px">.createSystemNow();</p>
 * </code>
 * </p>
 *
 * @author Florian Krone
 */
public class FluentSystemFactory {
    private SystemCreator systemCreator;

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.system.System System}.
     *
     * @return the <code>System</code> in the making
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
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AssemblyContextCreator newAssemblyContext() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new AssemblyContextCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.composition.AssemblyConnector
     * AssemblyConnector}.
     *
     * @return the <code>AssemblyConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.AssemblyConnector
     */
    public AssemblyConnectorCreator newAssemblyConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new AssemblyConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole}.
     *
     * @return the <code>OperationRequiredRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    public OperationRequiredRoleCreator newOperationRequiredRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new OperationRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     * RequiredDelegationConnector}.
     *
     * @return the <code>RequiredDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector
     */
    public RequiredDelegationConnectorCreator newRequiredDelegationConnectorCreator() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new RequiredDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole}.
     *
     * @return the <code>OperationProvidedRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    public OperationProvidedRoleCreator newOperationProvidedRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new OperationProvidedRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     * ProvidedDelegationConnector}.
     *
     * @return the <code>ProvidedDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector
     */
    public ProvidedDelegationConnectorCreator newProvidedDelegationConnectorCreator() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new ProvidedDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel}.
     *
     * @return the <code>EventChannel</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    public EventChannelCreator newEventChannelCreator() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new EventChannelCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     * EventChannelSinkConnector}.
     *
     * @return the <code>EventChannelSinkConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
     */
    public EventChannelSinkConnectorCreator newEventChannelSinkConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new EventChannelSinkConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     * EventChannelSourceConnector}.
     *
     * @return the <code>EventChannelSourceConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
     */
    public EventChannelSourceConnectorCreator newEventChannelSourceConnector() throws IllegalSelectorException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new EventChannelSourceConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole}.
     *
     * @return the <code>SinkRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    public SinkRoleCreator newSinkRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new SinkRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     * SinkDelegationConnector}.
     *
     * @return the <code>SinkDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.SinkDelegationConnector
     */
    public SinkDelegationConnectorCreator newSinkDelegationConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new SinkDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole}.
     *
     * @return the <code>SourceRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.SourceRole
     */
    public SourceRoleCreator newSourceRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new SourceRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     * SourceDelegationConnector}.
     *
     * @return the <code>SourceDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.SourceDelegationConnector
     */
    public SourceDelegationConnectorCreator newSourceDelegationConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new SourceDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     * AssemblyEventConnector}.
     *
     * @return the <code>AssemblyEventConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.AssemblyEventConnector
     */
    public AssemblyEventConnectorCreator newAssemblyEventConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new AssemblyEventConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     * AssemblyInfrastructureConnector}.
     *
     * @return the <code>AssemblyInfrastructureConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.AssemblyInfrastructureConnector
     */
    public AssemblyInfrastructureConnectorCreator newAssemblyInfrastructureConnector() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new AssemblyInfrastructureConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole}.
     *
     * @return the <code>InfrastructureRequiredRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    public InfrastructureRequiredRoleCreator newInfrastructureRequiredRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new InfrastructureRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     * RequiredInfrastructureDelegationConnector}.
     *
     * @return the <code>RequiredInfrastructureDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.RequiredInfrastructureDelegationConnector
     */
    public RequiredInfrastructureDelegationConnectorCreator newRequiredInfrastructureDelegationConnector()
            throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new RequiredInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole}.
     *
     * @return the <code>InfrastructureProvidedRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    public InfrastructureProvidedRoleCreator newInfrastructureProvidedRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new InfrastructureProvidedRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     * ProvidedInfrastructureDelegationConnector}.
     *
     * @return the <code>ProvidedInfrastructureDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.ProvidedInfrastructureDelegationConnector
     */
    public ProvidedInfrastructureDelegationConnectorCreator newProvidedInfrastructureDelegationConnector()
            throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new ProvidedInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     * QoSAnnotations}.
     *
     * @return the <code>QoSAnnotations</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     */
    public QoSAnnotationsCreator newQoSAnnotations() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new QoSAnnotationsCreator(this.systemCreator);
    }

    /**
     * Start the creation of a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole}.
     *
     * @return the <code>ResourceRequiredRole</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    public ResourceRequiredRoleCreator newResourceRequiredRole() throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new ResourceRequiredRoleCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * ResourceRequiredDelegationConnector}.
     *
     * @return the <code>ResourceRequiredDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     */
    public ResourceRequiredDelegationConnectorCreator newResourceRequiredDelegationConnector()
            throws IllegalStateException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new ResourceRequiredDelegationConnectorCreator(this.systemCreator);
    }

    /**
     * Start the creation of a
     * {@link org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     * RequiredResourceDelegationConnector}.
     *
     * @return the <code>RequiredResourceDelegationConnector</code> in the making
     * @throws IllegalStateException
     *             if newSystem has not been called before
     * @see org.palladiosimulator.pcm.core.composition.RequiredResourceDelegationConnector
     */
    public RequiredResourceDelegationConnectorCreator newRequiredResourceDelegationConnector()
            throws IllegalSelectorException {
        if (this.systemCreator == null) {
            throw new IllegalStateException("newSystem must have been called before");
        }
        return new RequiredResourceDelegationConnectorCreator(this.systemCreator);
    }
}
