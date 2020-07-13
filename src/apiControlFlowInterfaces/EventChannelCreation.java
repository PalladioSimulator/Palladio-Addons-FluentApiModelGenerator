package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.EventGroup;

import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.SubSystemCreator;

public interface EventChannelCreation {

	public interface Comp extends EventChannelCreation {

		/**
		 * Adds an
		 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
		 * EventChannelSinkConnector} to the event channel.
		 * <p>
		 * An existing <code>sinkConnection</code> can be fetched from the repository
		 * using the factory, i.e.
		 * <code>create.fetchOfEventChannelSinkConnector(name)</code>.
		 * </p>
		 * 
		 * @param sinkConnection
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventChannelSinkConnector(String)
		 */
		public Comp withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection);

		/**
		 * Adds an
		 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
		 * EventChannelSourceConnector} to the event channel.
		 * <p>
		 * An existing <code>sourceConnection</code> can be fetched from the repository
		 * using the factory, i.e.
		 * <code>create.fetchOfEventChannelSourceConnector(name)</code>.
		 * </p>
		 * 
		 * @param sourceConnection
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventChannelSourceConnector(String)
		 */
		public Comp withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection);

		/**
		 * Specifies the {@link org.palladiosimulator.pcm.repository.EventGroup
		 * EventGroup} of the event channel.
		 * <p>
		 * An existing <code>eventGroup</code> can be fetched from the repository using
		 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
		 * </p>
		 * 
		 * @param eventGroup
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
		 */
		public Comp withEventGroup(EventGroup eventGroup);

		/**
		 * Turns the event-channel-in-the-making into an '<em><b>Event Channel</b></em>'
		 * object and adds it to the composite component.
		 * 
		 * @return the composite component in the making
		 * @see org.palladiosimulator.pcm.core.composition.EventChannel
		 */
		public CompositeComponentCreator now1();
	}

	public interface Sub extends EventChannelCreation {

		/**
		 * Adds an
		 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector
		 * EventChannelSinkConnector} to the event channel.
		 * <p>
		 * An existing <code>sinkConnection</code> can be fetched from the repository
		 * using the factory, i.e.
		 * <code>create.fetchOfEventChannelSinkConnector(name)</code>.
		 * </p>
		 * 
		 * @param sinkConnection
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventChannelSinkConnector(String)
		 */
		public Sub withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection);

		/**
		 * Adds an
		 * {@link org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector
		 * EventChannelSourceConnector} to the event channel.
		 * <p>
		 * An existing <code>sourceConnection</code> can be fetched from the repository
		 * using the factory, i.e.
		 * <code>create.fetchOfEventChannelSourceConnector(name)</code>.
		 * </p>
		 * 
		 * @param sourceConnection
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventChannelSourceConnector(String)
		 */
		public Sub withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection);

		/**
		 * Specifies the {@link org.palladiosimulator.pcm.repository.EventGroup
		 * EventGroup} of the event channel.
		 * <p>
		 * An existing <code>eventGroup</code> can be fetched from the repository using
		 * the factory, i.e. <code>create.fetchOfEventGroup(name)</code>.
		 * </p>
		 * 
		 * @param eventGroup
		 * @return the event channel in the making
		 * @see factory.MyRepositoryFactory#fetchOfEventGroup(String)
		 */
		public Sub withEventGroup(EventGroup eventGroup);

		/**
		 * Turns the event-channel-in-the-making into an '<em><b>Event Channel</b></em>'
		 * object and adds it to the subsystem.
		 * 
		 * @return the subsystem in the making
		 * @see org.palladiosimulator.pcm.core.composition.EventChannel
		 */
		public SubSystemCreator now2();

	}

}
