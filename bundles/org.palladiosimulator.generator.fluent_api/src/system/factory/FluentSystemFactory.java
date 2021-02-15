package system.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

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

public class FluentSystemFactory {
    private SystemCreator systemCreator;

    public ISystem newSystem() {
        EcorePlugin.ExtensionProcessor.process(null);
        final ResourceRepository resources = ModelLoader.loadResourceTypeRepository(ModelLoader.RESOURCE_TYPE_PATH);
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        this.systemCreator = new SystemCreator(resources, validator);
        return this.systemCreator;
    }

    public AssemblyContextCreator newAssemblyContext() {
        return new AssemblyContextCreator(this.systemCreator);
    }

    public AssemblyConnectorCreator newAssemblyConnector() {
        return new AssemblyConnectorCreator(this.systemCreator);
    }

    public OperationRequiredRoleCreator newOperationRequiredRole() {
        return new OperationRequiredRoleCreator(this.systemCreator);
    }

    public RequiredDelegationConnectorCreator newRequiredDelegationConnectorCreator() {
        return new RequiredDelegationConnectorCreator(this.systemCreator);
    }

    public OperationProvidedRoleCreator newOperationProvidedRole() {
        return new OperationProvidedRoleCreator(this.systemCreator);
    }

    public ProvidedDelegationConnectorCreator newProvidedDelegationConnectorCreator() {
        return new ProvidedDelegationConnectorCreator(this.systemCreator);
    }

    public EventChannelCreator newEventChannelCreator() {
        return new EventChannelCreator(this.systemCreator);
    }

    public EventChannelSinkConnectorCreator newEventChannelSinkConnector() {
        return new EventChannelSinkConnectorCreator(this.systemCreator);
    }

    public EventChannelSourceConnectorCreator newEventChannelSourceConnector() {
        return new EventChannelSourceConnectorCreator(this.systemCreator);
    }

    public SinkRoleCreator newSinkRole() {
        return new SinkRoleCreator(this.systemCreator);
    }

    public SinkDelegationConnectorCreator newSinkDelegationConnector() {
        return new SinkDelegationConnectorCreator(this.systemCreator);
    }

    public SourceRoleCreator newSourceRole() {
        return new SourceRoleCreator(this.systemCreator);
    }

    public SourceDelegationConnectorCreator newSourceDelegationConnector() {
        return new SourceDelegationConnectorCreator(this.systemCreator);
    }

    public AssemblyEventConnectorCreator newAssemblyEventConnector() {
        return new AssemblyEventConnectorCreator(this.systemCreator);
    }

    public AssemblyInfrastructureConnectorCreator newAssemblyInfrastructureConnector() {
        return new AssemblyInfrastructureConnectorCreator(this.systemCreator);
    }

    public InfrastructureRequiredRoleCreator newInfrastructureRequiredRole() {
        return new InfrastructureRequiredRoleCreator(this.systemCreator);
    }

    public RequiredInfrastructureDelegationConnectorCreator newRequiredInfrastructureDelegationConnectorCreator() {
        return new RequiredInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    public InfrastructureProvidedRoleCreator newInfrastructurenProvidedRole() {
        return new InfrastructureProvidedRoleCreator(this.systemCreator);
    }

    public ProvidedInfrastructureDelegationConnectorCreator newProvidedInfrastructureDelegationConnectorCreator() {
        return new ProvidedInfrastructureDelegationConnectorCreator(this.systemCreator);
    }

    public QoSAnnotationsCreator newQoSAnnotations() {
        return new QoSAnnotationsCreator(this.systemCreator);
    }

    public ResourceRequiredRoleCreator newResourceRequiredRole() {
        return new ResourceRequiredRoleCreator(this.systemCreator);
    }

    public ResourceRequiredDelegationConnectorCreator newResourceRequiredDelegationConnector() {
        return new ResourceRequiredDelegationConnectorCreator(this.systemCreator);
    }

    public RequiredResourceDelegationConnectorCreator newRequiredResourceDelegationConnector() {
        return new RequiredResourceDelegationConnectorCreator(this.systemCreator);
    }
}
