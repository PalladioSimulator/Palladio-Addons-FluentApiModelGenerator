package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.core.composition.EventChannelSinkConnector;
import org.palladiosimulator.pcm.core.composition.EventChannelSourceConnector;
import org.palladiosimulator.pcm.repository.EventGroup;

import repositoryStructure.components.CompositeComponentCreator;
import repositoryStructure.components.SubSystemCreator;

public interface EventChannelCreation {

	public interface Comp extends EventChannelCreation {

		public Comp withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection);

		public Comp withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection);

		public Comp withEventGroup(EventGroup eventGroup);

		public CompositeComponentCreator now1();
	}

	public interface Sub extends EventChannelCreation {

		public Sub withEventChannelSinkConnector(EventChannelSinkConnector sinkConnection);

		public Sub withEventChannelSourceConnector(EventChannelSourceConnector sourceConnection);

		public Sub withEventGroup(EventGroup eventGroup);

		public SubSystemCreator now2();

	}

}
