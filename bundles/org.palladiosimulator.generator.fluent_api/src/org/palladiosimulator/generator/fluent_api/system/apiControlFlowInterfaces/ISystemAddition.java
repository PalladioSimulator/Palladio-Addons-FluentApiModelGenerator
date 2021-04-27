package org.palladiosimulator.generator.fluent_api.system.apiControlFlowInterfaces;

import org.palladiosimulator.generator.fluent_api.system.structure.AssemblyContextCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.EventChannelCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.connector.AbstractConnectorCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.connector.resource.ResourceRequiredDelegationConnectorCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.qosAnnotations.QoSAnnotationsCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.InfrastructureProvidedRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.InfrastructureRequiredRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.OperationProvidedRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.OperationRequiredRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.ResourceRequiredRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.SinkRoleCreator;
import org.palladiosimulator.generator.fluent_api.system.structure.systemRole.SourceRoleCreator;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

public interface ISystemAddition {

    /**
     * Completes the org.palladiosimulator.generator.fluent_api.system creation.
     *
     * @return the final org.palladiosimulator.generator.fluent_api.system object
     * @see org.palladiosimulator.pcm.system.System
     */
    System createSystemNow();

    /**
     * Adds a repository to the org.palladiosimulator.generator.fluent_api.system. Components from added repositories can be
     * added to the org.palladiosimulator.generator.fluent_api.system by name.
     *
     * @param repository
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.Repository
     */
    ISystemAddition addRepository(Repository repository);

    /**
     * Adds an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished
     * AssemblyContext.
     *
     * @param context
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    ISystemAddition addToSystem(AssemblyContextCreator context);

    /**
     * Adds a {@link org.palladiosimulator.pcm.core.composition.Connector Connector}
     * to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished connector.
     *
     * @param connector
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.core.composition.Connector
     */
    ISystemAddition addToSystem(AbstractConnectorCreator connector);

    /**
     * Adds an {@link org.palladiosimulator.pcm.repository.OperationRequiredRole
     * OperationRequiredRole} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.OperationRequiredRole
     */
    ISystemAddition addToSystem(OperationRequiredRoleCreator role);

    /**
     * Adds an {@link org.palladiosimulator.pcm.repository.OperationProvidedRole
     * OperationProvidedRole} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.OperationProvidedRole
     */
    ISystemAddition addToSystem(OperationProvidedRoleCreator role);

    /**
     * Adds an {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished
     * connector.
     *
     * @param eventChannel
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    ISystemAddition addToSystem(EventChannelCreator eventChannel);

    /**
     * Adds a {@link org.palladiosimulator.pcm.repository.SinkRole SinkRole} to the
     * org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.SinkRole
     */
    ISystemAddition addToSystem(SinkRoleCreator role);

    /**
     * Adds a {@link org.palladiosimulator.pcm.repository.SourceRole SourceRole} to
     * the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.
     */
    ISystemAddition addToSystem(SourceRoleCreator role);

    /**
     * Adds an
     * {@link org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     * InfrastructureRequiredRole} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into
     * the finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.InfrastructureRequiredRole
     */
    ISystemAddition addToSystem(InfrastructureRequiredRoleCreator role);

    /**
     * Adds an
     * {@link org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     * InfrastructureProvidedRole} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into
     * the finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.repository.InfrastructureProvidedRole
     */
    ISystemAddition addToSystem(InfrastructureProvidedRoleCreator role);

    /**
     * Adds {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     * QoSAnnotations} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the finished
     * connector.
     *
     * @param annotations
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations
     */
    ISystemAddition addToSystem(QoSAnnotationsCreator annotations);

    /**
     * Adds a {@link org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     * ResourceRequiredRole} to the org.palladiosimulator.generator.fluent_api.system. The creator will be turned into the
     * finished connector.
     *
     * @param role
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.core.entity.ResourceRequiredRole
     */
    ISystemAddition addToSystem(ResourceRequiredRoleCreator role);

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     * ResourceRequiredDelegationConnector} to the org.palladiosimulator.generator.fluent_api.system. The creator will be
     * turned into the finished connector.
     *
     * @param connector
     * @return this org.palladiosimulator.generator.fluent_api.system
     * @see org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector
     */
    ISystemAddition addToSystem(ResourceRequiredDelegationConnectorCreator connector);

}
